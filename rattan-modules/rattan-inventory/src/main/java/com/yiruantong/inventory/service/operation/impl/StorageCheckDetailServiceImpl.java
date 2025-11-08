package com.yiruantong.inventory.service.operation.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.service.product.IBaseProductService;
import com.yiruantong.basic.service.storage.IBasePositionService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.NumberUtils;
import com.yiruantong.common.core.utils.SpringUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.excel.core.ExcelResult;
import com.yiruantong.common.excel.listener.ImportCommonListener;
import com.yiruantong.common.excel.utils.ExcelUtil;
import com.yiruantong.common.mybatis.core.page.BuildWrapperHelper;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IDataAuthService;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.tenant.helper.TenantHelper;
import com.yiruantong.inventory.domain.operation.StorageCheck;
import com.yiruantong.inventory.domain.operation.StorageCheckDetail;
import com.yiruantong.inventory.domain.operation.bo.StorageCheckDetailBo;
import com.yiruantong.inventory.domain.operation.vo.StorageCheckDetailComposeVo;
import com.yiruantong.inventory.domain.operation.vo.StorageCheckDetailVo;
import com.yiruantong.inventory.mapper.operation.StorageCheckDetailMapper;
import com.yiruantong.inventory.service.operation.IStorageCheckDetailService;
import com.yiruantong.inventory.service.operation.IStorageCheckService;
import com.yiruantong.system.service.dataHandler.ISysImportService;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 盘点单明细Service业务层处理
 *
 * @author YRT
 * @date 2023-10-24
 */
@RequiredArgsConstructor
@Service
public class StorageCheckDetailServiceImpl extends ServiceImplPlus<StorageCheckDetailMapper, StorageCheckDetail, StorageCheckDetailVo, StorageCheckDetailBo> implements IStorageCheckDetailService {
  private final DataSourceTransactionManager transactionManager;
  private final ISysImportService sysImportService;
  private final IBaseProductService baseProductService;
  private final IBasePositionService basePositionService;
  private final IDataAuthService dataAuthService;
  private List<StorageCheckDetail> storageCheckDetails;
  private List<StorageCheckDetail> storageCheckDetails1;

  //#region 货位转移明细导入数据
  @Async
  @Override
  public void importData(InputStream inputStream, Long importId, HttpServletRequest request, LoginUser loginUser) {
    // 手动开启事务  start
    DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
    definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus transaction = transactionManager.getTransaction(definition);

    TenantHelper.setDynamic(loginUser.getTenantId()); // 在异步线程里面需要手动设置当前租户ID，缓存用到
    LoginHelper.setLoginUser(loginUser);
    String key = request.getParameter("key");
    sysImportService.setKey(key);
    DateTime startDate = DateUtil.date(); // 导入开始时间

    try {
      // 主表id
      Long checkId = Convert.toLong(request.getParameter("mainId"));
      // 是否开启唯一码
      sysImportService.isAssert(StringUtils.isEmpty(key), "上传key不存在");
      sysImportService.isAssert(ObjectUtil.isEmpty(importId), "未关联模板，不可导入");
      sysImportService.isAssert(ObjectUtil.isEmpty(checkId), "请先保存主表后再进行导入操作");

      // 处理解析结果
      ExcelResult<Map<String, Object>> excelResult = null;
      excelResult = ExcelUtil.importExcel(inputStream, new ImportCommonListener());
      var dataList = excelResult.getList();

      // 通用验证
      sysImportService.setError(false);
      R<Void> result = sysImportService.commonCheck(dataList, importId, request, loginUser);
      sysImportService.isAssert(!result.isResult(), "导入数据有错误，请处理好重新导入");
      sysImportService.writeMsg("开始导入...");

      // 主表信息
      LambdaQueryWrapper<StorageCheck> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
      orderLambdaQueryWrapper.eq(StorageCheck::getCheckId, checkId);
      IStorageCheckService bean = SpringUtils.getBean(IStorageCheckService.class);
      var storageCheck = bean.getOne(orderLambdaQueryWrapper);

      int i = 0;
      int successCount = 0;
      int updateCount = 0;
      for (var row : dataList) {
        i++;
        final String productCode = Convert.toStr(row.get("productCode"));
        final BigDecimal checkQuantity = Convert.toBigDecimal(row.get("checkQuantity"));
        sysImportService.isAssert(!NumberUtils.isPositiveInteger(checkQuantity), "{}、产品编号[{}]数量请输入正整数！", i, productCode);
      }
      sysImportService.isAssert(!result.isResult(), "导入数据有错误，请处理好重新导入");


      /*——————————————————————————————————————————————————
       * 处理明细，将明细挂载到主表下
       *——————————————————————————————————————————————————*/
      List<StorageCheckDetail> storageCheckDetails = new ArrayList<>();
      for (var detail : dataList) {
        final BigDecimal checkQuantity = Convert.toBigDecimal(detail.get("checkQuantity"));
        final BigDecimal productStorage = Convert.toBigDecimal(detail.get("productStorage"));
        // 明细信息
        LambdaQueryWrapper<StorageCheckDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
        detailLambdaQueryWrapper.eq(StorageCheckDetail::getCheckId, checkId)
          .eq(StorageCheckDetail::getCheckDetailId, Convert.toLong(detail.get("checkDetailId")));
        var checkDetail = this.getOne(detailLambdaQueryWrapper);
        // 商品信息
        var prodInfo = baseProductService.getByModel(Convert.toStr(detail.get("productModel")));

        BigDecimal profitQuantity = new BigDecimal(0); // 盘盈数量
        BigDecimal profitAmount = new BigDecimal(0); // 盘盈金额
        BigDecimal lossAmount = new BigDecimal(0); // 盘亏金额
        BigDecimal lossQuantity = new BigDecimal(0); // 盘亏数量

        if (ObjectUtil.isNotEmpty(checkDetail)) {
          // 根据ID判断，存在更新
          if (B.isLessOrEqual(productStorage, checkQuantity)) {
            profitQuantity = B.sub(checkQuantity, productStorage);
            profitAmount = B.mul(profitQuantity, checkDetail.getPurchasePrice());
          } else {
            lossQuantity = B.sub(productStorage, checkQuantity);
            lossAmount = B.mul(lossQuantity, checkDetail.getPurchasePrice());
          }
          BeanUtil.copyProperties(detail, checkDetail);
          checkDetail.setCheckQuantity(checkQuantity);
          checkDetail.setProfitQuantity(profitQuantity);
          checkDetail.setProfitAmount(profitAmount);
          checkDetail.setLossQuantity(lossQuantity);
          checkDetail.setLossAmount(lossAmount);
          this.saveOrUpdate(checkDetail);
          updateCount++;
        } else {
          // 根据ID判断，不存在新增
          if (B.isLessOrEqual(productStorage, checkQuantity)) {
            profitQuantity = B.sub(checkQuantity, productStorage);
            profitAmount = B.mul(profitQuantity, prodInfo.getPurchasePrice());
          } else {
            lossQuantity = B.sub(productStorage, checkQuantity);
            lossAmount = B.mul(lossQuantity, prodInfo.getPurchasePrice());
          }
          var detailInfo = new StorageCheckDetail();
          BeanUtil.copyProperties(prodInfo, detailInfo);
          BeanUtil.copyProperties(detail, detailInfo);

          detailInfo.setCheckQuantity(checkQuantity);
          detailInfo.setProfitQuantity(profitQuantity);
          detailInfo.setProfitAmount(profitAmount);
          detailInfo.setLossQuantity(lossQuantity);
          detailInfo.setLossAmount(lossAmount);


          // 单位毛重weight，小计毛重totalWeight，相互计算
          BigDecimal weight = Convert.toBigDecimal(detail.get("weight")); // 单位重量
          BigDecimal rowWeight = Convert.toBigDecimal(detail.get("rowWeight")); // 小计重量


          //#region 重量计算
          if (ObjectUtil.isEmpty(weight) && ObjectUtil.isEmpty(rowWeight)) {
            // 单位毛重和小计毛重都为空时，默认商品信息单位重量
            detailInfo.setWeight(prodInfo.getWeight()); // 单位重量
            detailInfo.setRowWeight(B.mul(prodInfo.getWeight(), detailInfo.getCheckQuantity())); // 合计重量
          } else if (ObjectUtil.isEmpty(weight) && ObjectUtil.isNotEmpty(rowWeight)) {
            // 单位毛重为空和小计毛重不为空时，单位毛重通过小计毛重反算
            detailInfo.setWeight(B.div(rowWeight, detailInfo.getCheckQuantity()));
            detailInfo.setRowWeight(rowWeight);
          } else if (ObjectUtil.isNotEmpty(weight) && ObjectUtil.isEmpty(rowWeight)) {
            // 单位毛重不为空和小计毛重为空时，小计毛重通过单位毛重计算
            detailInfo.setWeight(weight);
            detailInfo.setRowWeight(B.mul(weight, detailInfo.getCheckQuantity()));
          } else if (ObjectUtil.isNotEmpty(weight) && ObjectUtil.isNotEmpty(rowWeight)) {
            // 单位毛重和小计毛重都不为空时，直接赋值
            detailInfo.setWeight(weight);
            detailInfo.setRowWeight(rowWeight);
          }
          //#endregion

          //建立关系，设置主表ID
          detailInfo.setCheckId(storageCheck.getCheckId());
          this.saveOrUpdate(detailInfo);
          storageCheckDetails.add(detailInfo);
          successCount++;
        }
      }
      // 主表求和字段计算
      // 合计盘点数量
      var totalCheckQuantity = storageCheckDetails.stream().map(StorageCheckDetail::getCheckQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
      // 合计重量
      var totalWeight = storageCheckDetails.stream().map(StorageCheckDetail::getRowWeight).reduce(BigDecimal.ZERO, BigDecimal::add);
      // 合计盘盈数量
      var totalProfitQuantity = storageCheckDetails.stream().map(StorageCheckDetail::getProfitQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
      // 合计盘盈金额
      var totalProfitAmount = storageCheckDetails.stream().map(StorageCheckDetail::getProfitAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
      //  合计盘亏数量
      var totalLossQuantity = storageCheckDetails.stream().map(StorageCheckDetail::getLossQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
      //  合计盘亏金额
      var totalLossAmount = storageCheckDetails.stream().map(StorageCheckDetail::getLossAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

      LambdaUpdateWrapper<StorageCheck> lambda = new UpdateWrapper<StorageCheck>().lambda();
      lambda.set(StorageCheck::getTotalCheckQuantity, totalCheckQuantity)
        .set(StorageCheck::getTotalWeight, totalWeight)
        .set(StorageCheck::getTotalProfitQuantity, totalProfitQuantity)
        .set(StorageCheck::getTotalProfitAmount, totalProfitAmount)
        .set(StorageCheck::getTotalLossQuantity, totalLossQuantity)
        .set(StorageCheck::getTotalLossAmount, totalLossAmount)
        .eq(StorageCheck::getCheckId, checkId);
      bean.update(lambda);


      var endDate = DateUtil.date();
      var totalSeconds = DateUtil.between(startDate, endDate, DateUnit.SECOND);
      sysImportService.writeMsgBlue("导入完成，新增{}条", successCount);
      sysImportService.writeMsgBlue("导入完成，更新{}条", updateCount);
      sysImportService.writeMsg("导入成功,共耗时{}秒", totalSeconds);
      transactionManager.commit(transaction); // 手动提交事务
    } catch (Exception exception) {
      sysImportService.writeMsgRed("导入错误：{}", exception);
      transactionManager.rollback(transaction); // 手动回滚事务
    }
    sysImportService.writeEnd(); // 标记结算
  }

  //#endregion

  //#region 盘点单明细查询数据
  @Override
  public TableDataInfo<StorageCheckDetailComposeVo> selectStorageCheckDetailComposeList(PageQuery pageQuery) {
    IPage<StorageCheckDetailComposeVo> ipage = pageQuery.build();
    dataAuthService.getDataAuth(pageQuery); // 数据权限
    MPJLambdaWrapper<StorageCheckDetail> wrapper = new MPJLambdaWrapper<StorageCheckDetail>()
      .select(StorageCheck::getCheckCode, StorageCheck::getCheckStatus,
        StorageCheck::getConsignorName, StorageCheck::getCheckType, StorageCheck::getStorageName, StorageCheck::getApplyDate,
        StorageCheck::getDeptName, StorageCheck::getNickName, StorageCheck::getIsBlind, StorageCheck::getSortingStatus)
      .selectAll(StorageCheckDetail.class)
      .innerJoin(StorageCheck.class, StorageCheck::getCheckId, StorageCheckDetail::getCheckId);

    //# 查询条件的拼接
    BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), wrapper, StorageCheckDetail.class, StorageCheck.class);

    IPage<StorageCheckDetailComposeVo> page = this.selectJoinListPage(ipage, StorageCheckDetailComposeVo.class, wrapper);
    TableDataInfo<StorageCheckDetailComposeVo> tableDataInfoV = TableDataInfo.build(page);
    tableDataInfoV.setTableName(pageQuery.getTableName());
    return tableDataInfoV;
  }
  //#endregion

  /**
   * 根据主表ID获取明细集合
   *
   * @param mainId
   * @return 返回明细集合
   */
  public List<StorageCheckDetail> selectListByMainId(Long mainId) {
    LambdaQueryWrapper<StorageCheckDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
    detailLambdaQueryWrapper.eq(StorageCheckDetail::getCheckId, mainId);

    return this.list(detailLambdaQueryWrapper);
  }

  @Override
  public R<List<Map<String, Object>>> pdaCheckDetailList(Map<String, Object> map) {
    Long checkId = Convert.toLong(map.get("checkId"));
    MPJLambdaWrapper<StorageCheckDetail> checkDetailLqw = new MPJLambdaWrapper<>();
    checkDetailLqw
      .selectAll(StorageCheckDetail.class)
      .select(BaseProduct::getBigUnit, BaseProduct::getUnitConvert)
      .innerJoin(BaseProduct.class, BaseProduct::getProductId, StorageCheckDetail::getProductId)
      .eq(StorageCheckDetail::getCheckId, checkId)
      .and(a -> a.eq(StorageCheckDetail::getCheckQuantity, BigDecimal.ZERO)
        .or()
        .isNull(StorageCheckDetail::getCheckQuantity)
      );

    List<Map<String, Object>> storageCheckDetails = this.getBaseMapper().selectJoinMaps(checkDetailLqw);
    Assert.isTrue(B.isGreater(storageCheckDetails.size()), "盘点单下没有任何可以盘点的明细！");
    return R.ok(storageCheckDetails);
  }

  @Override
  public R<Void> pdaCheckSave(Map<String, Object> map) {
    Long checkId = Convert.toLong(map.get("checkId"));
    List<StorageCheckDetail> storageCheckDetails1 = BeanUtil.copyToList(Convert.toList(map.get("dataList")), StorageCheckDetail.class);
    for (StorageCheckDetail item : storageCheckDetails1) {
      LambdaUpdateWrapper<StorageCheckDetail> lambda = new UpdateWrapper<StorageCheckDetail>().lambda();
      lambda.set(StorageCheckDetail::getCheckQuantity, item.getCheckQuantity())
        .set(StorageCheckDetail::getProfitQuantity, item.getProfitQuantity())
        .set(StorageCheckDetail::getLossQuantity, item.getLossQuantity())
        .set(StorageCheckDetail::getProfitAmount, B.mul(item.getProfitQuantity(), item.getPurchasePrice()))
        .set(StorageCheckDetail::getLossAmount, B.mul(item.getLossQuantity(), item.getPurchasePrice()))
        .set(StorageCheckDetail::getProfitWeight, B.mul(item.getProfitQuantity(), item.getWeight()))
        .eq(StorageCheckDetail::getCheckId, checkId);
      this.update(lambda);
    }
    return R.ok("盘点成功！");
  }
  //#endregion
}

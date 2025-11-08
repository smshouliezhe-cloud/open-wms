package com.yiruantong.inventory.service.operation.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
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
import com.yiruantong.inventory.domain.operation.StorageAdjust;
import com.yiruantong.inventory.domain.operation.StorageAdjustDetail;
import com.yiruantong.inventory.domain.operation.bo.StorageAdjustDetailBo;
import com.yiruantong.inventory.domain.operation.vo.StorageAdjustDetailComposeVo;
import com.yiruantong.inventory.domain.operation.vo.StorageAdjustDetailVo;
import com.yiruantong.inventory.mapper.operation.StorageAdjustDetailMapper;
import com.yiruantong.inventory.service.operation.IStorageAdjustDetailService;
import com.yiruantong.inventory.service.operation.IStorageAdjustService;
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
 * 库存调整单明细Service业务层处理
 *
 * @author YRT
 * @date 2023-10-24
 */
@RequiredArgsConstructor
@Service
public class StorageAdjustDetailServiceImpl extends ServiceImplPlus<StorageAdjustDetailMapper, StorageAdjustDetail, StorageAdjustDetailVo, StorageAdjustDetailBo> implements IStorageAdjustDetailService {
  private final DataSourceTransactionManager transactionManager;
  private final ISysImportService sysImportService;
  private final IBaseProductService baseProductService;
  private final IBasePositionService basePositionService;
  private final IDataAuthService dataAuthService;

  @Override
  public List<StorageAdjustDetail> selectListByMainId(Long id) {
    LambdaQueryWrapper<StorageAdjustDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
    detailLambdaQueryWrapper.eq(StorageAdjustDetail::getAdjustId, id);

    return this.list(detailLambdaQueryWrapper);
  }


  //#region 库存调整明细导入数据
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
      // 主表ID
      Long adjustId = Convert.toLong(request.getParameter("mainId"));
      // 是否开启唯一码
      sysImportService.isAssert(StringUtils.isEmpty(key), "上传key不存在");
      sysImportService.isAssert(ObjectUtil.isEmpty(importId), "未关联模板，不可导入");
      sysImportService.isAssert(ObjectUtil.isEmpty(adjustId), "请先保存主表后再进行导入操作");

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
      LambdaQueryWrapper<StorageAdjust> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
      orderLambdaQueryWrapper.eq(StorageAdjust::getAdjustId, adjustId);
      IStorageAdjustService bean = SpringUtils.getBean(IStorageAdjustService.class);
      var adjustInfo = bean.getOne(orderLambdaQueryWrapper);

      int i = 0;
      int successCount = 0;
      int updateCount = 0;
      for (var row : dataList) {

        i++;
        final String productCode = Convert.toStr(row.get("productCode"));
        final String productModel = Convert.toStr(row.get("productModel"));
        final String consignorName = Convert.toStr(row.get("consignorName")); // 主表的货主
        final String positionName = Convert.toStr(row.get("positionName")); // 货位
//        final String transferQuantity = Convert.toStr(row.get("transferQuantity"));
//        sysImportService.isAssert(!NumberUtils.isPositiveInteger(transferQuantity), "{}、产品编号[{}]数量请输入正整数！", i, productCode);

        // 商品条码不存在，根据商品条码去找同货主的商品
        if (ObjectUtil.isNull(productModel)) {
          // 验证商品条码是否存在
          var prodList = baseProductService.selectByModel(productModel);
          BaseProduct prodInfo = null;
          if (prodList.size() > 1) {
            prodInfo = baseProductService.getByCodeAndConsignor(productCode, consignorName);
          } else if (prodList.size() == 1) {
            prodInfo = prodList.get(0);
          }
          sysImportService.isAssert(ObjectUtil.isNull(prodInfo), "商品条码[{}]与所选货主不一致，不允许导入", productModel);

          // 更新Excel数据集合中的数据
          row.put("productId", prodInfo.getProductId());
          row.put("productModel", prodInfo.getProductModel());
          row.put("productName", prodInfo.getProductName());
          row.put("productSpec", prodInfo.getProductSpec());
        } else {
          // 验证商品编号是否存在
          var prodInfo = baseProductService.getByCode(Convert.toStr(row.get("productCode")));
          sysImportService.isAssert(ObjectUtil.isEmpty(prodInfo), "商品编号[{}]与所选货主不一致，不允许导入", productCode);
        }

        var position = basePositionService.getByName(adjustInfo.getStorageId(), Convert.toStr(row.get("positionName")));
        sysImportService.isAssert(ObjectUtil.isEmpty(position), "货位名称[{}]在[{}]不存在，不允许导入", positionName, adjustInfo.getStorageName());
      }
      sysImportService.isAssert(!result.isResult(), "导入数据有错误，请处理好重新导入");


      /*——————————————————————————————————————————————————
       * 处理明细，将明细挂载到主表下
       *——————————————————————————————————————————————————*/
      List<StorageAdjustDetail> storageAdjustDetails = new ArrayList<>();
      for (var detail : dataList) {
        BaseProduct prodInfo = null;
        final String productCode = Convert.toStr(detail.get("productCode"));
        final String productModel = Convert.toStr(detail.get("productModel"));
        final String positionName = Convert.toStr(detail.get("positionName"));

        // 根据同货主同条码查找商品信息
        if (ObjectUtil.isNotEmpty(productCode)) {
          prodInfo = this.baseProductService.getByCode(Convert.toStr(detail.get("productCode")));
        } else {
          var prodList = baseProductService.selectByModel(productModel);
          if (prodList.size() > 1) {
            prodInfo = baseProductService.getByCodeAndConsignor(productModel, adjustInfo.getConsignorName());
          } else if (prodList.size() == 1) {
            prodInfo = prodList.get(0);
          }
          // 更新Excel数据集合中的数据
          detail.put("productId", prodInfo.getProductId());
          detail.put("productModel", prodInfo.getProductModel());
          detail.put("productName", prodInfo.getProductName());
          detail.put("productSpec", prodInfo.getProductSpec());
        }
        var position = basePositionService.getByName(adjustInfo.getStorageId(), Convert.toStr(detail.get("positionName")));
        sysImportService.isAssert(ObjectUtil.isEmpty(position), "货位名称[{}]在[{}]不存在，不允许导入", positionName, adjustInfo.getStorageName());
        // 明细信息
        LambdaQueryWrapper<StorageAdjustDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
        detailLambdaQueryWrapper.eq(StorageAdjustDetail::getAdjustId, adjustId)
          .eq(StorageAdjustDetail::getAdjustDetailId, Convert.toLong(detail.get("getAdjustDetailId")));
        var adjustDetail = this.getOne(detailLambdaQueryWrapper);

        BigDecimal holderStorage = new BigDecimal(0); // 占位数量
        BigDecimal profitAmount = new BigDecimal(0); // 盘盈金额
        BigDecimal lossQuantity = new BigDecimal(0); // 盘亏数量

        final BigDecimal productStorage = Convert.toBigDecimal(detail.get("productStorage"));
        final BigDecimal checkQuantity = Convert.toBigDecimal(detail.get("checkQuantity"));

        if (ObjectUtil.isNotEmpty(adjustDetail)) {
          // 根据ID判断，存在更新
          if (B.isLessOrEqual(productStorage, checkQuantity)) {
            holderStorage = B.sub(checkQuantity, productStorage);
            profitAmount = B.mul(holderStorage, adjustDetail.getPurchasePrice());
          } else {
            lossQuantity = B.sub(productStorage, checkQuantity);
          }
          BeanUtil.copyProperties(detail, adjustDetail);
          adjustDetail.setCheckQuantity(checkQuantity);
          adjustDetail.setHolderStorage(holderStorage);
          adjustDetail.setProfitAmount(profitAmount);
          adjustDetail.setLossQuantity(lossQuantity);
          adjustDetail.setProductStorage(productStorage);
          this.saveOrUpdate(adjustDetail);
          updateCount++;
        } else {
          // 根据ID判断，不存在新增
          if (B.isLessOrEqual(productStorage, checkQuantity)) {
            holderStorage = B.sub(checkQuantity, productStorage);
            profitAmount = B.mul(holderStorage, prodInfo.getPurchasePrice());
          } else {
            lossQuantity = B.sub(productStorage, checkQuantity);
          }
          var detailInfo = new StorageAdjustDetail();
          BeanUtil.copyProperties(prodInfo, detailInfo);
          BeanUtil.copyProperties(detail, detailInfo);

          detailInfo.setCheckQuantity(checkQuantity);
          detailInfo.setHolderStorage(holderStorage);
          detailInfo.setProfitAmount(profitAmount);
          detailInfo.setLossQuantity(lossQuantity);
          adjustDetail.setProductStorage(productStorage);

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
          detailInfo.setAdjustId(adjustInfo.getAdjustId());
          this.saveOrUpdate(detailInfo);
          storageAdjustDetails.add(detailInfo);

          successCount++;
        }
      }
      // 主表求和字段计算
      // 账面库存量
      var totalProductStorage = storageAdjustDetails.stream().map(StorageAdjustDetail::getProductStorage).reduce(BigDecimal.ZERO, BigDecimal::add);
      // 合计调整数量
      var totalCheckQuantity = storageAdjustDetails.stream().map(StorageAdjustDetail::getCheckQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
      // 合计盘盈数量
      var totalProfitQuantity = storageAdjustDetails.stream().map(StorageAdjustDetail::getProfitQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
      // 合计盘盈金额
      var totalProfitAmount = storageAdjustDetails.stream().map(StorageAdjustDetail::getProfitAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
      //  合计盘亏数量
      var totalLossQuantity = storageAdjustDetails.stream().map(StorageAdjustDetail::getLossQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
      //  合计盘亏金额
      var totalLossAmount = storageAdjustDetails.stream().map(StorageAdjustDetail::getLossAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

      LambdaUpdateWrapper<StorageAdjust> lambda = new UpdateWrapper<StorageAdjust>().lambda();
      lambda.set(StorageAdjust::getTotalProductStorage, totalProductStorage)
        .set(StorageAdjust::getTotalCheckQuantity, totalCheckQuantity)
        .set(StorageAdjust::getTotalProfitQuantity, totalProfitQuantity)
        .set(StorageAdjust::getTotalProfitAmount, totalProfitAmount)
        .set(StorageAdjust::getTotalLossQuantity, totalLossQuantity)
        .set(StorageAdjust::getTotalLossAmount, totalLossAmount)
        .eq(StorageAdjust::getAdjustId, adjustId);
      bean.update(lambda);


      var endDate = DateUtil.date();
      var totalSeconds = DateUtil.between(startDate, endDate, DateUnit.SECOND);
      sysImportService.writeMsgBlue("导入完成，新增{}条", successCount);
      sysImportService.writeMsg("导入成功,共耗时{}秒", totalSeconds);
      transactionManager.commit(transaction); // 手动提交事务
    } catch (Exception exception) {
      sysImportService.writeMsgRed("导入错误：{}", exception);
      transactionManager.rollback(transaction); // 手动回滚事务
    }
    sysImportService.writeEnd(); // 标记结算
  }
  //#endregion

  //#region 库存调整单明细查询数据
  @Override
  public TableDataInfo<StorageAdjustDetailComposeVo> selectAdjustDetailComposeList(PageQuery pageQuery) {
    IPage<StorageAdjustDetailComposeVo> ipage = pageQuery.build();
    dataAuthService.getDataAuth(pageQuery); // 数据权限
    MPJLambdaWrapper<StorageAdjustDetail> wrapper = new MPJLambdaWrapper<StorageAdjustDetail>()
      .selectAll(StorageAdjustDetail.class)
      .select(StorageAdjust::getAdjustCode, StorageAdjust::getCheckType,
        StorageAdjust::getConsignorName, StorageAdjust::getProviderShortName, StorageAdjust::getStorageName, StorageAdjust::getSourceCode,
        StorageAdjust::getApplyDate, StorageAdjust::getAdjustStatus, StorageAdjust::getNickName, StorageAdjust::getSortingStatus)
      .innerJoin(StorageAdjust.class, StorageAdjust::getAdjustId, StorageAdjustDetail::getAdjustId);

//      var detailInfo=this.selectList(wrapper);
    //# 查询条件的拼接
    BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), wrapper, StorageAdjustDetail.class, StorageAdjust.class);

    IPage<StorageAdjustDetailComposeVo> page = this.selectJoinListPage(ipage, StorageAdjustDetailComposeVo.class, wrapper);
    TableDataInfo<StorageAdjustDetailComposeVo> tableDataInfoV = TableDataInfo.build(page);
    tableDataInfoV.setTableName(pageQuery.getTableName());
    return tableDataInfoV;
  }
  //#endregion
}

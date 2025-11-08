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
import com.yiruantong.inventory.domain.operation.StoragePositionTransfer;
import com.yiruantong.inventory.domain.operation.StoragePositionTransferDetail;
import com.yiruantong.inventory.domain.operation.bo.StoragePositionTransferDetailBo;
import com.yiruantong.inventory.domain.operation.vo.StoragePositionTransferDetailComposeVo;
import com.yiruantong.inventory.domain.operation.vo.StoragePositionTransferDetailVo;
import com.yiruantong.inventory.mapper.operation.StoragePositionTransferDetailMapper;
import com.yiruantong.inventory.service.operation.IStoragePositionTransferDetailService;
import com.yiruantong.inventory.service.operation.IStoragePositionTransferService;
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
 * 货位转移明细Service业务层处理
 *
 * @author YRT
 * @date 2023-10-24
 */
@RequiredArgsConstructor
@Service
public class StoragePositionTransferDetailServiceImpl extends ServiceImplPlus<StoragePositionTransferDetailMapper, StoragePositionTransferDetail, StoragePositionTransferDetailVo, StoragePositionTransferDetailBo> implements IStoragePositionTransferDetailService {
  private final DataSourceTransactionManager transactionManager;
  private final ISysImportService sysImportService;
  private final IBaseProductService baseProductService;
  private final IBasePositionService basePositionService;
  private final IDataAuthService dataAuthService;

  @Override
  public List<StoragePositionTransferDetail> selectListByMainId(Long id) {
    LambdaQueryWrapper<StoragePositionTransferDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
    detailLambdaQueryWrapper.eq(StoragePositionTransferDetail::getTransferId, id);

    return this.list(detailLambdaQueryWrapper);
  }


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
      Long transferId = Convert.toLong(request.getParameter("mainId"));
//      Long consignorId = Convert.toLong(request.getParameter("consignorId"));
      // 是否开启唯一码
      sysImportService.isAssert(StringUtils.isEmpty(key), "上传key不存在");
      sysImportService.isAssert(ObjectUtil.isEmpty(importId), "未关联模板，不可导入");
      sysImportService.isAssert(ObjectUtil.isEmpty(transferId), "请先保存主表后再进行导入操作");

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
      LambdaQueryWrapper<StoragePositionTransfer> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
      orderLambdaQueryWrapper.eq(StoragePositionTransfer::getTransferId, transferId);
      IStoragePositionTransferService bean = SpringUtils.getBean(IStoragePositionTransferService.class);
      var transferInfo = bean.getOne(orderLambdaQueryWrapper);

      int i = 0;
      int successCount = 0;
      for (var row : dataList) {

        i++;
        final String productCode = Convert.toStr(row.get("productCode"));
        final String productModel = Convert.toStr(row.get("productModel"));
        final String consignorName = Convert.toStr(row.get("consignorName")); // 主表的货主
        final String positionName = Convert.toStr(row.get("positionName")); // 货位
        final String transferQuantity = Convert.toStr(row.get("transferQuantity"));
        sysImportService.isAssert(!NumberUtils.isPositiveInteger(transferQuantity), "{}、产品编号[{}]数量请输入正整数！", i, productCode);

        // 商品条码存在，根据商品条码去找同货主的商品
        if (ObjectUtil.isNotEmpty(productModel)) {
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

          var position = basePositionService.getByName(transferInfo.getStorageId(), Convert.toStr(row.get("positionName")));
          sysImportService.isAssert(ObjectUtil.isEmpty(position), "货位名称[{}]在[{}]不存在，不允许导入", positionName, transferInfo.getStorageName());
        }
      }
      sysImportService.isAssert(!result.isResult(), "导入数据有错误，请处理好重新导入");


      /*——————————————————————————————————————————————————
       * 处理明细，将明细挂载到主表下
       *——————————————————————————————————————————————————*/
      List<StoragePositionTransferDetail> storagePositionTransferDetails = new ArrayList<>();
      for (var detail : dataList) {
        var detailInfo = new StoragePositionTransferDetail();
        // 根据同货主同条码查找商品信息
        var baseProduct = this.baseProductService.getByModel(Convert.toStr(detail.get("productModel")));

        // 获得明细表扩展字段
        BeanUtil.copyProperties(baseProduct, detailInfo);
        BeanUtil.copyProperties(detail, detailInfo);


        // 单位毛重weight，小计毛重totalWeight，相互计算
        BigDecimal purchasePrice = Convert.toBigDecimal(detail.get("purchasePrice")); // 成本单价
        BigDecimal weight = Convert.toBigDecimal(detail.get("weight")); // 单位重量
        BigDecimal rowWeight = Convert.toBigDecimal(detail.get("rowWeight")); // 小计重量

        if (ObjectUtil.isEmpty(purchasePrice)) {
          purchasePrice = baseProduct.getPurchasePrice();
          detailInfo.setPurchasePrice(purchasePrice);
        }

        //#region 重量计算
        if (ObjectUtil.isEmpty(weight) && ObjectUtil.isEmpty(rowWeight)) {
          // 单位毛重和小计毛重都为空时，默认商品信息单位重量
          detailInfo.setWeight(baseProduct.getWeight()); // 单位重量
          detailInfo.setRowWeight(B.mul(baseProduct.getWeight(), detailInfo.getTransferQuantity())); // 合计重量
        } else if (ObjectUtil.isEmpty(weight) && ObjectUtil.isNotEmpty(rowWeight)) {
          // 单位毛重为空和小计毛重不为空时，单位毛重通过小计毛重反算
          detailInfo.setWeight(B.div(rowWeight, detailInfo.getTransferQuantity()));
          detailInfo.setRowWeight(rowWeight);
        } else if (ObjectUtil.isNotEmpty(weight) && ObjectUtil.isEmpty(rowWeight)) {
          // 单位毛重不为空和小计毛重为空时，小计毛重通过单位毛重计算
          detailInfo.setWeight(weight);
          detailInfo.setRowWeight(B.mul(weight, detailInfo.getTransferQuantity()));
        } else if (ObjectUtil.isNotEmpty(weight) && ObjectUtil.isNotEmpty(rowWeight)) {
          // 单位毛重和小计毛重都不为空时，直接赋值
          detailInfo.setWeight(weight);
          detailInfo.setRowWeight(rowWeight);
        }
        //#endregion

        // 计算金额
        detailInfo.setPurchaseAmount(B.mul(purchasePrice, detailInfo.getTransferQuantity()));

        //建立关系，设置主表ID
        detailInfo.setTransferId(transferInfo.getTransferId());
        this.saveOrUpdate(detailInfo);
        storagePositionTransferDetails.add(detailInfo);

        successCount++;
      }
      // 主表求和字段计算
      var totalQuantity = storagePositionTransferDetails.stream().map(StoragePositionTransferDetail::getTransferQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
      var totalWeight = storagePositionTransferDetails.stream().map(StoragePositionTransferDetail::getRowWeight).reduce(BigDecimal.ZERO, BigDecimal::add);
      var totalAmount = storagePositionTransferDetails.stream().map(StoragePositionTransferDetail::getPurchaseAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

      LambdaUpdateWrapper<StoragePositionTransfer> lambda = new UpdateWrapper<StoragePositionTransfer>().lambda();
      lambda.set(StoragePositionTransfer::getTotalQuantity, totalQuantity)
        .set(StoragePositionTransfer::getTotalWeight, totalWeight)
        .set(StoragePositionTransfer::getTotalPurchaseAmount, totalAmount)
        .eq(StoragePositionTransfer::getTransferId, transferId);
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

  //#region 货位转移明细查询数据
  @Override
  public TableDataInfo<StoragePositionTransferDetailComposeVo> selectPositionTransferDetailComposeList(PageQuery pageQuery) {
    IPage<StoragePositionTransferDetailComposeVo> ipage = pageQuery.build();
    dataAuthService.getDataAuth(pageQuery); // 数据权限
    MPJLambdaWrapper<StoragePositionTransferDetail> wrapper = new MPJLambdaWrapper<StoragePositionTransferDetail>()
      .select(StoragePositionTransfer::getTransferCode, StoragePositionTransfer::getTansfterStatus, StoragePositionTransfer::getStorageName, StoragePositionTransfer::getSourceCode,
        StoragePositionTransfer::getSortingDate, StoragePositionTransfer::getNickName)
      .selectAll(StoragePositionTransferDetail.class)
      .innerJoin(StoragePositionTransfer.class, StoragePositionTransfer::getTransferId, StoragePositionTransferDetail::getTransferId);

    //# 查询条件的拼接
    BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), wrapper, StoragePositionTransferDetail.class, StoragePositionTransfer.class);

    IPage<StoragePositionTransferDetailComposeVo> page = this.selectJoinListPage(ipage, StoragePositionTransferDetailComposeVo.class, wrapper);
    TableDataInfo<StoragePositionTransferDetailComposeVo> tableDataInfoV = TableDataInfo.build(page);
    tableDataInfoV.setTableName(pageQuery.getTableName());
    return tableDataInfoV;
  }
  //#endregion
}

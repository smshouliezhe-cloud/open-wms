package com.yiruantong.inventory.service.operation.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.base.BaseConsignor;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.domain.storage.BaseStorage;
import com.yiruantong.basic.service.base.IBaseConsignorService;
import com.yiruantong.basic.service.product.IBaseProductService;
import com.yiruantong.basic.service.product.IBaseProviderService;
import com.yiruantong.basic.service.storage.IBaseStorageService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.base.*;
import com.yiruantong.common.core.enums.inventory.StorageEnterStatusEnum;
import com.yiruantong.common.core.enums.inventory.StorageOuterStatusEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.DateUtils;
import com.yiruantong.common.core.utils.NumberUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.excel.core.ExcelResult;
import com.yiruantong.common.excel.listener.ImportCommonListener;
import com.yiruantong.common.excel.utils.ExcelUtil;
import com.yiruantong.common.mybatis.core.domain.bo.SaveEditorBo;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.tenant.helper.TenantHelper;
import com.yiruantong.inventory.domain.base.dto.CommonDetailDto;
import com.yiruantong.inventory.domain.base.dto.CommonMainDto;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryComposeVo;
import com.yiruantong.inventory.domain.operation.StorageOuter;
import com.yiruantong.inventory.domain.operation.StorageOuterDetail;
import com.yiruantong.inventory.domain.operation.StorageOuterSortingRule;
import com.yiruantong.inventory.domain.operation.api.ApiStorageOuterBo;
import com.yiruantong.inventory.domain.operation.bo.StorageOuterBo;
import com.yiruantong.inventory.domain.operation.vo.StorageOuterSortingRuleVo;
import com.yiruantong.inventory.domain.operation.vo.StorageOuterVo;
import com.yiruantong.inventory.mapper.operation.StorageOuterMapper;
import com.yiruantong.inventory.service.base.IInventoryBaseService;
import com.yiruantong.inventory.service.base.IInventoryCommonService;
import com.yiruantong.inventory.service.core.ICoreInventoryHolderService;
import com.yiruantong.inventory.service.operation.IStorageOuterDetailService;
import com.yiruantong.inventory.service.operation.IStorageOuterService;
import com.yiruantong.inventory.service.operation.IStorageOuterSortingRuleService;
import com.yiruantong.system.service.core.ISysConfigService;
import com.yiruantong.system.service.dataHandler.ISysImportService;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 其他出库单Service业务层处理
 *
 * @author YRT
 * @date 2023-10-24
 */
@RequiredArgsConstructor
@Service
public class StorageOuterServiceImpl extends ServiceImplPlus<StorageOuterMapper, StorageOuter, StorageOuterVo, StorageOuterBo> implements IStorageOuterService, IInventoryBaseService {
  private final IStorageOuterDetailService storageOuterDetailService;
  private final IInventoryCommonService inventoryCommonService;
  private final ICoreInventoryHolderService coreInventoryHolderService;
  private final DataSourceTransactionManager transactionManager;
  private final ISysImportService sysImportService;
  private final IBaseProductService baseProductService;
  private final IStorageOuterSortingRuleService storageOuterSortingRuleService;
  private final IBaseProviderService baseProviderService;
  private final IBaseConsignorService baseConsignorService;
  private final IBaseStorageService baseStorageService;
  private final ISysConfigService sysConfigService;

  //#region 复制单据
  @Override
  public R<Map<String, Object>> copyEditor(SaveEditorBo<StorageOuterBo> saveEditorBo) {
    if (ObjectUtil.isEmpty(saveEditorBo.getIdValue())) {
      return R.fail("复制单据ID不存在");
    }

    StorageOuter storageOuter = this.getById(saveEditorBo.getIdValue());
    List<StorageOuterDetail> details = storageOuterDetailService.selectListByMainId(saveEditorBo.getIdValue());

    String orderCode = DBUtils.getCodeRegular(Objects.requireNonNull(MenuEnum.getEnumById(saveEditorBo.getMenuId())));
    // 保存主表
    storageOuter.setOuterId(null);
    storageOuter.setOuterCode(orderCode);
    storageOuter.setOuterStatus(StorageOuterStatusEnum.NEWED.getName());
    storageOuter.setAuditor(null);
    storageOuter.setAuditing(AuditEnum.AUDIT.getId());
    storageOuter.setSortingDate(null);
    storageOuter.setSortingStatus(SortingStatusEnum.NONE.getId());
    this.save(storageOuter);

    // 保存明细
    details.forEach(item -> {
      item.setOuterId(storageOuter.getOuterId());
      item.setOuterDetailId(null);
      item.setLackStorage(BigDecimal.ZERO);
      item.setSortingStatus(SortingStatusEnum.NONE.getId());
    });
    storageOuterDetailService.saveBatch(details);

    return R.ok("复制成功");
  }
  //endregion

  //#region multiAuditing
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> multiAuditing(List<Long> ids) {
    Assert.isFalse(ids.isEmpty(), "至少选择一条数据");

    for (Long id : ids) {
      StorageOuter storageOuter = this.getById(id);
      Assert.isFalse(ObjectUtil.equal(storageOuter.getOuterStatus(), StorageOuterStatusEnum.FINISHED.getName()),
        storageOuter.getOuterCode() + "出库完成的，不允许重复操作");
      List<StorageOuterDetail> detailList = storageOuterDetailService.selectListByMainId(id);

      //#region 通用模块调用
      inventoryCommonService.setBizService(this);

      // 构建DTO数据 - 主表
      CommonMainDto commonMainDto = BeanUtil.copyProperties(storageOuter, CommonMainDto.class);
      commonMainDto.setMainId(storageOuter.getOuterId());
      commonMainDto.setMainCode(storageOuter.getOuterCode());

      // 构建DTO数据 - 明细集合
      List<CommonDetailDto> commonDetailDtoList = detailList.stream().map(m -> {
        CommonDetailDto detailDto = BeanUtil.copyProperties(m, CommonDetailDto.class);
        detailDto.setMainId(m.getOuterId()); // 主表ID
        detailDto.setDetailId(m.getOuterDetailId()); // 明细ID
        detailDto.setInQuantity(BigDecimal.ZERO);  // 入库数量
        detailDto.setOutQuantity(m.getOuterQuantity()); // 出库数量
        detailDto.setPositionNameIn(null); // 入库货位
        detailDto.setPositionNameOut(m.getPositionName());  // 出库货位
        detailDto.setBillCode(storageOuter.getOuterCode());
        detailDto.setSourceType(storageOuter.getOrderType());
        return detailDto;
      }).toList();
      HolderSourceTypeEnum typeEnum =  HolderSourceTypeEnum.STORAGE_OUTER;
      if(B.isEqual(storageOuter.getOrderType(),"出库单溢出")){
        typeEnum=HolderSourceTypeEnum.SPILLOVER;
      }
      // 分拣库存
      var sortingResult = inventoryCommonService.sorting(commonMainDto, commonDetailDtoList, typeEnum);
      Assert.isFalse(!sortingResult.isResult(), "分拣失败：" + sortingResult.getMsg());
      // 出库
      inventoryCommonService.out(commonMainDto, commonDetailDtoList, InventorySourceTypeEnum.PC_STORAGE_OUT);
      //#endregion
      String outerStatus = "";
      if (storageOuter.getSourceType().equals("erp其他出库")) {
        outerStatus = StorageOuterStatusEnum.SUCCESS.getName(); // 审核成功
      } else {
        outerStatus = StorageOuterStatusEnum.FINISHED.getName(); // 出库完成
      }
      // 业务类型

      //#region 更新状态
      StorageOuter updateStorageOuter = new StorageOuter();
      updateStorageOuter.setAuditor(LoginHelper.getNickname());
      updateStorageOuter.setAuditing(AuditEnum.AUDITED_SUCCESS.getId());
      updateStorageOuter.setAuditDate(DateUtil.date());
      updateStorageOuter.setOuterStatus(outerStatus);
      updateStorageOuter.setOuterId(storageOuter.getOuterId()); // 设置主键

      this.updateById(updateStorageOuter);
      //#endregion
    }
    return R.ok("审核成功");
  }
  //endregion

  //#region 更新缺货数量 updateLackStorage
  @Override
  public void updateLackStorage(List<CommonDetailDto> detailList) {
    for (var detail : detailList) {
      HolderSourceTypeEnum typeEnum =  HolderSourceTypeEnum.STORAGE_OUTER;
      if(B.isEqual(detail.getSourceType(),"出库单溢出")){
        typeEnum=HolderSourceTypeEnum.SPILLOVER;
      }

      // 获取占位
      BigDecimal placeholderStorage = coreInventoryHolderService.getPlaceholderStorage(detail.getMainId(), detail.getDetailId(), typeEnum);
      BigDecimal lackStorage = B.sub(detail.getOutQuantity(), placeholderStorage); // 缺货数量
      if (B.isLess(lackStorage)) lackStorage = BigDecimal.ZERO;

      detail.setLackStorage(lackStorage);
      // 更新缺货数量
      LambdaUpdateWrapper<StorageOuterDetail> detailLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
      detailLambdaUpdateWrapper
        .set(StorageOuterDetail::getLackStorage, lackStorage)
        // 已分配
        .set(B.isLessOrEqual(lackStorage), StorageOuterDetail::getSortingStatus, SortingStatusEnum.ASSIGNED.getId())
        // 部分缺货
        .set(B.isGreater(lackStorage) && B.isGreater(detail.getOutQuantity(), lackStorage), StorageOuterDetail::getSortingStatus, SortingStatusEnum.PARTIAL_ASSIGNED.getId())
        // 缺货
        .set(B.isEqual(detail.getOutQuantity(), lackStorage), StorageOuterDetail::getSortingStatus, SortingStatusEnum.LACK.getId())
        .eq(StorageOuterDetail::getOuterDetailId, detail.getDetailId());
      storageOuterDetailService.update(detailLambdaUpdateWrapper);
    }
  }
  //#endregion

  //#region 更新分拣状态 updateSortingStatus

  /**
   * 更新明细分拣状态
   *
   * @param mainID            主表ID
   * @param sortingStatusEnum 分拣状态
   */
  @Override
  public void updateSortingStatus(Long mainID, SortingStatusEnum sortingStatusEnum) {
    LambdaUpdateWrapper<StorageOuter> orderLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    orderLambdaUpdateWrapper
      .set(StorageOuter::getSortingStatus, sortingStatusEnum.getId())
      .set(StorageOuter::getSortingDate, DateUtil.date())
      .eq(StorageOuter::getOuterId, mainID);
    this.update(orderLambdaUpdateWrapper);
  }
  //#endregion


  //#region 自定义分拣过滤器
  @Override
  public List<CoreInventoryComposeVo> customSortingFilter(CommonMainDto mainInfo, CommonDetailDto detailInfo, List<CoreInventoryComposeVo> inventoryList) {
    // 出库单明细集合
    List<StorageOuterDetail> outOrderDetailList = storageOuterDetailService.selectListByMainId(mainInfo.getMainId());

    for (var detail : outOrderDetailList) {
      //自定义过滤
      // 查到当前明细数据
      LambdaQueryWrapper<StorageOuterSortingRule> queryWrapper = new LambdaQueryWrapper<>();
      queryWrapper.eq(StorageOuterSortingRule::getOrderDetailId, detail.getOuterDetailId());

      // 明细中设置的分拣规则
      List<StorageOuterSortingRuleVo> sortingRuleList = storageOuterSortingRuleService.selectList(queryWrapper);

      for (var sortingRule : sortingRuleList) {
        if (ObjectUtil.isNotEmpty(sortingRule)) {
          // 指定货位
          if (StringUtils.isNotEmpty(sortingRule.getPositionName())) {
            inventoryList = inventoryList.stream().filter(f -> ObjectUtil.equals(f.getPositionName(), sortingRule.getPositionName())).toList();
            if (inventoryList.isEmpty()) continue;
          }

          // 指定拍号
          if (StringUtils.isNotEmpty(sortingRule.getPlateCode())) {
            inventoryList = inventoryList.stream().filter(f -> ObjectUtil.equals(f.getPlateCode(), sortingRule.getPlateCode())).toList();
            if (inventoryList.isEmpty()) continue;
          }

          // 指定批次号
          if (StringUtils.isNotEmpty(sortingRule.getBatchNumber())) {
            inventoryList = inventoryList.stream().filter(f -> ObjectUtil.equals(f.getBatchNumber(), sortingRule.getBatchNumber())).toList();
            if (inventoryList.isEmpty()) continue;
          }

          // 指定唯一码
          if (StringUtils.isNotEmpty(sortingRule.getSingleSignCode())) {
            inventoryList = inventoryList.stream().filter(f -> ObjectUtil.equals(f.getSingleSignCode(), sortingRule.getSingleSignCode())).toList();
            if (inventoryList.isEmpty()) continue;
          }

          // 指定库存ID
          if (ObjectUtil.isNotEmpty(sortingRule.getInventoryId())) {
            inventoryList = inventoryList.stream().filter(f -> ObjectUtil.equals(f.getInventoryId(), sortingRule.getInventoryId())).toList();
            if (inventoryList.isEmpty()) continue;
          }

          // 生产日期
          if (ObjectUtil.isNotEmpty(sortingRule.getProduceDate())) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            inventoryList = inventoryList.stream().filter(f -> ObjectUtil.equals(formatter.format(f.getProduceDate()), formatter.format(sortingRule.getProduceDate()))).toList();
            if (inventoryList.isEmpty()) continue;
          }
        }

      }

    }

    return inventoryList;
  }
  //#endregion

  //#region 其他出库导入数据
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
      Long storageId = Convert.toLong(request.getParameter("storageId"));
      Long consignorId = Convert.toLong(request.getParameter("consignorId"));
      // 是否开启唯一码
      sysImportService.isAssert(StringUtils.isEmpty(key), "上传key不存在");
      sysImportService.isAssert(ObjectUtil.isEmpty(importId), "未关联模板，不可导入");

      // 处理解析结果
      ExcelResult<Map<String, Object>> excelResult = null;
      excelResult = ExcelUtil.importExcel(inputStream, new ImportCommonListener());
      var dataList = excelResult.getList();

      // 通用验证
      sysImportService.setError(false);
      R<Void> result = sysImportService.commonCheck(dataList, importId, request, loginUser);
      sysImportService.isAssert(!result.isResult(), "导入数据有错误，请处理好重新导入");

      sysImportService.writeMsg("开始导入...");
      int i = 0;
      int successCount = 0;
      for (var row : dataList) {
        i++;
        BigDecimal outerQuantity = Convert.toBigDecimal(row.get("outerQuantity"));
        BaseProduct prodInfo;
        String productCode = Convert.toStr(row.get("productCode"));
        String productModel = Convert.toStr(row.get("productModel"));

        prodInfo = baseProductService.getByCode(productCode);
        sysImportService.isAssert(ObjectUtil.isEmpty(prodInfo), "${}商品条码[{}]不存在，核对商品后在导入", i, productModel);

        sysImportService.isAssert(!NumberUtils.isPositiveInteger(outerQuantity), "{}、产品编号[{}]数量请输入正整数！", i, productCode);
      }
      sysImportService.isAssert(sysImportService.isError(), "导入数据有错误，请处理好重新导入");

      // 对单据进行分组
      var groupList = sysImportService.getGroupList(dataList, importId);
      // 循环处理分组数据，groupList对应的是主表数据
      i = 0;
      for (var item : groupList) {
        i++;
        sysImportService.writeMsg("正在导入第{}行", i);
        var orderInfo = new StorageOuter();
        BeanUtil.copyProperties(item, orderInfo);
        String orderCode = DBUtils.getCodeRegular(MenuEnum.MENU_1600, loginUser.getTenantId());
        orderInfo.setOuterCode(orderCode);
//        orderInfo.setPlanType(InOrderPlanTypeEnum.ORDINARY_ORDER.getName());
        orderInfo.setOuterStatus(StorageOuterStatusEnum.NEWED.getName());
        orderInfo.setSortingStatus(SortingStatusEnum.NONE.getId());
        orderInfo.setAuditing(AuditEnum.AUDIT.getId());

        orderInfo.setContainerNo(Convert.toStr(item.get("containerNo")));
        orderInfo.setCreateBy(loginUser.getUserId());
        orderInfo.setCreateByName(loginUser.getNickname());
        orderInfo.setUserId(loginUser.getUserId());
        orderInfo.setNickName(loginUser.getNickname());
        orderInfo.setDeptId(loginUser.getDeptId());
        orderInfo.setDeptName(loginUser.getDeptName());
        orderInfo.setApplyDate(DateUtil.date());
        // 保存主表
        this.getBaseMapper().insert(orderInfo);
        // 获得明细数据
        var detailList = sysImportService.getGroupDetails(dataList, item, importId);

        /*——————————————————————————————————————————————————
         * 处理明细，将明细挂载到主表下
         *——————————————————————————————————————————————————*/
        List<StorageOuterDetail> storageOuterDetails = new ArrayList<>();
        for (var detail : detailList) {
          var detailInfo = new StorageOuterDetail();
          // 根据同货主同条码查找商品信息
          var baseProduct = this.baseProductService.getByCodeAndConsignor(Convert.toStr(detail.get("productCode")), Convert.toStr(detail.get("consignorName")));
          if (ObjectUtil.isEmpty(baseProduct)) {
            // 同货主没找到，在根据商品条码查询
            baseProduct = this.baseProductService.getByCode(Convert.toStr(detail.get("productCode")));
          }
          // 获得明细表扩展字段
          BeanUtil.copyProperties(baseProduct, detailInfo);
          BeanUtil.copyProperties(detail, detailInfo);


          // 单位毛重weight，小计毛重totalWeight，相互计算
          BigDecimal salePrice = Convert.toBigDecimal(detail.get("salePrice")); // 成本单价
          BigDecimal weight = Convert.toBigDecimal(detail.get("weight")); // 单位重量
          BigDecimal rowWeight = Convert.toBigDecimal(detail.get("rowWeight")); // 小计重量
          BigDecimal unitCube = Convert.toBigDecimal(detail.get("unitCube")); // 单位重量
          BigDecimal rowCube = Convert.toBigDecimal(detail.get("rowCube")); // 小计重量
          BigDecimal ratePrice = Convert.toBigDecimal(detail.get("ratePrice"));
          BigDecimal outerQuantity = Convert.toBigDecimal(detail.get("outerQuantity"));
          BigDecimal bigQty = Convert.toBigDecimal(detail.get("bigQty")); // 大单位数量

          if (ObjectUtil.isEmpty(salePrice)) {
            salePrice = baseProduct.getSalePrice();
            detailInfo.setSalePrice(baseProduct.getSalePrice());
          }
          //#region 重量计算
          if (ObjectUtil.isEmpty(weight) && ObjectUtil.isEmpty(rowWeight)) {
            // 单位毛重和小计毛重都为空时，默认商品信息单位重量
            detailInfo.setWeight(baseProduct.getWeight()); // 单位重量
            detailInfo.setRowWeight(B.mul(baseProduct.getWeight(), detailInfo.getOuterQuantity())); // 合计重量
          } else if (ObjectUtil.isEmpty(weight) && ObjectUtil.isNotEmpty(rowWeight)) {
            // 单位毛重为空和小计毛重不为空时，单位毛重通过小计毛重反算
            detailInfo.setWeight(B.div(rowWeight, detailInfo.getOuterQuantity()));
            detailInfo.setRowWeight(rowWeight);
          } else if (ObjectUtil.isNotEmpty(weight) && ObjectUtil.isEmpty(rowWeight)) {
            // 单位毛重不为空和小计毛重为空时，小计毛重通过单位毛重计算
            detailInfo.setWeight(weight);
            detailInfo.setRowWeight(B.mul(weight, detailInfo.getOuterQuantity()));
          } else if (ObjectUtil.isNotEmpty(weight) && ObjectUtil.isNotEmpty(rowWeight)) {
            // 单位毛重和小计毛重都不为空时，直接赋值
            detailInfo.setWeight(weight);
            detailInfo.setRowWeight(rowWeight);
          }
          //#endregion

          //#region 体积计算
          if (ObjectUtil.isEmpty(unitCube) && ObjectUtil.isEmpty(rowCube)) {
            // 单位体积和小计体积都为空时，默认商品信息单位体积
            detailInfo.setUnitCube(baseProduct.getUnitCube()); // 单位体积
            detailInfo.setRowCube(B.mul(baseProduct.getUnitCube(), detailInfo.getOuterQuantity())); // 合计体积
          } else if (ObjectUtil.isEmpty(unitCube) && ObjectUtil.isNotEmpty(rowCube)) {
            // 单位体积为空和小计体积不为空时，单位体积通过小计体积反算
            detailInfo.setUnitCube(B.div(rowCube, detailInfo.getOuterQuantity()));
            detailInfo.setRowCube(rowCube);
          } else if (ObjectUtil.isNotEmpty(unitCube) && ObjectUtil.isEmpty(rowCube)) {
            // 单位体积不为空和小计体积为空时，小计体积通过单位体积计算
            detailInfo.setUnitCube(unitCube);
            detailInfo.setRowCube(B.mul(unitCube, detailInfo.getOuterQuantity()));
          } else if (ObjectUtil.isNotEmpty(unitCube) && ObjectUtil.isNotEmpty(rowCube)) {
            // 单位体积和小计体积都不为空时，直接赋值
            detailInfo.setUnitCube(unitCube);
            detailInfo.setRowCube(rowCube);
          }
          //#endregion

          // 大单位数量 = 数量 / 大单位换算
          // 数量 = 大单位数 * 换算		// 数量必填
          if (ObjectUtil.isEmpty(bigQty)) {
            detailInfo.setBigQty(B.div(outerQuantity, baseProduct.getUnitConvert()));
          }

          // 计算金额
          BigDecimal hundred = new BigDecimal(100);
          BigDecimal rate = BigDecimal.ZERO;

          rate = B.div(baseProduct.getRate(), hundred); // 税率小数点除100
          ratePrice = B.mul(detailInfo.getSalePrice(), rate);
          detailInfo.setRatePrice(B.add(detailInfo.getSalePrice(), ratePrice)); // 含税单价
          detailInfo.setRateAmount(B.muls(outerQuantity, detailInfo.getRatePrice())); // 含税金额
          detailInfo.setSaleAmount(B.mul(outerQuantity, salePrice)); // 成本金额
          detailInfo.setSortingStatus(SortingStatusEnum.NONE.getId());
          //建立关系，设置主表ID
          detailInfo.setOuterId(orderInfo.getOuterId());
          storageOuterDetailService.save(detailInfo);
          storageOuterDetails.add(detailInfo);
        }
        // 主表求和字段计算
        var totalOuterQuantity = storageOuterDetails.stream().map(StorageOuterDetail::getOuterQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
        orderInfo.setTotalOuterQuantity(totalOuterQuantity);
        var totalWeight = storageOuterDetails.stream().map(StorageOuterDetail::getRowWeight).reduce(BigDecimal.ZERO, BigDecimal::add);
        orderInfo.setTotalWeight(totalWeight);
        var totalCube = storageOuterDetails.stream().map(StorageOuterDetail::getRowCube).reduce(BigDecimal.ZERO, BigDecimal::add);
        orderInfo.setTotalCube(totalCube);
        var totalAmount = storageOuterDetails.stream().map(StorageOuterDetail::getSaleAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        orderInfo.setTotalAmount(totalAmount);
        var totalRateAmount = storageOuterDetails.stream().map(StorageOuterDetail::getRateAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        orderInfo.setTotalRateAmount(totalRateAmount);
        var bigQtyTotal = storageOuterDetails.stream().map(StorageOuterDetail::getBigQty).reduce(BigDecimal.ZERO, BigDecimal::add);
        orderInfo.setBigQtyTotal(bigQtyTotal);
        this.getBaseMapper().updateById(orderInfo);

        successCount++;
      }

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

  //#region 获取分拣列表
  @Override
  @Transactional(rollbackFor = Exception.class)
  public List<StorageOuterSortingRule> getSortingRule(Map<String, Object> map) {
    // 获得分拣规则
    LambdaQueryWrapper<StorageOuterSortingRule> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
    orderLambdaQueryWrapper.eq(StorageOuterSortingRule::getOrderDetailId, Convert.toLong(map.get("orderDetailId")));


    return storageOuterSortingRuleService.list(orderLambdaQueryWrapper);
  }
  //#endregion

  //#region 设置分拣规则
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> setSortingRule(Map<String, Object> map) {

    LambdaQueryWrapper<StorageOuterSortingRule> queryWrapper222 = new LambdaQueryWrapper<>();
    queryWrapper222.eq(StorageOuterSortingRule::getProductCode, map.get("productCode"))
      .eq(StorageOuterSortingRule::getOuterId, map.get("orderId"))
      .eq(StorageOuterSortingRule::getOrderDetailId, map.get("orderDetailId"));
    List<StorageOuterSortingRuleVo> sortingRule222 = storageOuterSortingRuleService.selectList(queryWrapper222);


    for (var item : sortingRule222) {
      if (!Objects.equals(map.get("batchNumber"), "") && !Objects.equals(item.getBatchNumber(), "") && ObjectUtil.isNotNull(item.getBatchNumber())) {
        throw new ServiceException("批次号已存在!");
      }
      if (!Objects.equals(map.get("produceDate"), "") && ObjectUtil.isNotNull(map.get("produceDate")) && !Objects.equals(item.getProduceDate(), "") && ObjectUtil.isNotNull(item.getProduceDate())) {
        throw new ServiceException("生产日期已存在!");
      }
      if (!Objects.equals(map.get("positionName"), "") && !Objects.equals(item.getPositionName(), "") && ObjectUtil.isNotNull(item.getPositionName())) {
        throw new ServiceException("拣货货位已存在!");
      }
      if (!Objects.equals(map.get("plateCode"), "") && !Objects.equals(item.getPlateCode(), "") && ObjectUtil.isNotNull(item.getPlateCode())) {
        throw new ServiceException("托盘号已存在!");
      }
      if (!Objects.equals(map.get("singleSignCode"), "") && !Objects.equals(item.getSingleSignCode(), "") && ObjectUtil.isNotNull(item.getSingleSignCode())) {
        throw new ServiceException("唯一码已存在!");
      }
      if (!Objects.equals(map.get("inventoryId"), "") && !Objects.equals(item.getInventoryId(), "") && ObjectUtil.isNotNull(item.getInventoryId())) {
        throw new ServiceException("库存ID已存在!");
      }
    }

    StorageOuterSortingRule outSortingRule = new StorageOuterSortingRule();
    outSortingRule.setBatchNumber(Convert.toStr(map.get("batchNumber")));
    outSortingRule.setConsignorId(Convert.toLong(map.get("consignorId")));
    outSortingRule.setConsignorCode(Convert.toStr(map.get("consignorCode")));
    outSortingRule.setConsignorName(Convert.toStr(map.get("consignorName")));
    outSortingRule.setCreateTime(new Date());
    outSortingRule.setOuterId(Convert.toLong(map.get("orderId")));
    outSortingRule.setOrderDetailId(Convert.toLong(map.get("orderDetailId")));
    outSortingRule.setOuterCode(Convert.toStr(map.get("orderCode")));
    outSortingRule.setPlateCode(Convert.toStr(map.get("plateCode")));
    outSortingRule.setPositionName(Convert.toStr(map.get("positionName")));
    outSortingRule.setProduceDate(Convert.toDate(map.get("produceDate")));
    outSortingRule.setProductId(Convert.toLong(map.get("productId")));
    outSortingRule.setProductCode(Convert.toStr(map.get("productCode")));
    outSortingRule.setSingleSignCode(Convert.toStr(map.get("singleSignCode")));
    outSortingRule.setInventoryId(Convert.toLong(map.get("inventoryId")));
    outSortingRule.setStorageId(Convert.toLong(map.get("storageId")));
    outSortingRule.setStorageName(Convert.toStr(map.get("storageName")));
    storageOuterSortingRuleService.save(outSortingRule);

    return R.ok(null);
  }
  //#endregion

  //#region 关闭分拣规则
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> deleteSortingRule(Map<String, Object> map) {
    // 删除分拣规则
    LambdaQueryWrapper<StorageOuterSortingRule> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
    orderLambdaQueryWrapper.eq(StorageOuterSortingRule::getRuleId, Convert.toLong(map.get("ruleId")));

    storageOuterSortingRuleService.remove(orderLambdaQueryWrapper);
    return R.ok(null);
  }
  //endregion

  /**
   * 新增数据
   */
  @Override
  public R<Map<String, Object>> add(ApiStorageOuterBo bo) {

    try {

      if (bo.getDetailList().isEmpty()) {
        return R.fail("出库单明细不能为空");
      }

      // 验证单号是否已推送
      LambdaQueryWrapper<StorageOuter> queryWrapper = new LambdaQueryWrapper<>();
      queryWrapper.eq(StorageOuter::getSourceCode, bo.getSourceCode());
      StorageOuter storeOrderInfo = this.getOnly(queryWrapper);

      if (ObjectUtil.isNotNull(storeOrderInfo)) {
        Map<String, Object> result = new HashMap<>();
        result.put("outerId", storeOrderInfo.getOuterId());
        result.put("outerCode", storeOrderInfo.getOuterCode());
        return R.ok(bo.getSourceCode() + "已存在，不允许重复推送", result);
      }

      // 验证货主
      BaseConsignor consignorInfo = baseConsignorService.getByName(bo.getConsignorName());
      if (ObjectUtil.isNull(consignorInfo)) {
        return R.fail("货主不存在");
      }
      bo.setConsignorId(consignorInfo.getConsignorId());
      bo.setConsignorCode(consignorInfo.getConsignorCode());

      // 接口推送时需要仓库
      boolean erp_storage_Id = sysConfigService.getConfigBool("erp_storage_Id");
//      if (erp_storage_Id) {
      // 验证仓库
      BaseStorage storageInfo = baseStorageService.getByName(bo.getStorageName());
      if (ObjectUtil.isNull(storageInfo)) {
        return R.fail("仓库不存在");
      }
      bo.setStorageId(storageInfo.getStorageId());
//      }
      bo.setApplyDate(DateUtils.getNowDate());


      for (var detailInfo : bo.getDetailList()) {
        BaseProduct prodInfo = baseProductService.getByCode(detailInfo.getProductCode());

        if (ObjectUtil.isNull(prodInfo)) {
          return R.fail(detailInfo.getProductCode() + "商品编号不存在");
        }
        detailInfo.setOuterQuantity(detailInfo.getOuterQuantity());
        detailInfo.setProductId(prodInfo.getProductId());
        detailInfo.setProductModel(prodInfo.getProductModel());
        detailInfo.setProductName(prodInfo.getProductName());
        detailInfo.setProductSpec(prodInfo.getProductSpec());

        if (ObjectUtil.isNull(detailInfo.getOuterQuantity()) || B.isLessOrEqual(detailInfo.getOuterQuantity(), BigDecimal.ZERO)) {
          return R.fail(detailInfo.getProductModel() + "数量必须大于0");
        }
      }


      StorageOuter dataInfo = new StorageOuter();
      BeanUtil.copyProperties(bo, dataInfo, new CopyOptions().setIgnoreProperties("outerId"));
      dataInfo.setOuterCode(DBUtils.getCodeRegular(MenuEnum.MENU_1600));
      dataInfo.setOuterStatus(StorageEnterStatusEnum.NEWED.getName());

      if (ObjectUtil.isNotNull(bo.getOrderType())) {
        dataInfo.setOrderType(bo.getOrderType());
      } else {
        dataInfo.setOrderType("API入库");
      }
      this.save(dataInfo);

      for (var detailInfo : bo.getDetailList()) {
        StorageOuterDetail detail = new StorageOuterDetail();
        BaseProduct baseProduct = baseProductService.getByCode(detailInfo.getProductCode());

        BeanUtil.copyProperties(detailInfo, detail, new CopyOptions().setIgnoreProperties("outerDetailId"));

        detail.setOuterId(dataInfo.getOuterId());
        //#region 重量计算
        if (ObjectUtil.isEmpty(detailInfo.getWeight()) && ObjectUtil.isEmpty(detailInfo.getRowWeight())) {
          // 单位毛重和小计毛重都为空时，默认商品信息单位重量
          detailInfo.setWeight(baseProduct.getWeight()); // 单位重量
          detailInfo.setRowWeight(B.mul(baseProduct.getWeight(), detailInfo.getOuterQuantity())); // 合计重量
        } else if (ObjectUtil.isEmpty(detailInfo.getWeight()) && ObjectUtil.isNotEmpty(detailInfo.getRowWeight())) {
          // 单位毛重为空和小计毛重不为空时，单位毛重通过小计毛重反算
          detailInfo.setWeight(B.div(detailInfo.getRowWeight(), detailInfo.getOuterQuantity()));
          detailInfo.setRowWeight(detailInfo.getRowWeight());
        } else if (ObjectUtil.isNotEmpty(detailInfo.getWeight()) && ObjectUtil.isEmpty(detailInfo.getRowWeight())) {
          // 单位毛重不为空和小计毛重为空时，小计毛重通过单位毛重计算
          detailInfo.setWeight(detailInfo.getWeight());
          detailInfo.setRowWeight(B.mul(detailInfo.getWeight(), detailInfo.getOuterQuantity()));
        } else if (ObjectUtil.isNotEmpty(detailInfo.getWeight()) && ObjectUtil.isNotEmpty(detailInfo.getRowWeight())) {
          // 单位毛重和小计毛重都不为空时，直接赋值
          detailInfo.setWeight(detailInfo.getWeight());
          detailInfo.setRowWeight(detailInfo.getRowWeight());
        }
        //#endregion

        //#region 体积计算
        if (ObjectUtil.isEmpty(detailInfo.getUnitCube()) && ObjectUtil.isEmpty(detailInfo.getRowCube())) {
          // 单位体积和小计体积都为空时，默认商品信息单位体积
          detailInfo.setUnitCube(baseProduct.getUnitCube()); // 单位体积
          detailInfo.setRowCube(B.mul(baseProduct.getUnitCube(), detailInfo.getOuterQuantity())); // 合计体积
        } else if (ObjectUtil.isEmpty(detailInfo.getUnitCube()) && ObjectUtil.isNotEmpty(detailInfo.getRowCube())) {
          // 单位体积为空和小计体积不为空时，单位体积通过小计体积反算
          detailInfo.setUnitCube(B.div(detailInfo.getRowCube(), detailInfo.getOuterQuantity()));
          detailInfo.setRowCube(detailInfo.getRowCube());
        } else if (ObjectUtil.isNotEmpty(detailInfo.getUnitCube()) && ObjectUtil.isEmpty(detailInfo.getRowCube())) {
          // 单位体积不为空和小计体积为空时，小计体积通过单位体积计算
          detailInfo.setUnitCube(detailInfo.getUnitCube());
          detailInfo.setRowCube(B.mul(detailInfo.getUnitCube(), detailInfo.getOuterQuantity()));
        } else if (ObjectUtil.isNotEmpty(detailInfo.getUnitCube()) && ObjectUtil.isNotEmpty(detailInfo.getRowCube())) {
          // 单位体积和小计体积都不为空时，直接赋值
          detailInfo.setUnitCube(detailInfo.getUnitCube());
          detailInfo.setRowCube(detailInfo.getRowCube());
        }
        //#endregion

        // 大单位数量 = 数量 / 大单位换算
        // 数量 = 大单位数 * 换算		// 数量必填
        if (ObjectUtil.isEmpty(detailInfo.getBigQty())) {
          detailInfo.setBigQty(B.div(detailInfo.getOuterQuantity(), baseProduct.getUnitConvert()));
        }
        if (ObjectUtil.isEmpty(detailInfo.getOuterQuantity())) {
          detailInfo.setOuterQuantity(B.mul(baseProduct.getUnitConvert(), detailInfo.getBigQty()));
        }

        // 计算金额
        BigDecimal hundred = new BigDecimal(100);
        BigDecimal rate = BigDecimal.ZERO;
        BigDecimal ratePrice = BigDecimal.ZERO;

        rate = B.div(baseProduct.getRate(), hundred); // 税率小数点除100
        ratePrice = B.mul(detailInfo.getPurchasePrice(), rate);
        detailInfo.setRatePrice(B.add(ratePrice, detailInfo.getSalePrice())); // 含税单价
        detailInfo.setRateAmount(B.muls(detailInfo.getOuterQuantity(), detailInfo.getRatePrice())); // 含税金额
//          detailInfo.setSaleAmount(B.mul(enterQuantity, purchasePrice)); // 成本金额

        detailInfo.setSaleAmount(B.mul(detailInfo.getSalePrice(), detailInfo.getOuterQuantity()));
        storageOuterDetailService.save(detail);
      }

      List<StorageOuterDetail> storageEnterDetails = storageOuterDetailService.selectListByMainId(dataInfo.getOuterId());

      BigDecimal totalOuterQuantity = storageEnterDetails.stream().map(StorageOuterDetail::getOuterQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
      BigDecimal totalAmount = storageEnterDetails.stream().map(StorageOuterDetail::getSaleAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
      BigDecimal totalWeight = storageEnterDetails.stream().map(StorageOuterDetail::getWeight).reduce(BigDecimal.ZERO, BigDecimal::add);

      LambdaUpdateWrapper<StorageOuter> lambda = new LambdaUpdateWrapper<>();
      lambda.set(StorageOuter::getTotalOuterQuantity, totalOuterQuantity)
        .set(StorageOuter::getTotalAmount, totalAmount)
        .set(StorageOuter::getTotalWeight, totalWeight)
        .eq(StorageOuter::getOuterId, dataInfo.getOuterId());

      Map<String, Object> result = new HashMap<>();
      result.put("outerId", dataInfo.getOuterId());
      result.put("outerCode", dataInfo.getOuterCode());
      return R.ok("订单保存成功", result);

    } catch (Exception error) {
      return R.fail("推送订单失败，" + error.getMessage());
    }
  }
  //#endregion

}

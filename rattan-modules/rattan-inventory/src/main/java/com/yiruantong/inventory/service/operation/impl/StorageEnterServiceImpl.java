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
import com.yiruantong.common.core.enums.base.AuditEnum;
import com.yiruantong.common.core.enums.base.InventorySourceTypeEnum;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.inventory.StorageEnterStatusEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.DateUtils;
import com.yiruantong.common.core.utils.NumberUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.excel.core.ExcelResult;
import com.yiruantong.common.excel.listener.ImportCommonListener;
import com.yiruantong.common.excel.utils.ExcelUtil;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.tenant.helper.TenantHelper;
import com.yiruantong.inventory.domain.base.dto.CommonDetailDto;
import com.yiruantong.inventory.domain.base.dto.CommonMainDto;
import com.yiruantong.inventory.domain.operation.StorageEnter;
import com.yiruantong.inventory.domain.operation.StorageEnterDetail;
import com.yiruantong.inventory.domain.operation.api.ApiStorageEnterBo;
import com.yiruantong.inventory.domain.operation.bo.StorageEnterBo;
import com.yiruantong.inventory.domain.operation.vo.StorageEnterVo;
import com.yiruantong.inventory.mapper.operation.StorageEnterMapper;
import com.yiruantong.inventory.service.base.IInventoryBaseService;
import com.yiruantong.inventory.service.base.IInventoryCommonService;
import com.yiruantong.inventory.service.operation.IStorageEnterDetailService;
import com.yiruantong.inventory.service.operation.IStorageEnterService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 其他入库单Service业务层处理
 *
 * @author YRT
 * @date 2023-10-24
 */
@RequiredArgsConstructor
@Service
public class StorageEnterServiceImpl extends ServiceImplPlus<StorageEnterMapper, StorageEnter, StorageEnterVo, StorageEnterBo> implements IStorageEnterService, IInventoryBaseService {
  private final IStorageEnterDetailService storageEnterDetailService;
  private final IInventoryCommonService inventoryCommonService;
  private final DataSourceTransactionManager transactionManager;
  private final ISysImportService sysImportService;
  private final IBaseProductService baseProductService;
  private final IBaseProviderService baseProviderService;
  private final IBaseConsignorService baseConsignorService;
  private final IBaseStorageService baseStorageService;

  //#region 批量审核入库
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> multiAuditing(List<Long> ids) {
    for (Long id : ids) {
      StorageEnter storageEnter = this.getById(id);
      Assert.isFalse(ObjectUtil.equal(storageEnter.getEnterStatus(), StorageEnterStatusEnum.FINISHED.getName()),
        storageEnter.getEnterCode() + "入库完成的，不允许重复操作");
      List<StorageEnterDetail> detailList = storageEnterDetailService.selectListByMainId(id);

      //#region 通用模块调用
      inventoryCommonService.setBizService(this);

      // 构建DTO数据 - 主表
      CommonMainDto commonMainDto = BeanUtil.copyProperties(storageEnter, CommonMainDto.class);
      commonMainDto.setMainId(storageEnter.getEnterId());
      commonMainDto.setMainCode(storageEnter.getEnterCode());

      // 构建DTO数据 - 明细集合
      List<CommonDetailDto> commonDetailDtoList = detailList.stream().map(m -> {
        CommonDetailDto detailDto = BeanUtil.copyProperties(m, CommonDetailDto.class);
        detailDto.setMainId(m.getEnterId()); // 主表ID
        detailDto.setDetailId(m.getEnterDetailId()); // 明细ID
        detailDto.setInQuantity(m.getEnterQuantity());  // 入库数量
        detailDto.setOutQuantity(BigDecimal.ZERO); // 出库数量
        detailDto.setPositionNameIn(m.getPositionName()); // 入库货位
        detailDto.setPositionNameOut(null);  // 出库货位
        return detailDto;
      }).toList();
      InventorySourceTypeEnum typeEnum =  InventorySourceTypeEnum.PC_STORAGE_IN;
      if(B.isEqual(storageEnter.getOrderType(),"出库单溢出入库")){
        typeEnum =  InventorySourceTypeEnum.OUT_SPILLOVER_IN;
      }
      // 入库
      inventoryCommonService.in(commonMainDto, commonDetailDtoList, typeEnum);
      //#endregion

      //#region 更新状态
      storageEnter.setAuditor(LoginHelper.getNickname());
      storageEnter.setAuditing(AuditEnum.AUDITED_SUCCESS.getId());
      storageEnter.setAuditDate(DateUtil.date());
      storageEnter.setEnterStatus(StorageEnterStatusEnum.FINISHED.getName());

      this.updateById(storageEnter);
      //#endregion
    }
    return R.ok("入库成功");
  }
  //#endregion

  //#region 其他入库导入数据
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
        BigDecimal enterQuantity = Convert.toBigDecimal(row.get("enterQuantity"));
        BaseProduct prodInfo;
        String productCode = Convert.toStr(row.get("productCode"));
        String productModel = Convert.toStr(row.get("productModel"));

        prodInfo = baseProductService.getByCode(productCode);
        sysImportService.isAssert(ObjectUtil.isEmpty(prodInfo), "${}商品条码[{}]不存在，核对商品后在导入", i, productModel);

        sysImportService.isAssert(!NumberUtils.isPositiveInteger(enterQuantity), "{}、产品编号[{}]数量请输入正整数！", i, productCode);
      }
      sysImportService.isAssert(sysImportService.isError(), "导入数据有错误，请处理好重新导入");

      // 对单据进行分组
      var groupList = sysImportService.getGroupList(dataList, importId);
      // 循环处理分组数据，groupList对应的是主表数据
      i = 0;
      for (var item : groupList) {
        i++;
        sysImportService.writeMsg("正在导入第{}行", i);
        var orderInfo = new StorageEnter();
        BeanUtil.copyProperties(item, orderInfo);
        String orderCode = DBUtils.getCodeRegular(MenuEnum.MENU_1043, loginUser.getTenantId());
        orderInfo.setEnterCode(orderCode);
//        orderInfo.setPlanType(InOrderPlanTypeEnum.ORDINARY_ORDER.getName());
        orderInfo.setEnterStatus(StorageEnterStatusEnum.NEWED.getName());
        orderInfo.setAuditing(AuditEnum.AUDIT.getId());
        // 供应商为空，默认第一个供应商
        if (StringUtils.isEmpty(orderInfo.getProviderShortName())) {
          var providerInfo = baseProviderService.getDefaultOne();
          if (ObjectUtil.isNotNull(providerInfo)) {
            orderInfo.setProviderId(providerInfo.getProviderId());
            orderInfo.setProviderCode(providerInfo.getProviderCode());
            orderInfo.setProviderShortName(providerInfo.getProviderShortName());
          }
        }
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
        List<StorageEnterDetail> storageEnterDetails = new ArrayList<>();
        for (var detail : detailList) {
          var detailInfo = new StorageEnterDetail();
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
          BigDecimal purchasePrice = Convert.toBigDecimal(detail.get("purchasePrice")); // 成本单价
          BigDecimal weight = Convert.toBigDecimal(detail.get("weight")); // 单位重量
          BigDecimal rowWeight = Convert.toBigDecimal(detail.get("rowWeight")); // 小计重量
          BigDecimal unitCube = Convert.toBigDecimal(detail.get("unitCube")); // 单位重量
          BigDecimal rowCube = Convert.toBigDecimal(detail.get("rowCube")); // 小计重量
          BigDecimal ratePrice = Convert.toBigDecimal(detail.get("ratePrice"));
          BigDecimal enterQuantity = Convert.toBigDecimal(detail.get("enterQuantity"));
          BigDecimal bigQty = Convert.toBigDecimal(detail.get("bigQty")); // 大单位数量

          if (ObjectUtil.isEmpty(purchasePrice)) {
            purchasePrice = baseProduct.getPurchasePrice();
          }
          //#region 重量计算
          if (ObjectUtil.isEmpty(weight) && ObjectUtil.isEmpty(rowWeight)) {
            // 单位毛重和小计毛重都为空时，默认商品信息单位重量
            detailInfo.setWeight(baseProduct.getWeight()); // 单位重量
            detailInfo.setRowWeight(B.mul(baseProduct.getWeight(), detailInfo.getEnterQuantity())); // 合计重量
          } else if (ObjectUtil.isEmpty(weight) && ObjectUtil.isNotEmpty(rowWeight)) {
            // 单位毛重为空和小计毛重不为空时，单位毛重通过小计毛重反算
            detailInfo.setWeight(B.div(rowWeight, detailInfo.getEnterQuantity()));
            detailInfo.setRowWeight(rowWeight);
          } else if (ObjectUtil.isNotEmpty(weight) && ObjectUtil.isEmpty(rowWeight)) {
            // 单位毛重不为空和小计毛重为空时，小计毛重通过单位毛重计算
            detailInfo.setWeight(weight);
            detailInfo.setRowWeight(B.mul(weight, detailInfo.getEnterQuantity()));
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
            detailInfo.setRowCube(B.mul(baseProduct.getUnitCube(), detailInfo.getEnterQuantity())); // 合计体积
          } else if (ObjectUtil.isEmpty(unitCube) && ObjectUtil.isNotEmpty(rowCube)) {
            // 单位体积为空和小计体积不为空时，单位体积通过小计体积反算
            detailInfo.setUnitCube(B.div(rowCube, detailInfo.getEnterQuantity()));
            detailInfo.setRowCube(rowCube);
          } else if (ObjectUtil.isNotEmpty(unitCube) && ObjectUtil.isEmpty(rowCube)) {
            // 单位体积不为空和小计体积为空时，小计体积通过单位体积计算
            detailInfo.setUnitCube(unitCube);
            detailInfo.setRowCube(B.mul(unitCube, detailInfo.getEnterQuantity()));
          } else if (ObjectUtil.isNotEmpty(unitCube) && ObjectUtil.isNotEmpty(rowCube)) {
            // 单位体积和小计体积都不为空时，直接赋值
            detailInfo.setUnitCube(unitCube);
            detailInfo.setRowCube(rowCube);
          }
          //#endregion

          // 大单位数量 = 数量 / 大单位换算
          // 数量 = 大单位数 * 换算		// 数量必填
          if (ObjectUtil.isEmpty(bigQty)) {
            detailInfo.setBigQty(B.div(enterQuantity, baseProduct.getUnitConvert()));
          }
          if (ObjectUtil.isEmpty(enterQuantity)) {
            detailInfo.setEnterQuantity(B.mul(baseProduct.getUnitConvert(), bigQty));
          }

          // 计算金额
          BigDecimal hundred = new BigDecimal(100);
          BigDecimal rate = BigDecimal.ZERO;

          rate = B.div(baseProduct.getRate(), hundred); // 税率小数点除100
          ratePrice = B.mul(purchasePrice, rate);
          detailInfo.setRatePrice(B.add(ratePrice, purchasePrice)); // 含税单价
          detailInfo.setRateAmount(B.muls(enterQuantity, detailInfo.getRatePrice())); // 含税金额
//          detailInfo.setSaleAmount(B.mul(enterQuantity, purchasePrice)); // 成本金额

          detailInfo.setPurchaseAmount(B.mul(purchasePrice, detailInfo.getEnterQuantity()));

          //建立关系，设置主表ID
          detailInfo.setEnterId(orderInfo.getEnterId());
          storageEnterDetailService.save(detailInfo);
          storageEnterDetails.add(detailInfo);
        }
        // 主表求和字段计算
        var totalEnterQuantity = storageEnterDetails.stream().map(StorageEnterDetail::getEnterQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
        orderInfo.setTotalEnterQuantity(totalEnterQuantity);
        var totalWeight = storageEnterDetails.stream().map(StorageEnterDetail::getRowWeight).reduce(BigDecimal.ZERO, BigDecimal::add);
        orderInfo.setTotalWeight(totalWeight);
        var totalAmount = storageEnterDetails.stream().map(StorageEnterDetail::getPurchaseAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        orderInfo.setTotalAmount(totalAmount);
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
  //#region

  /**
   * 新增数据
   */
  @Override
  public R<Map<String, Object>> add(ApiStorageEnterBo bo) {

    Assert.isFalse(ObjectUtil.isNull(bo.getDetailList()), "入库单明细不能为空！");

    try {
      // 验证单号是否已推送
      LambdaQueryWrapper<StorageEnter> queryWrapper = new LambdaQueryWrapper<>();
      queryWrapper.eq(StorageEnter::getSourceCode, bo.getSourceCode());
      StorageEnter storeOrderInfo = this.getOnly(queryWrapper);

      if (ObjectUtil.isNotNull(storeOrderInfo)) {
        Map<String, Object> result = new HashMap<>();
        result.put("enterId", storeOrderInfo.getEnterId());
        result.put("enterCode", storeOrderInfo.getEnterCode());
        return R.ok(bo.getSourceCode() + "已存在，不允许重复推送", result);
      }

      // 验证货主
      BaseConsignor consignorInfo = baseConsignorService.getByName(bo.getConsignorName());
      Assert.isFalse(ObjectUtil.isNull(consignorInfo), "货主不存在！");

      bo.setConsignorId(consignorInfo.getConsignorId());
      bo.setConsignorCode(consignorInfo.getConsignorCode());

      // 验证仓库
      BaseStorage storageInfo = baseStorageService.getByName(bo.getStorageName());
      Assert.isFalse(ObjectUtil.isNull(storageInfo), "仓库不存在！");

      bo.setStorageId(storageInfo.getStorageId());
      bo.setApplyDate(DateUtils.getNowDate());

      for (var detailInfo : bo.getDetailList()) {
        BaseProduct prodInfo = baseProductService.getByCode(detailInfo.getProductCode());
        Assert.isFalse(ObjectUtil.isNull(prodInfo), detailInfo.getProductCode() + "商品编号不存在");

        detailInfo.setEnterQuantity(detailInfo.getEnterQuantity());
        detailInfo.setProductId(prodInfo.getProductId());
        detailInfo.setProductModel(prodInfo.getProductModel());
        detailInfo.setProductName(prodInfo.getProductName());
        detailInfo.setProductSpec(prodInfo.getProductSpec());

        if (ObjectUtil.isNull(detailInfo.getEnterQuantity()) || B.isLessOrEqual(detailInfo.getEnterQuantity(), BigDecimal.ZERO)) {
          return R.fail(detailInfo.getProductModel() + "数量必须大于0");
        }
      }


      StorageEnter dataInfo = new StorageEnter();
      BeanUtil.copyProperties(bo, dataInfo, new CopyOptions().setIgnoreProperties("enterId"));
      dataInfo.setEnterCode(DBUtils.getCodeRegular(MenuEnum.MENU_1043));

      dataInfo.setEnterStatus(StorageEnterStatusEnum.NEWED.getName());
      if (ObjectUtil.isNotNull(bo.getOrderType())) {
        dataInfo.setOrderType(bo.getOrderType());
      } else {
        dataInfo.setOrderType("API入库");
      }

      this.save(dataInfo);

      for (var detailInfo : bo.getDetailList()) {
        BaseProduct baseProduct = baseProductService.getByCode(detailInfo.getProductCode());

        StorageEnterDetail detail = new StorageEnterDetail();
        BeanUtil.copyProperties(detailInfo, detail, new CopyOptions().setIgnoreProperties("enterDetailId"));

        detail.setEnterId(dataInfo.getEnterId());

        //#region 重量计算
        if (ObjectUtil.isEmpty(detailInfo.getWeight()) && ObjectUtil.isEmpty(detailInfo.getRowWeight())) {
          // 单位毛重和小计毛重都为空时，默认商品信息单位重量
          detailInfo.setWeight(baseProduct.getWeight()); // 单位重量
          detailInfo.setRowWeight(B.mul(baseProduct.getWeight(), detailInfo.getEnterQuantity())); // 合计重量
        } else if (ObjectUtil.isEmpty(detailInfo.getWeight()) && ObjectUtil.isNotEmpty(detailInfo.getRowWeight())) {
          // 单位毛重为空和小计毛重不为空时，单位毛重通过小计毛重反算
          detailInfo.setWeight(B.div(detailInfo.getRowWeight(), detailInfo.getEnterQuantity()));
          detailInfo.setRowWeight(detailInfo.getRowWeight());
        } else if (ObjectUtil.isNotEmpty(detailInfo.getWeight()) && ObjectUtil.isEmpty(detailInfo.getRowWeight())) {
          // 单位毛重不为空和小计毛重为空时，小计毛重通过单位毛重计算
          detailInfo.setWeight(detailInfo.getWeight());
          detailInfo.setRowWeight(B.mul(detailInfo.getWeight(), detailInfo.getEnterQuantity()));
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
          detailInfo.setRowCube(B.mul(baseProduct.getUnitCube(), detailInfo.getEnterQuantity())); // 合计体积
        } else if (ObjectUtil.isEmpty(detailInfo.getUnitCube()) && ObjectUtil.isNotEmpty(detailInfo.getRowCube())) {
          // 单位体积为空和小计体积不为空时，单位体积通过小计体积反算
          detailInfo.setUnitCube(B.div(detailInfo.getRowCube(), detailInfo.getEnterQuantity()));
          detailInfo.setRowCube(detailInfo.getRowCube());
        } else if (ObjectUtil.isNotEmpty(detailInfo.getUnitCube()) && ObjectUtil.isEmpty(detailInfo.getRowCube())) {
          // 单位体积不为空和小计体积为空时，小计体积通过单位体积计算
          detailInfo.setUnitCube(detailInfo.getUnitCube());
          detailInfo.setRowCube(B.mul(detailInfo.getUnitCube(), detailInfo.getEnterQuantity()));
        } else if (ObjectUtil.isNotEmpty(detailInfo.getUnitCube()) && ObjectUtil.isNotEmpty(detailInfo.getRowCube())) {
          // 单位体积和小计体积都不为空时，直接赋值
          detailInfo.setUnitCube(detailInfo.getUnitCube());
          detailInfo.setRowCube(detailInfo.getRowCube());
        }
        //#endregion

        // 大单位数量 = 数量 / 大单位换算
        // 数量 = 大单位数 * 换算		// 数量必填
        if (ObjectUtil.isEmpty(detailInfo.getBigQty())) {
          detailInfo.setBigQty(B.div(detailInfo.getEnterQuantity(), baseProduct.getUnitConvert()));
        }
        if (ObjectUtil.isEmpty(detailInfo.getEnterQuantity())) {
          detailInfo.setEnterQuantity(B.mul(baseProduct.getUnitConvert(), detailInfo.getBigQty()));
        }

        // 计算金额
        BigDecimal hundred = new BigDecimal(100);
        BigDecimal rate = BigDecimal.ZERO;
        BigDecimal ratePrice = BigDecimal.ZERO;

        rate = B.div(baseProduct.getRate(), hundred); // 税率小数点除100
        ratePrice = B.mul(detailInfo.getPurchasePrice(), rate);
        detailInfo.setRatePrice(B.add(ratePrice, detailInfo.getPurchasePrice())); // 含税单价
        detailInfo.setRateAmount(B.muls(detailInfo.getEnterQuantity(), detailInfo.getRatePrice())); // 含税金额
//          detailInfo.setSaleAmount(B.mul(enterQuantity, purchasePrice)); // 成本金额

        detailInfo.setPurchaseAmount(B.mul(detailInfo.getPurchasePrice(), detailInfo.getEnterQuantity()));

        storageEnterDetailService.save(detail);
      }

      List<StorageEnterDetail> storageEnterDetails = storageEnterDetailService.selectListByMainId(dataInfo.getEnterId());

      BigDecimal totalEnterQuantity = storageEnterDetails.stream().map(StorageEnterDetail::getEnterQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
      BigDecimal totalAmount = storageEnterDetails.stream().map(StorageEnterDetail::getPurchaseAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
      BigDecimal totalWeight = storageEnterDetails.stream().map(StorageEnterDetail::getWeight).reduce(BigDecimal.ZERO, BigDecimal::add);

      LambdaUpdateWrapper<StorageEnter> lambda = new LambdaUpdateWrapper<>();
      lambda.set(StorageEnter::getTotalEnterQuantity, totalEnterQuantity)
        .set(StorageEnter::getTotalAmount, totalAmount)
        .set(StorageEnter::getTotalWeight, totalWeight)
        .eq(StorageEnter::getEnterId, dataInfo.getEnterId());


      Map<String, Object> result = new HashMap<>();
      result.put("enterId", dataInfo.getEnterId());
      result.put("enterCode", dataInfo.getEnterCode());
      return R.ok("订单保存成功", result);

    } catch (Exception error) {
      return R.fail("推送订单失败，" + error.getMessage());
    }
  }
  //#endregion

}

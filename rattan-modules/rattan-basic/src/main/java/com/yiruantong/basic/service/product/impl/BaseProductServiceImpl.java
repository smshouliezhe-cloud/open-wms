package com.yiruantong.basic.service.product.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yiruantong.basic.domain.base.BaseConsignor;
import com.yiruantong.basic.domain.base.bo.GetListBo;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.domain.product.BaseProductType;
import com.yiruantong.basic.domain.product.BaseProvider;
import com.yiruantong.basic.domain.product.bo.BaseProductBo;
import com.yiruantong.basic.domain.product.vo.BaseProductVo;
import com.yiruantong.basic.mapper.product.BaseProductMapper;
import com.yiruantong.basic.service.base.IBaseConsignorService;
import com.yiruantong.basic.service.product.IBaseProductService;
import com.yiruantong.basic.service.product.IBaseProductTypeService;
import com.yiruantong.basic.service.product.IBaseProviderService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.base.AuditEnum;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.DateUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.excel.core.ExcelResult;
import com.yiruantong.common.excel.listener.ImportCommonListener;
import com.yiruantong.common.excel.utils.ExcelUtil;
import com.yiruantong.common.mybatis.core.domain.bo.SaveEditorBo;
import com.yiruantong.common.mybatis.core.domain.vo.EditorVo;
import com.yiruantong.common.mybatis.core.dto.QueryBo;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.mybatis.enums.DataTypeEnum;
import com.yiruantong.common.mybatis.enums.QueryTypeEnum;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.tenant.helper.TenantHelper;
import com.yiruantong.system.service.core.ISysConfigService;
import com.yiruantong.system.service.dataHandler.ISysImportService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.NoSuchMessageException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.InputStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 商品基础信息Service业务层处理
 *
 * @author YRT
 * @date 2023-10-15
 */
@RequiredArgsConstructor
@Service
public class BaseProductServiceImpl extends ServiceImplPlus<BaseProductMapper, BaseProduct, BaseProductVo, BaseProductBo> implements IBaseProductService {

  //  private final IBaseProductService baseProductService;
  private final DataSourceTransactionManager transactionManager;
  private final ISysImportService sysImportService;
  private final ISysConfigService sysConfigService;
  private final IBaseConsignorService baseConsignorService;
  private final IBaseProviderService baseProviderService;
  private final IBaseProductTypeService baseProductTypeService;

  //#region 根据编号获取商品信息

  /**
   * 根据编号获取商品信息
   *
   * @param productCode 商品编号
   * @return 返回实体商品信息
   */
  public BaseProduct getByCode(String productCode) {
    LambdaQueryWrapper<BaseProduct> productLambdaQueryWrapper = new LambdaQueryWrapper<>();
    productLambdaQueryWrapper.eq(BaseProduct::getProductCode, productCode);

    return this.getOnly(productLambdaQueryWrapper);
  }

  @Override
  public BaseProduct getByModel(String productModel) {
    LambdaQueryWrapper<BaseProduct> productLambdaQueryWrapper = new LambdaQueryWrapper<>();
    productLambdaQueryWrapper.eq(BaseProduct::getProductModel, productModel);

    return this.getOne(productLambdaQueryWrapper);
  }

  @Override
  public BaseProduct getByName(String productName) {
    LambdaQueryWrapper<BaseProduct> productLambdaQueryWrapper = new LambdaQueryWrapper<>();
    productLambdaQueryWrapper.eq(BaseProduct::getProductName, productName);

    return this.getOnly(productLambdaQueryWrapper);
  }

  @Override
  public BaseProduct getByCodeAndConsignor(String productCode, String consignorName) {
    LambdaQueryWrapper<BaseProduct> productLambdaQueryWrapper = new LambdaQueryWrapper<>();
    productLambdaQueryWrapper.eq(BaseProduct::getProductCode, productCode)
      .eq(BaseProduct::getConsignorName, consignorName);

    return this.getOne(productLambdaQueryWrapper);
  }

  @Override
  public List<BaseProduct> selectByModel(String productModel) {
    LambdaQueryWrapper<BaseProduct> productLambdaQueryWrapper = new LambdaQueryWrapper<>();
    productLambdaQueryWrapper.eq(BaseProduct::getProductModel, productModel);
    return this.getBaseMapper().selectList(productLambdaQueryWrapper);
  }
  //#endregion

  //#region 通用 - 查询商品列表

  /**
   * 通用 - 查询商品列表
   *
   * @param getListBo 查询条件
   * @return 返回查询结果
   */
  @Override
  public List<Map<String, Object>> getList(GetListBo getListBo) {
    Integer take = Optional.ofNullable(getListBo).map(m -> Convert.toInt(m.getTake())).orElse(200); // 查询top N，如果为空，默认200
    String name = Convert.toStr(getListBo.getName());
    // amis下拉框搜索
    if (StringUtils.isEmpty(name)) {
      name = Convert.toStr(getListBo.getTerm());
    }

    String searchFields = Convert.toStr(getListBo.getSearchFields());

    LambdaQueryWrapper<BaseProduct> queryWrapper = new LambdaQueryWrapper<>();
    String finalName = name;
    queryWrapper
      .and(StringUtils.isNotEmpty(name), a -> a.like(StringUtils.isNotEmpty(finalName), BaseProduct::getProductCode, finalName)
        .or()
        .like(StringUtils.isNotEmpty(finalName), BaseProduct::getProductName, finalName)
        .or()
        .like(StringUtils.isNotEmpty(finalName), BaseProduct::getProductModel, finalName)
      ); // 关键词对编号和名称模糊查询

    try {
      List<String> fields = CollUtil.newArrayList("productId", "productCode", "productName", "productModel", "productSpec", "salePrice", "purchasePrice", "productBarCode"); // 查询默认字段
      // 自定义查询字段

      if ("*".equals(searchFields)) {
        fields.clear(); // 清空，查询所有字段
      } else if (StringUtils.isNotEmpty(searchFields)) {
        fields = CollUtil.newArrayList(StringUtils.split(searchFields, ",")); // 查询指定字段
      }
      // 自定义查询字段
      if (!fields.isEmpty()) {
        List<String> finalFields = fields;
        queryWrapper.select(BaseProduct.class, s -> finalFields.contains(s.getProperty()));
      }
      queryWrapper.orderByAsc(BaseProduct::getProductId); // 排序
      queryWrapper.last("limit " + take); // top N
      return this.baseMapper.selectMaps(queryWrapper);
    } catch (Exception error) {
      var msg = "异常错误信息：" + error;
      throw new ServiceException(msg);
    }
  }

  //#endregion

  //#region 编辑保存前事件

  /**
   * 编辑保存后事件
   *
   * @param editor
   */
  @Override
  public void afterEditor(EditorVo<BaseProductVo> editor) {
    editor.getMaster();

    super.afterEditor(editor);
  }
  //#endregion

  //#region 保存前事件

  /**
   * 保存前事件
   *
   * @param saveEditorBo 保存提交bo数据
   */
  @Override
  public void beforeSaveEditor(SaveEditorBo<BaseProductBo> saveEditorBo) {
    LambdaQueryWrapper<BaseProduct> productLambdaQueryWrapper = new LambdaQueryWrapper<>();
    productLambdaQueryWrapper.eq(BaseProduct::getProductCode, saveEditorBo.getData().getMaster().getProductCode());
    // 如果商品信息Id不为空
    if (ObjectUtil.isNotEmpty(saveEditorBo.getData().getMaster().getProductId())) {
      productLambdaQueryWrapper.eq(BaseProduct::getProductCode, saveEditorBo.getData().getMaster().getProductCode())
        .ne(BaseProduct::getProductId, saveEditorBo.getData().getMaster().getProductId());
    }
    long count = this.count(productLambdaQueryWrapper);
    if (count > 0) {
      throw new ServiceException("商品编码【" + saveEditorBo.getData().getMaster().getProductCode() + "】已存在不允许重复添加，无法添加！");
    }
    super.beforeSaveEditor(saveEditorBo);
  }
  //#endregion

  //#region 商品信息导入数据
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

      //#endregion

      // 判断是否开启 自动编码
      var sku_autoProductCode = sysConfigService.getConfigBool("sku_autoProductCode");

      //#region 验证数据正确性
      int i = 1;
      int successCount = 0;
      int updateCount = 0;
      // 商品集合，根据编号或者条形码，提前加载出商品信息
      var productCodes = dataList.stream().map(item -> item.get("productCode")).toList();
      var productModels = dataList.stream().map(item -> item.get("productModel")).toList();

      LambdaQueryWrapper<BaseProduct> productLambdaQueryWrapper = new LambdaQueryWrapper<>();
      productLambdaQueryWrapper
        .and(a -> a.in(BaseProduct::getProductCode, productCodes)
          .or()
          .in(BaseProduct::getProductModel, productModels)
        );
      List<BaseProduct> productList = this.baseMapper.selectList(productLambdaQueryWrapper);

      // 验证excel重复数据
      // 定义一个函数Function，该函数将元素对象映射到一个键的集合里
      Function<Map<String, Object>, List<Object>> compositeKey = person ->
        Collections.singletonList(person.get("productCode"));
      // 分组
      Map<List<Object>, List<Map<String, Object>>> groupingMap = dataList.stream().collect(Collectors.groupingBy(compositeKey, Collectors.toList()));
      final List<List<Map<String, Object>>> filterList = groupingMap.values().stream().filter(f -> f.size() > 1).toList(); // 大于1表示重复了
      String repeatMsg = "excel存在重复的商品编号" + filterList.stream().map(f -> Convert.toStr(f.get(0).get("productCode"))).collect(Collectors.joining(","));
      if (!filterList.isEmpty()) {
        sysImportService.writeEnd(repeatMsg);
        return;
      }

      for (var row : dataList) {
        i++;
        final String productCode = Convert.toStr(row.get("productCode"));
        final String productModel = Convert.toStr(row.get("productModel"));
        final String consignorName = Convert.toStr(row.get("consignorName"));

        BaseProduct dataInfo;
        if (ObjectUtil.isNotEmpty(productCode)) {
          // 如果商品编号存在，使用商品编号判断是否存在
          dataInfo = productList.stream().filter(item -> Objects.equals(item.getProductCode(), productCode)).findFirst().orElse(null);
        } else {
          // 如果商品编号不存在存在，使用商品条码和货主判断是否存在
          dataInfo = productList.stream().filter(item -> Objects.equals(item.getProductModel(), productModel) && Objects.equals(item.getConsignorName(), consignorName)).findFirst().orElse(null)
          ;
        }

        // 如果不存在，增长商品信息
        if (ObjectUtil.isNull(dataInfo)) {
          successCount++;
          dataInfo = new BaseProduct(); // 新建数据
          dataInfo.setAuditing(Convert.toLong(AuditEnum.AUDIT.getId())); // 导入状态默认为待审核

          // 打开自动编码
          if (sku_autoProductCode) {
            dataInfo.setProductCode(DBUtils.getCodeRegular(MenuEnum.MENU_1758));
//            row.put("productCode", DBUtils.getCodeRegular(MenuEnum.MENU_1758));
          }
          // 数据拷贝
          BeanUtil.copyProperties(row, dataInfo);
          sysImportService.writeMsgBlue("第{}行、{}正在导入新增...", i, dataInfo.getProductCode());
        } else {
          sysImportService.writeMsgBlue("第{}行、{}正在导入更新...", i, dataInfo.getProductCode());
          updateCount++;
          row.put("productId", dataInfo.getProductId());
          row.put("productCode", dataInfo.getProductCode());
          dataInfo.setUpdateBy(loginUser.getUserId());
          dataInfo.setUpdateByName(loginUser.getNickname());
          dataInfo.setUpdateTime(DateUtils.getNowDate());
        }

        if (!sku_autoProductCode) {
          sysImportService.isAssert(ObjectUtil.isEmpty(productCode), "第{}行" + "商品编号为空，不允许导入");
        }

        // 体积(判断体积是否为空)
        if (ObjectUtil.isEmpty(row.get("unitCube"))) {
          row.put("unitCube", B.muls(row.get("length"), row.get("height"), row.get("width")));
        }
        BeanUtil.copyProperties(row, dataInfo);
        dataInfo.setCreateBy(loginUser.getUserId());
        dataInfo.setCreateByName(loginUser.getNickname());
        dataInfo.setCreateTime(DateUtils.getNowDate());
        dataInfo.setTenantId(loginUser.getTenantId());

        // 扩展字段处理
        dataInfo.setExpandFields(sysImportService.getExpandFields(row));

        this.saveOrUpdate(dataInfo);
      }

      //#endregion

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

  //#region 批量审核

  /**
   * 批量审核
   *
   * @param ids 审核参数
   */
  @Override
  public R<Void> multiAuditing(List<Long> ids) {
    try {

      for (Long productId : ids) {
        BaseProduct product = this.getById(productId);
        if (ObjectUtil.isEmpty(product)) {
          throw new ServiceException("未获取到商品信息");
        }

//        if (product.getAuditing().equals(Convert.toLong(AuditEnum.AUDITED_SUCCESS.getId()))) {
//          throw new ServiceException("只有待审核的单据才可以进行审核操作！");
//        }

        //修改数据
        LambdaUpdateWrapper<BaseProduct> lambda = new UpdateWrapper<BaseProduct>().lambda();
        lambda.set(BaseProduct::getAuditing, AuditEnum.AUDITED_SUCCESS.getId())
          .set(BaseProduct::getAuditDate, DateUtils.getNowDate())
          .set(BaseProduct::getAuditor, LoginHelper.getNickname())
          .eq(BaseProduct::getProductId, productId);
        this.update(lambda);//提交

      }
      return R.ok();
    } catch (NoSuchMessageException e) {
      throw new ServiceException("错误" + e.getMessage());
    }
  }
  //#endregion

  //#region 反审

  /**
   * 反审
   *
   * @param ids
   * @return
   */
  @Override
  public R<Void> reAudit(Long[] ids) {
    try {
      Long[] _ids = Convert.toLongArray(ids);

      for (Long productId : _ids) {
        BaseProduct product = this.getById(productId);
        if (ObjectUtil.isEmpty(product)) {
          throw new ServiceException("未获取到商品信息");
        }

        if (!product.getAuditing().equals(Convert.toLong(AuditEnum.AUDITED_SUCCESS.getId()))) {
          throw new ServiceException("只有审核成功的单据才可以进行审核操作！");
        }

        //修改数据
        LambdaUpdateWrapper<BaseProduct> lambda = new UpdateWrapper<BaseProduct>().lambda();
        lambda.set(BaseProduct::getAuditing, AuditEnum.AUDIT.getId())
          .set(BaseProduct::getAuditDate, DateUtils.getNowDate())
          .set(BaseProduct::getAuditor, LoginHelper.getNickname())
          .eq(BaseProduct::getProductId, productId);
        this.update(lambda);//提交

      }
      return R.ok();
    } catch (NoSuchMessageException e) {
      throw new ServiceException("错误" + e.getMessage());
    }
  }

  //#endregion


  //#region 新增数据
  @Override
  public R<Map<String, Object>> add(BaseProductBo bo) {

    if (ObjectUtil.isNull(bo.getConsignorName())) {
      // 货主不存在时默认第一个货主
      boolean sku_noConsignorDefaultFirst = sysConfigService.getConfigBool("sku_noConsignorDefaultFirst");

      if (sku_noConsignorDefaultFirst) {
        LambdaQueryWrapper<BaseConsignor> lambda = new LambdaQueryWrapper<>();
        var conInfo = baseConsignorService.getOnly(lambda);
        if (ObjectUtil.isNotNull(conInfo)) {
          bo.setConsignorName(conInfo.getConsignorName());
        } else {
          Assert.isFalse(ObjectUtil.isNull(conInfo), "默认货主不存在，请设置货主");
        }
      } else {
        Assert.isFalse(ObjectUtil.isNull(bo.getConsignorName()), "货主名称不能为空");
      }
    }

    // 如果商品条码为空，默认商品编号
    if (ObjectUtil.isNull(bo.getProductModel())) {
      bo.setProductModel(bo.getProductCode());
    }
    Assert.isFalse(ObjectUtil.isNull(bo.getProductName()), "商品名称不能为空");


    // 验证货主
    BaseConsignor consignorInfo = baseConsignorService.getByName(bo.getConsignorName());
    Assert.isFalse(ObjectUtil.isNull(consignorInfo), "货主不存在！");

    bo.setConsignorId(consignorInfo.getConsignorId());
    bo.setConsignorCode(consignorInfo.getConsignorCode());


    // 先用名称查找
    BaseProvider providerInfo = baseProviderService.getByShortName(bo.getProviderShortName());
    // 如果名称没有找到，用编号查找
    if (ObjectUtil.isNull(providerInfo)) {
      LambdaQueryWrapper<BaseProvider> lambda = new LambdaQueryWrapper<>();
      lambda.eq(BaseProvider::getProviderCode, bo.getProviderCode());
      providerInfo = baseProviderService.getOnly(lambda);
    }
    if (ObjectUtil.isNull(providerInfo)) {
      // 供应商不存在时自动创建
      boolean sku_noProviderAutoCreate = sysConfigService.getConfigBool("sku_noProviderAutoCreate");

      // 供应商不存在时默认第一个供应商
      boolean sku_noProviderDefaultFirst = sysConfigService.getConfigBool("sku_noProviderDefaultFirst");

      //默认供应商
      boolean erp_provider_Id = sysConfigService.getConfigBool("erp_provider_Id");


      if (ObjectUtil.isNotNull(bo.getProviderShortName())) {

        providerInfo = baseProviderService.getByShortName(bo.getProviderShortName());
      }
      //创建新的供应商
      if (ObjectUtil.isNull(providerInfo) && sku_noProviderAutoCreate) {
        providerInfo = new BaseProvider();
        providerInfo.setProviderCode(bo.getProviderCode());
        providerInfo.setProviderShortName(bo.getProviderShortName());
        baseProviderService.save(providerInfo);
      }

      if (ObjectUtil.isNull(providerInfo) && erp_provider_Id) {
        LambdaQueryWrapper<BaseProvider> lambda = new LambdaQueryWrapper<>();
        lambda.eq(BaseProvider::getProviderId, bo.getProviderId());
        providerInfo = baseProviderService.getOnly(lambda);
      }

      if (ObjectUtil.isNull(providerInfo) && sku_noProviderDefaultFirst) {
        LambdaQueryWrapper<BaseProvider> lambda = new LambdaQueryWrapper<>();
        providerInfo = baseProviderService.getOnly(lambda);
      }

    }
    if (ObjectUtil.isNotNull(providerInfo)) {
      bo.setProviderId(providerInfo.getProviderId());
      bo.setProviderCode(providerInfo.getProviderCode());
      bo.setProviderShortName(providerInfo.getProviderShortName());
    }


    // 判断类别是否存在
    BaseProductType typeInfo = baseProductTypeService.getByName(bo.getTypeName());
    Map<String, Object> expandFields = Optional.of(bo).map(BaseProductBo::getExpandFields).orElse(new HashMap<>());
    Object categoryCode = expandFields.get("categoryCode");

    if (ObjectUtil.isNull(typeInfo) && ObjectUtil.isNotEmpty(bo.getTypeName())) {
      // 不存在类别将自动添加类别
      typeInfo = new BaseProductType();
      typeInfo.setTypeName(bo.getTypeName());
      if (ObjectUtil.isNotNull(categoryCode)) {
        typeInfo.setTypeCode(Convert.toStr(categoryCode));
      }
      typeInfo.setParentId(0L);
      typeInfo.setEnable(EnableEnum.ENABLE.getId());
      baseProductTypeService.save(typeInfo);
    }
    if (ObjectUtil.isNotNull(typeInfo)) {
      bo.setTypeId(typeInfo.getTypeId());
    }

    // 判断商品是否已存在
    BaseProduct dataInfo = this.getByCode(bo.getProductCode());
    if (ObjectUtil.isNull(dataInfo)) {
      dataInfo = new BaseProduct();
      BeanUtil.copyProperties(bo, dataInfo, new CopyOptions().setIgnoreProperties("productId"));
      dataInfo.setAuditing(Convert.toLong(AuditEnum.AUDIT.getId())); // 导入状态默认为待审核
    } else {
      BeanUtil.copyProperties(bo, dataInfo, new CopyOptions().setIgnoreProperties("productId"));
    }
    if (ObjectUtil.isNull(bo.getProductCode())) {
      // 生成商品编号
      bo.setProductCode(DBUtils.getCodeRegular(MenuEnum.MENU_1758));
    }

    // 如果商品中单位有值，大单位没值，将中单位写入大单位
    if (ObjectUtil.isNotNull(dataInfo.getMiddleUnit()) && ObjectUtil.isNull(dataInfo.getBigUnit())) {
      dataInfo.setBigUnit(dataInfo.getMiddleUnit());
      dataInfo.setUnitConvert(dataInfo.getMiddleUnitConvert());
      dataInfo.setBigBarcode(dataInfo.getMiddleBarcode());

      dataInfo.setMiddleUnit(null);
      dataInfo.setMiddleUnitConvert(null);
      dataInfo.setMiddleBarcode(null);
    }
    // 如果中单位条码和大单位条码相同，那么清除掉中单位相关设置
    if (ObjectUtil.isNotNull(dataInfo.getBigBarcode()) && B.isEqual(dataInfo.getBigBarcode(), dataInfo.getMiddleBarcode())) {
      dataInfo.setMiddleUnit(null);
      dataInfo.setMiddleUnitConvert(null);
    }
    this.save(dataInfo);

    Map<String, Object> result = new HashMap<>();
    result.put("productId", dataInfo.getProductId());
    result.put("productCode", dataInfo.getProductCode());
    result.put("productName", dataInfo.getProductName());

    return R.ok("商品信息保存成功", result);

  }
  //#endregion

  //#region getByCodeAndName
  @Override
  public BaseProduct getByCodeAndName(String productCode, String productName) {
    LambdaQueryWrapper<BaseProduct> productLambdaQueryWrapper = new LambdaQueryWrapper<>();
    productLambdaQueryWrapper.eq(BaseProduct::getProductCode, productCode)
      .eq(BaseProduct::getProductName, productName);

    return this.getOne(productLambdaQueryWrapper);
  }
  //#endregion
}

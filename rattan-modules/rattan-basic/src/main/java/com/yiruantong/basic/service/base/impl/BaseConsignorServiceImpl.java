package com.yiruantong.basic.service.base.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.base.BaseConsignor;
import com.yiruantong.basic.domain.base.BaseConsignorCheckInRecord;
import com.yiruantong.basic.domain.base.BaseConsignorSales;
import com.yiruantong.basic.domain.base.bo.BaseConsignorAddBo;
import com.yiruantong.basic.domain.base.bo.BaseConsignorBo;
import com.yiruantong.basic.domain.base.bo.GetListBo;
import com.yiruantong.basic.domain.base.vo.BaseConsignorVo;
import com.yiruantong.basic.domain.client.BaseClient;
import com.yiruantong.basic.domain.storage.BasePosition;
import com.yiruantong.basic.domain.storage.BaseStorage;
import com.yiruantong.basic.mapper.base.BaseConsignorMapper;
import com.yiruantong.basic.service.base.IBaseConsignorCheckInRecordService;
import com.yiruantong.basic.service.base.IBaseConsignorSalesService;
import com.yiruantong.basic.service.base.IBaseConsignorService;
import com.yiruantong.basic.service.client.IBaseClientService;
import com.yiruantong.basic.service.storage.IBasePositionService;
import com.yiruantong.basic.service.storage.IBaseStorageService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.base.PositionTypeEnum;
import com.yiruantong.common.core.enums.system.DataAuthNodeTypeEnum;
import com.yiruantong.common.core.enums.system.UserAuthModuleTypeEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.SpringUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.excel.core.ExcelResult;
import com.yiruantong.common.excel.listener.ImportCommonListener;
import com.yiruantong.common.excel.utils.ExcelUtil;
import com.yiruantong.common.mybatis.core.domain.bo.SaveDataBo;
import com.yiruantong.common.mybatis.core.domain.bo.SaveEditorBo;
import com.yiruantong.common.mybatis.core.domain.vo.EditorVo;
import com.yiruantong.common.mybatis.core.dto.QueryBo;
import com.yiruantong.common.mybatis.core.service.IDataAuthService;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.rabbitmq.service.IRabbitProducerService;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.tenant.helper.TenantHelper;
import com.yiruantong.system.domain.permission.SysRoleAuthData;
import com.yiruantong.system.service.core.ISysConfigService;
import com.yiruantong.system.service.dataHandler.ISysImportService;
import com.yiruantong.system.service.permission.ISysRoleAuthDataService;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 货主信息Service业务层处理
 *
 * @author YRT
 * @date 2023-08-31
 */
@RequiredArgsConstructor
@Service
public class BaseConsignorServiceImpl extends ServiceImplPlus<BaseConsignorMapper, BaseConsignor, BaseConsignorVo, BaseConsignorBo> implements IBaseConsignorService {
  private final DataSourceTransactionManager transactionManager;
  private final ISysImportService sysImportService;
  private final ISysConfigService sysConfigService;
  private final IRabbitProducerService rabbitProducerService;
  private final IDataAuthService dataAuthService;
  private final IBaseStorageService baseStorageService;
  private final IBaseClientService baseClientService;
  private final ISysRoleAuthDataService sysRoleAuthDataService;
  private final IBaseConsignorSalesService baseConsignorSalesService;
  private final IBaseConsignorCheckInRecordService baseConsignorCheckInRecordService;

  @Override
  public void afterEditor(EditorVo<BaseConsignorVo> editor) {
  }

  //#region getList 查询货主

  /**
   * getList 查询货主
   *
   * @param getListBo@return 返回查询结果
   */
  @Override
  public List<Map<String, Object>> getList(GetListBo getListBo) {
    Integer take = Optional.ofNullable(getListBo).map(m -> Convert.toInt(m.getTake())).orElse(200); // 查询top N，如果为空，默认200
    boolean isDesc = Optional.ofNullable(getListBo).map(GetListBo::isDesc).orElse(false); // 倒序方式
    String name = getListBo.getName();
    // amis下拉框搜索
    if (StringUtils.isEmpty(name)) {
      name = Convert.toStr(getListBo.getTerm());
    }

    String searchFields = Convert.toStr(getListBo.getSearchFields());
    LoginUser loginUser = LoginHelper.getLoginUser();
    if (ObjectUtil.isNull(loginUser)) return null;

    QueryWrapper<BaseConsignor> queryWrapper = new QueryWrapper<>();
    String finalName = name;
    queryWrapper.lambda().eq(BaseConsignor::getEnable, EnableEnum.ENABLE.getId()) // 是否可用
      .and(StringUtils.isNotEmpty(name), a -> a.like(StringUtils.isNotEmpty(finalName), BaseConsignor::getConsignorCode, finalName)
        .or()
        .like(StringUtils.isNotEmpty(finalName), BaseConsignor::getConsignorName, finalName)
      ); // 关键词对编号和名称模糊查询

    try {
      List<String> fields = CollUtil.newArrayList("consignorId", "consignorCode", "consignorName"); // 查询默认字段
      // 自定义查询字段
      if (StringUtils.isNotEmpty(searchFields)) {
        fields = CollUtil.newArrayList(StringUtils.split(searchFields, ",")); // 查询指定字段
      }
      if ("*".equals(searchFields)) {
        fields.clear(); // 清空，查询所有字段
      }

      // 自定义查询字段
      if (!fields.isEmpty()) {
        List<String> finalFields = fields;
        queryWrapper.select(BaseConsignor.class, s -> finalFields.contains(s.getProperty()));
      }
      // 数据权限
      if (!loginUser.isAdministrator()) {
        dataAuthService.getConsignorAuth(queryWrapper);
      }
      queryWrapper.lambda().orderByDesc(isDesc, BaseConsignor::getConsignorId).orderByAsc(!isDesc, BaseConsignor::getConsignorId); // 排序
      queryWrapper.last("limit " + take); // top N

      // return this.baseMapper.selectMaps(queryWrapper);

      var mapList = this.baseMapper.selectMaps(queryWrapper);
      for (var map : mapList) {
        String expandFields = Convert.toStr(map.get("expandFields"));
        if (StringUtils.isNotEmpty(expandFields)) {
          map.put("expandFields", JSONUtil.parseObj(expandFields));
        }
      }
      return mapList;
    } catch (Exception error) {
      var msg = "异常错误信息：" + error.getCause();
      throw new ServiceException(msg);
    }
  }
  //#endregion

  //#region resetUserPwd
  @Override
  public int resetUserPwd(Long userId, String password) {
    return baseMapper.update(null,
      new LambdaUpdateWrapper<BaseConsignor>()
        .set(BaseConsignor::getPassword, password)
        .eq(BaseConsignor::getConsignorId, userId));
  }
  //#endregion

  //#region getByName
  @Override
  public BaseConsignor getByName(String name) {
    LambdaQueryWrapper<BaseConsignor> consignorLambdaQueryWrapper = new LambdaQueryWrapper<>();
    consignorLambdaQueryWrapper.eq(BaseConsignor::getConsignorName, name);
    return this.getOnly(consignorLambdaQueryWrapper);
  }
  //#endregion

  //#region getByCode
  @Override
  public BaseConsignor getByCode(String code) {
    LambdaQueryWrapper<BaseConsignor> consignorLambdaQueryWrapper = new LambdaQueryWrapper<>();
    consignorLambdaQueryWrapper.eq(BaseConsignor::getConsignorCode, code);
    return this.getOnly(consignorLambdaQueryWrapper);
  }
  //#endregion

  //#region add
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Map<String, Object>> add(BaseConsignorAddBo bo) {
    try {
      BaseConsignor baseConsignor = this.getByName(bo.getConsignorName());

      LambdaQueryWrapper<BaseConsignor> consignorLambdaQueryWrapper = new LambdaQueryWrapper<>();
      consignorLambdaQueryWrapper.eq(BaseConsignor::getConsignorName, bo.getConsignorName());
      // 如果货主Id不为空
      if (ObjectUtil.isNotEmpty(bo.getConsignorId())) {
        consignorLambdaQueryWrapper.eq(BaseConsignor::getConsignorName, bo.getConsignorName())
          .ne(BaseConsignor::getConsignorId, bo.getConsignorId());
      }

      long count = this.count(consignorLambdaQueryWrapper);
      if (count > 0) {
        throw new ServiceException("货主名称【" + bo.getConsignorName() + "】已存在不允许重复添加，无法添加！");
      }


      if (ObjectUtil.isNotNull(baseConsignor)) {
        BeanUtil.copyProperties(bo, baseConsignor, new CopyOptions().setIgnoreProperties("consignorId"));
        this.updateById(baseConsignor);

        addConsignorSales(bo, baseConsignor);

        Map<String, Object> result = new HashMap<>();
        result.put("consignorId", baseConsignor.getConsignorId());
        result.put("consignorCode", baseConsignor.getConsignorCode());
        result.put("consignorName", baseConsignor.getConsignorName());

        return R.ok("货主更新成功", result);
      }

      baseConsignor = new BaseConsignor();
      BeanUtil.copyProperties(bo, baseConsignor, new CopyOptions().setIgnoreProperties("consignorId"));
      baseConsignor.setEnable(EnableEnum.ENABLE.getId());
      this.save(baseConsignor);
      addConsignorSales(bo, baseConsignor);
      bo.setConsignorId(baseConsignor.getConsignorId());


      // 创建同名仓库
      SaveEditorBo<BaseConsignorBo> saveEditorBo = new SaveEditorBo<>();
      SaveDataBo<BaseConsignorBo> consignorSaveDataBo = new SaveDataBo<>();
      BaseConsignorBo baseConsignorBo = new BaseConsignorBo();
      BeanUtil.copyProperties(bo, baseConsignorBo);
      consignorSaveDataBo.setMaster(baseConsignorBo);
      saveEditorBo.setData(consignorSaveDataBo);
      afterSaveEditor(saveEditorBo, null);

      Map<String, Object> result = new HashMap<>();
      result.put("consignorId", baseConsignor.getConsignorId());
      result.put("consignorCode", baseConsignor.getConsignorCode());
      result.put("consignorName", baseConsignor.getConsignorName());
      return R.ok("货主信息保存成功", result);
    } catch (Exception error) {
      return R.fail("货主信息保存失败，" + error.getMessage());
    }
  }

  private void addConsignorSales(BaseConsignorAddBo bo, BaseConsignor baseConsignor) {
    for (var item : bo.getDetailBoList()) {
      Long enterDetailId = Convert.toLong(item.getConsignorDetailId()); // 明细ID
      BaseConsignorSales baseConsignorSales = baseConsignorSalesService.getBaseMapper().selectById(enterDetailId); // 明细

      if (ObjectUtil.isNotEmpty(baseConsignorSales)) {
        BeanUtil.copyProperties(item, baseConsignorSales);

        baseConsignorSalesService.saveOrUpdate(baseConsignorSales);
      } else {
        var detailInfo = new BaseConsignorSales();
        BeanUtil.copyProperties(item, detailInfo);
        detailInfo.setConsignorId(baseConsignor.getConsignorId());
        baseConsignorSalesService.save(detailInfo);
      }
    }

    // 有删除的数据，就删除掉
    if (ObjectUtils.isNotEmpty(bo.getRemovedIds())) {
      baseConsignorSalesService.deleteByIds(bo.getRemovedIds());
    }
  }
  //#endregion

  //#region updateInfo
  @Override
  public R<Map<String, Object>> updateInfo(BaseConsignorBo bo) {
    try {
      LambdaQueryWrapper<BaseConsignor> consignorLambdaQueryWrapper = new LambdaQueryWrapper<>();
      consignorLambdaQueryWrapper.eq(BaseConsignor::getConsignorCode, bo.getConsignorCode());
      var baseConsignor = this.getOnly(consignorLambdaQueryWrapper);

      if (ObjectUtil.isNull(baseConsignor)) {
        return R.fail("货主信息不存在");
      }

      BeanUtil.copyProperties(bo, baseConsignor, new CopyOptions().setIgnoreProperties("consignorId"));
      this.saveOrUpdate(baseConsignor);

      Map<String, Object> result = new HashMap<>();
      result.put("consignorId", baseConsignor.getConsignorId());
      result.put("consignorCode", baseConsignor.getConsignorCode());
      result.put("consignorName", baseConsignor.getConsignorName());

      return R.ok("货主更新成功", result);

    } catch (Exception error) {
      return R.fail("货主信息保存失败，" + error.getMessage());
    }
  }
  //#endregion

  //#region 保存前事件

  /**
   * 保存前事件
   *
   * @param saveEditorBo 保存提交bo数据
   */
  @Override
  public void beforeSaveEditor(SaveEditorBo<BaseConsignorBo> saveEditorBo) {

    LambdaQueryWrapper<BaseConsignor> consignorLambdaQueryWrapper = new LambdaQueryWrapper<>();
    consignorLambdaQueryWrapper.eq(BaseConsignor::getConsignorName, saveEditorBo.getData().getMaster().getConsignorName());
    // 如果货主Id不为空
    if (ObjectUtil.isNotEmpty(saveEditorBo.getData().getMaster().getConsignorId())) {
      consignorLambdaQueryWrapper.eq(BaseConsignor::getConsignorName, saveEditorBo.getData().getMaster().getConsignorName())
        .ne(BaseConsignor::getConsignorId, saveEditorBo.getData().getMaster().getConsignorId());
    }

    long count = this.count(consignorLambdaQueryWrapper);
    if (count > 0) {
      throw new ServiceException("货主名称【" + saveEditorBo.getData().getMaster().getConsignorName() + "】已存在不允许重复添加，无法添加！");
    }
    super.beforeSaveEditor(saveEditorBo);
  }
  //#endregion

  //#region 保存后事件
  @Override
  public void afterSaveEditor(SaveEditorBo<BaseConsignorBo> saveEditorBo, EditorVo<BaseConsignorVo> editor) {
    // 自动创建同名的仓库
    boolean consignor_createStorage = sysConfigService.getConfigBool("consignor_createStorage");
    Long consignorId = saveEditorBo.getData().getMaster().getConsignorId();
    String consignorCode = saveEditorBo.getData().getMaster().getConsignorCode();
    String consignorName = saveEditorBo.getData().getMaster().getConsignorName();
    BaseStorage baseStorage = baseStorageService.getByName(consignorName);
    if (consignor_createStorage && ObjectUtil.isNull(baseStorage)) {
      baseStorage = new BaseStorage();
      baseStorage.setStorageName(consignorName);
      baseStorage.setStorageCode(DateUtil.format(DateUtil.date(), "yyyyMMdd"));
      baseStorage.setEnable(EnableEnum.ENABLE.getId().longValue());
      baseStorageService.save(baseStorage);

      IBasePositionService bean = SpringUtils.getBean(IBasePositionService.class);
      BasePosition basePosition = new BasePosition();
      basePosition.setPositionName(PositionTypeEnum.UNLOADING.getName()); // 下架理货位
      basePosition.setPositionType(PositionTypeEnum.UNLOADING.getId()); // 下架理货位
      basePosition.setStorageId(baseStorage.getStorageId());
      basePosition.setStorageName(baseStorage.getStorageName());
      basePosition.setIsLocked(EnableEnum.DISABLE.getId());
      basePosition.setEnable(EnableEnum.ENABLE.getId());
      basePosition.setIsMixProduct(EnableEnum.ENABLE.getId());
      bean.saveOrUpdate(basePosition);

      BasePosition basePosition2 = new BasePosition();
      basePosition2.setPositionName(PositionTypeEnum.RECEIVING.getName()); // 收货位
      basePosition2.setPositionType(PositionTypeEnum.RECEIVING.getId()); // 收货位
      basePosition2.setStorageId(baseStorage.getStorageId());
      basePosition2.setStorageName(baseStorage.getStorageName());
      basePosition2.setIsLocked(EnableEnum.DISABLE.getId());
      basePosition2.setEnable(EnableEnum.ENABLE.getId());
      basePosition2.setIsMixProduct(EnableEnum.ENABLE.getId());
      bean.saveOrUpdate(basePosition2);

      BasePosition basePosition3 = new BasePosition();
      basePosition3.setPositionName(PositionTypeEnum.IN_TRANSIT.getName()); // 在途货位
      basePosition3.setPositionType(PositionTypeEnum.VIRTUAL.getId()); // 虚拟货位
      basePosition3.setStorageId(baseStorage.getStorageId());
      basePosition3.setStorageName(baseStorage.getStorageName());
      basePosition3.setIsLocked(EnableEnum.DISABLE.getId());
      basePosition3.setEnable(EnableEnum.ENABLE.getId());
      basePosition3.setIsMixProduct(EnableEnum.ENABLE.getId());
      bean.saveOrUpdate(basePosition3);

      BasePosition basePosition4 = new BasePosition();
      basePosition4.setPositionName("P01"); // 常规货位
      basePosition4.setPositionType(PositionTypeEnum.NORMAL.getId()); // 常规货位
      basePosition4.setStorageId(baseStorage.getStorageId());
      basePosition4.setStorageName(baseStorage.getStorageName());
      basePosition4.setIsLocked(EnableEnum.DISABLE.getId());
      basePosition4.setEnable(EnableEnum.ENABLE.getId());
      basePosition4.setIsMixProduct(EnableEnum.ENABLE.getId());
      bean.saveOrUpdate(basePosition4);
    }

    // 自动创建同名客户
    boolean consignor_createClient = sysConfigService.getConfigBool("consignor_createClient");
    BaseClient baseClient = baseClientService.getByName(consignorName);
    if (consignor_createClient && ObjectUtil.isNull(baseClient)) {
      baseClient = new BaseClient();
      baseClient.setClientFullName(consignorName);
      baseClient.setClientShortName(consignorName);
      baseClient.setClientCode(DateUtil.format(DateUtil.date(), "yyyyMMdd"));
      baseClient.setEnable(EnableEnum.ENABLE.getId().longValue());
      baseClient.setConsignorId(consignorId);
      baseClient.setConsignorCode(consignorCode);
      baseClient.setConsignorName(consignorName);
      baseClientService.save(baseClient);
    }

    // 自动给当前用户设置上货主权限
    boolean consignor_createDataAuth = sysConfigService.getConfigBool("consignor_createDataAuth");
    if (consignor_createDataAuth) {
      LambdaQueryWrapper<SysRoleAuthData> roleAuthDataLambdaQueryWrapper = new LambdaQueryWrapper<>();
      roleAuthDataLambdaQueryWrapper
        .eq(SysRoleAuthData::getUserId, LoginHelper.getUserId())
        .eq(SysRoleAuthData::getModuleId, UserAuthModuleTypeEnum.CONSIGNOR.getId())
        .eq(SysRoleAuthData::getNodeId, consignorId);
      if (!sysRoleAuthDataService.exists(roleAuthDataLambdaQueryWrapper)) {
        var authDataInfo = new SysRoleAuthData();
        authDataInfo.setAuthValue("1");
        authDataInfo.setModuleId(UserAuthModuleTypeEnum.CONSIGNOR.getId());
        authDataInfo.setNodeId(consignorId);
        authDataInfo.setNodeName(consignorName);
        authDataInfo.setNodeType(DataAuthNodeTypeEnum.USER.getName());
        authDataInfo.setUserId(LoginHelper.getUserId());
        sysRoleAuthDataService.save(authDataInfo);
      }
    }
  }
  //#endregion

  //#region 货主信息导入数据
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

      //#region 验证数据正确性
      int i = 1;
      int successCount = 0;
      int updateCount = 0;
      for (var row : dataList) {
        i++;
        final String consignorName = Convert.toStr(row.get("consignorName"));

        LambdaQueryWrapper<BaseConsignor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BaseConsignor::getConsignorName, consignorName); // 货主名称
        var dataInfo = this.baseMapper.selectOne(queryWrapper);
        if (ObjectUtil.isNotEmpty(dataInfo)) {

          var exp = dataInfo.getExpandFields();
          if (ObjectUtil.isNull(exp)) {
            exp = new HashMap<>();
          }
          exp.put("originalSalesName", row.get("originalSalesName"));
          dataInfo.setExpandFields(exp);

          BeanUtil.copyProperties(row, dataInfo);
          sysImportService.writeMsgBlue("第{}行、{}正在导入更新...", i, dataInfo.getConsignorCode());
          updateCount++;
        } else {
          dataInfo = new BaseConsignor(); // 新建数据
          // 数据拷贝
          BeanUtil.copyProperties(row, dataInfo);
          if (ObjectUtil.isNull(dataInfo.getConsignorCode())) {
            String orderCode = DBUtils.getCodeRegular(MenuEnum.MENU_1508, loginUser.getTenantId());
            dataInfo.setConsignorCode(orderCode);
          }
          sysImportService.writeMsgBlue("第{}行、{}正在导入新增...", i, dataInfo.getConsignorCode());
          successCount++;
        }
        if (B.isEqual(row.get("enable"), EnableEnum.ENABLE.getName()) || ObjectUtil.isNull(row.get("enable"))) {
          dataInfo.setEnable(EnableEnum.ENABLE.getId());
        } else {
          dataInfo.setEnable(EnableEnum.DISABLE.getId());
        }
        dataInfo.setTenantId(loginUser.getTenantId());
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


  //#region getConsignorData
  @Transactional(rollbackFor = Exception.class)
  @Override
  public R<Map<String, Object>> getConsignorData(List<QueryBo> queryBoList) {
    Long consignorId = queryBoList.stream().filter(f -> StringUtils.equals(f.getColumn(), "consignorId")).map(m -> Convert.toLong(m.getValues())).findFirst().orElse(null);
    Assert.isFalse(consignorId == null, "门店ID不能为空！");


    LambdaQueryWrapper<BaseConsignor> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(BaseConsignor::getConsignorId, consignorId); // 货主名称
    var dataInfo = this.baseMapper.selectOne(queryWrapper);

    LambdaQueryWrapper<BaseConsignorSales> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
    detailLambdaQueryWrapper.eq(BaseConsignorSales::getConsignorId, consignorId); // 货主名称
    var consignorDetailList = baseConsignorSalesService.list(detailLambdaQueryWrapper);

    Map<String, Object> result = new HashMap<>();
    result.put("dataList", dataInfo);
    result.put("consignorDetailList", consignorDetailList);

    return R.ok(result);
  }
  //#endregion


  //#region 添加门店打卡记录
  @Transactional(rollbackFor = Exception.class)
  @Override
  public R<Map<String, Object>> addCheckInRecord(BaseConsignorCheckInRecord bo) {


    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String today = sdf1.format(new Date());
    BaseConsignorCheckInRecord baseConsignorCheckInRecord = new BaseConsignorCheckInRecord();

    baseConsignorCheckInRecord.setConsignorId(bo.getConsignorId());
    baseConsignorCheckInRecord.setConsignorCode(bo.getConsignorCode());
    baseConsignorCheckInRecord.setConsignorName(bo.getConsignorName());
    baseConsignorCheckInRecord.setSalesName(bo.getSalesName());
    baseConsignorCheckInRecord.setVisitDate(Convert.toDate(today));
    baseConsignorCheckInRecord.setLat(bo.getLat());
    baseConsignorCheckInRecord.setLng(bo.getLng());
    baseConsignorCheckInRecord.setPunchInAddress(bo.getPunchInAddress());
    baseConsignorCheckInRecord.setImages(bo.getImages());
    baseConsignorCheckInRecordService.save(baseConsignorCheckInRecord);

    return R.ok("打卡成功");
  }
  //#endregion


  //#region 获取门店打开记录
  @Transactional(rollbackFor = Exception.class)
  @Override
  public R<Map<String, Object>> getCheckInRecord(String consignorName) {
    Assert.isFalse(consignorName == null, "门店不能为空！");


    LambdaQueryWrapper<BaseConsignorCheckInRecord> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(BaseConsignorCheckInRecord::getConsignorName, consignorName)
      .orderByDesc(BaseConsignorCheckInRecord::getVisitDate); // 货主名称
    var dataInfo = baseConsignorCheckInRecordService.list(queryWrapper);


    Map<String, Object> result = new HashMap<>();
    result.put("dataList", dataInfo);

    return R.ok(result);
  }
  //#endregion


}

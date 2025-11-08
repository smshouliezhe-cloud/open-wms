package com.yiruantong.basic.service.storage.impl;

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
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.storage.BasePosition;
import com.yiruantong.basic.domain.storage.BaseStorage;
import com.yiruantong.basic.domain.storage.BaseStorageArea;
import com.yiruantong.basic.domain.storage.BaseStorageShelve;
import com.yiruantong.basic.domain.storage.bo.*;
import com.yiruantong.basic.domain.storage.vo.BasePositionVo;
import com.yiruantong.basic.mapper.storage.BasePositionMapper;
import com.yiruantong.basic.service.storage.IBasePositionService;
import com.yiruantong.basic.service.storage.IBaseStorageAreaService;
import com.yiruantong.basic.service.storage.IBaseStorageService;
import com.yiruantong.basic.service.storage.IBaseStorageShelveService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.enums.base.PositionTypeEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.excel.core.ExcelResult;
import com.yiruantong.common.excel.listener.ImportCommonListener;
import com.yiruantong.common.excel.utils.ExcelUtil;
import com.yiruantong.common.json.utils.JsonUtils;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.tenant.helper.TenantHelper;
import com.yiruantong.system.service.core.ISysConfigService;
import com.yiruantong.system.service.dataHandler.ISysImportService;
import org.springframework.context.NoSuchMessageException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 货位管理Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-18
 */
@RequiredArgsConstructor
@Service
public class BasePositionServiceImpl extends ServiceImplPlus<BasePositionMapper, BasePosition, BasePositionVo, BasePositionBo> implements IBasePositionService {
  private final IBaseStorageService baseStorageService;
  private final DataSourceTransactionManager transactionManager;
  private final ISysImportService sysImportService;
  private final IBaseStorageAreaService baseStorageAreaService;
  private final ISysConfigService sysConfigService;
  private final IBaseStorageShelveService baseStorageShelveService;

  //#region 通用 - 查询货位列表

  /**
   * 通用 - 查询货位列表
   *
   * @param positionSearchBo 查询条件
   * @return 返回查询结果
   */
  @Override
  public List<Map<String, Object>> getList(BasePositionSearchBo positionSearchBo) {
    if (ObjectUtil.isNull(positionSearchBo)) {
      return new ArrayList<>();
    }

    Integer take = Optional.of(positionSearchBo).map(BasePositionSearchBo::getTake).orElse(100); // 查询top N，如果为空，默认100
    String name = positionSearchBo.getName();
    // amis下拉框搜索
    if (StringUtils.isEmpty(name)) {
      name = Convert.toStr(positionSearchBo.getTerm());
    }
    Long storageId = positionSearchBo.getStorageId();
    String searchFields = positionSearchBo.getSearchFields();
    byte positionType = positionSearchBo.getPositionType();
    String areaCode = positionSearchBo.getAreaCode();
    PositionTypeEnum positionTypeEnum = Optional.ofNullable(PositionTypeEnum.matchingEnumById(positionType)).orElse(PositionTypeEnum.NORMAL);
    Assert.isFalse(ObjectUtil.isEmpty(storageId), "获取货位仓库不能为空！");

    LambdaQueryWrapper<BasePosition> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper
      .eq(BasePosition::getStorageId, storageId)
      .eq(StringUtils.isNotEmpty(areaCode), BasePosition::getAreaCode, areaCode);
    if (StringUtils.contains(name, ",")) {
      queryWrapper.in(BasePosition::getPositionName, (Object) StringUtils.split(name, ","));
    } else {
      queryWrapper.like(StringUtils.isNotEmpty(name), BasePosition::getPositionName, name); // 关键词对编号和名称模糊查询
    }

    setPositionTypeQuery(storageId, positionTypeEnum, queryWrapper);

    try {
      List<String> fields = CollUtil.newArrayList("positionName"); // 查询默认字段
      // 自定义查询字段
      if ("*".equals(searchFields)) {
        fields.clear(); // 清空，查询所有字段
      } else if (StringUtils.isNotEmpty(searchFields)) {
        fields = CollUtil.newArrayList(StringUtils.split(searchFields, ",")); // 查询指定字段
      }

      // 自定义查询字段
      if (!fields.isEmpty()) {
        List<String> finalFields = fields;
        queryWrapper.select(BasePosition.class, s -> finalFields.contains(s.getProperty()));
      }
      queryWrapper.orderByAsc(BasePosition::getPositionName); // 排序
      queryWrapper.last("limit " + take); // top N

      return this.baseMapper.selectMaps(queryWrapper);
    } catch (Exception error) {
      var msg = "异常错误信息：" + error.getCause();
      throw new ServiceException(msg);
    }
  }

  /**
   * 设置货位查询类型
   *
   * @param storageId        仓库ID
   * @param positionTypeEnum 货位类型
   * @param queryWrapper     查询wrapper
   */
  private void setPositionTypeQuery(Long storageId, PositionTypeEnum positionTypeEnum, LambdaQueryWrapper<BasePosition> queryWrapper) {
    if (positionTypeEnum == PositionTypeEnum.UNLOADING) {
      // 下架货位
      queryWrapper.eq(BasePosition::getPositionType, PositionTypeEnum.UNLOADING.getId());
    } else if (positionTypeEnum == PositionTypeEnum.RECEIVING) {
      // 收货货位
      queryWrapper.eq(BasePosition::getPositionType, PositionTypeEnum.RECEIVING.getId());
    } else {
      // positionTypeEnum=NORMAL为上架货位， 根据仓库设置的货位类型
      var storageInfo = baseStorageService.selectById(storageId);
      com.baomidou.mybatisplus.core.toolkit.Assert.isFalse(ObjectUtil.isNull(storageInfo), "获取有效库仓库信息不存在！");
      String positionType = storageInfo.getPositionType();
      if (ObjectUtil.isEmpty(positionType)) {
        // 全局设定的：允许参与分拣货位类型
        positionType = sysConfigService.selectConfigByKey("sorting_positionType");
      }
      Object[] positionTypes = Convert.toIntArray(Optional.ofNullable(positionType).orElse("0").split(","));

      queryWrapper.in(BasePosition::getPositionType, positionTypes);
    }
  }
  //#endregion

  //#region 通用 - 查询货位

  /**
   * 查询货位
   *
   * @param positionSearchBo
   * @return 返回查询列表数据
   */
  @Override
  public List<Map<String, Object>> getPositionList(BasePositionSearchBo positionSearchBo) {
    if (ObjectUtil.isNull(positionSearchBo)) {
      return new ArrayList<>();
    }
    Integer take = Optional.of(positionSearchBo).map(BasePositionSearchBo::getTake).orElse(200); // 查询top N，如果为空，默认200
    Long storageId = positionSearchBo.getStorageId();
    String areaCode = positionSearchBo.getAreaCode();
    Byte positionType = positionSearchBo.getPositionType();
    Assert.isFalse(B.isLessOrEqual(storageId), "仓库ID不能为空");

    String[] positionTypes = new String[0];
    if (ObjectUtil.isNotNull(positionSearchBo.getPositionTypes())) {
      positionTypes = positionSearchBo.getPositionTypes().split(",");
    }
    String name = positionSearchBo.getName();
    String searchFields = positionSearchBo.getSearchFields();
    PositionTypeEnum positionTypeEnum = Optional.ofNullable(PositionTypeEnum.matchingEnumById(positionType)).orElse(PositionTypeEnum.NORMAL);

    LambdaQueryWrapper<BasePosition> positionQueryWrapper = new LambdaQueryWrapper<>();
    positionQueryWrapper.eq(BasePosition::getStorageId, storageId) // 查询仓库
      .eq(StringUtils.isNotEmpty(areaCode), BasePosition::getAreaCode, areaCode) // 查询库区
      .like(StringUtils.isNotEmpty(name), BasePosition::getPositionName, name) // 关键词模糊查询
      .eq(BasePosition::getIsLocked, EnableEnum.DISABLE.getId())
      .eq(BasePosition::getEnable, EnableEnum.ENABLE.getId());

    if (ObjectUtil.isNotEmpty(positionType) && positionType > 0) {
      // 指定货位类型
      positionQueryWrapper.eq(BasePosition::getPositionType, positionType);
    } else if (B.isGreater(positionTypes.length)) {
      // 指定货位类型
      positionQueryWrapper.in(BasePosition::getPositionType, positionTypes);
    } else {
      setPositionTypeQuery(storageId, positionTypeEnum, positionQueryWrapper);
    }

    try {
      List<String> fields = Arrays.asList("positionName", "positionType", "enable", "isLocked", "isMixProduct"); // 查询默认字段
      // 自定义查询字段
      if (StringUtils.isNotEmpty(searchFields)) {
        fields = List.of(StringUtils.split(searchFields, ",")); // 查询指定字段
      }
      if ("*".equals(searchFields)) {
        fields = new ArrayList<>(); // 清空，查询所有字段
      }

      // 自定义查询字段
      if (!fields.isEmpty()) {
        List<String> finalFields = fields;
        positionQueryWrapper.select(BasePosition.class, s -> finalFields.contains(s.getProperty()));
      }
      positionQueryWrapper.orderByAsc(BasePosition::getPositionId); // 排序
      positionQueryWrapper.last("limit " + take); // top N

      return this.baseMapper.selectMaps(positionQueryWrapper);
    } catch (Exception error) {
      var msg = "异常错误信息：" + error.getMessage();
      throw new ServiceException(msg);
    }
  }
  //#endregion

  //#region 根据编号获取货位是否可用

  /**
   * 根据编号获取货位是否可用
   *
   * @param positionName 货位名称
   * @return 返回boolean，是否可用
   */
  @Override
  public boolean existEnableByName(Long storageId, String positionName) {
    return this.existEnableByName(storageId, positionName, null);
  }

  @Override
  public boolean existEnableByName(Long storageId, String positionName, List<PositionTypeEnum> positionTypeEnumList) {
    LambdaQueryWrapper<BasePosition> positionLambdaQueryWrapper = new LambdaQueryWrapper<>();
    positionLambdaQueryWrapper.eq(BasePosition::getPositionName, positionName)
      .eq(BasePosition::getStorageId, storageId)
      .eq(BasePosition::getEnable, EnableEnum.ENABLE.getId());
    if (CollUtil.isNotEmpty(positionTypeEnumList)) {
      positionLambdaQueryWrapper.in(BasePosition::getPositionType, positionTypeEnumList.stream().map(PositionTypeEnum::getId).toList());
    }
    return this.exists(positionLambdaQueryWrapper);
  }
  //#endregion

  //#region 根据货位名称获取货位信息

  /**
   * 根据货位名称获取货位信息
   *
   * @param positionName 货位名称
   * @return 返回货位信息
   */
  @Override
  public BasePosition getByName(Long storageId, String positionName) {
    LambdaQueryWrapper<BasePosition> positionLambdaQueryWrapper = new LambdaQueryWrapper<>();
    positionLambdaQueryWrapper.eq(BasePosition::getPositionName, positionName)
      .eq(BasePosition::getStorageId, storageId)
      .eq(BasePosition::getEnable, EnableEnum.ENABLE.getId());
    return this.getOnly(positionLambdaQueryWrapper);
  }

  @Override
  public List<BasePosition> getOffPositionList(Long storageId) {
    LambdaQueryWrapper<BasePosition> positionLambdaQueryWrapper = new LambdaQueryWrapper<>();
    positionLambdaQueryWrapper.eq(BasePosition::getStorageId, storageId)
      .eq(BasePosition::getPositionType, PositionTypeEnum.UNLOADING.getId());

    return this.baseMapper.selectList(positionLambdaQueryWrapper);
  }
  //#endregion

  //#region 货位信息导入数据
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
      final List<Map<String, Object>> dataList = excelResult.getList();

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
        final String positionName = Convert.toStr(row.get("positionName"));
        final String storageName = Convert.toStr(row.get("storageName"));

        BasePosition dataInfo;
        LambdaQueryWrapper<BasePosition> positionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        positionLambdaQueryWrapper.eq(BasePosition::getPositionName, positionName)
          .eq(BasePosition::getStorageName, storageName);
        dataInfo = this.getOne(positionLambdaQueryWrapper);

        // 如果不存在，增长货位
        if (ObjectUtil.isNull(dataInfo)) {
          successCount++;
          dataInfo = new BasePosition(); // 新建数据

          // 数据拷贝
          BeanUtil.copyProperties(row, dataInfo);
          sysImportService.writeMsgBlue("第{}行、{}正在导入新增...", i, dataInfo.getPositionName());
        } else {
          sysImportService.writeMsgBlue("第{}行、{}正在导入更新...", i, dataInfo.getPositionName());
          updateCount++;
        }
        dataInfo.setTenantId(loginUser.getTenantId());
        BeanUtil.copyProperties(row, dataInfo);
        this.saveOrUpdate(dataInfo);
      }
      //单字段分组
      Map<String, List<Map<String, Object>>> groupList = dataList.stream().collect(Collectors.groupingBy(item -> Convert.toStr(item.get("areaCode"))));
      for (var item : groupList.entrySet()) {
        final String areaCode = item.getKey();

        final List<Map<String, Object>> valueList = item.getValue();
        final Map<String, Object> stringObjectMap = valueList.get(0);
        final String storageName = stringObjectMap.get("storageName").toString();

        LambdaQueryWrapper<BaseStorageArea> storageAreaLambdaQueryWrapper = new LambdaQueryWrapper<>();
        storageAreaLambdaQueryWrapper.eq(BaseStorageArea::getAreaCode, areaCode)
          .eq(BaseStorageArea::getStorageName, storageName);
//        List<BaseStorageAreaVo> areaExist = baseStorageAreaService.selectList(storageAreaLambdaQueryWrapper);
        var areaExist = baseStorageAreaService.getOne(storageAreaLambdaQueryWrapper);
        // 库存不存在，新增库区
        if (ObjectUtil.isNull(areaExist)) {
          var newInfo = new BaseStorageArea();
          newInfo.setAreaCode(areaCode);
          newInfo.setChannelNum(Convert.toLong(0));
          newInfo.setStorageId(Convert.toLong(stringObjectMap.get("storageId")));
          newInfo.setStorageName(Convert.toStr(stringObjectMap.get("storageName")));
          newInfo.setShelveMode("立体货架");
          newInfo.setPositionRegular("{库区}-{通道}-{货架}-{层}");
          baseStorageAreaService.saveOrUpdate(newInfo);
        }
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

  //#region 获取库区信息

  /**
   * 获取库区信息
   *
   * @param map 查询条件
   * @return 返回货位信息
   */
  @Override
  public List<Map<String, Object>> selectAreaCodeList(Map<String, Object> map) {
    Long storageId = Convert.toLong(map.get("storageId"));
    String areaCode = Convert.toStr(map.get("areaCode"));
    MPJLambdaWrapper<BasePosition> positionMPJLambdaWrapper = new MPJLambdaWrapper<>();
    positionMPJLambdaWrapper
      .select(BasePosition::getStorageId, BasePosition::getAreaCode, BasePosition::getChannelCode)
      .groupBy("storage_id,area_code,channel_code")
      .eq(BasePosition::getStorageId, storageId)
      .eq(BasePosition::getAreaCode, areaCode);
    return this.selectJoinMaps(positionMPJLambdaWrapper);
  }

  /**
   * 获取货架信息
   *
   * @param map
   * @return
   */
  @Override
  public List<Map<String, Object>> selectShelveCodeList(Map<String, Object> map) {
    Long storageId = Convert.toLong(map.get("storageId"));
    String areaCode = Convert.toStr(map.get("areaCode"));
    String channelCode = Convert.toStr(map.get("channelCode"));
    MPJLambdaWrapper<BasePosition> positionMPJLambdaWrapper = new MPJLambdaWrapper<>();
    positionMPJLambdaWrapper
      .select(BasePosition::getStorageId, BasePosition::getAreaCode, BasePosition::getChannelCode, BasePosition::getLineCode, BasePosition::getShelveCode)
      .groupBy("storage_id,area_code,channel_code,line_code,shelve_code")
      .eq(BasePosition::getStorageId, storageId)
      .eq(BasePosition::getAreaCode, areaCode)
      .eq(BasePosition::getChannelCode, channelCode);
    return this.selectJoinMaps(positionMPJLambdaWrapper);
  }
  //#endregion

  @Override
  public R<List<String>> getShelveCodes(Map<String, Object> map) {
    String storageName = Convert.toStr(map.get("storageName"));
    Object[] channelCodes = Convert.toStrArray(map.get("channelCodes"));
    Object[] areas = Convert.toStrArray(map.get("areas"));
    LambdaQueryWrapper<BasePosition> baseStorageAreaLqw = new LambdaQueryWrapper<>();
    baseStorageAreaLqw.select(BasePosition::getShelveCode)
      .eq(BasePosition::getStorageName, storageName)
      .isNotNull(BasePosition::getShelveCode)
      .in(BasePosition::getChannelCode, channelCodes)
      .in(BasePosition::getAreaCode, areas);
    List<BasePosition> storageAreas = this.getBaseMapper().selectList(baseStorageAreaLqw);
    storageAreas = storageAreas.stream().distinct().toList();

    return R.ok(storageAreas.stream().map(BasePosition::getShelveCode).toList());
  }

  @Override
  public R<List<String>> getChannelCodes(Map<String, Object> map) {
    String storageName = Convert.toStr(map.get("storageName"));
    Object[] areas = Convert.toStrArray(map.get("areas"));
    LambdaQueryWrapper<BasePosition> baseStorageAreaLqw = new LambdaQueryWrapper<>();
    baseStorageAreaLqw.select(BasePosition::getChannelCode)
      .eq(BasePosition::getStorageName, storageName)
      .isNotNull(BasePosition::getChannelCode)
      .in(BasePosition::getAreaCode, areas);
    List<BasePosition> storageAreas = this.getBaseMapper().selectList(baseStorageAreaLqw);
    storageAreas = storageAreas.stream().distinct().toList();

    return R.ok(storageAreas.stream().map(BasePosition::getChannelCode).toList());
  }

  //#region 保存库区
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> saveArea(PositionConfigSaveBo positionConfigSaveBo) {
    try {
      AreaDataBo objAreaData = positionConfigSaveBo.getAreaData();
      var channelDataList = positionConfigSaveBo.getChannelDataList();
      var shelveDataList = positionConfigSaveBo.getShelveDataList(); // 货架信息列表

      if (ObjectUtil.isNotNull(objAreaData)) {
        var storageId = objAreaData.getStorageId();
        var action = objAreaData.getAction();
        var storageName = objAreaData.getStorageName();
        var areaCode = objAreaData.getAreaCode();
        var channelRegular = objAreaData.getChannelRegular();
        var positionRegular = objAreaData.getPositionRegular();
        var shelvesRegular = objAreaData.getShelvesRegular();
        var rowRegular = objAreaData.getRowRegular();
        var columnRegular = objAreaData.getColumnRegular();
        var pickMode = objAreaData.getPickMode();
        var shelveMode = objAreaData.getShelveMode();
        var positionType = objAreaData.getPositionType();
        var channelNum = objAreaData.getChannelNum();
        var maxCapacity = objAreaData.getMaxCapacity();
        var rowNum = objAreaData.getRowNum();
        var columnNum = objAreaData.getColumnNum();

        Long shelveNumA1;
        Long shelveNumA2;
        Long shelveNumB1;
        Long shelveNumB2;
        if (shelveMode.equals("地堆")) {
          shelveNumA1 = 0L;
          shelveNumA2 = 0L;
          shelveNumB1 = 0L;
          shelveNumB2 = 0L;
        } else {
          shelveNumA1 = objAreaData.getShelveNumA1();
          shelveNumA2 = objAreaData.getShelveNumA2();
          shelveNumB1 = objAreaData.getShelveNumB1();
          shelveNumB2 = objAreaData.getShelveNumB2();
        }

        if (ObjectUtil.isNull(positionRegular)) {
          throw new ServiceException("货位编码规则不存在，无法生成货位！");
        }
        var thermocline = objAreaData.getThermocLine();
        var maxWeight = objAreaData.getMaxWeight();
        var maxBeatNumber = objAreaData.getMaxBeatNumber();
        var inventoryRate = objAreaData.getInventoryRate();
        //#region 库区校验
        BaseStorageArea areaInfo = baseStorageAreaService.getStorageAreaInfo(storageId, areaCode);
        if (ObjectUtil.isNull(areaInfo)) {
          areaInfo = new BaseStorageArea();
          areaInfo.setCreateBy(LoginHelper.getUserId());
          areaInfo.setCreateByName(LoginHelper.getNickname());
          areaInfo.setCreateTime(new Date());
        } else {
          //区域是否已经生成过库区
          if (StringUtils.equals(action, "add")) {
            throw new ServiceException("库区编号已经使用了，不允许重复创建，请选择修改模式！");
          }
          areaInfo.setUpdateTime(new Date());
          areaInfo.setUpdateBy(LoginHelper.getUserId());
          areaInfo.setUpdateByName(LoginHelper.getNickname());
        }
        areaInfo.setMaxCapacity(maxCapacity);
        areaInfo.setChannelNum(channelNum);
        areaInfo.setColumnNum(columnNum);
        areaInfo.setRowNum(rowNum);
        areaInfo.setPickMode(pickMode);
        areaInfo.setShelveMode(shelveMode);
        areaInfo.setShelveNumA1(shelveNumA1);
        areaInfo.setShelveNumA2(shelveNumA2);
        areaInfo.setShelveNumB1(shelveNumB1);
        areaInfo.setShelveNumB2(shelveNumB2);
        areaInfo.setStorageId(storageId);
        areaInfo.setAreaCode(areaCode);
        areaInfo.setPositionType(positionType);
        areaInfo.setStorageName(storageName);
        areaInfo.setPositionRegular(positionRegular);
        areaInfo.setChannelRegular(channelRegular);
        areaInfo.setShelvesRegular(shelvesRegular);
        areaInfo.setRowRegular(rowRegular);
        areaInfo.setColumnRegular(columnRegular);
        areaInfo.setJsonData(JsonUtils.toJsonString(shelveDataList)); // 存放自定义货架信息
        areaInfo.setChannelDataList(JsonUtils.toJsonString(channelDataList)); // 通道货架数

        areaInfo.setThermocLine(thermocline);
        areaInfo.setMaxWeight(maxWeight);
        areaInfo.setMaxBeatNumber(maxBeatNumber);
        areaInfo.setInventoryRate(inventoryRate);
        baseStorageAreaService.saveOrUpdate(areaInfo);
        //#endregion

        // 获取所有限制存在的库区货位
        LambdaQueryWrapper<BasePosition> positionQueryWrapper = new LambdaQueryWrapper<>();
        positionQueryWrapper
          .select(BasePosition::getPositionId, BasePosition::getPositionName, BasePosition::getEnable, BasePosition::getIsMixProduct, BasePosition::getIsFreeze, BasePosition::getIsLocked, BasePosition::getMaxCapacity)
          .eq(BasePosition::getStorageId, storageId) // 查询仓库
          .eq(BasePosition::getAreaCode, areaCode); // 查询库区
        var positionList = this.baseMapper.selectList(positionQueryWrapper); // 历史已存在货位
        this.saveShelve(shelveDataList); //保存自定义货架信息

        List<BasePosition> allPositionList = new ArrayList<>(); // 全部新货位
        //通道
        for (int channelIndex = 1; channelIndex <= channelNum; channelIndex++) {
          var channelCode = this.channelCodeCreator(channelIndex, channelRegular); //(channelIndex < 10 ? "0" : "") + channelIndex;
          if (shelveMode.equals("地堆")) {
            //#region 货架编码
            //let shelveCode = "01";
            var shelveCode = this.shelveCodeCreator(1L, shelvesRegular);
            var shelveInfo = this.findShelve(shelveDataList, storageId, areaCode, channelCode, shelveCode);

            long _rowNum = rowNum;
            var _columnNum = columnNum;
            if (ObjectUtil.isNotNull(shelveInfo)) {
              _rowNum = shelveInfo.getRowNum();
              _columnNum = shelveInfo.getColumnNum();
            }
            //行数据
            for (int rowIndex = 1; rowIndex <= _rowNum; rowIndex++) {
              var rowCode = this.rowCodeCreator((long) rowIndex, rowRegular); //(rowIndex < 10 ? "0" : "") + rowIndex;
              List<BasePosition> waitSaveList = new ArrayList<>(); // 待保存货位集合
              //列数据
              for (int columnIndex = 1; columnIndex <= _columnNum; columnIndex++) {
                //#region 货位编码
                var columnCode = this.columnCodeCreator((long) columnIndex, columnRegular); //(columnIndex < 10 ? "0" : "") + columnIndex;
                this.columnCodeCreator((long) columnIndex, columnRegular); //(columnIndex < 10 ? "0" : "") + columnIndex;
                var positionTmpl = positionRegular; // "{库区}-{通道}{货架}{层}{列}";
                if (shelveInfo != null) {
                  //自定义编码规则
                  var _positionRegular = "";
                  var _rowNo = (rowIndex < 10 ? "0" : "") + rowIndex;
                  var _colNo = (columnIndex < 10 ? "0" : "") + columnIndex;
                  if (ObjectUtil.isNotNull(shelveInfo.getColumnConfigs())) {
                    // 列级别配置
                    for (ColumnConfigBo item : shelveInfo.getColumnConfigs()) {
                      if (ObjectUtil.equal(item.getColNo(), _colNo)) {
                        _positionRegular = item.getPositionRegular();
                      }
                    }
                  }
                  if (ObjectUtil.isNull(_positionRegular) && ObjectUtil.isNotNull(shelveInfo.getRowRegular())) {
                    // 层级别配置
                    for (RowConfigsBo item : shelveInfo.getRowConfigs()) {
                      if (ObjectUtil.equal(item.getRowNo(), _rowNo)) {
                        _positionRegular = item.getPositionRegular();
                      }
                    }
                  }

                  if (ObjectUtil.isNull(_positionRegular) && ObjectUtil.isNotNull(shelveInfo.getPositionRegular())) {
                    // 通道级别配置
                    positionTmpl = shelveInfo.getPositionRegular();
                  }
                }
                positionTmpl = positionTmpl.replace("{库区}", areaCode);
                positionTmpl = positionTmpl.replace("{通道}", channelCode);
                positionTmpl = positionTmpl.replace("{货架}", shelveCode);
                positionTmpl = positionTmpl.replace("{层}", rowCode);
                positionTmpl = positionTmpl.replace("{列}", columnCode);
                //#endregion

                //#region 创建货位
                String finalPositionTmpl = positionTmpl;
                var posInfo = positionList.stream().filter(item -> Objects.equals(item.getPositionName(), finalPositionTmpl)).findFirst().orElse(null);
                if (ObjectUtil.isNull(posInfo)) {
                  posInfo = new BasePosition();
                  posInfo.setCreateTime(new Date());
                  posInfo.setCreateBy(LoginHelper.getUserId());
                  posInfo.setCreateByName(LoginHelper.getNickname());
                  posInfo.setEnable(EnableEnum.ENABLE.getId());
                  posInfo.setIsMixProduct(EnableEnum.ENABLE.getId());
                  posInfo.setIsLocked(EnableEnum.DISABLE.getId());
                } else {
                  posInfo.setUpdateTime(new Date());
                  posInfo.setUpdateBy(LoginHelper.getUserId());
                  posInfo.setUpdateByName(LoginHelper.getNickname());
                }
                posInfo.setIsFreeze(EnableEnum.DISABLE.getId());
                posInfo.setOrderNum(0L);
                posInfo.setParentId(0L);
                posInfo.setPositionName(positionTmpl);
                posInfo.setPositionType(Convert.toByte(positionType)); //常规货位
                posInfo.setStorageId(storageId);
                posInfo.setStorageName(storageName);
                posInfo.setMaxCapacity(maxCapacity);
                posInfo.setThermocLine(areaInfo.getThermocLine());

                posInfo.setChannelCode(channelCode);
                posInfo.setColumnCode(columnCode);
                posInfo.setShelveCode(shelveCode);
                posInfo.setRowCode(rowCode);
                posInfo.setAreaCode(areaCode);
                posInfo.setLineCode("A面");
                posInfo.setShelveMode(shelveMode);

                // 检测货位是否存在了，如果存在保留存在的enable属性值
                BasePosition finalPosInfo = posInfo;
                var existPos = positionList.stream().filter(item -> Objects.equals(item.getPositionName(), finalPosInfo.getPositionName())).findFirst().orElse(null);
                if (ObjectUtil.isNotNull(existPos)) {
                  posInfo.setEnable(existPos.getEnable());
                  posInfo.setIsFreeze(existPos.getIsFreeze());
                  posInfo.setIsLocked(existPos.getIsLocked());
                  posInfo.setIsMixProduct(existPos.getIsMixProduct());
                  posInfo.setMaxCapacity(existPos.getMaxCapacity());
                }
                waitSaveList.add(posInfo);
                allPositionList.add(posInfo);
                if (B.isEqual(waitSaveList.size() % 20, 0)) {
                  this.saveOrUpdateBatch(waitSaveList);
                  waitSaveList.clear();
                }
                //#endregion
              }
              // 批量保存
              if (ObjectUtil.isNotNull(waitSaveList.size())) {
                this.saveOrUpdateBatch(waitSaveList);
                waitSaveList.clear();
              }
            }
            //#endregion
          } else {
            var channelInfo = this.findChannelInfo(areaInfo, channelCode); // 自定义货架数
            if (ObjectUtil.isNotNull(channelInfo)) {
              shelveNumA1 = channelInfo.getShelveNumA1();
              shelveNumA2 = channelInfo.getShelveNumA2();
            }
            //#region A面货架
            for (var ShelveAIndex = shelveNumA1; ShelveAIndex <= shelveNumA2; ShelveAIndex++) {
              var shelveCode = this.shelveCodeCreator(ShelveAIndex, shelvesRegular); //(ShelveAIndex < 10 ? "0" : "") + ShelveAIndex;
              this.shelveCodeCreator(ShelveAIndex, shelvesRegular); //(ShelveAIndex < 10 ? "0" : "") + ShelveAIndex;
              var shelveInfo = this.findShelve(shelveDataList, storageId, areaCode, channelCode, shelveCode);
              long _rowNum = rowNum;
              var _columnNum = columnNum;
              if (shelveInfo != null) {
                _rowNum = shelveInfo.getRowNum();
                _columnNum = shelveInfo.getColumnNum();
              }
              //行数据
              for (int rowIndex = 1; rowIndex <= _rowNum; rowIndex++) {
                var rowCode = this.rowCodeCreator((long) rowIndex, rowRegular);

                List<BasePosition> waitSaveList = new ArrayList<>(); // 待保存货位集合

                //列数据
                for (long columnIndex = 1L; columnIndex <= _columnNum; columnIndex++) {
                  //#region 货位编码
                  long colNo = columnIndex;
                  if (Objects.equals(pickMode, "AB面奇偶型")) {
                    if (ShelveAIndex == 1) colNo = columnIndex * 2 - 1;
                    if (ShelveAIndex == 2) colNo = columnIndex * 2;
                  }
                  var columnCode = this.columnCodeCreator(colNo, columnRegular); //(columnIndex < 10 ? "0" : "") + columnIndex;
                  this.columnCodeCreator(colNo, columnRegular); //(columnIndex < 10 ? "0" : "") + columnIndex;
                  var positionTmpl = positionRegular; // "{库区}-{通道}{货架}{层}{列}";
                  if (ObjectUtil.isNotNull(shelveInfo)) {
                    //自定义编码规则
                    var _positionRegular = "";
                    var _rowNo = (rowIndex < 10 ? "0" : "") + rowIndex;
                    var _colNo = (columnIndex < 10 ? "0" : "") + columnIndex;
                    if (ObjectUtil.isNotNull(shelveInfo.getColumnConfigs())) {
                      // 列级别配置
                      for (ColumnConfigBo item : shelveInfo.getColumnConfigs()) {
                        if (ObjectUtil.equal(item.getColNo(), _colNo)) {
                          _positionRegular = item.getPositionRegular();
                        }
                      }
                    }
                    if (ObjectUtil.isNull(_positionRegular) && ObjectUtil.isNotNull(shelveInfo.getRowConfigs())) {
                      // 层级别配置
                      for (RowConfigsBo item : shelveInfo.getRowConfigs()) {
                        if (ObjectUtil.equal(item.getRowNo(), _rowNo)) {
                          _positionRegular = item.getPositionRegular();
                        }
                      }
                    }

                    if (ObjectUtil.isNull(_positionRegular) && ObjectUtil.isNotNull(shelveInfo.getPositionRegular())) {
                      // 通道级别配置
                      positionTmpl = shelveInfo.getPositionRegular();
                    }
                  }
                  positionTmpl = positionTmpl.replace("{库区}", areaCode);
                  positionTmpl = positionTmpl.replace("{通道}", channelCode);
                  positionTmpl = positionTmpl.replace("{货架}", shelveCode);
                  positionTmpl = positionTmpl.replace("{层}", rowCode);
                  positionTmpl = positionTmpl.replace("{列}", columnCode);
                  //#endregion

                  //#region 创建货位
                  String finalPositionTmpl = positionTmpl;
                  var posInfo = positionList.stream().filter(item -> Objects.equals(item.getPositionName(), finalPositionTmpl)).findFirst().orElse(null);

                  if (ObjectUtil.isNull(posInfo)) {
                    posInfo = new BasePosition();
                    posInfo.setCreateTime(new Date());
                    posInfo.setCreateBy(LoginHelper.getUserId());
                    posInfo.setCreateByName(LoginHelper.getNickname());
                    posInfo.setEnable(EnableEnum.ENABLE.getId());
                    posInfo.setIsMixProduct(EnableEnum.ENABLE.getId());
                    posInfo.setIsLocked(EnableEnum.DISABLE.getId());
                  } else {
                    posInfo.setUpdateTime(new Date());
                    posInfo.setUpdateBy(LoginHelper.getUserId());
                    posInfo.setUpdateByName(LoginHelper.getNickname());
                  }
                  posInfo.setIsFreeze(EnableEnum.DISABLE.getId());
                  posInfo.setOrderNum(0L);
                  posInfo.setParentId(0L);
                  posInfo.setPositionName(positionTmpl);
                  posInfo.setPositionType(Convert.toByte(positionType)); //常规货位
                  posInfo.setStorageId(storageId);
                  posInfo.setStorageName(storageName);
                  posInfo.setMaxCapacity(maxCapacity);
                  posInfo.setThermocLine(areaInfo.getThermocLine());

                  posInfo.setChannelCode(channelCode);
                  posInfo.setColumnCode(columnCode);
                  posInfo.setShelveCode(shelveCode);
                  posInfo.setRowCode(rowCode);
                  posInfo.setAreaCode(areaCode);
//                  posInfo.setLineCode("A面");
//                  posInfo.lineCode = Number(shelveCode) % 2 == = 1 ? "A面" : "B面";

                  posInfo.setShelveMode(shelveMode);

                  // 检测货位是否存在了，如果存在保留存在的enable属性值
                  BasePosition finalPosInfo = posInfo;
                  var existPos = positionList.stream().filter(item -> Objects.equals(item.getPositionName(), finalPosInfo.getPositionName())).findFirst().orElse(null);

                  if (ObjectUtil.isNotNull(existPos)) {
                    posInfo.setEnable(existPos.getEnable());
                    posInfo.setIsFreeze(existPos.getIsFreeze());
                    posInfo.setIsLocked(existPos.getIsLocked());
                    posInfo.setIsMixProduct(existPos.getIsMixProduct());
                    posInfo.setMaxCapacity(existPos.getMaxCapacity());
                  }
                  waitSaveList.add(posInfo);
                  allPositionList.add(posInfo);
                  //#endregion
                }
                // 批量保存
                if (ObjectUtil.isNotNull(waitSaveList.size())) {
                  this.saveOrUpdateBatch(waitSaveList);
                  waitSaveList.clear();
                }
              }
            }
            //#endregion

            if (Objects.equals(pickMode, "U型")) {
              if (ObjectUtil.isNotNull(channelInfo)) {
                shelveNumB1 = channelInfo.getShelveNumB1();
                shelveNumB2 = channelInfo.getShelveNumB2();
              }
              //#region B面货架
              for (var shelveBIndex = shelveNumB1; shelveBIndex <= shelveNumB2; shelveBIndex++) {
                var shelveCode = this.shelveCodeCreator(shelveBIndex, shelvesRegular);
                var shelveInfo = this.findShelve(shelveDataList, storageId, areaCode, channelCode, shelveCode);
                long _rowNum = rowNum;
                var _columnNum = columnNum;
                if (ObjectUtil.isNotNull(shelveInfo)) {
                  _rowNum = shelveInfo.getRowNum();
                  _columnNum = shelveInfo.getColumnNum();
                }
                //行数据
                for (int rowIndex = 1; rowIndex <= _rowNum; rowIndex++) {
                  var rowCode = this.rowCodeCreator((long) rowIndex, rowRegular); //(rowIndex < 10 ? "0" : "") + rowIndex;
                  this.rowCodeCreator((long) rowIndex, rowRegular); //(rowIndex < 10 ? "0" : "") + rowIndex;
                  List<BasePosition> waitSaveList = new ArrayList<>();

                  //列数据
                  for (Long columnIndex = 1L; columnIndex <= _columnNum; columnIndex++) {
                    //#region 货位编码
                    var columnCode = this.columnCodeCreator(columnIndex, columnRegular); //(columnIndex < 10 ? "0" : "") + columnIndex;
                    this.columnCodeCreator(columnIndex, columnRegular); //(columnIndex < 10 ? "0" : "") + columnIndex;
                    var positionTmpl = positionRegular; // "{库区}-{通道}{货架}{层}{列}";
                    if (shelveInfo != null) {
                      //自定义编码规则
                      var _positionRegular = "";
                      var _rowNo = (rowIndex < 10 ? "0" : "") + rowIndex;
                      var _colNo = (columnIndex < 10 ? "0" : "") + columnIndex;
                      if (ObjectUtil.isNotNull(shelveInfo.getColumnConfigs())) {
                        // 列级别配置
                        for (ColumnConfigBo item : shelveInfo.getColumnConfigs()) {
                          if (ObjectUtil.equal(item.getColNo(), _colNo)) {
                            _positionRegular = item.getPositionRegular();
                          }
                        }
                      }
                      if (ObjectUtil.isNull(_positionRegular) && shelveInfo.getRowConfigs() != null) {
                        // 层级别配置
                        for (RowConfigsBo item : shelveInfo.getRowConfigs()) {
                          if (ObjectUtil.equal(item.getRowNo(), _rowNo)) {
                            _positionRegular = item.getPositionRegular();
                          }
                        }
                      }

                      if (ObjectUtil.isNull(_positionRegular) && ObjectUtil.isNotNull(shelveInfo.getPositionRegular())) {
                        // 通道级别配置
                        positionTmpl = shelveInfo.getPositionRegular();
                      }
                    }
                    positionTmpl = positionTmpl.replace("{库区}", areaCode);
                    positionTmpl = positionTmpl.replace("{通道}", channelCode);
                    positionTmpl = positionTmpl.replace("{货架}", shelveCode);
                    positionTmpl = positionTmpl.replace("{层}", rowCode);
                    positionTmpl = positionTmpl.replace("{列}", columnCode);
                    //#endregion

                    //#region 创建货位
                    String finalPositionTmpl = positionTmpl;
                    var posInfo = positionList.stream().filter(item -> Objects.equals(item.getPositionName(), finalPositionTmpl)).findFirst().orElse(null);

                    if (ObjectUtil.isNull(posInfo)) {
                      posInfo = new BasePosition();
                      posInfo.setCreateTime(new Date());
                      posInfo.setCreateBy(LoginHelper.getUserId());
                      posInfo.setCreateByName(LoginHelper.getNickname());
                      posInfo.setEnable(EnableEnum.ENABLE.getId());
                      posInfo.setIsMixProduct(EnableEnum.ENABLE.getId());
                      posInfo.setIsLocked(EnableEnum.DISABLE.getId());
                    } else {
                      posInfo.setUpdateTime(new Date());
                      posInfo.setUpdateBy(LoginHelper.getUserId());
                      posInfo.setUpdateByName(LoginHelper.getNickname());
                    }
                    posInfo.setIsFreeze(EnableEnum.DISABLE.getId());
                    posInfo.setOrderNum(0L);
                    posInfo.setParentId(0L);
                    posInfo.setPositionName(positionTmpl);
                    posInfo.setPositionType(Convert.toByte(positionType)); //常规货位
                    posInfo.setStorageId(storageId);
                    posInfo.setStorageName(storageName);
                    posInfo.setMaxCapacity(maxCapacity);
                    posInfo.setThermocLine(areaInfo.getThermocLine());

                    posInfo.setChannelCode(channelCode);
                    posInfo.setColumnCode(columnCode);
                    posInfo.setShelveCode(shelveCode);
                    posInfo.setRowCode(rowCode);
                    posInfo.setAreaCode(areaCode);
                    posInfo.setLineCode("A面");
                    posInfo.setShelveMode(shelveMode);

                    // 检测货位是否存在了，如果存在保留存在的enable属性值
                    BasePosition finalPosInfo = posInfo;
                    var existPos = positionList.stream().filter(item -> Objects.equals(item.getPositionName(), finalPosInfo.getPositionName())).findFirst().orElse(null);
                    if (ObjectUtil.isNotNull(existPos)) {
                      posInfo.setEnable(existPos.getEnable());
                      posInfo.setIsFreeze(existPos.getIsFreeze());
                      posInfo.setIsLocked(existPos.getIsLocked());
                      posInfo.setIsMixProduct(existPos.getIsMixProduct());
                      posInfo.setMaxCapacity(existPos.getMaxCapacity());
                    }

                    waitSaveList.add(posInfo);
                    allPositionList.add(posInfo);
                    //#endregion
                  }
                  // 批量保存
                  if (ObjectUtil.isNotNull(waitSaveList.size())) {
                    this.saveOrUpdateBatch(waitSaveList);
                    waitSaveList.clear();
                  }
                }
              }
              //#endregion
            }
          }
        }

        // 删除不存在的货位
        var notExists = positionList.stream().filter(f -> allPositionList.stream().noneMatch(n -> StringUtils.equals(f.getPositionName(), n.getPositionName()))).toList();
        if (!notExists.isEmpty()) {
          this.removeBatchByIds(notExists);
        }
      }

      return R.ok("保存库区成功！");
    } catch (NoSuchMessageException e) {
      throw new ServiceException("错误" + e.getMessage());
    }
  }
//#endregion

  //#region 查找货架信息
  private ShelveDataBo findShelve(ArrayList<ShelveDataBo> shelveDataList, Long storageId, String areaCode, String channelCode, String shelveCode) {
    for (var shelveInfo : shelveDataList) {
      if (
        Objects.equals(shelveInfo.getStorageId(), storageId) &&
          Objects.equals(shelveInfo.getAreaCode(), areaCode) &&
          Objects.equals(shelveInfo.getChannelCode(), channelCode) &&
          Objects.equals(shelveInfo.getShelveCode(), shelveCode)
      ) {
        return shelveInfo;
      }
    }

    return null;
  }
  //#endregion

  //#region 查找通道货架数
  private ChannelDataBo findChannelInfo(BaseStorageArea areaInfo, String channelCode) {
    String channelDataListStr = areaInfo.getChannelDataList(); // 通道货架数;
    List<ChannelDataBo> channelDataList = new ArrayList<>();
    if (ObjectUtil.isNotNull(channelDataListStr)) {
      channelDataList = JsonUtils.toList(channelDataListStr, ChannelDataBo.class);
    }
    for (var channelInfo : channelDataList) {
      if (ObjectUtil.equal(channelInfo.getStorageId(), areaInfo.getStorageId()) && ObjectUtil.equal(channelInfo.getAreaCode(), areaInfo.getAreaCode()) && ObjectUtil.equal(channelInfo.getChannelCode(), channelCode)) {
        return channelInfo;
      }
    }

    return null;
  }
  //#endregion

  //#region 保存货架信息
  private void saveShelve(ArrayList<ShelveDataBo> shelveDataList) {
    for (var shelveInfo : shelveDataList) {

      LambdaQueryWrapper<BaseStorageShelve> baseStorageArea = new LambdaQueryWrapper<>();
      baseStorageArea.eq(BaseStorageShelve::getStorageId, shelveInfo.getStorageId())
        .eq(BaseStorageShelve::getAreaCode, shelveInfo.getAreaCode())
        .eq(BaseStorageShelve::getChannelCode, shelveInfo.getChannelCode())
        .eq(BaseStorageShelve::getShelveCode, shelveInfo.getShelveCode());

      var storageShelveInfo = baseStorageShelveService.getOne(baseStorageArea);

      if (ObjectUtil.isNull(storageShelveInfo)) {
        storageShelveInfo = new BaseStorageShelve();
      }
      storageShelveInfo.setAreaCode(shelveInfo.getAreaCode());
      storageShelveInfo.setChannelCode(shelveInfo.getChannelCode());
      storageShelveInfo.setRowNum(shelveInfo.getRowNum());
      storageShelveInfo.setColumnNum(shelveInfo.getColumnNum());
      storageShelveInfo.setStorageId(shelveInfo.getStorageId());
      storageShelveInfo.setStorageName(shelveInfo.getStorageName());
      storageShelveInfo.setShelveCode(shelveInfo.getShelveCode());
      storageShelveInfo.setPositionRegular(shelveInfo.getPositionRegular());

      baseStorageShelveService.saveOrUpdate(storageShelveInfo);
    }
  }
  //#endregion

  //#region 编码规则
  //通道编码规则
  private String channelCodeCreator(Integer numIndex, String channelRegular) {
    if (ObjectUtil.isNull(channelRegular)) {
      channelRegular = "{数字}{数字}";
    }

    return this.codeCreator(BigDecimal.valueOf(numIndex), channelRegular);
  }

  //货架编码规则
  private String shelveCodeCreator(Long numIndex, String shelvesRegular) {
    if (ObjectUtil.isNull(shelvesRegular)) {
      shelvesRegular = "{数字}{数字}";
    }
    return this.codeCreator(BigDecimal.valueOf(numIndex), shelvesRegular);
  }

  //行编码规则
  private String rowCodeCreator(Long numIndex, String rowRegular) {
    if (ObjectUtil.isNull(rowRegular)) {
      rowRegular = "{数字}{数字}";
    }
    return this.codeCreator(BigDecimal.valueOf(numIndex), rowRegular);
  }

  //货架编码规则
  private String columnCodeCreator(Long numIndex, String columnRegular) {
    if (ObjectUtil.isNull(columnRegular)) {
      columnRegular = "{数字}{数字}";
    }
    return this.codeCreator(BigDecimal.valueOf(numIndex), columnRegular);
  }

  //通用编码规则
  private String codeCreator(BigDecimal numIndex, String regular) {
    var letterList = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z".split(",");
    if (ObjectUtil.isNull(regular)) {
      regular = "{数字}{数字}";
    }

    List<String> codeList = new ArrayList<>();
    Pattern regHelper = Pattern.compile("数字|字母", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
    Matcher matcher = regHelper.matcher(regular);

    while (matcher.find()) {
      codeList.add(matcher.group());
    }
    List<Object> codeValue = new ArrayList<>();
    for (var i = codeList.size() - 1; i >= 0; i--) {
      String item = codeList.get(i);
      if (item.equals("数字")) {
        if (B.isLess(numIndex, 10)) {
          codeValue.add(numIndex);
          numIndex = BigDecimal.valueOf(0);
        } else {
          var lastNum = Convert.toByte(numIndex) % 10;
          codeValue.add(lastNum);
//          numIndex = Math.floor((1.0 * numIndex) / 10);
          numIndex = B.floor(B.div(Optional.ofNullable(numIndex), 10));
          ;
        }
      } else if (item.equals("字母")) {
        if (B.isLess(numIndex, 27)) {
          codeValue.add(letterList[Convert.toByte(B.sub(numIndex, 1))]);
          numIndex = BigDecimal.valueOf(0);
        } else {
          var lastNum = Convert.toByte(numIndex) % 27;

          codeValue.add(lastNum);
          numIndex = B.floor(B.div(Optional.ofNullable(numIndex), 27));
        }
      }
    }

    for (int i = 0; i < codeList.size(); i++) {
      String val = codeValue.get(codeList.size() - 1 - i).toString();
      String rep = "\\{" + codeList.get(i) + "\\}";
      regular = regular.replaceFirst(rep, val);
    }

    return regular;
  }

  //#endregion

  //#region lockPosition 锁定货位
  @Override
  public R<Void> lockPosition(Map<String, Object> map) {
    var storageId = Convert.toLong(map.get("storageId"));
    var storageArea = Convert.toStr(map.get("storageArea"));
    var channelCode = Convert.toStr(map.get("channelCode"));
    var shelveCode = Convert.toStr(map.get("shelveCode"));
    var colNo = Convert.toStr(map.get("colNo"));
    var rowNo = Convert.toStr(map.get("rowNo"));

    // 修改源单数据
    LambdaUpdateWrapper<BasePosition> updateWrapper = new UpdateWrapper<BasePosition>().lambda();
    updateWrapper
      .set(BasePosition::getIsLocked, EnableEnum.ENABLE.getId())
      .eq(BasePosition::getStorageId, storageId)
      .eq(BasePosition::getChannelCode, channelCode);
    if (ObjectUtil.isNotEmpty(storageArea)) {
      updateWrapper.eq(BasePosition::getAreaCode, storageArea);
    }
    if (ObjectUtil.isNotEmpty(shelveCode)) {
      updateWrapper.eq(BasePosition::getShelveCode, shelveCode);
    }
    if (ObjectUtil.isNotEmpty(colNo)) {
      updateWrapper.eq(BasePosition::getColumnCode, colNo);
    }
    if (ObjectUtil.isNotEmpty(rowNo)) {
      updateWrapper.eq(BasePosition::getRowCode, rowNo);
    }

    this.update(updateWrapper);
    return R.ok("库存已经锁定！");

  }
  //#endregion

  //#region unlockPosition 解锁货位
  @Override
  public R<Void> unlockPosition(Map<String, Object> map) {
    var storageId = Convert.toLong(map.get("storageId"));
    var storageArea = Convert.toStr(map.get("storageArea"));
    var channelCode = Convert.toStr(map.get("channelCode"));
    var shelveCode = Convert.toStr(map.get("shelveCode"));
    var colNo = Convert.toStr(map.get("colNo"));
    var rowNo = Convert.toStr(map.get("rowNo"));

    // 修改源单数据
    LambdaUpdateWrapper<BasePosition> updateWrapper = new UpdateWrapper<BasePosition>().lambda();
    updateWrapper
      .set(BasePosition::getIsLocked, EnableEnum.DISABLE.getId())
      .eq(BasePosition::getStorageId, storageId)
      .eq(BasePosition::getChannelCode, channelCode);
    if (ObjectUtil.isNotEmpty(storageArea)) {
      updateWrapper.eq(BasePosition::getAreaCode, storageArea);
    }
    if (ObjectUtil.isNotEmpty(shelveCode)) {
      updateWrapper.eq(BasePosition::getShelveCode, shelveCode);
    }
    if (ObjectUtil.isNotEmpty(colNo)) {
      updateWrapper.eq(BasePosition::getColumnCode, colNo);
    }
    if (ObjectUtil.isNotEmpty(rowNo)) {
      updateWrapper.eq(BasePosition::getRowCode, rowNo);
    }

    this.update(updateWrapper);
    return R.ok("库存已经解锁！");
  }
  //#endregion

  //#region isMixProductPosition 是否混放
  @Override
  public R<Void> isMixProductPosition(Map<String, Object> map) {
    var storageId = Convert.toLong(map.get("storageId"));
    var storageArea = Convert.toStr(map.get("storageArea"));
    var channelCode = Convert.toStr(map.get("channelCode"));
    var shelveCode = Convert.toStr(map.get("shelveCode"));
    var colNo = Convert.toStr(map.get("colNo"));
    var rowNo = Convert.toStr(map.get("rowNo"));
    var isMixProduct = Convert.toStr(map.get("isMixProduct"));


    // 修改源单数据
    LambdaUpdateWrapper<BasePosition> updateWrapper = new UpdateWrapper<BasePosition>().lambda();
    updateWrapper
      .set(BasePosition::getIsMixProduct, isMixProduct)
      .eq(BasePosition::getStorageId, storageId)
      .eq(BasePosition::getChannelCode, channelCode);
    if (ObjectUtil.isNotEmpty(storageArea)) {
      updateWrapper.eq(BasePosition::getAreaCode, storageArea);
    }
    if (ObjectUtil.isNotEmpty(shelveCode)) {
      updateWrapper.eq(BasePosition::getShelveCode, shelveCode);
    }
    if (ObjectUtil.isNotEmpty(colNo)) {
      updateWrapper.eq(BasePosition::getColumnCode, colNo);
    }
    if (ObjectUtil.isNotEmpty(rowNo)) {
      updateWrapper.eq(BasePosition::getRowCode, rowNo);
    }

    this.update(updateWrapper);
    return R.ok("设置成功！");
  }
  //#endregion

  //#region enablePosition 是否可用
  @Override
  public R<Void> enablePosition(Map<String, Object> map) {
    var storageId = Convert.toLong(map.get("storageId"));
    var storageArea = Convert.toStr(map.get("storageArea"));
    var channelCode = Convert.toStr(map.get("channelCode"));
    var shelveCode = Convert.toStr(map.get("shelveCode"));
    var colNo = Convert.toStr(map.get("colNo"));
    var rowNo = Convert.toStr(map.get("rowNo"));
    var enable = Convert.toStr(map.get("enable"));


    // 修改源单数据
    LambdaUpdateWrapper<BasePosition> updateWrapper = new UpdateWrapper<BasePosition>().lambda();
    updateWrapper
      .set(BasePosition::getEnable, enable)
      .eq(BasePosition::getStorageId, storageId)
      .eq(BasePosition::getChannelCode, channelCode);
    if (ObjectUtil.isNotEmpty(storageArea)) {
      updateWrapper.eq(BasePosition::getAreaCode, storageArea);
    }
    if (ObjectUtil.isNotEmpty(shelveCode)) {
      updateWrapper.eq(BasePosition::getShelveCode, shelveCode);
    }
    if (ObjectUtil.isNotEmpty(colNo)) {
      updateWrapper.eq(BasePosition::getColumnCode, colNo);
    }
    if (ObjectUtil.isNotEmpty(rowNo)) {
      updateWrapper.eq(BasePosition::getRowCode, rowNo);
    }

    this.update(updateWrapper);
    return R.ok("设置成功！");
  }
  //#endregion

  //#region submitminCapacity 最低库存
  @Override
  public R<Void> submitminCapacity(Map<String, Object> map) {
    var storageId = Convert.toLong(map.get("storageId"));
    var storageArea = Convert.toStr(map.get("storageArea"));
    var channelCode = Convert.toStr(map.get("channelCode"));
    var shelveCode = Convert.toStr(map.get("shelveCode"));
    var colNo = Convert.toStr(map.get("colNo"));
    var rowNo = Convert.toStr(map.get("rowNo"));
    var minCapacity = Convert.toStr(map.get("minCapacity"));


    // 修改源单数据
    LambdaUpdateWrapper<BasePosition> updateWrapper = new UpdateWrapper<BasePosition>().lambda();
    updateWrapper
      .set(BasePosition::getMinCapacity, minCapacity)
      .eq(BasePosition::getStorageId, storageId)
      .eq(BasePosition::getChannelCode, channelCode);
    if (ObjectUtil.isNotEmpty(storageArea)) {
      updateWrapper.eq(BasePosition::getAreaCode, storageArea);
    }
    if (ObjectUtil.isNotEmpty(shelveCode)) {
      updateWrapper.eq(BasePosition::getShelveCode, shelveCode);
    }
    if (ObjectUtil.isNotEmpty(colNo)) {
      updateWrapper.eq(BasePosition::getColumnCode, colNo);
    }
    if (ObjectUtil.isNotEmpty(rowNo)) {
      updateWrapper.eq(BasePosition::getRowCode, rowNo);
    }

    this.update(updateWrapper);
    return R.ok("设置成功！");
  }

  //#endregion

  //#region loadPositionData 根据仓库和货位获得货位数据
  @Override
  public R<List<Map<String, Object>>> loadPositionData(Map<String, Object> map) {
    long storageId = Convert.toLong(map.get("storageId"));
    String sql = """
       SELECT   MAX(inventory_id) AS inventory_id, pp.storage_id, pp.storage_name, pp.position_name, SUM(pp.product_storage) AS product_storage,
       SUM(pp.origin_storage) AS origin_storage, SUM(pp.valid_storage) AS valid_storage,
       SUM(holder_storage) AS holder_storage, MAX(p.max_capacity) AS max_capacity
       FROM      core_inventory pp INNER JOIN base_position p ON pp.storage_id=p.storage_id AND pp.position_name=p.position_name
       WHERE   (pp.storage_id = {0}) AND pp.product_storage>0
       GROUP BY pp.storage_id, pp.storage_name, pp.position_name
      """;
    List<Map<String, Object>> mapList = SqlRunner.db().selectList(sql, storageId);

    return R.ok(mapList);
  }

  //#endregion

  //#region add
  @Override
  public R<Map<String, Object>> add(BasePositionBo bo) {
    BaseStorage baseStorage = baseStorageService.getByName(bo.getStorageName());
    Assert.isFalse(ObjectUtil.isNull(baseStorage), bo.getStorageName() + "仓库信息不存在，请先添加仓库信息");


    BasePosition basePosition = this.getByName(baseStorage.getStorageId(), bo.getPositionName());

    if (ObjectUtil.isNotNull(basePosition)) {
      BeanUtil.copyProperties(bo, basePosition, new CopyOptions().setIgnoreProperties("positionId"));
      this.updateByBo(bo);

      Map<String, Object> result = new HashMap<>();
      result.put("positionId", basePosition.getPositionId());
      result.put("positionName", basePosition.getPositionName());
      result.put("storageId", basePosition.getStorageId());
      result.put("storageName", basePosition.getStorageName());

      return R.ok("货位信息更新成功", result);
    }

    basePosition = new BasePosition();
    BeanUtil.copyProperties(bo, basePosition, new CopyOptions().setIgnoreProperties("positionId"));
    basePosition.setStorageId(baseStorage.getStorageId());
    basePosition.setStorageName(baseStorage.getStorageName());
    this.save(basePosition);

    Map<String, Object> result = new HashMap<>();
    result.put("positionId", basePosition.getPositionId());
    result.put("positionName", basePosition.getPositionName());
    result.put("storageId", basePosition.getStorageId());
    result.put("storageName", basePosition.getStorageName());

    return R.ok("库区信息保存成功", result);

  }
  //#endregion

}

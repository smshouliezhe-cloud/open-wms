package com.yiruantong.basic.service.tms.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yiruantong.basic.domain.tms.BaseVehicle;
import com.yiruantong.basic.domain.tms.bo.BaseVehicleBo;
import com.yiruantong.basic.domain.tms.vo.BaseVehicleVo;
import com.yiruantong.basic.mapper.tms.BaseVehicleMapper;
import com.yiruantong.basic.service.tms.IBaseVehicleService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.enums.base.VehicleStatusEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.domain.bo.SaveEditorBo;
import com.yiruantong.common.mybatis.core.service.IDataAuthService;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.satoken.utils.LoginHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 车辆管理Service业务层处理
 *
 * @author YRT
 * @date 2023-11-03
 */
@RequiredArgsConstructor
@Service
public class BaseVehicleServiceImpl extends ServiceImplPlus<BaseVehicleMapper, BaseVehicle, BaseVehicleVo, BaseVehicleBo> implements IBaseVehicleService {

  private final IDataAuthService dataAuthService;

  /**
   * 保存前事件
   *
   * @param saveEditorBo 保存提交bo数据
   */
  @Override
  public void beforeSaveEditor(SaveEditorBo<BaseVehicleBo> saveEditorBo) {
    LambdaQueryWrapper<BaseVehicle> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(BaseVehicle::getTruckNo, saveEditorBo.getData().getMaster().getTruckNo());
    // 如果车辆信息Id不为空
    if (ObjectUtil.isNotEmpty(saveEditorBo.getData().getMaster().getVehicleId())) {
      lambdaQueryWrapper.eq(BaseVehicle::getTruckNo, saveEditorBo.getData().getMaster().getTruckNo())
        .ne(BaseVehicle::getVehicleId, saveEditorBo.getData().getMaster().getVehicleId());
    }
    long count = this.count(lambdaQueryWrapper);
    if (count > 0) {
      throw new ServiceException("车牌号【" + saveEditorBo.getData().getMaster().getTruckNo() + "】已存在不允许重复添加，无法添加！");
    }
    super.beforeSaveEditor(saveEditorBo);
  }

  /**
   * 通用 - 查询车辆
   *
   * @param map 查询条件
   * @return 返回查询结果
   */
  @Override
  public List<Map<String, Object>> getList(Map<String, Object> map) {
    Integer take = Optional.ofNullable(map).map(m -> Convert.toInt(m.get("take"))).orElse(200); // 查询top N，如果为空，默认200
    String name = Convert.toStr(map.get("name"));
    // amis下拉框搜索
    if (StringUtils.isEmpty(name)) {
      name = Convert.toStr(map.get("term"));
    }

    String searchFields = Convert.toStr(map.get("searchFields"));
    LoginUser loginUser = LoginHelper.getLoginUser();
    if (ObjectUtil.isNull(loginUser)) return null;

    QueryWrapper<BaseVehicle> queryWrapper = new QueryWrapper<>();
    String finalName = name;
    queryWrapper.lambda().eq(BaseVehicle::getEnable, EnableEnum.ENABLE.getId()) // 是否可用
      .and(StringUtils.isNotEmpty(name),
        a -> a.like(StringUtils.isNotEmpty(finalName), BaseVehicle::getTruckNo, finalName)
      ); // 关键词对编号和名称模糊查询

    try {
      List<String> fields = Arrays.asList("truckNo", "driverName", "driverMobile", "carrierName", "vehicleType", "vehicleload", "vehicleVolume", "tel", "carrierName"); // 查询默认字段
      // 自定义查询字段
      if (StringUtils.isNotEmpty(searchFields)) {
        fields = List.of(StringUtils.split(searchFields, ",")); // 查询指定字段
      }
      if ("*".equals(searchFields)) {
        fields.clear(); // 清空，查询所有字段
      }

      // 自定义查询字段
      if (fields.size() > 0) {
        List<String> finalFields = fields;
        queryWrapper.select(BaseVehicle.class, s -> finalFields.contains(s.getProperty()));
      }
      // 数据权限
      if (!loginUser.isAdministrator()) {
        dataAuthService.getProviderAuth(queryWrapper);
      }
      queryWrapper.lambda().orderByAsc(BaseVehicle::getVehicleId); // 排序
      queryWrapper.last("limit " + take); // top N

      var dataList = this.baseMapper.selectMaps(queryWrapper);
      return dataList;
    } catch (Exception error) {
      var msg = "异常错误信息：" + error.getCause();
      throw new ServiceException(msg);
    }
  }

  /**
   * 获取车辆下拉框信息
   *
   * @param map 查询条件
   * @return 返回保存结果
   */
  @Override
  public List<Map<String, Object>> getTruckNoList(Map<String, Object> map) {

    try {
      String storageName = Convert.toStr(map.get("storageName"));
      String searchFields = Convert.toStr(map.get("searchFields"));

      LambdaQueryWrapper<BaseVehicle> queryWrapper = new LambdaQueryWrapper<>();
      queryWrapper.eq(BaseVehicle::getVehicleStatus, "待排班");//

      if (StrUtil.isNotEmpty(storageName)) {
        queryWrapper.eq(BaseVehicle::getStorageName, storageName);
      }

      List<String> fields = Arrays.asList("truckNo", "driverName", "driverMobile", "carrierName", "vehicleType", "vehicleload", "vehicleVolume"); // 查询默认字段
      // 自定义查询字段
      if (StringUtils.isNotEmpty(searchFields)) {
        fields = List.of(StringUtils.split(searchFields, ",")); // 查询指定字段
      }
      if ("*".equals(searchFields)) {
        fields.clear(); // 清空，查询所有字段
      }

      // 自定义查询字段
      if (fields.size() > 0) {
        List<String> finalFields = fields;
        queryWrapper.select(BaseVehicle.class, s -> finalFields.contains(s.getProperty()));
      }
      queryWrapper.orderByAsc(BaseVehicle::getVehicleId); // 排序

      // 自定义查询字

      var dataList = this.baseMapper.selectMaps(queryWrapper);
      return dataList;


    } catch (Exception error) {
      var msg = "异常错误信息：" + error.getCause();
      throw new ServiceException(msg);
    }
  }

  /**
   * 车辆停用
   *
   * @param ids
   * @return 返回查询列表数据
   */
  @Override
  public R<Void> stopUsing(List<Long> ids) {
    try {
      LambdaQueryWrapper<BaseVehicle> pickupLambdaQueryWrapper = new LambdaQueryWrapper<>();
      pickupLambdaQueryWrapper.in(BaseVehicle::getVehicleId, ids);
      List<BaseVehicle> baseVehicles = this.list(pickupLambdaQueryWrapper);
      for (var vehicleInfo : baseVehicles) {

        //  修改车辆信息为停用
        LambdaUpdateWrapper<BaseVehicle> vehicleLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        vehicleLambdaUpdateWrapper
          .set(BaseVehicle::getEnable, EnableEnum.DISABLE.getId())
          .set(BaseVehicle::getVehicleStatus, "停用")
          .eq(BaseVehicle::getVehicleId, vehicleInfo.getVehicleId());
        this.update(vehicleLambdaUpdateWrapper);
      }

      return R.ok("已停用");
    } catch (NoSuchMessageException e) {
      throw new ServiceException("错误" + e.getMessage());
    }
  }

  /**
   * 车辆启用
   *
   * @param ids 查询条件
   * @return 返回查询列表数据
   */
  @Override
  public R<Void> openUsing(List<Long> ids) {
    try {
      LambdaQueryWrapper<BaseVehicle> pickupLambdaQueryWrapper = new LambdaQueryWrapper<>();
      pickupLambdaQueryWrapper.in(BaseVehicle::getVehicleId, ids);
      List<BaseVehicle> baseVehicles = this.list(pickupLambdaQueryWrapper);
      for (var vehicleInfo : baseVehicles) {

        //  修改车辆信息为停用
        LambdaUpdateWrapper<BaseVehicle> vehicleLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        vehicleLambdaUpdateWrapper
          .set(BaseVehicle::getEnable, EnableEnum.ENABLE.getId())
          .set(BaseVehicle::getVehicleStatus, "待排班")
          .eq(BaseVehicle::getVehicleId, vehicleInfo.getVehicleId());
        this.update(vehicleLambdaUpdateWrapper);
      }

      return R.ok("待排班");
    } catch (NoSuchMessageException e) {
      throw new ServiceException("错误" + e.getMessage());
    }
  }

  @Override
  public BaseVehicle getByTruckNo(String truckNo) {
    LambdaQueryWrapper<BaseVehicle> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(BaseVehicle::getTruckNo, truckNo);
    return this.getOne(queryWrapper);
  }


  @Override
  public void updateStatus(Long vehicleId, VehicleStatusEnum vehicleStatusEnum) {
    //  修改车辆信息为任务中
    LambdaUpdateWrapper<BaseVehicle> vehicleLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    vehicleLambdaUpdateWrapper
      .set(BaseVehicle::getVehicleStatus, vehicleStatusEnum.getName())
      .eq(BaseVehicle::getVehicleId, vehicleId);
    this.update(vehicleLambdaUpdateWrapper);
  }

  @Override
  public void updateStatus(String truckNo, VehicleStatusEnum vehicleStatusEnum) {
    //  修改车辆信息为任务中
    LambdaUpdateWrapper<BaseVehicle> vehicleLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    vehicleLambdaUpdateWrapper
      .set(BaseVehicle::getVehicleStatus, vehicleStatusEnum.getName())
      .eq(BaseVehicle::getTruckNo, truckNo);
    this.update(vehicleLambdaUpdateWrapper);
  }
}

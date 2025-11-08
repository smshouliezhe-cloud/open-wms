package com.yiruantong.basic.service.tms.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yiruantong.basic.domain.tms.BaseVehicle;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.service.IDataAuthService;
import com.yiruantong.common.satoken.utils.LoginHelper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.tms.BaseDriver;
import com.yiruantong.basic.domain.tms.bo.BaseDriverBo;
import com.yiruantong.basic.domain.tms.vo.BaseDriverVo;
import com.yiruantong.basic.mapper.tms.BaseDriverMapper;
import com.yiruantong.basic.service.tms.IBaseDriverService;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 司机管理Service业务层处理
 *
 * @author YRT
 * @date 2023-11-03
 */
@RequiredArgsConstructor
@Service
public class BaseDriverServiceImpl extends ServiceImplPlus<BaseDriverMapper, BaseDriver, BaseDriverVo, BaseDriverBo> implements IBaseDriverService {

  private final IDataAuthService dataAuthService;
  /**
   * 获取司机下拉框信息
   *
   * @param map 查询条件
   * @return 返回保存结果
   */
  @Override
  public List<Map<String, Object>> getDriverNameList(Map<String, Object> map) {
    String storageName = Convert.toStr(map.get("storageName"));

    LambdaQueryWrapper<BaseDriver> queryWrapper = new LambdaQueryWrapper<>();

    try {
  if(StrUtil.isNotEmpty(storageName)){
    queryWrapper.eq(BaseDriver::getStorageName, storageName);
  }
      List<String> fields = Arrays.asList("driverId", "driverCode", "driverName", "tel"); // 查询默认字段
      // 自定义查询字段
      if (fields.size() > 0) {
        List<String> finalFields = fields;
        queryWrapper.select(BaseDriver.class, s -> finalFields.contains(s.getProperty()));
      }
      var dataList = this.baseMapper.selectMaps(queryWrapper);
      return dataList;


    } catch (Exception error) {
      var msg = "异常错误信息：" + error.getCause();
      throw new ServiceException(msg);
    }
  }


  /**
   * 通用 - 查询司机
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

    QueryWrapper<BaseDriver> queryWrapper = new QueryWrapper<>();
    String finalName = name;
    queryWrapper.lambda().eq(BaseDriver::getEnable, EnableEnum.ENABLE.getId()) // 是否可用
      .and(StringUtils.isNotEmpty(name),
        a -> a.like(StringUtils.isNotEmpty(finalName), BaseDriver::getDriverName, finalName)
      ); // 关键词对编号和名称模糊查询

    try {
      List<String> fields = Arrays.asList("driverId", "driverCode", "driverName", "tel"); // 查询默认字段
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
        queryWrapper.select(BaseDriver.class, s -> finalFields.contains(s.getProperty()));
      }
      // 数据权限
      if (!loginUser.isAdministrator()) {
        dataAuthService.getProviderAuth(queryWrapper);
      }
      queryWrapper.lambda().orderByAsc(BaseDriver::getDriverId); // 排序
      queryWrapper.last("limit " + take); // top N

      var dataList = this.baseMapper.selectMaps(queryWrapper);
      return dataList;
    } catch (Exception error) {
      var msg = "异常错误信息：" + error.getCause();
      throw new ServiceException(msg);
    }
  }


  @Override
  public int resetUserPwd(Long userId, String password) {
    return baseMapper.update(null,
      new LambdaUpdateWrapper<BaseDriver>()
        .set(BaseDriver::getUserPwd, password)
        .eq(BaseDriver::getDriverId, userId));

  }

  @Override
  public BaseDriver getByName(String driverName) {
    LambdaQueryWrapper<BaseDriver> driverLambdaQueryWrapper = new LambdaQueryWrapper<>();
    driverLambdaQueryWrapper.eq(BaseDriver::getDriverName, driverName);
    return this.getOnly(driverLambdaQueryWrapper);
  }
}

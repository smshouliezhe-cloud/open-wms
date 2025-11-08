package com.yiruantong.basic.service.base.impl;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.base.BaseCity;
import com.yiruantong.basic.domain.base.bo.BaseCityBo;
import com.yiruantong.basic.domain.base.bo.GetListBo;
import com.yiruantong.basic.domain.base.vo.BaseCityVo;
import com.yiruantong.basic.mapper.base.BaseCityMapper;
import com.yiruantong.basic.service.base.IBaseCityService;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.service.IDataAuthService;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.satoken.utils.LoginHelper;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 省市区管理Service业务层处理
 *
 * @author YRT
 * @date 2024-11-05
 */
@RequiredArgsConstructor
@Service
@SaIgnore // 忽略接口鉴权
public class BaseCityServiceImpl extends ServiceImplPlus<BaseCityMapper, BaseCity, BaseCityVo, BaseCityBo> implements IBaseCityService {
  private final IDataAuthService dataAuthService;

  /**
   * 获取省
   *
   * @param map 查询条件
   * @return 返回保存结果
   */
  @Override
  public List<Map<String, Object>> getProvinceList(Map<String, Object> map) {

    LambdaQueryWrapper<BaseCity> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.select(BaseCity::getCityId, BaseCity::getCityName)
      .eq(BaseCity::getParentId, 0)
      .orderByDesc(BaseCity::getOrderNum);
    return this.baseMapper.selectMaps(queryWrapper);
  }

  /**
   * 获取省
   *
   * @param map 查询条件
   * @return 返回保存结果
   */
  @Override
  public List<Map<String, Object>> getCityList(Map<String, Object> map) {
    LambdaQueryWrapper<BaseCity> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.select(BaseCity::getCityId, BaseCity::getCityName)
      .orderByDesc(BaseCity::getOrderNum)
      .last("LIMIT 0");
    return this.baseMapper.selectMaps(queryWrapper);
  }

  /**
   * 根据id获取
   *
   * @param id 查询条件
   * @return 返回保存结果
   */
  @Override
  public BaseCity getById(Long id) {
    LambdaQueryWrapper<BaseCity> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(BaseCity::getCityId, id);
    return this.getOnly(queryWrapper);
  }

  @Override
  public List<Map<String, Object>> getList(GetListBo getListBo) {
    Long take = Optional.ofNullable(getListBo).map(GetListBo::getTake).orElse(200L); // 查询top N，如果为空，默认200
    String name = getListBo.getName();
    // amis下拉框搜索
    if (StringUtils.isEmpty(name)) {
      name = getListBo.getTerm();
    }

    String searchFields = getListBo.getSearchFields();
    LoginUser loginUser = LoginHelper.getLoginUser();
    if (ObjectUtil.isNull(loginUser)) return null;

    QueryWrapper<BaseCity> queryWrapper = new QueryWrapper<>();
    String finalName = name;
    queryWrapper.lambda()
      .eq(BaseCity::getParentId, getListBo.getParentId())
      .and(StringUtils.isNotEmpty(name),
        a -> a.like(StringUtils.isNotEmpty(finalName), BaseCity::getCityName, finalName)
          .or()
          .like(StringUtils.isNotEmpty(finalName), BaseCity::getCityName, finalName)
      ); // 关键词对编号和名称模糊查询

    try {
      List<String> fields = Arrays.asList("cityId", "cityName"); // 查询默认字段
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
        queryWrapper.select(BaseCity.class, s -> finalFields.contains(s.getProperty()));
      }
      // 数据权限
      if (!loginUser.isAdministrator()) {
        dataAuthService.getStorageAuth(queryWrapper);
      }
      queryWrapper.lambda().orderByAsc(BaseCity::getStorageId); // 排序
      queryWrapper.last("limit " + take); // top N

      return this.baseMapper.selectMaps(queryWrapper);
    } catch (Exception error) {
      var msg = "异常错误信息：" + error.getCause();
      throw new ServiceException(msg);
    }
  }
}

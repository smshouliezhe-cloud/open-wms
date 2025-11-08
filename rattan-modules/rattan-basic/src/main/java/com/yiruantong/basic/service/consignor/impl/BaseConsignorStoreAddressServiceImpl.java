package com.yiruantong.basic.service.consignor.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.consignor.BaseConsignorStoreAddress;
import com.yiruantong.basic.domain.consignor.bo.BaseConsignorStoreAddressBo;
import com.yiruantong.basic.domain.consignor.vo.BaseConsignorStoreAddressVo;
import com.yiruantong.basic.mapper.consignor.BaseConsignorStoreAddressMapper;
import com.yiruantong.basic.service.consignor.IBaseConsignorStoreAddressService;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.service.IDataAuthService;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.satoken.utils.LoginHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 收货人管理Service业务层处理
 *
 * @author YRT
 * @date 2024-03-12
 */
@RequiredArgsConstructor
@Service
public class BaseConsignorStoreAddressServiceImpl extends ServiceImplPlus<BaseConsignorStoreAddressMapper, BaseConsignorStoreAddress, BaseConsignorStoreAddressVo, BaseConsignorStoreAddressBo> implements IBaseConsignorStoreAddressService {
  private final IDataAuthService dataAuthService;
  //#region 通用 - 查询发货人信息
  /**
   * 通用 - 查询发货人信息
   *
   * @param map 查询条件
   * @return 返回查询结果
   */
  @Override
  public List<Map<String, Object>> getList(Map<String, Object> map) {
    Integer take = Optional.ofNullable(map).map(m -> Convert.toInt(m.get("take"))).orElse(200); // 查询top N，如果为空，默认200
    String name = Convert.toStr(map.get("name"));
    String searchFields = Convert.toStr(map.get("searchFields"));
    LoginUser loginUser = LoginHelper.getLoginUser();
    if(ObjectUtil.isNull(loginUser))return null;

    QueryWrapper<BaseConsignorStoreAddress> queryWrapper = new QueryWrapper<>();
    queryWrapper.lambda()
      .and(StringUtils.isNotEmpty(name), a -> a.like(StringUtils.isNotEmpty(name), BaseConsignorStoreAddress::getConsigneeCode, name)
        .or()
        .like(StringUtils.isNotEmpty(name), BaseConsignorStoreAddress::getBillingName, name)
      ); // 关键词对编号和名称模糊查询

    try {
      List<String> fields = CollUtil.newArrayList("addressId", "consigneeCode", "consigneeName"); // 查询默认字段
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
        queryWrapper.select(BaseConsignorStoreAddress.class, s -> finalFields.contains(s.getProperty()));
      }
      // 数据权限
      if(!loginUser.isAdministrator()) {
        dataAuthService.getConsignorAuth(queryWrapper);
      }
      queryWrapper.lambda().orderByAsc(BaseConsignorStoreAddress::getAddressId); // 排序
      queryWrapper.last("limit " + take); // top N

      return this.baseMapper.selectMaps(queryWrapper);
    } catch (Exception error) {
      var msg = "异常错误信息：" + error.getCause();
      throw new ServiceException(msg);
    }
  }
  //#endregion
}

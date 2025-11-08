package com.yiruantong.basic.service.consignor.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yiruantong.basic.domain.base.bo.GetListBo;
import com.yiruantong.basic.domain.consignor.BaseConsignorAddress;
import com.yiruantong.basic.domain.consignor.bo.BaseConsignorAddressBo;
import com.yiruantong.basic.domain.consignor.vo.BaseConsignorAddressVo;
import com.yiruantong.basic.mapper.consignor.BaseConsignorAddressMapper;
import com.yiruantong.basic.service.consignor.IBaseConsignorAddressService;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.service.IDataAuthService;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.mybatis.helper.DataBaseHelper;
import com.yiruantong.common.satoken.utils.LoginHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 货主地址管理Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-13
 */
@RequiredArgsConstructor
@Service
public class BaseConsignorAddressServiceImpl extends ServiceImplPlus<BaseConsignorAddressMapper, BaseConsignorAddress, BaseConsignorAddressVo, BaseConsignorAddressBo> implements IBaseConsignorAddressService {
  private final IDataAuthService dataAuthService;

  //#region 通用 - 查询发货人信息

  /**
   * 通用 - 查询发货人信息
   *
   * @param getListBo@return 返回查询结果
   */
  @Override
  public List<Map<String, Object>> getList(GetListBo getListBo) {
    Long take = Optional.ofNullable(getListBo).map(GetListBo::getTake).orElse(200L); // 查询top N，如果为空，默认200
    String name = Convert.toStr(getListBo.getName());
    // amis下拉框搜索
    if (StringUtils.isEmpty(name)) {
      name = Convert.toStr(getListBo.getTerm());
    }
    String searchFields = Convert.toStr(getListBo.getSearchFields());
    LoginUser loginUser = LoginHelper.getLoginUser();
    if (ObjectUtil.isNull(loginUser)) return null;

    QueryWrapper<BaseConsignorAddress> queryWrapper = new QueryWrapper<>();
    String finalName = name;
    queryWrapper.lambda()
      .apply(StringUtils.isNotEmpty(getListBo.getType()), DataBaseHelper.findInSet(getListBo.getType(), "address_type"))
      .isNotNull(StringUtils.isEmpty(getListBo.getType()), BaseConsignorAddress::getConsignee)
      .and(StringUtils.isNotEmpty(finalName), a -> a
        .like(StringUtils.isNotEmpty(finalName), BaseConsignorAddress::getBillingCode, finalName)
        .or()
        .like(StringUtils.isNotEmpty(finalName), BaseConsignorAddress::getBillingName, finalName)
        .or()
        .like(StringUtils.isNotEmpty(finalName), BaseConsignorAddress::getConsignorName, finalName)
      ); // 关键词对编号和名称模糊查询

    try {
      List<String> fields = CollUtil.newArrayList("addressId", "addressType", "provinceName", "cityName", "regionName", "detailAddress", "consignee", "email", "mobile", "zip", "tel", "billingName", "billingMobile", "consignorName"); // 查询默认字段
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
        queryWrapper.select(BaseConsignorAddress.class, s -> finalFields.contains(s.getProperty()));
      }
      // 数据权限
      if (!loginUser.isAdministrator()) {
        dataAuthService.getConsignorAuth(queryWrapper);
      }
      queryWrapper.lambda().orderByAsc(BaseConsignorAddress::getAddressId); // 排序
      queryWrapper.last("limit " + take); // top N

      return this.baseMapper.selectMaps(queryWrapper);
    } catch (Exception error) {
      var msg = "异常错误信息：" + error.getCause();
      throw new ServiceException(msg);
    }
  }
  //#endregion

}

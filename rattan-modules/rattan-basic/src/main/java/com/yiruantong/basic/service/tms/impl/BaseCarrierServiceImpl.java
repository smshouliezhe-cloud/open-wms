package com.yiruantong.basic.service.tms.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yiruantong.basic.domain.base.bo.GetListBo;
import com.yiruantong.basic.domain.tms.BaseCarrier;
import com.yiruantong.basic.domain.tms.bo.BaseCarrierBo;
import com.yiruantong.basic.domain.tms.vo.BaseCarrierVo;
import com.yiruantong.basic.mapper.tms.BaseCarrierMapper;
import com.yiruantong.basic.service.tms.IBaseCarrierService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.service.IDataAuthService;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.satoken.utils.LoginHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 承运商管理Service业务层处理
 *
 * @author YRT
 * @date 2024-03-08
 */
@RequiredArgsConstructor
@Service
public class BaseCarrierServiceImpl extends ServiceImplPlus<BaseCarrierMapper, BaseCarrier, BaseCarrierVo, BaseCarrierBo> implements IBaseCarrierService {

  private final IDataAuthService dataAuthService;

  /**
   * 通用 - 查询承运商
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

    QueryWrapper<BaseCarrier> queryWrapper = new QueryWrapper<>();
    String finalName = name;
    queryWrapper.lambda().eq(BaseCarrier::getEnable, EnableEnum.ENABLE.getId()) // 是否可用
      .and(StringUtils.isNotEmpty(name),
        a -> a.like(StringUtils.isNotEmpty(finalName), BaseCarrier::getCarrierCode, finalName)
          .or()
          .like(StringUtils.isNotEmpty(finalName), BaseCarrier::getCarrierName, finalName)
      ); // 关键词对编号和名称模糊查询

    try {
      List<String> fields = Arrays.asList("carrierId", "carrierCode", "carrierName"); // 查询默认字段
      // 自定义查询字段
      if (StringUtils.isNotEmpty(searchFields)) {
        fields = List.of(StringUtils.split(searchFields, ",")); // 查询指定字段
      }
      if ("*".equals(searchFields)) {
        fields = new ArrayList<>(); // 清空，查询所有字段
      }

      // 自定义查询字段
      if (fields.size() > 0) {
        List<String> finalFields = fields;
        queryWrapper.select(BaseCarrier.class, s -> finalFields.contains(s.getProperty()));
      }
      // 数据权限
      if (!loginUser.isAdministrator()) {
        dataAuthService.getProviderAuth(queryWrapper);
      }
      queryWrapper.lambda().orderByAsc(BaseCarrier::getCarrierId); // 排序
      queryWrapper.last("limit " + take); // top N

      var dataList = this.baseMapper.selectMaps(queryWrapper);
      return dataList;
    } catch (Exception error) {
      var msg = "异常错误信息：" + error.getCause();
      throw new ServiceException(msg);
    }
  }

  @Override
  public List<Map<String, Object>> getListNew(GetListBo getListBo) {
    Integer take = Optional.ofNullable(getListBo).map(m -> Convert.toInt(m.getTake())).orElse(50); // 查询top N，如果为空，默认50
    boolean isDesc = Optional.ofNullable(getListBo).map(GetListBo::isDesc).orElse(false); // 倒序方式
    String name = getListBo.getName();
    // amis下拉框搜索
    if (StringUtils.isEmpty(name)) {
      name = Convert.toStr(getListBo.getTerm());
    }

    String searchFields = Convert.toStr(getListBo.getSearchFields());
    LoginUser loginUser = LoginHelper.getLoginUser();
    if (ObjectUtil.isNull(loginUser)) return null;

    QueryWrapper<BaseCarrier> queryWrapper = new QueryWrapper<>();
    String finalName = name;
    queryWrapper.lambda()
      .eq(StringUtils.isNotEmpty(getListBo.getType()), BaseCarrier::getCarrierType, getListBo.getType())
      .isNotNull(StringUtils.isEmpty(getListBo.getType()), BaseCarrier::getCarrierCode)
      .and(StringUtils.isNotEmpty(finalName), a -> a
        .like(BaseCarrier::getCarrierCode, finalName)
        .or()
        .like(BaseCarrier::getCarrierName, finalName)
      )
    ; // 关键词对编号和名称模糊查询

    try {
      List<String> fields = CollUtil.newArrayList("carrierId", "carrierCode", "carrierName", "carrierType", "contactName", "contactNumber"); // 查询默认字段
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
        queryWrapper.select(BaseCarrier.class, s -> finalFields.contains(s.getProperty()));
      }

      queryWrapper.lambda().orderByDesc(isDesc, BaseCarrier::getCarrierId).orderByAsc(!isDesc, BaseCarrier::getCarrierId); // 排序
      queryWrapper.last("limit " + take); // top N

      return this.baseMapper.selectMaps(queryWrapper);
    } catch (Exception error) {
      var msg = "异常错误信息：" + error.getCause();
      throw new ServiceException(msg);
    }
  }


  @Override
  public BaseCarrier getByName(String carrierName, Long enable) {
    LambdaQueryWrapper<BaseCarrier> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(BaseCarrier::getCarrierName, carrierName)
      .eq(ObjectUtil.isNotEmpty(enable), BaseCarrier::getEnable, enable);
    return this.getOne(lambdaQueryWrapper);
  }

  @Override
  public BaseCarrier getByName(String carrierName) {
    return getByName(carrierName, null);
  }

  @Override
  public R<Map<String, Object>> add(BaseCarrierBo bo) {
    BaseCarrier baseCarrier = this.getByName(bo.getCarrierName(), null);


//    LambdaQueryWrapper<BaseCarrier> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//    lambdaQueryWrapper.eq(BaseCarrier::getCarrierName, bo.getCarrierName());
//    // 如果货主Id不为空
//    if (ObjectUtil.isNotEmpty(bo.getCarrierId())) {
//      lambdaQueryWrapper.eq(BaseCarrier::getCarrierName, bo.getCarrierName())
//        .ne(BaseCarrier::getCarrierId, bo.getCarrierId());
//    }
//
//    long count = this.count(lambdaQueryWrapper);
//    if (count > 0) {
//      throw new ServiceException("承运商【" + bo.getCarrierName() + "】已存在不允许重复添加，无法添加！");
//    }


    if (ObjectUtil.isNotNull(baseCarrier)) {
      BeanUtil.copyProperties(bo, baseCarrier, new CopyOptions().setIgnoreProperties("consignorId"));
      this.saveOrUpdate(baseCarrier);

      Map<String, Object> result = new HashMap<>();
      result.put("carrierId", baseCarrier.getCarrierId());
      result.put("carrierCode", baseCarrier.getCarrierCode());
      result.put("carrierName", baseCarrier.getCarrierName());

      return R.ok("承运商更新成功", result);
    }

    baseCarrier = new BaseCarrier();
    BeanUtil.copyProperties(bo, baseCarrier, new CopyOptions().setIgnoreProperties("consignorId"));
    baseCarrier.setEnable(EnableEnum.ENABLE.getId());
    this.save(baseCarrier);


    Map<String, Object> result = new HashMap<>();
    result.put("carrierId", baseCarrier.getCarrierId());
    result.put("carrierCode", baseCarrier.getCarrierCode());
    result.put("carrierName", baseCarrier.getCarrierName());
    return R.ok("承运商信息保存成功", result);

  }

  @Override
  public BaseCarrier getBySite(Long carrierId, String distributionSite) {
    LambdaQueryWrapper<BaseCarrier> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(BaseCarrier::getCarrierId, carrierId)
      .eq(ObjectUtil.isNotEmpty(distributionSite), BaseCarrier::getHomeIand, distributionSite);
    return this.getOne(lambdaQueryWrapper);
  }

  @Override
  public int resetUserPwd(Long carrierId, String password) {
    return baseMapper.update(null,
      new LambdaUpdateWrapper<BaseCarrier>()
        .set(BaseCarrier::getPassword, password)
        .eq(BaseCarrier::getCarrierId, carrierId));
  }
}

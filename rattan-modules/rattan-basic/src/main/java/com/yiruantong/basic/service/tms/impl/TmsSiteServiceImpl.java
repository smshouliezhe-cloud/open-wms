package com.yiruantong.basic.service.tms.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.base.bo.GetListBo;
import com.yiruantong.basic.domain.tms.TmsSite;
import com.yiruantong.basic.domain.tms.bo.TmsSiteBo;
import com.yiruantong.basic.domain.tms.vo.TmsSiteVo;
import com.yiruantong.basic.mapper.tms.TmsSiteMapper;
import com.yiruantong.basic.service.tms.ITmsSiteService;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.satoken.utils.LoginHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 网点管理Service业务层处理
 *
 * @author YRT
 * @date 2024-03-08
 */
@RequiredArgsConstructor
@Service
public class TmsSiteServiceImpl extends ServiceImplPlus<TmsSiteMapper, TmsSite, TmsSiteVo, TmsSiteBo> implements ITmsSiteService {

  //#region getList 下拉框通用查询

  /**
   * getList 下拉框通用查询
   *
   * @param getListBo@return 返回查询结果
   */
  @Override
  public List<Map<String, Object>> getList(GetListBo getListBo) {
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

    QueryWrapper<TmsSite> queryWrapper = new QueryWrapper<>();
    String finalName = name;
    queryWrapper.lambda()
      .eq(StringUtils.isNotEmpty(getListBo.getType()), TmsSite::getSiteType, getListBo.getType())
      .isNotNull(StringUtils.isEmpty(getListBo.getType()), TmsSite::getSiteCode)
      .and(StringUtils.isNotEmpty(finalName), a -> a
        .like(TmsSite::getSiteName, finalName)
        .or()
        .like(TmsSite::getSiteCode, finalName)
        .or()
        .like(TmsSite::getProvinceName, finalName)
      )
    ; // 关键词对编号和名称模糊查询

    try {
      List<String> fields = CollUtil.newArrayList("continent", "countryNameCn", "provinceName", "cityName", "regionName", "siteType", "siteName", "siteAddress", "region", "siteCode"); // 查询默认字段
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
        queryWrapper.select(TmsSite.class, s -> finalFields.contains(s.getProperty()));
      }

      queryWrapper.lambda().orderByDesc(isDesc, TmsSite::getSiteId).orderByAsc(!isDesc, TmsSite::getSiteId); // 排序
      queryWrapper.last("limit " + take); // top N

      return this.baseMapper.selectMaps(queryWrapper);
    } catch (Exception error) {
      var msg = "异常错误信息：" + error.getCause();
      throw new ServiceException(msg);
    }
  }

  //#endregion

  @Override
  public TmsSite getByName(String siteName) {
    LambdaQueryWrapper<TmsSite> siteLambdaQueryWrapper = new LambdaQueryWrapper<>();
    siteLambdaQueryWrapper.eq(TmsSite::getSiteName, siteName);
    return this.getOnly(siteLambdaQueryWrapper);
  }
}

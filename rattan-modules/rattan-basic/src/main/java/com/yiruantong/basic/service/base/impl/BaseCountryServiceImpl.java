package com.yiruantong.basic.service.base.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.base.BaseCountry;
import com.yiruantong.basic.domain.base.bo.BaseCountryBo;
import com.yiruantong.basic.domain.base.bo.GetListBo;
import com.yiruantong.basic.domain.base.vo.BaseCountryVo;
import com.yiruantong.basic.mapper.base.BaseCountryMapper;
import com.yiruantong.basic.service.base.IBaseCountryService;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.domain.bo.SaveEditorBo;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.satoken.utils.LoginHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 国家信息Service业务层处理
 *
 * @author YRT
 * @date 2024-06-06
 */
@RequiredArgsConstructor
@Service
public class BaseCountryServiceImpl extends ServiceImplPlus<BaseCountryMapper, BaseCountry, BaseCountryVo, BaseCountryBo> implements IBaseCountryService {

  //#region 保存前事件

  /**
   * 保存前事件
   *
   * @param saveEditorBo 保存提交bo数据
   */
  @Override
  public void beforeSaveEditor(SaveEditorBo<BaseCountryBo> saveEditorBo) {

    LambdaQueryWrapper<BaseCountry> countryLambdaQueryWrapper = new LambdaQueryWrapper<>();
    countryLambdaQueryWrapper.eq(BaseCountry::getCountryNameCn, saveEditorBo.getData().getMaster().getCountryNameCn());
    // 如果国家Id不为空
    if (ObjectUtil.isNotEmpty(saveEditorBo.getData().getMaster().getCountryId())) {
      countryLambdaQueryWrapper.eq(BaseCountry::getCountryNameCn, saveEditorBo.getData().getMaster().getCountryNameCn())
        .ne(BaseCountry::getCountryId, saveEditorBo.getData().getMaster().getCountryId());
    }

    long count = this.count(countryLambdaQueryWrapper);
    if (count > 0) {
      throw new ServiceException("国家【" + saveEditorBo.getData().getMaster().getCountryNameCn() + "】已存在不允许重复添加，无法添加！");
    }
    super.beforeSaveEditor(saveEditorBo);
  }
  //#endregion

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

    QueryWrapper<BaseCountry> queryWrapper = new QueryWrapper<>();
    String finalName = name;
    queryWrapper.lambda()
      .like(StringUtils.isNotEmpty(finalName), BaseCountry::getCountryName, finalName)
      .or()
      .like(StringUtils.isNotEmpty(finalName), BaseCountry::getCountryNameCn, finalName)
    ; // 关键词对编号和名称模糊查询

    try {
      List<String> fields = CollUtil.newArrayList("countryId", "countryName", "countryNameCn", "iso2Code", "iso3Code"); // 查询默认字段
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
        queryWrapper.select(BaseCountry.class, s -> finalFields.contains(s.getProperty()));
      }

      queryWrapper.lambda().orderByDesc(isDesc, BaseCountry::getCountryId).orderByAsc(!isDesc, BaseCountry::getCountryId); // 排序
      queryWrapper.last("limit " + take); // top N

      return this.baseMapper.selectMaps(queryWrapper);
    } catch (Exception error) {
      var msg = "异常错误信息：" + error.getCause();
      throw new ServiceException(msg);
    }
  }
  //#endregion

}

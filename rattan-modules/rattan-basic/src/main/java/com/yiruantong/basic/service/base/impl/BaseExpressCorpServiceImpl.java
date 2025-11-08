package com.yiruantong.basic.service.base.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.base.BaseExpressCorp;
import com.yiruantong.basic.domain.base.bo.BaseExpressCorpBo;
import com.yiruantong.basic.domain.base.vo.BaseExpressCorpVo;
import com.yiruantong.basic.mapper.base.BaseExpressCorpMapper;
import com.yiruantong.basic.service.base.IBaseExpressCorpService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.domain.bo.SaveEditorBo;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 快递管理Service业务层处理
 *
 * @author YRT
 * @date 2023-08-25
 */
@RequiredArgsConstructor
@Service
public class BaseExpressCorpServiceImpl extends ServiceImplPlus<BaseExpressCorpMapper, BaseExpressCorp, BaseExpressCorpVo, BaseExpressCorpBo> implements IBaseExpressCorpService {


  //#region 获取快递公司
  @Override
  @Transactional(rollbackFor = Exception.class)
  public List<Map<String, Object>> getList(Map<String, Object> map) {
    Integer take = Optional.ofNullable(map).map(m -> Convert.toInt(m.get("take"))).orElse(200); // 查询top N，如果为空，默认200
    String name = Convert.toStr(map.get("name"));
    String searchFields = Convert.toStr(map.get("searchFields"));

    LambdaQueryWrapper<BaseExpressCorp> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.and(StringUtils.isNotEmpty(name),
      a -> a.like(StringUtils.isNotEmpty(name), BaseExpressCorp::getExpressCorpType, name)
    ); // 关键词对编号和名称模糊查询

    try {
      List<String> fields = Arrays.asList("expressCorpId", "expressCorpCode", "expressCorpName"); // 查询默认字段
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
        queryWrapper.select(BaseExpressCorp.class, s -> finalFields.contains(s.getProperty()));
      }
      queryWrapper.orderByAsc(BaseExpressCorp::getExpressCorpId); // 排序
      queryWrapper.last("limit " + take); // top N

      var dataList = this.baseMapper.selectMaps(queryWrapper);
      return dataList;
    } catch (Exception error) {
      var msg = "异常错误信息：" + error.getCause();
      throw new ServiceException(msg);
    }
  }


  //#region loadDropDown
  @Override
  public R<Map<String, Object>> getExpressCorp(Map<String, Object> map) {


    LambdaQueryWrapper<BaseExpressCorp> queryWrapper = new LambdaQueryWrapper<>();
    if (ObjectUtil.isNotEmpty(map.get("expressCorpId"))) {
      queryWrapper.eq(BaseExpressCorp::getExpressCorpId, map.get("expressCorpId"));
    }

    List<BaseExpressCorp> list = this.baseMapper.selectList(queryWrapper);
    Map<String, Object> result = new HashMap<>();
    result.put("dataList", list);
    return R.ok(result);
  }

  //#endregion


  /**
   * 保存前事件
   *
   * @param saveEditorBo 保存提交bo数据
   */
  @Override
  public void beforeSaveEditor(SaveEditorBo<BaseExpressCorpBo> saveEditorBo) {

    LambdaQueryWrapper<BaseExpressCorp> consignorLambdaQueryWrapper = new LambdaQueryWrapper<>();
    consignorLambdaQueryWrapper.eq(BaseExpressCorp::getExpressCorpName, saveEditorBo.getData().getMaster().getExpressCorpName());
    // 如果公司Id不为空
    if (ObjectUtil.isNotEmpty(saveEditorBo.getData().getMaster().getExpressCorpId())) {
      consignorLambdaQueryWrapper.eq(BaseExpressCorp::getExpressCorpName, saveEditorBo.getData().getMaster().getExpressCorpName())
        .ne(BaseExpressCorp::getExpressCorpId, saveEditorBo.getData().getMaster().getExpressCorpId());
    }

    long count = this.count(consignorLambdaQueryWrapper);
    if (count > 0) {
      throw new ServiceException("公司名称【" + saveEditorBo.getData().getMaster().getExpressCorpName() + "】已存在不允许重复添加，无法添加！");
    }


    LambdaQueryWrapper<BaseExpressCorp> consignorLambdaQueryWrapperTwo = new LambdaQueryWrapper<>();
    consignorLambdaQueryWrapperTwo.eq(BaseExpressCorp::getExpressCorpCode, saveEditorBo.getData().getMaster().getExpressCorpCode());
    // 如果公司Id不为空
    if (ObjectUtil.isNotEmpty(saveEditorBo.getData().getMaster().getExpressCorpId())) {
      consignorLambdaQueryWrapperTwo.eq(BaseExpressCorp::getExpressCorpCode, saveEditorBo.getData().getMaster().getExpressCorpCode())
        .ne(BaseExpressCorp::getExpressCorpId, saveEditorBo.getData().getMaster().getExpressCorpId());
    }

    long countTwo = this.count(consignorLambdaQueryWrapperTwo);
    if (countTwo > 0) {
      throw new ServiceException("公司编号【" + saveEditorBo.getData().getMaster().getExpressCorpCode() + "】已存在不允许重复添加，无法添加！");
    }
    super.beforeSaveEditor(saveEditorBo);

  }

  //#region getByName
  @Override
  public BaseExpressCorp getByName(String expressCorpName) {
    LambdaQueryWrapper<BaseExpressCorp> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(BaseExpressCorp::getExpressCorpName, expressCorpName);
    return this.getOnly(lambdaQueryWrapper);
  }
  //#endregion

  //#region getById
  @Override
  public BaseExpressCorp getById(Long expressCorpId) {
    LambdaQueryWrapper<BaseExpressCorp> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(BaseExpressCorp::getExpressCorpId, expressCorpId);
    return this.getOnly(lambdaQueryWrapper);
  }
  //#endregion

}

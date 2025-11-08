package com.yiruantong.basic.service.tms.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.base.bo.GetListBo;
import com.yiruantong.basic.domain.tms.BaseContainer;
import com.yiruantong.basic.domain.tms.bo.BaseContainerBo;
import com.yiruantong.basic.domain.tms.vo.BaseContainerVo;
import com.yiruantong.basic.mapper.tms.BaseContainerMapper;
import com.yiruantong.basic.service.tms.IBaseContainerService;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 集装箱信息Service业务层处理
 *
 * @author YRT
 * @date 2025-01-19
 */
@RequiredArgsConstructor
@Service
public class BaseContainerServiceImpl extends ServiceImplPlus<BaseContainerMapper, BaseContainer, BaseContainerVo, BaseContainerBo> implements IBaseContainerService {
  @Override
  public List<Map<String, Object>> getList(GetListBo getListBo) {
    Integer take = Optional.ofNullable(getListBo).map(m -> Convert.toInt(m.getTake())).orElse(200); // 查询top N，如果为空，默认200
    String name = Convert.toStr(getListBo.getName());
    // amis下拉框搜索
    if (StringUtils.isEmpty(name)) {
      name = Convert.toStr(getListBo.getTerm());
    }

    String searchFields = Convert.toStr(getListBo.getSearchFields());

    LambdaQueryWrapper<BaseContainer> queryWrapper = new LambdaQueryWrapper<>();
    String finalName = name;
    queryWrapper
      .and(StringUtils.isNotEmpty(name), a -> a.like(StringUtils.isNotEmpty(finalName), BaseContainer::getContainerNo, finalName)
        .or()
        .like(StringUtils.isNotEmpty(finalName), BaseContainer::getContainerType, finalName)
        .or()
        .like(StringUtils.isNotEmpty(finalName), BaseContainer::getSealNo, finalName)
      ); // 关键词对编号和名称模糊查询

    try {
      List<String> fields = CollUtil.newArrayList("containerId", "containerNo", "containerType", "size", "sealNo", "cabinLwhBooking", "cabinLwhActual", "cabinLwhAfter", "cube", "weight"); // 查询默认字段
      // 自定义查询字段

      if ("*".equals(searchFields)) {
        fields.clear(); // 清空，查询所有字段
      } else if (StringUtils.isNotEmpty(searchFields)) {
        fields = CollUtil.newArrayList(StringUtils.split(searchFields, ",")); // 查询指定字段
      }
      // 自定义查询字段
      if (!fields.isEmpty()) {
        List<String> finalFields = fields;
        queryWrapper.select(BaseContainer.class, s -> finalFields.contains(s.getProperty()));
      }
      queryWrapper.orderByAsc(BaseContainer::getContainerId); // 排序
      queryWrapper.last("limit " + take); // top N
      return this.baseMapper.selectMaps(queryWrapper);
    } catch (Exception error) {
      var msg = "异常错误信息：" + error.toString();
      throw new ServiceException(msg);
    }
  }

}

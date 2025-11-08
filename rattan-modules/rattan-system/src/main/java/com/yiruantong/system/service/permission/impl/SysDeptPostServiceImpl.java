package com.yiruantong.system.service.permission.impl;

import cn.hutool.core.convert.Convert;
import com.yiruantong.system.domain.permission.SysDeptPost;
import com.yiruantong.system.domain.permission.bo.SysDeptPostBo;
import com.yiruantong.system.domain.permission.vo.SysDeptPostVo;
import com.yiruantong.system.mapper.permission.SysDeptMapper;
import com.yiruantong.system.mapper.permission.SysDeptPostMapper;
import com.yiruantong.system.service.permission.ISysDeptPostService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.dto.QueryBo;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.mybatis.enums.DataTypeEnum;
import com.yiruantong.common.mybatis.enums.QueryTypeEnum;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 部门岗位设置Service业务层处理
 *
 * @author YRT
 * @date 2024-07-10
 */
@RequiredArgsConstructor
@Service
public class SysDeptPostServiceImpl extends ServiceImplPlus<SysDeptPostMapper, SysDeptPost, SysDeptPostVo, SysDeptPostBo> implements ISysDeptPostService {
  private final SysDeptMapper deptMapper;

  /**
   * 分页查询前事件
   *
   * @param pageQuery 加载参数
   */
  @Override
  public void beforePageQuery(PageQuery pageQuery) {
    treeQuery(pageQuery, deptMapper);
  }

  static void treeQuery(PageQuery pageQuery, SysDeptMapper deptMapper) {
    Optional.ofNullable(pageQuery).map(PageQuery::getOtherParams).map(m -> m.get("currentDeptId")).ifPresent(deptId -> {
      Long currentDeptId = Convert.toLong(deptId);
      // 根据当前部门ID获取所有子孙ID，包含自己
      var childrenId = deptMapper.getChildrenId(currentDeptId);
      // 加载部门条件
      var queryBo = new QueryBo();
      queryBo.setQueryType(QueryTypeEnum.IN);
      queryBo.setColumn("deptId");
      queryBo.setValues(childrenId);
      queryBo.setDataType(DataTypeEnum.LONG);
      pageQuery.addQueryBo(queryBo);
    });
  }
}

package com.yiruantong.system.domain.core.vo;

import cn.hutool.core.lang.tree.Tree;
import lombok.Data;

import java.util.List;

/**
 * 角色部门列表树信息
 *
 * @author YiRuanTong
 */
@Data
public class DeptTreeSelectVo {

  /**
   * 选中部门列表
   */
  private List<Long> checkedKeys;

  /**
   * 下拉树结构列表
   */
  private List<Tree<Long>> depts;

}

package com.yiruantong.composite.service.basic;

import com.yiruantong.basic.domain.base.bo.LevelSettingBo;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;

import java.util.Map;

public interface IBaseConsignorCompositeService {
  /**
   * 删除前事件
   *
   * @param Ids
   * @return
   */
  int deleteByIds(Long[] Ids);

  /**
   * 销售等级设置
   *
   * @param bo 等级设置
   */
  R<Map<String, Object>> saveGrade(LevelSettingBo bo);

  /**
   * 获取销售等级
   */
  R<Map<String, Object>> getGrade();

  /**
   * 销售等级匹配
   *
   * @param loginUser 等级设置
   */
  void matchGrade(LoginUser loginUser);
}

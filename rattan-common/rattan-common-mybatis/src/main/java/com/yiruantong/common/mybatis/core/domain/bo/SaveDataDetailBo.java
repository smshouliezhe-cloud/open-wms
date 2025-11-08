package com.yiruantong.common.mybatis.core.domain.bo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SaveDataDetailBo {
  /** 明细表名 */
  private String subTableName;

  /** 明细数据 */
  private List<Map<String, Object>> rows;
}

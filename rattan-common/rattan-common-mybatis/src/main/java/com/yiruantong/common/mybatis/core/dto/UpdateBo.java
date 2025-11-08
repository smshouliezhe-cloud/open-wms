package com.yiruantong.common.mybatis.core.dto;

import lombok.Data;
import com.yiruantong.common.mybatis.enums.DataTypeEnum;
import com.yiruantong.common.mybatis.enums.QueryTypeEnum;

import java.util.List;
import java.util.Map;

/**
 * @Description: 通用更新条件
 * @Author: 谢天保
 * @CreateDate: 2023/11/20 19:15
 * @Version: 1.0
 */
@Data
public class UpdateBo {
  /** 更新字段集合 */
  private  Map<String, Object> columns;
  /** 更新条件 */
  private List<QueryBo> queryBoList;
}

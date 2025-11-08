package com.yiruantong.common.mybatis.core.dto;

import lombok.Data;
import com.yiruantong.common.mybatis.enums.DataTypeEnum;
import com.yiruantong.common.mybatis.enums.QueryTypeEnum;

import java.util.List;

/**
 * @Description: 查询条件 @Author: 谢天保 @CreateDate: 2023/05/27 19:15 @Version: 1.0
 */
@Data
public class SumColumnNameBo {
  /** 字段名 */
  private String prop;
  /** 是否扩展字段 */
  private boolean isExpandField;
  /** 扩展字段类型 */
  private String expandFieldName;
}

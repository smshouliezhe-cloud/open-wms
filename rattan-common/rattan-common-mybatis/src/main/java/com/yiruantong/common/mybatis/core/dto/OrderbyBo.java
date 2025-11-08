package com.yiruantong.common.mybatis.core.dto;

import lombok.Data;
import com.yiruantong.common.mybatis.enums.OrderByTypeEnum;

/**
 * @Description: 排序字段 @Author: 谢天保 @CreateDate: 2023/05/27 19:15 @Version: 1.0
 */
@Data
public class OrderbyBo {
  /** 字段名 */
  private String column;
  /** 排序类型 */
  private OrderByTypeEnum orderByType;
}

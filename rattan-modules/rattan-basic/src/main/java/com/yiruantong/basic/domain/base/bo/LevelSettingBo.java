package com.yiruantong.basic.domain.base.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.basic.domain.base.BaseConsignor;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;


/**
 * 客户等级
 *
 * @author YRT
 * @date 2024-12-27
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BaseConsignor.class, reverseConvertGenerate = false)
public class LevelSettingBo extends BaseEntity {

  /**
   * 货主ID
   */
  private Long consignorId;
  /**
   * 等级C
   */
  private String gradeC;

  /**
   * 等级B
   */
  private String gradeB;

  /**
   * 等级A
   */
  private String gradeA;

  /**
   * 等级S
   */
  private String gradeS;

}

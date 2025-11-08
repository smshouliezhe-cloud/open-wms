package com.yiruantong.system.domain.goview.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.system.domain.goview.GoViewProject;

import java.util.Date;
import java.util.Map;


/**
 * 大屏项目业务对象 go_view_project
 *
 * @author YRT
 * @date 2024-11-01
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = GoViewProject.class, reverseConvertGenerate = false)
public class GoViewProjectBo extends BaseEntity {

  /**
   * 编号
   */
  private Long id;
  private Long projectId;

  /**
   * 项目名
   */
  @NotBlank(message = "项目名不能为空", groups = {AddGroup.class, EditGroup.class})
  private String projectName;

  /**
   * 封面
   */
  private String indexImage;

  /**
   * 发布(1发布-1取消发布)
   */
  private Long state;

  /**
   * 内容
   */
  private String content;

  /**
   * 备注
   */
  private String remark;

  /**
   * 排序号
   */
  private Long orderNum;

  /**
   * 扩展字段
   */
  private Map<String, Object> expandFields;

  /**
   * 删除时间
   */
  private Date deleteTime;

  /**
   * 删除人id
   */
  private Long deleteBy;

  /**
   * 删除人
   */
  private String deleteByName;


}

package com.yiruantong.system.domain.goview;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.common.mybatis.core.domain.TenantEntity;

import java.io.Serial;
import java.util.Date;
import java.util.Map;

/**
 * 大屏项目对象 go_view_project
 *
 * @author YRT
 * @date 2024-11-01
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "go_view_project", autoResultMap = true)
public class GoViewProject extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 编号
   */
  @TableId(value = "id")
  private Long id;

  /**
   * 项目名
   */
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
  @TableField(value = "expand_fields", typeHandler = JacksonTypeHandler.class)
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

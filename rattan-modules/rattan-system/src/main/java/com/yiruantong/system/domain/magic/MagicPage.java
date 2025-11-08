package com.yiruantong.system.domain.magic;

  import com.yiruantong.common.mybatis.core.domain.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;

import java.util.Map;
  import java.util.Date;
  import com.fasterxml.jackson.annotation.JsonFormat;


import java.io.Serial;

/**
 * 页面开发对象 magic_page
 *
 * @author YRT
 * @date 2024-12-14
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "magic_page", autoResultMap = true)
public class MagicPage extends TenantEntity {

@Serial
private static final long serialVersionUID=1L;

  /**
   * 主键
   */
    @TableId(value = "page_id")
  private Long pageId;

  /**
   * 页面标题
   */
  private String title;

  /**
   * 副标题
   */
  private String subtitle;

  /**
   * 父页面
   */
  private Long parentId;

  /**
   * 页面json
   */
  private String pageschema;

  /**
   * 状态：0、正常，1、禁用
   */
  private Long status;

  /**
   * 分类编码
   */
  private String classify;

  /**
   * 文件描述
   */
  private String remarks;

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
   * 备注
   */
  private String remark;

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

  /**
   * 页面脚本
   */
  private String pageScript;

  /**
   * 版本号
   */
    @Version
  private Long version;

  /**
   * 菜单ID
   */
  private Long menuId;

  /**
   * 表ID
   */
  private Long tableId;

  /**
   * 页面编号
   */
  private String pageCode;


}

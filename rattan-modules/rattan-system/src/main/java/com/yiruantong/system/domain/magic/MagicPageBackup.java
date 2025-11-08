package com.yiruantong.system.domain.magic;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.common.mybatis.core.domain.TenantEntity;

import java.io.Serial;
import java.util.Date;
import java.util.Map;

/**
 * 设计器备份对象 magic_page_backup
 *
 * @author YRT
 * @date 2024-11-20
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "magic_page_backup", autoResultMap = true)
public class MagicPageBackup extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 备份ID
   */
  @TableId(value = "backup_id")
  private Long backupId;

  /**
   * 主键
   */
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
   * 状态
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


}

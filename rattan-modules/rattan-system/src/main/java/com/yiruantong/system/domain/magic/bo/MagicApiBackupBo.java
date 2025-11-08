package com.yiruantong.system.domain.magic.bo;

import com.yiruantong.system.domain.magic.MagicApiBackup;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.Date;


/**
 * magic api 备份业务对象 magic_api_backup
 *
 * @author YRT
 * @date 2024-11-10
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = MagicApiBackup.class, reverseConvertGenerate = false)
public class MagicApiBackupBo extends BaseEntity {

  /**
   * 原对象ID
   */
  @NotBlank(message = "原对象ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private String id;

  /**
   * 原名称
   */
  @NotBlank(message = "原名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String name;

  /**
   * 备份内容
   */
  @NotBlank(message = "备份内容不能为空", groups = {AddGroup.class, EditGroup.class})
  private String content;

  /**
   * 标签
   */
  @NotBlank(message = "标签不能为空", groups = {AddGroup.class, EditGroup.class})
  private String tag;

  /**
   * 类型
   */
  @NotBlank(message = "类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String type;

  /**
   * 备份时间
   */
  @NotNull(message = "备份时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date createDate;


}

package com.yiruantong.system.domain.magic.bo;

import com.yiruantong.system.domain.magic.MagicApiFile;
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
 * magic api 接口业务对象 magic_api_file
 *
 * @author YRT
 * @date 2024-11-10
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = MagicApiFile.class, reverseConvertGenerate = false)
public class MagicApiFileBo extends BaseEntity {

  /**
   * 路径
   */
  @NotBlank(message = "路径不能为空", groups = {AddGroup.class, EditGroup.class})
  private String filePath;

  /**
   * 内容
   */
  @NotBlank(message = "内容不能为空", groups = {AddGroup.class, EditGroup.class})
  private String fileContent;

  /**
   * 备注
   */
  @NotBlank(message = "备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String remark;

  /**
   * 删除时间
   */
  @NotNull(message = "删除时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date deleteTime;

  /**
   * 删除人id
   */
  @NotNull(message = "删除人id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long deleteBy;

  /**
   * 删除人
   */
  @NotBlank(message = "删除人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String deleteByName;


}

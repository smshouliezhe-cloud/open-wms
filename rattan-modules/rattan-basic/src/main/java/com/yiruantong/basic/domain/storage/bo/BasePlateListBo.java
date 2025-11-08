package com.yiruantong.basic.domain.storage.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.basic.domain.storage.BasePlate;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;


/**
 * 容器管理业务对象 base_plate
 *
 * @author YiRuanTong
 * @date 2023-10-18
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BasePlate.class, reverseConvertGenerate = false)
public class BasePlateListBo extends BaseEntity {

  /**
   * 仓库ID
   */
  @NotNull(message = "仓库ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long storageId;

  /**
   * 仓库名称
   */
  @NotBlank(message = "仓库名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageName;

  /**
   * 容器类别
   */
  @NotBlank(message = "容器类别不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateType;

  /**
   * 编号前缀
   */
  @NotBlank(message = "编号前缀不能为空", groups = {AddGroup.class, EditGroup.class})
  private String codePrefix;

  /**
   * 条数
   */
  @NotNull(message = "条数不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long generate;
  /**
   * 容器规格
   */
  @NotBlank(message = "容器规格不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateSpec;

}

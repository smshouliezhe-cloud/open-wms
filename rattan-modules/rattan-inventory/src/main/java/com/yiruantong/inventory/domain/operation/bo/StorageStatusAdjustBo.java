package com.yiruantong.inventory.domain.operation.bo;

import com.yiruantong.inventory.domain.operation.StorageStatusAdjust;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 状态属性调整业务对象 storage_status_adjust
 *
 * @author YRT
 * @date 2025-02-14
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = StorageStatusAdjust.class, reverseConvertGenerate = false)
public class StorageStatusAdjustBo extends BaseEntity {

      /**
       * 调整ID
       */
        @NotNull(message = "调整ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long statusAdjustId;

      /**
       * 调整编号
       */
        @NotBlank(message = "调整编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String statusAdjustCode;

      /**
       * 调整仓库ID
       */
        @NotNull(message = "调整仓库ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long storageId;

      /**
       * 调整仓库
       */
        @NotBlank(message = "调整仓库不能为空", groups = { AddGroup.class, EditGroup.class })
    private String storageName;

      /**
       * 货主ID
       */
        @NotNull(message = "货主ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long consignorId;

      /**
       * 货主编号
       */
        @NotBlank(message = "货主编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String consignorCode;

      /**
       * 货主名称
       */
        @NotBlank(message = "货主名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String consignorName;

      /**
       * 调整状态
       */
        @NotBlank(message = "调整状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private String adjustStatus;

      /**
       * 调整日期
       */
        @NotNull(message = "调整日期不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date adjustDate;

      /**
       * 经手人ID
       */
        @NotNull(message = "经手人ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

      /**
       * 经手人
       */
        @NotBlank(message = "经手人不能为空", groups = { AddGroup.class, EditGroup.class })
    private String nickName;

      /**
       * 审核人
       */
        @NotBlank(message = "审核人不能为空", groups = { AddGroup.class, EditGroup.class })
    private String auditor;

      /**
       * 审核
       */
        @NotNull(message = "审核不能为空", groups = { AddGroup.class, EditGroup.class })
    private Byte auditing;

      /**
       * 审核日期
       */
        @NotNull(message = "审核日期不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date auditDate;

      /**
       * 审核备注
       */
        @NotBlank(message = "审核备注不能为空", groups = { AddGroup.class, EditGroup.class })
    private String auditRemark;

      /**
       * 合计毛重
       */
        @NotNull(message = "合计毛重不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal totalWeight;

      /**
       * 排序号
       */
        @NotNull(message = "排序号不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long orderNum;

      /**
       * 扩展字段
       */
        @NotNull(message = "扩展字段不能为空", groups = { AddGroup.class, EditGroup.class })
    private Map<String, Object> expandFields;

      /**
       * 备注
       */
        @NotBlank(message = "备注不能为空", groups = { AddGroup.class, EditGroup.class })
    private String remark;

      /**
       * 删除时间
       */
        @NotNull(message = "删除时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date deleteTime;

      /**
       * 删除人id
       */
        @NotNull(message = "删除人id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long deleteBy;

      /**
       * 删除人
       */
        @NotBlank(message = "删除人不能为空", groups = { AddGroup.class, EditGroup.class })
    private String deleteByName;


}

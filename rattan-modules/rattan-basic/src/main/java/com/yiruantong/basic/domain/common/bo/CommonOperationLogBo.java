package com.yiruantong.basic.domain.common.bo;

import com.yiruantong.basic.domain.common.CommonOperationLog;
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
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 业务操作日志业务对象 common_operation_log
 *
 * @author YRT
 * @date 2025-03-15
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CommonOperationLog.class, reverseConvertGenerate = false)
public class CommonOperationLogBo extends BaseEntity {

      /**
       * 历史ID
       */
        @NotNull(message = "历史ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long operationId;

      /**
       * 单据类型
       */
        @NotBlank(message = "单据类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String billType;

      /**
       * 单据id
       */
        @NotNull(message = "单据id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long billId;

      /**
       * 单号
       */
        @NotBlank(message = "单号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String billCode;

      /**
       * 状态类型
       */
        @NotBlank(message = "状态类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String statusType;

      /**
       * 操作类型
       */
        @NotBlank(message = "操作类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String operationType;

      /**
       * 变更前状态
       */
        @NotBlank(message = "变更前状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private String fromStatus;

      /**
       * 变更后状态
       */
        @NotBlank(message = "变更后状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private String toStatus;

      /**
       * 状态级别
       */
        @NotBlank(message = "状态级别不能为空", groups = { AddGroup.class, EditGroup.class })
    private String statusLevel;

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

      /**
       * 模块ID
       */
        @NotNull(message = "模块ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long menuId;


}

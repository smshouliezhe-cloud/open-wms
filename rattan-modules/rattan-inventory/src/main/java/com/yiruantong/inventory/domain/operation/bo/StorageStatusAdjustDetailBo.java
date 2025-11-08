package com.yiruantong.inventory.domain.operation.bo;

import com.yiruantong.inventory.domain.operation.StorageStatusAdjustDetail;
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
 * 状态属性调整明细业务对象 storage_status_adjust_detail
 *
 * @author YRT
 * @date 2025-02-18
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = StorageStatusAdjustDetail.class, reverseConvertGenerate = false)
public class StorageStatusAdjustDetailBo extends BaseEntity {

      /**
       * 调整ID
       */
        @NotNull(message = "调整ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long statusAdjustDetailId;

      /**
       * 调整ID
       */
        @NotNull(message = "调整ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long statusAdjustId;

      /**
       * 库存ID
       */
        @NotNull(message = "库存ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long inventoryId;

      /**
       * 货位名称
       */
        @NotBlank(message = "货位名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String positionName;

      /**
       * 库存量
       */
        @NotNull(message = "库存量不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal productStorage;

      /**
       * 产品ID
       */
        @NotNull(message = "产品ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long productId;

      /**
       * 产品编号
       */
        @NotBlank(message = "产品编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String productCode;

      /**
       * 产品名称
       */
        @NotBlank(message = "产品名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String productName;

      /**
       * 条形码
       */
        @NotBlank(message = "条形码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String productModel;

      /**
       * 产品规格
       */
        @NotBlank(message = "产品规格不能为空", groups = { AddGroup.class, EditGroup.class })
    private String productSpec;

      /**
       * 批次号
       */
        @NotBlank(message = "批次号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String batchNumber;

      /**
       * 保质期天数
       */
        @NotNull(message = "保质期天数不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long shelfLifeDay;

      /**
       * 生产日期
       */
        @NotNull(message = "生产日期不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date produceDate;

      /**
       * 到期日期
       */
        @NotNull(message = "到期日期不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date limitDate;

      /**
       * 原属性
       */
        @NotBlank(message = "原属性不能为空", groups = { AddGroup.class, EditGroup.class })
    private String productAttribute;

      /**
       * 原状态
       */
        @NotBlank(message = "原状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private String storageStatus;

      /**
       * 目标属性
       */
        @NotBlank(message = "目标属性不能为空", groups = { AddGroup.class, EditGroup.class })
    private String productAttributeTarget;

      /**
       * 目标状态
       */
        @NotBlank(message = "目标状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private String storageStatusTarget;

      /**
       * 合计毛重
       */
        @NotNull(message = "合计毛重不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal rowWeight;

      /**
       * 单位毛重
       */
        @NotNull(message = "单位毛重不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal weight;

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
       * 单位净重
       */
        @NotNull(message = "单位净重不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal netWeight;

      /**
       * 小计净重
       */
        @NotNull(message = "小计净重不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal rowNetWeight;

      /**
       * 来源类别
       */
        @NotBlank(message = "来源类别不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sourceType;

      /**
       * 来源主表ID
       */
        @NotBlank(message = "来源主表ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sourceMainId;

      /**
       * 来源明细ID
       */
        @NotBlank(message = "来源明细ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sourceDetailId;

      /**
       * 项目号
       */
        @NotBlank(message = "项目号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String projectCode;

      /**
       * 箱号
       */
        @NotBlank(message = "箱号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String caseNumber;

      /**
       * 分拣状态
       */
        @NotNull(message = "分拣状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long sortingStatus;

      /**
       * 供应商ID
       */
        @NotNull(message = "供应商ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long providerId;

      /**
       * 供应商编号
       */
        @NotBlank(message = "供应商编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String providerCode;

      /**
       * 供应商全称
       */
        @NotBlank(message = "供应商全称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String providerName;

      /**
       * 供应商简称
       */
        @NotBlank(message = "供应商简称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String providerShortName;

      /**
       * 订单行号
       */
        @NotBlank(message = "订单行号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String orderLineCode;

      /**
       * 异议号
       */
        @NotBlank(message = "异议号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String objectionCode;

      /**
       * 缺货数量
       */
        @NotNull(message = "缺货数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal lackStorage;


}

package com.yiruantong.inventory.domain.operation.bo;

import com.yiruantong.inventory.domain.operation.CoreInventoryAdvise;
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


/**
 * 建议采购转遇到货业务对象 core_inventory_advise
 *
 * @author YRT
 * @date 2025-02-07
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CoreInventoryAdvise.class, reverseConvertGenerate = false)
public class CoreInventoryAdviseBo extends BaseEntity {

      /**
       * 行ID
       */
        @NotNull(message = "行ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long adviseId;

      /**
       * 仓库ID
       */
        @NotNull(message = "仓库ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long storageId;

      /**
       * 仓库名称
       */
        @NotBlank(message = "仓库名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String storageName;

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
       * 供应商简称
       */
        @NotBlank(message = "供应商简称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String providerShortName;

      /**
       * 库存量
       */
        @NotNull(message = "库存量不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal productStorage;

      /**
       * 最低库存
       */
        @NotNull(message = "最低库存不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long storageLower;

      /**
       * 30天销量
       */
        @NotNull(message = "30天销量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long thirtyDaySale;

      /**
       *  周期
       */
        @NotNull(message = " 周期不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long cycle;

      /**
       * 最近30天销量均值
       */
        @NotNull(message = "最近30天销量均值不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal thirtyDayAverageSale;

      /**
       *  建议采购量
       */
        @NotNull(message = " 建议采购量不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal adviseQty;

      /**
       * 备注
       */
        @NotBlank(message = "备注不能为空", groups = { AddGroup.class, EditGroup.class })
    private String remark;

      /**
       * 预定数量
       */
        @NotNull(message = "预定数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long reserveQty;

      /**
       * 成本价
       */
        @NotNull(message = "成本价不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal purchasePrice;

      /**
       * 仓库编号
       */
        @NotBlank(message = "仓库编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String storageCode;


}

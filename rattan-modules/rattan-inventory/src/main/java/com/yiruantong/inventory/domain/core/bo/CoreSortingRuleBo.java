package com.yiruantong.inventory.domain.core.bo;

import com.yiruantong.inventory.domain.core.CoreSortingRule;
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


/**
 * 分拣规则业务对象 core_sorting_rule
 *
 * @author YRT
 * @date 2025-02-27
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CoreSortingRule.class, reverseConvertGenerate = false)
public class CoreSortingRuleBo extends BaseEntity {

      /**
       * 分拣规则ID
       */
        @NotNull(message = "分拣规则ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long sortingRuleId;

      /**
       * 单价类型
       */
        @NotBlank(message = "单价类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String billType;

      /**
       * 单据ID
       */
        @NotNull(message = "单据ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long billId;

      /**
       * 单据号
       */
        @NotBlank(message = "单据号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String billCode;

      /**
       * 单据明细ID
       */
        @NotNull(message = "单据明细ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long billDetailId;

      /**
       * 商品ID
       */
        @NotNull(message = "商品ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long productId;

      /**
       * 商品编号
       */
        @NotBlank(message = "商品编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String productCode;

      /**
       * 商品名称
       */
        @NotBlank(message = "商品名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String productName;

      /**
       * 规则类型
       */
        @NotBlank(message = "规则类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sortingType;

      /**
       * 分拣规则
       */
        @NotNull(message = "分拣规则不能为空", groups = { AddGroup.class, EditGroup.class })
    private Map<String, Object> sortingRule;

      /**
       * 来源类别
       */
        @NotBlank(message = "来源类别不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sourceType;

      /**
       * 来源ID
       */
        @NotBlank(message = "来源ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sourceId;

      /**
       * 来源单号
       */
        @NotBlank(message = "来源单号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sourceCode;

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


}

package com.yiruantong.outbound.domain.out.bo;

import com.yiruantong.outbound.domain.out.OutPackageDetail;
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
 * 打包单明细业务对象 out_package_detail
 *
 * @author YRT
 * @date 2025-04-01
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = OutPackageDetail.class, reverseConvertGenerate = false)
public class OutPackageDetailBo extends BaseEntity {

      /**
       * 出库明细ID
       */
        @NotNull(message = "出库明细ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long packageDetailId;

      /**
       * 出库单ID
       */
        @NotNull(message = "出库单ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long packageId;

      /**
       * 销售单ID
       */
        @NotNull(message = "销售单ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long orderId;

      /**
       * 销售明细ID
       */
        @NotNull(message = "销售明细ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long orderDetailId;

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
       * 商品规格
       */
        @NotBlank(message = "商品规格不能为空", groups = { AddGroup.class, EditGroup.class })
    private String productSpec;

      /**
       * 小单位
       */
        @NotBlank(message = "小单位不能为空", groups = { AddGroup.class, EditGroup.class })
    private String smallUnit;

      /**
       * 大单位
       */
        @NotBlank(message = "大单位不能为空", groups = { AddGroup.class, EditGroup.class })
    private String bigUnit;

      /**
       * 打包数量
       */
        @NotNull(message = "打包数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal packageQuantity;

      /**
       * 换算关系
       */
        @NotNull(message = "换算关系不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal unitConvert;

      /**
       * 单位换算
       */
        @NotBlank(message = "单位换算不能为空", groups = { AddGroup.class, EditGroup.class })
    private String unitConvertText;

      /**
       * 采购价
       */
        @NotNull(message = "采购价不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal purchasePrice;

      /**
       * 销售价
       */
        @NotNull(message = "销售价不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal salePrice;

      /**
       * 折扣金额
       */
        @NotNull(message = "折扣金额不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal discountAmount;

      /**
       * 应收总额
       */
        @NotNull(message = "应收总额不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal saleAmount;

      /**
       * 税率
       */
        @NotNull(message = "税率不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal rate;

      /**
       * 税价
       */
        @NotNull(message = "税价不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal ratePrice;

      /**
       * 价税合计
       */
        @NotNull(message = "价税合计不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal rateAmount;

      /**
       * SN码
       */
        @NotBlank(message = "SN码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String singleSignCode;

      /**
       * 批次号
       */
        @NotBlank(message = "批次号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String batchNumber;

      /**
       * 出库单号
       */
        @NotBlank(message = "出库单号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String orderCode;

      /**
       * 箱号
       */
        @NotBlank(message = "箱号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String caseNumber;

      /**
       * 单位毛重
       */
        @NotNull(message = "单位毛重不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal weight;

      /**
       * 小计毛重
       */
        @NotNull(message = "小计毛重不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal rowWeight;

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
       * 类别编号
       */
        @NotNull(message = "类别编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long typeId;

      /**
       * 类别名称
       */
        @NotBlank(message = "类别名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String typeName;

      /**
       * 产品型号
       */
        @NotBlank(message = "产品型号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String productBarCode;

      /**
       * 品牌ID
       */
        @NotNull(message = "品牌ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long brandId;

      /**
       * 品牌名
       */
        @NotBlank(message = "品牌名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String brandName;

      /**
       * 生产日期
       */
        @NotNull(message = "生产日期不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date produceDate;

      /**
       * 大单位数量
       */
        @NotNull(message = "大单位数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal bigQty;

      /**
       * 合计重量(吨)
       */
        @NotNull(message = "合计重量(吨)不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal rowWeightTon;

      /**
       * 单位体积
       */
        @NotNull(message = "单位体积不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal unitCube;

      /**
       * 小计体积
       */
        @NotNull(message = "小计体积不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal rowCube;

      /**
       * 小计包裹数
       */
        @NotNull(message = "小计包裹数不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal rowPackage;

      /**
       * 区域id
       */
        @NotNull(message = "区域id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long consignorIdSale;

      /**
       * 区域
       */
        @NotBlank(message = "区域不能为空", groups = { AddGroup.class, EditGroup.class })
    private String consignorNameSale;

      /**
       * 项目号
       */
        @NotBlank(message = "项目号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String projectCode;

      /**
       * 拍号
       */
        @NotBlank(message = "拍号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String plateCode;

  
}

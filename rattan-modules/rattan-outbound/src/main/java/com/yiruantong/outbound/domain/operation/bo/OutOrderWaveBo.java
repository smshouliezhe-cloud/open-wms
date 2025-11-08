package com.yiruantong.outbound.domain.operation.bo;

import com.yiruantong.outbound.domain.operation.OutOrderWave;
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
 * 出库单波次业务对象 out_order_wave
 *
 * @author YRT
 * @date 2025-02-07
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = OutOrderWave.class, reverseConvertGenerate = false)
public class OutOrderWaveBo extends BaseEntity {

      /**
       * 波次单ID
       */
        @NotNull(message = "波次单ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long orderWaveId;

      /**
       * 波次单号
       */
        @NotBlank(message = "波次单号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String orderWaveCode;

      /**
       * 单据类型
       */
        @NotBlank(message = "单据类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String orderType;

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
       * 订单数
       */
        @NotNull(message = "订单数不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long orderCount;

      /**
       * 冻结数量
       */
        @NotNull(message = "冻结数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal freezeQuantity;

      /**
       * 完成数量
       */
        @NotNull(message = "完成数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long finishedCount;

      /**
       * 未完成数量
       */
        @NotNull(message = "未完成数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long unFinishedCount;

      /**
       * 订单数量
       */
        @NotNull(message = "订单数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal totalQuanityOrder;

      /**
       * 波次状态
       */
        @NotBlank(message = "波次状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private String waveStatus;

      /**
       * 打印状态
       */
        @NotNull(message = "打印状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long printStatus;

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
       * 货主编号
       */
        @NotBlank(message = "货主编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String consignorName;

      /**
       * 快递公司ID
       */
        @NotNull(message = "快递公司ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long expressCorpId;

      /**
       * 快递公司名称
       */
        @NotBlank(message = "快递公司名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String expressCorpName;

      /**
       * 拣货状态
       */
        @NotBlank(message = "拣货状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private String pickingStatus;

      /**
       * 拣货人ID
       */
        @NotNull(message = "拣货人ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long pickUserId;

      /**
       * 拣货人
       */
        @NotBlank(message = "拣货人不能为空", groups = { AddGroup.class, EditGroup.class })
    private String pickNickName;

      /**
       * 拣货数量
       */
        @NotNull(message = "拣货数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal pickQuantity;

      /**
       * 拣配单打印次数
       */
        @NotNull(message = "拣配单打印次数不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long pickingPrint;

      /**
       * 出库单打印次数
       */
        @NotNull(message = "出库单打印次数不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long outerPrint;

      /**
       * 物流单打印次数
       */
        @NotNull(message = "物流单打印次数不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long logisticsPrint;

      /**
       * 合计重量
       */
        @NotNull(message = "合计重量不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal totalWeight;

      /**
       * 生成子波次
       */
        @NotNull(message = "生成子波次不能为空", groups = { AddGroup.class, EditGroup.class })
    private Byte subBatch;

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
       * 合计净重
       */
        @NotNull(message = "合计净重不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal totalNetWeight;

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
       * 仓库编号
       */
        @NotBlank(message = "仓库编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String storageCode;


}

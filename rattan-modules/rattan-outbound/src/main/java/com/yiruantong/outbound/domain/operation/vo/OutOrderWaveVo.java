package com.yiruantong.outbound.domain.operation.vo;

  import java.math.BigDecimal;
  import java.util.Date;
  import com.fasterxml.jackson.annotation.JsonFormat;
import com.yiruantong.outbound.domain.operation.OutOrderWave;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yiruantong.common.excel.annotation.ExcelDictFormat;
import com.yiruantong.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;
import java.util.Map;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * 出库单波次视图对象 out_order_wave
 *
 * @author YRT
 * @date 2025-02-07
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = OutOrderWave.class)
public class OutOrderWaveVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

      /**
       * 波次单ID
       */
      @ExcelProperty(value = "波次单ID")
    private Long orderWaveId;

      /**
       * 波次单号
       */
      @ExcelProperty(value = "波次单号")
    private String orderWaveCode;

      /**
       * 单据类型
       */
      @ExcelProperty(value = "单据类型")
    private String orderType;

      /**
       * 仓库ID
       */
      @ExcelProperty(value = "仓库ID")
    private Long storageId;

      /**
       * 仓库名称
       */
      @ExcelProperty(value = "仓库名称")
    private String storageName;

      /**
       * 订单数
       */
      @ExcelProperty(value = "订单数")
    private Long orderCount;

      /**
       * 冻结数量
       */
      @ExcelProperty(value = "冻结数量")
    private BigDecimal freezeQuantity;

      /**
       * 完成数量
       */
      @ExcelProperty(value = "完成数量")
    private Long finishedCount;

      /**
       * 未完成数量
       */
      @ExcelProperty(value = "未完成数量")
    private Long unFinishedCount;

      /**
       * 订单数量
       */
      @ExcelProperty(value = "订单数量")
    private BigDecimal totalQuanityOrder;

      /**
       * 波次状态
       */
      @ExcelProperty(value = "波次状态")
    private String waveStatus;

      /**
       * 打印状态
       */
      @ExcelProperty(value = "打印状态")
    private Long printStatus;

      /**
       * 货主ID
       */
      @ExcelProperty(value = "货主ID")
    private Long consignorId;

      /**
       * 货主编号
       */
      @ExcelProperty(value = "货主编号")
    private String consignorCode;

      /**
       * 货主编号
       */
      @ExcelProperty(value = "货主编号")
    private String consignorName;

      /**
       * 快递公司ID
       */
      @ExcelProperty(value = "快递公司ID")
    private Long expressCorpId;

      /**
       * 快递公司名称
       */
      @ExcelProperty(value = "快递公司名称")
    private String expressCorpName;

      /**
       * 拣货状态
       */
      @ExcelProperty(value = "拣货状态")
    private String pickingStatus;

      /**
       * 拣货人ID
       */
      @ExcelProperty(value = "拣货人ID")
    private Long pickUserId;

      /**
       * 拣货人
       */
      @ExcelProperty(value = "拣货人")
    private String pickNickName;

      /**
       * 拣货数量
       */
      @ExcelProperty(value = "拣货数量")
    private BigDecimal pickQuantity;

      /**
       * 拣配单打印次数
       */
      @ExcelProperty(value = "拣配单打印次数")
    private Long pickingPrint;

      /**
       * 出库单打印次数
       */
      @ExcelProperty(value = "出库单打印次数")
    private Long outerPrint;

      /**
       * 物流单打印次数
       */
      @ExcelProperty(value = "物流单打印次数")
    private Long logisticsPrint;

      /**
       * 合计重量
       */
      @ExcelProperty(value = "合计重量")
    private BigDecimal totalWeight;

      /**
       * 生成子波次
       */
      @ExcelProperty(value = "生成子波次")
    private Byte subBatch;

      /**
       * 排序号
       */
      @ExcelProperty(value = "排序号")
    private Long orderNum;

      /**
       * 扩展字段
       */
      @ExcelProperty(value = "扩展字段")
    private Map<String, Object> expandFields;

      /**
       * 备注
       */
      @ExcelProperty(value = "备注")
    private String remark;

      /**
       * 创建人
       */
      @ExcelProperty(value = "创建人")
    private String createByName;

      /**
       * 创建时间
       */
      @ExcelProperty(value = "创建时间")
    private Date createTime;

      /**
       * 修改人
       */
      @ExcelProperty(value = "修改人")
    private String updateByName;

      /**
       * 修改时间
       */
      @ExcelProperty(value = "修改时间")
    private Date updateTime;

      /**
       * 删除时间
       */
      @ExcelProperty(value = "删除时间")
    private Date deleteTime;

      /**
       * 删除人id
       */
      @ExcelProperty(value = "删除人id")
    private Long deleteBy;

      /**
       * 删除人
       */
      @ExcelProperty(value = "删除人")
    private String deleteByName;

      /**
       * 合计净重
       */
      @ExcelProperty(value = "合计净重")
    private BigDecimal totalNetWeight;

      /**
       * 来源类别
       */
      @ExcelProperty(value = "来源类别")
    private String sourceType;

      /**
       * 来源ID
       */
      @ExcelProperty(value = "来源ID")
    private String sourceId;

      /**
       * 来源单号
       */
      @ExcelProperty(value = "来源单号")
    private String sourceCode;

      /**
       * 经手人ID
       */
      @ExcelProperty(value = "经手人ID")
    private Long userId;

      /**
       * 经手人
       */
      @ExcelProperty(value = "经手人")
    private String nickName;

      /**
       * 仓库编号
       */
      @ExcelProperty(value = "仓库编号")
    private String storageCode;


}

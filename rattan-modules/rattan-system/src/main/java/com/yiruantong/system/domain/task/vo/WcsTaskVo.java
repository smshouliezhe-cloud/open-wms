package com.yiruantong.system.domain.task.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.system.domain.task.WcsTask;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * WCS接口视图对象 wcs_task
 *
 * @author YRT
 * @date 2025-01-09
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = WcsTask.class)
public class WcsTaskVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 任务ID
   */
  @ExcelProperty(value = "任务ID")
  private Long wcsTaskId;

  /**
   * 任务类型
   */
  @ExcelProperty(value = "任务类型")
  private String wcsTaskType;

  /**
   * 来源主表ID
   */
  @ExcelProperty(value = "来源主表ID")
  private Long mainId;

  /**
   * 来源明细ID
   */
  @ExcelProperty(value = "来源明细ID")
  private Long detailId;

  /**
   * 执行单号
   */
  @ExcelProperty(value = "执行单号")
  private String billCode;

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
   * 货位
   */
  @ExcelProperty(value = "货位")
  private String positionName;

  /**
   * 产品ID
   */
  @ExcelProperty(value = "产品ID")
  private Long productId;

  /**
   * 产品编号
   */
  @ExcelProperty(value = "产品编号")
  private String productCode;

  /**
   * 产品名称
   */
  @ExcelProperty(value = "产品名称")
  private String productName;

  /**
   * 条形码
   */
  @ExcelProperty(value = "条形码")
  private String productModel;

  /**
   * 产品规格
   */
  @ExcelProperty(value = "产品规格")
  private String productSpec;

  /**
   * 来源单号
   */
  @ExcelProperty(value = "来源单号")
  private String sourceCode;

  /**
   * 批次号
   */
  @ExcelProperty(value = "批次号")
  private String batchNumber;

  /**
   * 生成日期
   */
  @ExcelProperty(value = "生成日期")
  private Date produceDate;

  /**
   * 拍号
   */
  @ExcelProperty(value = "拍号")
  private String plateCode;

  /**
   * 关联号
   */
  @ExcelProperty(value = "关联号")
  private String relationCode;

  /**
   * 数量
   */
  @ExcelProperty(value = "数量")
  private BigDecimal qty;

  /**
   * 排序号
   */
  @ExcelProperty(value = "排序号")
  private Long orderNum;

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
   * 推送状态
   */
  @ExcelProperty(value = "推送状态")
  private String pushStatus;

  /**
   * 推送时间
   */
  @ExcelProperty(value = "推送时间")
  private Date pushDate;

  /**
   * 推送消息
   */
  @ExcelProperty(value = "推送消息")
  private String pushMsg;

  /**
   * 推送次数
   */
  @ExcelProperty(value = "推送次数")
  private Long pushCount;

  /**
   * 来源ID
   */
  @ExcelProperty(value = "来源ID")
  private Long sourceId;

  /**
   * 仓库编号
   */
  @ExcelProperty(value = "仓库编号")
  private String storageCode;

  /**
   * sn号
   */
  @ExcelProperty(value = "sn号")
  private String singleSignCode;

  /**
   * wcs优先级
   */
  @ExcelProperty(value = "wcs优先级")
  private String itemGroup;


}

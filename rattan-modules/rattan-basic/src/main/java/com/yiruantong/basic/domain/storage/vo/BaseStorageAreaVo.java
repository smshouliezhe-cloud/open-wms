package com.yiruantong.basic.domain.storage.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.storage.BaseStorageArea;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 库区管理视图对象 base_storage_area
 *
 * @author YiRuanTong
 * @date 2024-12-31
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BaseStorageArea.class)
public class BaseStorageAreaVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 库区ID
   */
  @ExcelProperty(value = "库区ID")
  private Long storageAreaId;

  /**
   * 库区
   */
  @ExcelProperty(value = "库区")
  private String areaCode;

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
   * 摆放模式
   */
  @ExcelProperty(value = "摆放模式")
  private String shelveMode;

  /**
   * 通道数
   */
  @ExcelProperty(value = "通道数")
  private Long channelNum;

  /**
   * A面架数开始数
   */
  @ExcelProperty(value = "A面架数开始数")
  private Long shelveNumA1;

  /**
   * A面架数结束数
   */
  @ExcelProperty(value = "A面架数结束数")
  private Long shelveNumA2;

  /**
   * B面架数开始数
   */
  @ExcelProperty(value = "B面架数开始数")
  private Long shelveNumB1;

  /**
   * B面架数结束数
   */
  @ExcelProperty(value = "B面架数结束数")
  private Long shelveNumB2;

  /**
   * 列数
   */
  @ExcelProperty(value = "列数")
  private Long columnNum;

  /**
   * 层数
   */
  @ExcelProperty(value = "层数")
  private Long rowNum;

  /**
   * A面
   */
  @ExcelProperty(value = "A面")
  private Long isALine;

  /**
   * B面
   */
  @ExcelProperty(value = "B面")
  private Long isBLine;

  /**
   * 货位类型
   */
  @ExcelProperty(value = "货位类型")
  private Long positionType;

  /**
   * 货位编码规则
   */
  @ExcelProperty(value = "货位编码规则")
  private String positionRegular;

  /**
   * 拣货模式
   */
  @ExcelProperty(value = "拣货模式")
  private String pickMode;

  /**
   * 最大容量
   */
  @ExcelProperty(value = "最大容量")
  private BigDecimal maxCapacity;

  /**
   * 货架编码
   */
  @ExcelProperty(value = "货架编码")
  private String shelvesRegular;

  /**
   * 通道编码规则
   */
  @ExcelProperty(value = "通道编码规则")
  private String channelRegular;

  /**
   * 行编码规则
   */
  @ExcelProperty(value = "行编码规则")
  private String rowRegular;

  /**
   * 列编码规则
   */
  @ExcelProperty(value = "列编码规则")
  private String columnRegular;

  /**
   * 货架JSON
   */
  @ExcelProperty(value = "货架JSON")
  private String jsonData;

  /**
   * 温层类型
   */
  @ExcelProperty(value = "温层类型")
  private String thermocLine;

  /**
   * 货位最大重量
   */
  @ExcelProperty(value = "货位最大重量")
  private BigDecimal maxWeight;

  /**
   * 货位最大拍数
   */
  @ExcelProperty(value = "货位最大拍数")
  private Long maxBeatNumber;

  /**
   * 存货率计算
   */
  @ExcelProperty(value = "存货率计算")
  private String inventoryRate;

  /**
   * 上架操作员ID
   */
  @ExcelProperty(value = "上架操作员ID")
  private String userIds;

  /**
   * 上架操作员
   */
  @ExcelProperty(value = "上架操作员")
  private String userTrueNames;

  /**
   * 拣货操作员ID
   */
  @ExcelProperty(value = "拣货操作员ID")
  private String packUserIds;

  /**
   * 拣货操作员
   */
  @ExcelProperty(value = "拣货操作员")
  private String packUserTrueNames;

  /**
   * x6Data
   */
  @ExcelProperty(value = "x6Data")
  private String x6Data;

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
   * 自定义货架通道数据
   */
  @ExcelProperty(value = "自定义货架通道数据")
  private String channelDataList;

  /**
   * svg地址
   */
  @ExcelProperty(value = "svg地址")
  private String svgUrl;


}

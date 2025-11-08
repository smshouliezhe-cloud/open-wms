package com.yiruantong.basic.domain.storage.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.storage.BasePosition;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 货位管理视图对象 base_position
 *
 * @author YiRuanTong
 * @date 2024-12-25
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BasePosition.class)
public class BasePositionVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ExcelProperty(value = "ID")
  private Long positionId;

  /**
   * 父级ID
   */
  @ExcelProperty(value = "父级ID")
  private Long parentId;

  /**
   * 货位名称
   */
  @ExcelProperty(value = "货位名称")
  private String positionName;

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
   * 货位类型
   */
  @ExcelProperty(value = "货位类型")
  private Byte positionType;

  /**
   * 是否锁定
   */
  @ExcelProperty(value = "是否锁定")
  private Byte isLocked;

  /**
   * 是否冻结
   */
  @ExcelProperty(value = "是否冻结")
  private Byte isFreeze;

  /**
   * 最大容量
   */
  @ExcelProperty(value = "最大容量")
  private BigDecimal maxCapacity;

  /**
   * 通道代码
   */
  @ExcelProperty(value = "通道代码")
  private String channelCode;

  /**
   * 面
   */
  @ExcelProperty(value = "面")
  private String lineCode;

  /**
   * 货架号
   */
  @ExcelProperty(value = "货架号")
  private String shelveCode;

  /**
   * 列
   */
  @ExcelProperty(value = "列")
  private String columnCode;

  /**
   * 层
   */
  @ExcelProperty(value = "层")
  private String rowCode;

  /**
   * 库区
   */
  @ExcelProperty(value = "库区")
  private String areaCode;

  /**
   * 摆放模式
   */
  @ExcelProperty(value = "摆放模式")
  private String shelveMode;

  /**
   * 是否支持混物料编号
   */
  @ExcelProperty(value = "是否支持混物料编号")
  private Byte isMixProduct;

  /**
   * 货位长度
   */
  @ExcelProperty(value = "货位长度")
  private BigDecimal positionLength;

  /**
   * 货位宽度
   */
  @ExcelProperty(value = "货位宽度")
  private BigDecimal positionWidth;

  /**
   * 最低库存
   */
  @ExcelProperty(value = "最低库存")
  private Long minCapacity;

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
   * 是否可用
   */
  @ExcelProperty(value = "是否可用")
  private Byte enable;

  /**
   * 最大拍数
   */
  @ExcelProperty(value = "最大拍数")
  private Long maxPaiQty;

  /**
   * 温层
   */
  @ExcelProperty(value = "温层")
  private String thermocLine;


}

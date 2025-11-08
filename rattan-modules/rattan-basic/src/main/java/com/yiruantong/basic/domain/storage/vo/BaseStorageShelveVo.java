package com.yiruantong.basic.domain.storage.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.storage.BaseStorageShelve;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * 仓库货架视图对象 base_storage_shelve
 *
 * @author YRT
 * @date 2024-02-22
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BaseStorageShelve.class)
public class BaseStorageShelveVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 货架信息ID
   */
  @ExcelProperty(value = "货架信息ID")
  private Long storageShelveId;

  /**
   *
   */
  @ExcelProperty(value = "")
  private String areaCode;

  /**
   * 仓库ID
   */
  @ExcelProperty(value = "仓库ID")
  private Long storageId;

  /**
   *
   */
  @ExcelProperty(value = "")
  private String storageName;

  /**
   *
   */
  @ExcelProperty(value = "")
  private String channelCode;

  /**
   *
   */
  @ExcelProperty(value = "")
  private String shelveCode;

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
   *
   */
  @ExcelProperty(value = "")
  private String positionType;

  /**
   *
   */
  @ExcelProperty(value = "")
  private String columnRegular;

  /**
   *
   */
  @ExcelProperty(value = "")
  private String positionRegular;

  /**
   * 最大容量
   */
  @ExcelProperty(value = "最大容量")
  private Long maxCapacity;

  /**
   *
   */
  @ExcelProperty(value = "")
  private String shelvesRegular;

  /**
   *
   */
  @ExcelProperty(value = "")
  private String channelRegular;

  /**
   *
   */
  @ExcelProperty(value = "")
  private String rowRegular;

  /**
   * 排序号
   */
  @ExcelProperty(value = "排序号")
  private Long orderNo;

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


}

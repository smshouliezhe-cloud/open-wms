package com.yiruantong.basic.domain.storage.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.storage.BasePlateHistory;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 容器使用轨迹视图对象 base_plateHistory
 *
 * @author YiRuanTong
 * @date 2023-10-19
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BasePlateHistory.class)
public class BasePlateHistoryVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 容器历史ID
   */
  @ExcelProperty(value = "容器历史ID")
  private Long plateHistoryId;

  /**
   * 单据ID
   */
  @ExcelProperty(value = "单据ID")
  private Long billId;

  /**
   * 单据编号
   */
  @ExcelProperty(value = "单据编号")
  private String billCode;

  /**
   * 容器Id
   */
  @ExcelProperty(value = "容器Id")
  private Long plateId;

  /**
   * 容易编号
   */
  @ExcelProperty(value = "容易编号")
  private String plateCode;

  /**
   * 容器类型
   */
  @ExcelProperty(value = "容器类型")
  private String plateType;

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
   * 货主名称
   */
  @ExcelProperty(value = "货主名称")
  private String consignorName;

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
   * 操作类型
   */
  @ExcelProperty(value = "操作类型")
  private String actionType;

  /**
   * 货位
   */
  @ExcelProperty(value = "货位")
  private String positionName;

  /**
   * 使用前状态
   */
  @ExcelProperty(value = "使用前状态")
  private String beforeStatus;

  /**
   * 使用后状态
   */
  @ExcelProperty(value = "使用后状态")
  private String afterStatus;

  /**
   * 操作时间
   */
  @ExcelProperty(value = "操作时间")
  private Date operateDate;

  /**
   * 扩展字段
   */
  @ExcelProperty(value = "扩展字段")
  private Map<String, Object> expandFields;

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

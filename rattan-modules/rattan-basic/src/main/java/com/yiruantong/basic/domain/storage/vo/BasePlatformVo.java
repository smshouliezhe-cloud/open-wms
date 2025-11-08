package com.yiruantong.basic.domain.storage.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.storage.BasePlatform;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 月台管理视图对象 base_platform
 *
 * @author YRT
 * @date 2024-05-21
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BasePlatform.class)
public class BasePlatformVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 月台ID
   */
  @ExcelProperty(value = "月台ID")
  private Long platformId;

  /**
   * 月台编号
   */
  @ExcelProperty(value = "月台编号")
  private String platformCode;

  /**
   * 月台名称
   */
  @ExcelProperty(value = "月台名称")
  private String platformName;

  /**
   * 仓库ID
   */
  @ExcelProperty(value = "仓库ID")
  private Long storageId;

  /**
   * 所属仓库
   */
  @ExcelProperty(value = "所属仓库")
  private String storageName;

  /**
   * 是否可用
   */
  @ExcelProperty(value = "是否可用")
  private Long enable;

  /**
   * 备注
   */
  @ExcelProperty(value = "备注")
  private String remark;

  /**
   * 扩展字段
   */
  @ExcelProperty(value = "扩展字段")
  private Map<String, Object> expandFields;

  /**
   * 网点名称
   */
  @ExcelProperty(value = "网点名称")
  private String siteName;

  /**
   * 月台状态
   */
  @ExcelProperty(value = "月台状态")
  private String platStatus;

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
   * 排序号
   */
  @ExcelProperty(value = "排序号")
  private Long orderNum;

  /**
   * 开始时间
   */
  @ExcelProperty(value = "开始时间")
  private Date startTime;

  /**
   * 结束时间
   */
  @ExcelProperty(value = "结束时间")
  private Date endTime;

  /**
   * 月台使用类型
   */
  @ExcelProperty(value = "月台使用类型")
  private String platformType;

  /**
   * 车牌号
   */
  @ExcelProperty(value = "车牌号")
  private String truckNo;


}

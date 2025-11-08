package com.yiruantong.basic.domain.storage.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.storage.BaseShelveRegular;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 商品上架策略视图对象 base_shelve_regular
 *
 * @author YiRuanTong
 * @date 2024-01-18
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BaseShelveRegular.class)
public class BaseShelveRegularVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 规则ID
   */
  @ExcelProperty(value = "规则ID")
  private Long shelveRegularId;

  /**
   * 规则名称
   */
  @ExcelProperty(value = "规则名称")
  private String shelveRegularName;

  /**
   * 货位类型
   */
  @ExcelProperty(value = "货位类型")
  private Long positionType;

  /**
   * jsondata
   */
  @ExcelProperty(value = "jsondata")
  private String jsonData;

  /**
   * 适用仓库ID
   */
  @ExcelProperty(value = "适用仓库ID")
  private Long storageId;

  /**
   * 仓库名称
   */
  @ExcelProperty(value = "仓库名称")
  private String storageName;

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
  private Long enable;


}

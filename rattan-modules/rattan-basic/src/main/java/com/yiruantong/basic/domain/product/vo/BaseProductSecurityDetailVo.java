package com.yiruantong.basic.domain.product.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.product.BaseProductSecurityDetail;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 防伪标签明细视图对象 base_product_security_detail
 *
 * @author YRT
 * @date 2024-04-25
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BaseProductSecurityDetail.class)
public class BaseProductSecurityDetailVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 防伪码明细ID
   */
  @ExcelProperty(value = "防伪码明细ID")
  private Long securityDetailId;

  /**
   * 防伪标签id
   */
  @ExcelProperty(value = "防伪标签id")
  private Long securityId;

  /**
   * 防伪码
   */
  @ExcelProperty(value = "防伪码")
  private String securityCode;

  /**
   * 状态
   */
  @ExcelProperty(value = "状态")
  private String securityStatus;

  /**
   * 打印次数
   */
  @ExcelProperty(value = "打印次数")
  private Long printCount;

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


}

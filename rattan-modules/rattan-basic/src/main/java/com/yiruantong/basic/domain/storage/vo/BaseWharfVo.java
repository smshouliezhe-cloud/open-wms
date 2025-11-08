package com.yiruantong.basic.domain.storage.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.storage.BaseWharf;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 码头管理视图对象 base_wharf
 *
 * @author YiRuanTong
 * @date 2023-10-18
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BaseWharf.class)
public class BaseWharfVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 码头ID
   */
  @ExcelProperty(value = "码头ID")
  private Long wharfId;

  /**
   * 码头编号
   */
  @ExcelProperty(value = "码头编号")
  private String wharfCode;

  /**
   * 码头名称
   */
  @ExcelProperty(value = "码头名称")
  private String wharfName;

  /**
   * 码头地址
   */
  @ExcelProperty(value = "码头地址")
  private String wharfAddress;

  /**
   * 公司名称
   */
  @ExcelProperty(value = "公司名称")
  private String corpName;

  /**
   * 手机
   */
  @ExcelProperty(value = "手机")
  private String mobile;

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


}

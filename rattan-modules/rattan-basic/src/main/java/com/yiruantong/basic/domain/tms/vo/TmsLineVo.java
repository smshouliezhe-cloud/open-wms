package com.yiruantong.basic.domain.tms.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.tms.TmsLine;
import com.yiruantong.common.excel.annotation.ExcelDictFormat;
import com.yiruantong.common.excel.convert.ExcelDictConvert;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 线路管理视图对象 tms_line
 *
 * @author YRT
 * @date 2024-04-12
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TmsLine.class)
public class TmsLineVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 线路Id
   */
  @ExcelProperty(value = "线路Id")
  private Long lineId;

  /**
   * 线路编号
   */
  @ExcelProperty(value = "线路编号")
  private String lineCode;

  /**
   * 线路类型
   */
  @ExcelProperty(value = "线路类型")
  private String lineType;

  /**
   * 线路名称
   */
  @ExcelProperty(value = "线路名称")
  private String lineName;

  /**
   * 路由属性
   */
  @ExcelProperty(value = "路由属性")
  private String routeAttribute;

  /**
   * 出发网点ID
   */
  @ExcelProperty(value = "出发网点ID")
  private Long distributionSiteId;

  /**
   * 出发网点编号
   */
  @ExcelProperty(value = "出发网点编号")
  private String siteCode;

  /**
   * 出发网点
   */
  @ExcelProperty(value = "出发网点")
  private String distributionSite;

  /**
   * 途经点
   */
  @ExcelProperty(value = "途经点")
  private String passing;

  /**
   * 目的地网点
   */
  @ExcelProperty(value = "目的地网点")
  private String unloadSite;

  /**
   * 路线路由
   */
  @ExcelProperty(value = "路线路由")
  private String lineRoute;

  /**
   * 里程(km)
   */
  @ExcelProperty(value = "里程(km)")
  private String mileage;

  /**
   * 时效（h）
   */
  @ExcelProperty(value = "时效", converter = ExcelDictConvert.class)
  @ExcelDictFormat(readConverterExp = "h=")
  private String prescription;

  /**
   * 日均里程（km）
   */
  @ExcelProperty(value = "日均里程", converter = ExcelDictConvert.class)
  @ExcelDictFormat(readConverterExp = "k=m")
  private String averageMileage;

  /**
   * 操作人ID
   */
  @ExcelProperty(value = "操作人ID")
  private Long userId;

  /**
   * 审核状态
   */
  @ExcelProperty(value = "审核状态")
  private Byte auditing;

  /**
   * 审核人
   */
  @ExcelProperty(value = "审核人")
  private String auditor;

  /**
   * 审核时间
   */
  @ExcelProperty(value = "审核时间")
  private Date auditDate;

  /**
   *
   */
  @ExcelProperty(value = "")
  private String statusText;

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
   * 目的地网点id
   */
  @ExcelProperty(value = "目的地网点id")
  private Long unloadSiteId;

  /**
   * 物流专线
   */
  @ExcelProperty(value = "物流专线")
  private String expressCorpLine;

  /**
   * 物流站电话
   */
  @ExcelProperty(value = "物流站电话")
  private String expressCorpTel;


}

package com.yiruantong.basic.domain.tms.bo;

import com.yiruantong.basic.domain.tms.TmsLine;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.Map;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 线路管理业务对象 tms_line
 *
 * @author YRT
 * @date 2024-04-12
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TmsLine.class, reverseConvertGenerate = false)
public class TmsLineBo extends BaseEntity {

  /**
   * 线路Id
   */
  @NotNull(message = "线路Id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long lineId;

  /**
   * 线路编号
   */
  @NotBlank(message = "线路编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String lineCode;

  /**
   * 线路类型
   */
  @NotBlank(message = "线路类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String lineType;

  /**
   * 线路名称
   */
  @NotBlank(message = "线路名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String lineName;

  /**
   * 路由属性
   */
  @NotBlank(message = "路由属性不能为空", groups = {AddGroup.class, EditGroup.class})
  private String routeAttribute;

  /**
   * 出发网点ID
   */
  @NotNull(message = "出发网点ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long distributionSiteId;

  /**
   * 出发网点编号
   */
  @NotBlank(message = "出发网点编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String siteCode;

  /**
   * 出发网点
   */
  @NotBlank(message = "出发网点不能为空", groups = {AddGroup.class, EditGroup.class})
  private String distributionSite;

  /**
   * 途经点
   */
  @NotBlank(message = "途经点不能为空", groups = {AddGroup.class, EditGroup.class})
  private String passing;

  /**
   * 目的地网点
   */
  @NotBlank(message = "目的地网点不能为空", groups = {AddGroup.class, EditGroup.class})
  private String unloadSite;

  /**
   * 路线路由
   */
  @NotBlank(message = "路线路由不能为空", groups = {AddGroup.class, EditGroup.class})
  private String lineRoute;

  /**
   * 里程(km)
   */
  @NotBlank(message = "里程(km)不能为空", groups = {AddGroup.class, EditGroup.class})
  private String mileage;

  /**
   * 时效（h）
   */
  @NotBlank(message = "时效（h）不能为空", groups = {AddGroup.class, EditGroup.class})
  private String prescription;

  /**
   * 日均里程（km）
   */
  @NotBlank(message = "日均里程（km）不能为空", groups = {AddGroup.class, EditGroup.class})
  private String averageMileage;

  /**
   * 操作人ID
   */
  @NotNull(message = "操作人ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long userId;

  /**
   * 审核状态
   */
  @NotNull(message = "审核状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte auditing;

  /**
   * 审核人
   */
  @NotBlank(message = "审核人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String auditor;

  /**
   * 审核时间
   */
  @NotNull(message = "审核时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date auditDate;

  /**
   *
   */
  @NotBlank(message = "不能为空", groups = {AddGroup.class, EditGroup.class})
  private String statusText;

  /**
   * 排序号
   */
  @NotNull(message = "排序号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderNum;

  /**
   * 扩展字段
   */
  @NotBlank(message = "扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
  private Map<String, Object> expandFields;

  /**
   * 备注
   */
  @NotBlank(message = "备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String remark;

  /**
   * 删除时间
   */
  @NotNull(message = "删除时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date deleteTime;

  /**
   * 删除人id
   */
  @NotNull(message = "删除人id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long deleteBy;

  /**
   * 删除人
   */
  @NotBlank(message = "删除人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String deleteByName;

  /**
   * 目的地网点id
   */
  @NotNull(message = "目的地网点id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long unloadSiteId;

  /**
   * 物流专线
   */
  @NotBlank(message = "物流专线不能为空", groups = {AddGroup.class, EditGroup.class})
  private String expressCorpLine;

  /**
   * 物流站电话
   */
  @NotBlank(message = "物流站电话不能为空", groups = {AddGroup.class, EditGroup.class})
  private String expressCorpTel;


}

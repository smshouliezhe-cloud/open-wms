package com.yiruantong.basic.domain.tms;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.common.mybatis.core.domain.TenantEntity;

import java.io.Serial;
import java.util.Date;
import java.util.Map;

/**
 * 线路管理对象 tms_line
 *
 * @author YRT
 * @date 2024-04-12
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tms_line", autoResultMap = true)
public class TmsLine extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 线路Id
   */
  @TableId(value = "line_id")
  private Long lineId;

  /**
   * 线路编号
   */
  private String lineCode;

  /**
   * 线路类型
   */
  private String lineType;

  /**
   * 线路名称
   */
  private String lineName;

  /**
   * 路由属性
   */
  private String routeAttribute;

  /**
   * 出发网点ID
   */
  private Long distributionSiteId;

  /**
   * 出发网点编号
   */
  private String siteCode;

  /**
   * 出发网点
   */
  private String distributionSite;

  /**
   * 途经点
   */
  private String passing;

  /**
   * 目的地网点
   */
  private String unloadSite;

  /**
   * 路线路由
   */
  private String lineRoute;

  /**
   * 里程(km)
   */
  private String mileage;

  /**
   * 时效（h）
   */
  private String prescription;

  /**
   * 日均里程（km）
   */
  private String averageMileage;

  /**
   * 操作人ID
   */
  private Long userId;

  /**
   * 审核状态
   */
  private Byte auditing;

  /**
   * 审核人
   */
  private String auditor;

  /**
   * 审核时间
   */
  private Date auditDate;

  /**
   *
   */
  private String statusText;

  /**
   * 排序号
   */
  private Long orderNum;

  /**
   * 扩展字段
   */
  @TableField(value = "expand_fields", typeHandler = JacksonTypeHandler.class)
  private Map<String, Object> expandFields;

  /**
   * 备注
   */
  private String remark;

  /**
   * 删除时间
   */
  private Date deleteTime;

  /**
   * 删除人id
   */
  private Long deleteBy;

  /**
   * 删除人
   */
  private String deleteByName;

  /**
   * 目的地网点id
   */
  private Long unloadSiteId;

  /**
   * 物流专线
   */
  private String expressCorpLine;

  /**
   * 物流站电话
   */
  private String expressCorpTel;


}

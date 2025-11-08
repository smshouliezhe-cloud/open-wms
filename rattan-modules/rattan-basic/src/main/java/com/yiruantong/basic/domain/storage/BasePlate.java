package com.yiruantong.basic.domain.storage;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.common.mybatis.core.domain.TenantEntity;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 容器管理对象 base_plate
 *
 * @author YiRuanTong
 * @date 2024-03-14
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_plate", autoResultMap = true)
public class BasePlate extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 容器ID
   */
  @TableId(value = "plate_id")
  private Long plateId;

  /**
   * 容器编号
   */
  private String plateCode;

  /**
   * 容器类别
   */
  private String plateType;

  /**
   * 容器名称
   */
  private String plateName;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

  /**
   * 已打印
   */
  private Long isPrinted;

  /**
   * 备注
   */
  private String remark;

  /**
   * 排序号
   */
  private Long orderNum;

  /**
   * 是否可用
   */
  private Long enable;

  /**
   * 是否使用
   */
  private Long isUse;

  /**
   * 容器状态
   */
  private String plateState;

  /**
   * 打印状态
   */
  private String printState;

  /**
   * 打印次数
   */
  private Long pringQuantity;

  /**
   * 已使用
   */
  private Long isUsing;

  /**
   * 扩展字段
   */
  @TableField(value = "expand_fields", typeHandler = JacksonTypeHandler.class)
  private Map<String, Object> expandFields;

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
   * 容器规格
   */
  private String plateSpec;

  /**
   * 单位重量
   */
  private BigDecimal weight;

  /**
   * 单位体积
   */
  private BigDecimal unitCube;


}

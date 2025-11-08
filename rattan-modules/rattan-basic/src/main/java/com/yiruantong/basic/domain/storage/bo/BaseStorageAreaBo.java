package com.yiruantong.basic.domain.storage.bo;

import com.yiruantong.basic.domain.storage.BaseStorageArea;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.Map;
import java.util.Map;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 库区管理业务对象 base_storage_area
 *
 * @author YiRuanTong
 * @date 2024-12-31
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BaseStorageArea.class, reverseConvertGenerate = false)
public class BaseStorageAreaBo extends BaseEntity {

  /**
   * 库区ID
   */
  private Long storageAreaId;

  /**
   * 库区
   */
  @NotBlank(message = "库区不能为空", groups = {AddGroup.class, EditGroup.class})
  private String areaCode;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  @NotBlank(message = "仓库名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageName;

  /**
   * 摆放模式
   */
  private String shelveMode;

  /**
   * 通道数
   */
  private Long channelNum;

  /**
   * A面架数开始数
   */
  private Long shelveNumA1;

  /**
   * A面架数结束数
   */
  private Long shelveNumA2;

  /**
   * B面架数开始数
   */
  private Long shelveNumB1;

  /**
   * B面架数结束数
   */
  private Long shelveNumB2;

  /**
   * 列数
   */
  private Long columnNum;

  /**
   * 层数
   */
  private Long rowNum;

  /**
   * A面
   */
  private Long isALine;

  /**
   * B面
   */
  private Long isBLine;

  /**
   * 货位类型
   */
  @NotNull(message = "货位类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long positionType;

  /**
   * 货位编码规则
   */
  private String positionRegular;

  /**
   * 拣货模式
   */
  private String pickMode;

  /**
   * 最大容量
   */
  private BigDecimal maxCapacity;

  /**
   * 货架编码
   */
  private String shelvesRegular;

  /**
   * 通道编码规则
   */
  private String channelRegular;

  /**
   * 行编码规则
   */
  private String rowRegular;

  /**
   * 列编码规则
   */
  private String columnRegular;

  /**
   * 货架JSON
   */
  private String jsonData;

  /**
   * 温层类型
   */
  private String thermocLine;

  /**
   * 货位最大重量
   */
  private BigDecimal maxWeight;

  /**
   * 货位最大拍数
   */
  private Long maxBeatNumber;

  /**
   * 存货率计算
   */
  private String inventoryRate;

  /**
   * 上架操作员ID
   */
  private String userIds;

  /**
   * 上架操作员
   */
  private String userTrueNames;

  /**
   * 拣货操作员ID
   */
  private String packUserIds;

  /**
   * 拣货操作员
   */
  private String packUserTrueNames;

  /**
   * x6Data
   */
  private String x6Data;

  /**
   * 排序号
   */
  private Long orderNum;

  /**
   * 扩展字段
   */
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
   * 自定义货架通道数据
   */
  private String channelDataList;

  /**
   * svg地址
   */
  @NotBlank(message = "svg地址不能为空", groups = {AddGroup.class, EditGroup.class})
  private String svgUrl;


}

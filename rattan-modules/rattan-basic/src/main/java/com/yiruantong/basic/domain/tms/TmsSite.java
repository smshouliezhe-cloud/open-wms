package com.yiruantong.basic.domain.tms;

  import com.yiruantong.common.mybatis.core.domain.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;

import java.util.List;
import java.util.Map;
  import java.util.Date;
  import com.fasterxml.jackson.annotation.JsonFormat;


import java.io.Serial;

/**
 * 网点管理对象 tms_site
 *
 * @author YRT
 * @date 2025-02-20
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tms_site", autoResultMap = true)
public class TmsSite extends TenantEntity {

@Serial
private static final long serialVersionUID=1L;

  /**
   * 网点Id
   */
    @TableId(value = "site_id")
  private Long siteId;

  /**
   * 网点编号
   */
  private String siteCode;

  /**
   * 网点类型
   */
  private String siteType;

  /**
   * 网点名称
   */
  private String siteName;

  /**
   * 详细地址
   */
  private String siteAddress;

  /**
   * 所属区域
   */
  private String region;

  /**
   * 网店负责人
   */
  private String sitePerson;

  /**
   * 负责人电话
   */
  private String personPhone;

  /**
   * 仓库编号
   */
  private String storageCode;

  /**
   * 仓库编号
   */
  private String storageName;

  /**
   * 仓库Id
   */
  private Long storageId;

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
   * 省id
   */
  private Long provinceId;

  /**
   * 省
   */
  private String provinceName;

  /**
   * 市id
   */
  private Long cityId;

  /**
   * 市
   */
  private String cityName;

  /**
   * 区id
   */
  private Long regionId;

  /**
   * 区
   */
  private String regionName;

  /**
   * 街道
   */
  private String street;

  /**
   * 国家
   */
  private String countryNameCn;

  /**
   * 洲
   */
  private String continent;


}

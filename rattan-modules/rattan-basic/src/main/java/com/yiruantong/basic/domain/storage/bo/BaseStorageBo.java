package com.yiruantong.basic.domain.storage.bo;

import com.yiruantong.basic.domain.storage.BaseStorage;
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
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 仓库管理业务对象 base_storage
 *
 * @author YiRuanTong
 * @date 2024-12-25
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BaseStorage.class, reverseConvertGenerate = false)
public class BaseStorageBo extends BaseEntity {

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库编码
   */
  @NotBlank(message = "仓库编码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageCode;

  /**
   * 仓库名称
   */
  @NotBlank(message = "仓库名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageName;

  /**
   * 父级ID
   */
  private Long parentId;

  /**
   * 是否管理到拍
   */
  private Long isPlateManager;

  /**
   * 启用区域
   */
  private Long isArea;

  /**
   * 启用货架
   */
  private Long isShelves;

  /**
   * 区域编码
   */
  private String areaRegular;

  /**
   * 货架编码
   */
  private String shelvesRegular;

  /**
   * 货位编码规则
   */
  private String positionRegular;

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
   * 仓库地址
   */
  private String storageAdress;

  /**
   * 仓库所对应的接口地址
   */
  private String storageUrl;

  /**
   * 仓库类型
   */
  private String storageType;

  /**
   * 货位分拣类型
   */
  private String positionType;

  /**
   * 公司名称
   */
  private String shipperCompanyName;

  /**
   * 姓名
   */
  private String shipperName;

  /**
   * 电话号码
   */
  private String shipperTelephone;

  /**
   * 手机
   */
  private String shipperMobile;

  /**
   * 发货地址
   */
  private String shipperAddress;

  /**
   * 省ID
   */
  private Long provinceId;

  /**
   * 省
   */
  private String provinceName;

  /**
   * 市ID
   */
  private Long cityId;

  /**
   * 市
   */
  private String cityName;

  /**
   * 区ID
   */
  private Long regionId;

  /**
   * 区
   */
  private String regionName;

  /**
   * 经度
   */
  private String lng;

  /**
   * 纬度
   */
  private String lat;

  /**
   * 上架货位类型
   */
  private String positionTypeShelve;

  /**
   * x6JSON
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
   * 是否可用
   */
  private Long enable;

  /**
   * 关闭SN管理
   */
  private Byte snDisabled;

  /**
   * 温层类型
   */
  private String thermocLine;

  /**
   * 所属网点
   */
  private String siteName;

  /**
   * 网点Id
   */
  private Long siteId;

  /**
   * 别名
   */
  private String aliasName;


}

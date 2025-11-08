package com.yiruantong.basic.domain.storage.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.storage.BaseStorage;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 仓库管理视图对象 base_storage
 *
 * @author YiRuanTong
 * @date 2024-12-25
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BaseStorage.class)
public class BaseStorageVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 仓库ID
   */
  @ExcelProperty(value = "仓库ID")
  private Long storageId;

  /**
   * 仓库编码
   */
  @ExcelProperty(value = "仓库编码")
  private String storageCode;

  /**
   * 仓库名称
   */
  @ExcelProperty(value = "仓库名称")
  private String storageName;

  /**
   * 父级ID
   */
  @ExcelProperty(value = "父级ID")
  private Long parentId;

  /**
   * 是否管理到拍
   */
  @ExcelProperty(value = "是否管理到拍")
  private Long isPlateManager;

  /**
   * 启用区域
   */
  @ExcelProperty(value = "启用区域")
  private Long isArea;

  /**
   * 启用货架
   */
  @ExcelProperty(value = "启用货架")
  private Long isShelves;

  /**
   * 区域编码
   */
  @ExcelProperty(value = "区域编码")
  private String areaRegular;

  /**
   * 货架编码
   */
  @ExcelProperty(value = "货架编码")
  private String shelvesRegular;

  /**
   * 货位编码规则
   */
  @ExcelProperty(value = "货位编码规则")
  private String positionRegular;

  /**
   * 通道编码规则
   */
  @ExcelProperty(value = "通道编码规则")
  private String channelRegular;

  /**
   * 行编码规则
   */
  @ExcelProperty(value = "行编码规则")
  private String rowRegular;

  /**
   * 列编码规则
   */
  @ExcelProperty(value = "列编码规则")
  private String columnRegular;

  /**
   * 仓库地址
   */
  @ExcelProperty(value = "仓库地址")
  private String storageAdress;

  /**
   * 仓库所对应的接口地址
   */
  @ExcelProperty(value = "仓库所对应的接口地址")
  private String storageUrl;

  /**
   * 仓库类型
   */
  @ExcelProperty(value = "仓库类型")
  private String storageType;

  /**
   * 货位分拣类型
   */
  @ExcelProperty(value = "货位分拣类型")
  private String positionType;

  /**
   * 公司名称
   */
  @ExcelProperty(value = "公司名称")
  private String shipperCompanyName;

  /**
   * 姓名
   */
  @ExcelProperty(value = "姓名")
  private String shipperName;

  /**
   * 电话号码
   */
  @ExcelProperty(value = "电话号码")
  private String shipperTelephone;

  /**
   * 手机
   */
  @ExcelProperty(value = "手机")
  private String shipperMobile;

  /**
   * 发货地址
   */
  @ExcelProperty(value = "发货地址")
  private String shipperAddress;

  /**
   * 省ID
   */
  @ExcelProperty(value = "省ID")
  private Long provinceId;

  /**
   * 省
   */
  @ExcelProperty(value = "省")
  private String provinceName;

  /**
   * 市ID
   */
  @ExcelProperty(value = "市ID")
  private Long cityId;

  /**
   * 市
   */
  @ExcelProperty(value = "市")
  private String cityName;

  /**
   * 区ID
   */
  @ExcelProperty(value = "区ID")
  private Long regionId;

  /**
   * 区
   */
  @ExcelProperty(value = "区")
  private String regionName;

  /**
   * 经度
   */
  @ExcelProperty(value = "经度")
  private String lng;

  /**
   * 纬度
   */
  @ExcelProperty(value = "纬度")
  private String lat;

  /**
   * 上架货位类型
   */
  @ExcelProperty(value = "上架货位类型")
  private String positionTypeShelve;

  /**
   * x6JSON
   */
  @ExcelProperty(value = "x6JSON")
  private String x6Data;

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

  /**
   * 关闭SN管理
   */
  @ExcelProperty(value = "关闭SN管理")
  private Byte snDisabled;

  /**
   * 温层类型
   */
  @ExcelProperty(value = "温层类型")
  private String thermocLine;

  /**
   * 所属网点
   */
  @ExcelProperty(value = "所属网点")
  private String siteName;

  /**
   * 网点Id
   */
  @ExcelProperty(value = "网点Id")
  private Long siteId;

  /**
   * 别名
   */
  @ExcelProperty(value = "别名")
  private String aliasName;


}

package com.yiruantong.common.core.enums.other;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * serviceId类型枚举
 */
@Getter
@AllArgsConstructor
public enum XgServiceIdTypeEnum {
  /**
   * 材料备件送货单
   */
  OMWMA1((byte) 1, "OMWMA1"),
  /**
   * 合金辅料送货单
   */
  OMWMB2((byte) 2, "OMWMB2"),
  /**
   * 材料备件领用计划
   */
  OMWMA3((byte) 3, "OMWMA3"),
  /**
   * 合金辅料领用计划
   */
  OMWMB1((byte) 4, "OMWMB1"),
  /**
   * 仓库信息
   */
  OMWMD1((byte) 5, "OMWMD1"),
  /**
   * 材料备件质检单
   */
  OMWMA2((byte) 6, "OMWMA2"),
  /**
   * 自产材入库
   */
  OMWMC1((byte) 7, "OMWMC1"),
  /**
   * 自产材确认
   */
  OMWMC2((byte) 8, "OMWMC2"),
  /**
   * 现场收货确认、自动收货确认
   */
  OMWMA4((byte) 9, "OMWMA4"),
  /**
   * 货位对照表
   */
  OMWMD2((byte) 10, "OMWMD2"),
  /**
   * 物料（商品信息）对照表
   */
  OMWMD4((byte) 11, "OMWMD4"),
  /**
   * 库管员物料对照信息（ERP-WMS）
   */
  OMWMD3((byte) 12, "OMWMD3"),
  /**
   * 库区（容器）对照表
   */
  OMWMD5((byte) 13, "OMWMD5"),
  /**
   * 大中叶类（商品类别）信息
   */
  OMWMD7((byte) 14, "OMWMD7"),
  /**
   * ERP->WMS出库退货(退仓)
   */
  OMWMA5((byte) 15, "OMWMA5"),
  /**
   * 质量异议（ERP-WMS）
   */
  OMWMB3((byte) 16, "OMWMB3"),
  /**
   * 质量异议结果（ERP-WMS）
   */
  OMWMB4((byte) 17, "OMWMB4"),
  /**
   * 退货（退供应商）（ERP-WMS）
   */
  OMWMA6((byte) 18, "OMWMA6"),
  /**
   * 退仓质检员确认（ERP-WMS）
   */
  OMWMA7((byte) 19, "OMWMA7"),
  /**
   * 岗位物理库区对照表（ERP-WMS）
   */
  OMWMD6((byte) 20, "OMWMD6"),
  ;

  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static XgServiceIdTypeEnum matchingEnum(String name) {
    for (XgServiceIdTypeEnum i : values()) {
      if (ObjectUtil.equal(i.getName(), name)) {
        return i;
      }
    }
    return null;
  }

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param id 值
   * @return 枚举
   */
  public static XgServiceIdTypeEnum matchingEnumById(Byte id) {
    for (XgServiceIdTypeEnum i : values()) {
      if (ObjectUtil.equal(i.getId(), id)) {
        return i;
      }
    }
    return null;
  }
}

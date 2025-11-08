package com.yiruantong.inbound.liteflow.Context;

import lombok.Data;
import com.yiruantong.basic.domain.storage.BaseStorage;
import com.yiruantong.common.core.enums.base.PositionTypeEnum;
import com.yiruantong.common.core.enums.in.InEnterActionEnum;
import com.yiruantong.common.core.enums.in.InOrderActionEnum;
import com.yiruantong.inbound.domain.in.*;
import com.yiruantong.inbound.domain.in.bo.InScanOrderBo;

import java.util.List;

/**
 * 扫描入库上下文
 */
@Data
public class InScanContext {
  /**
   * 扫描入库bo数据
   */
  InScanOrderBo inScanOrderBo;
  /**
   * 预到货单信息
   */
  InOrder inOrder;
  /**
   * 仓库信息
   */
  BaseStorage storageInfo;
  /**
   * 按拍上架推荐货位根据拍数推荐
   */
  boolean inShelvePaiCount;
  /**
   * 入库时启用禁收日期
   */
  boolean inNoReceivingDate;
  /**
   * 预到货常规扫描入库时允许超收
   */
  boolean inOvercharges;
  /**
   * 是否生成上架单
   */
  boolean inGenerateShelve;
  /**
   * 扫描入库时合并生成上架单
   */
  boolean inGenerateShelveOnly;
  /**
   * 是否生成质检单
   */
  boolean inGenerateQualityCheck;
  /**
   * 托盘号默认为货位号 - 待上架单扫描
   */
  boolean inWaitShelveBillScan;
  /**
   * 上架货位类型
   */
  List<PositionTypeEnum> positionTypeEnumList;
  /**
   * 预到货单状态 - 历史轨迹枚举
   */
  InOrderActionEnum orderActionEnumHistory;
  /**
   * 预到货单状态 - 上架动作枚举
   */
  InOrderActionEnum orderShelveActionEnum;
  /**
   * 预到货单入库状态 - 入库动作枚举
   */
  InEnterActionEnum inEnterActionEnumEntered;
  /**
   * 预到货单入库状态 - 入库上架动作枚举
   */
  InEnterActionEnum inEnterActionEnumShelve;
  /**
   * 入库单
   */
  InEnter inEnter;
  /**
   * 入库单明细
   */
  List<InEnterDetail> inEnterDetailList;
  /**
   * 报废明细集合
   */
  List<InEnterDetail> invalidateDetails;
  /**
   * 质检单
   */
  InQualityCheck inQualityCheck;
  /**
   * 上架单
   */
  InShelve inShelve;

}

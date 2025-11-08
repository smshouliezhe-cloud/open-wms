package com.yiruantong.outbound.liteflow.Context;

import lombok.Data;
import com.yiruantong.basic.domain.storage.BaseStorage;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.out.OutOperationTypeEnum;
import com.yiruantong.common.core.enums.out.OutWaveOperationTypeEnum;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.domain.out.OutPackage;
import com.yiruantong.outbound.domain.out.OutPackageDetail;
import com.yiruantong.outbound.domain.out.bo.OutScanMainBo;

import java.util.List;


@Data
public class OutScanContext {
  /**
   * 扫描出库bo数据
   */
  OutScanMainBo outScanMainBo;
  /**
   * 出库单信息
   */
  OutOrder outOrder;
  /**
   * 仓库信息
   */
  BaseStorage storageInfo;

  /**
   * 打包单
   */
  OutPackage outPackage;


  /**
   * 出库单明细
   */
  List<OutOrderDetail> outOrderDetailList;

  /**
   * 打包单明细
   */
  List<OutPackageDetail> outPackageDetailList;

  /**
   * 按拣货数量打包
   */
  boolean outerPickQuantity;

  /**
   * 生成WCS出库任务
   */
  boolean outFinishedToWcs;
  /**
   * 出库轨迹动作
   */
  OutOperationTypeEnum outOperationTypeEnum;
  /**
   * 波次轨迹动作
   */
  OutWaveOperationTypeEnum outWaveOperationTypeEnum;
  /**
   * 登录信息
   */
  LoginUser loginUser;
}

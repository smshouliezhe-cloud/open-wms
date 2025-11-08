package com.yiruantong.basic.service.common;

import com.yiruantong.basic.domain.common.CommonOperationLog;
import com.yiruantong.basic.domain.common.bo.CommonOperationLogBo;
import com.yiruantong.basic.domain.common.vo.CommonOperationLogVo;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.in.InOrderActionEnum;
import com.yiruantong.common.core.enums.in.InOrderStatusEnum;
import com.yiruantong.common.core.enums.out.OutOperationTypeEnum;
import com.yiruantong.common.core.enums.out.OutOrderStatusEnum;
import com.yiruantong.common.core.enums.out.OutWaveOperationTypeEnum;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

/**
 * 业务操作日志Service接口
 *
 * @author YRT
 * @date 2025-03-08
 */
public interface ICommonOperationLogService extends IServicePlus<CommonOperationLog, CommonOperationLogVo, CommonOperationLogBo> {
  /**
   * 添加通用日志
   *
   * @param billId     单据ID
   * @param billCode   单据编号
   * @param action     动作
   * @param fromStatus 来源状态
   * @param toStatus   目标状态
   * @param loginUser  登录用户信息
   * @param menuId     模块ID
   * @param billType   模块名
   * @param remark     备注
   */
  void addCommonLog(Long billId, String billCode, String action, String fromStatus, String toStatus, LoginUser loginUser, Integer menuId, String billType, String remark);

  /**
   * 添加预到货单日志
   *
   * @param billId     单据ID
   * @param billCode   单据编号
   * @param action     动作
   * @param fromStatus 来源状态
   * @param toStatus   目标状态
   * @param loginUser  登录用户信息
   * @param menuId     模块ID
   * @param billType   模块名
   * @param remark     备注
   */
  void addInOrderLog(Long billId, String billCode, String action, String fromStatus, String toStatus, LoginUser loginUser, Integer menuId, String billType, String remark);

  /**
   * 添加预到货单日志
   *
   * @param billId     单据ID
   * @param billCode   单据编号
   * @param action     动作
   * @param fromStatus 来源状态
   * @param toStatus   目标状态
   * @param menuEnum   模块枚举
   * @param remark     备注
   */
  void addInOrderLog(Long billId, String billCode, String action, String fromStatus, String toStatus, MenuEnum menuEnum, String remark);

  /**
   * 添加预到货单日志
   *
   * @param billId     单据ID
   * @param billCode   单据编号
   * @param actionEnum 动作
   * @param fromStatus 来源状态
   * @param toStatus   目标状态
   */
  void addInOrderLog(Long billId, String billCode, InOrderActionEnum actionEnum, InOrderStatusEnum fromStatus, InOrderStatusEnum toStatus);

  /**
   * 添加出库单操作轨迹
   *
   * @param billId            单据ID
   * @param billCode          单据编号
   * @param operationTypeEnum 动作
   * @param fromStatus        源状态
   * @param toStatus          目标状态
   */
  void addOutOrderLog(Long billId, String billCode, OutOperationTypeEnum operationTypeEnum, OutOrderStatusEnum fromStatus, OutOrderStatusEnum toStatus, String remark);

  /**
   * 添加出库单操作轨迹
   *
   * @param billId            单据ID
   * @param billCode          单据编号
   * @param operationTypeEnum 动作
   * @param fromStatus        源状态
   * @param toStatus          目标状态
   */
  void addOutOrderLog(Long billId, String billCode, OutOperationTypeEnum operationTypeEnum, OutOrderStatusEnum fromStatus, OutOrderStatusEnum toStatus);

  /**
   * 添加波次查询操作轨迹
   *
   * @param billId                   单据ID
   * @param billCode                 单据编号
   * @param outWaveOperationTypeEnum 动作
   * @param fromStatus               源状态
   * @param toStatus                 目标状态
   * @param remark
   */
  void addOutOrderWaveLog(Long billId, String billCode, OutWaveOperationTypeEnum outWaveOperationTypeEnum, OutOrderStatusEnum fromStatus, OutOrderStatusEnum toStatus, String remark);

  /**
   * 添加波次查询操作轨迹
   *
   * @param billId                   单据ID
   * @param billCode                 单据编号
   * @param outWaveOperationTypeEnum 动作
   * @param fromStatus               源状态
   * @param toStatus                 目标状态
   */
  void addOutOrderWaveLog(Long billId, String billCode, OutWaveOperationTypeEnum outWaveOperationTypeEnum, OutOrderStatusEnum fromStatus, OutOrderStatusEnum toStatus);

}

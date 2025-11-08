package com.yiruantong.outbound.service.out;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.outbound.domain.out.OutPackage;
import com.yiruantong.outbound.domain.out.bo.OutPackageBo;
import com.yiruantong.outbound.domain.out.vo.OutPackageVo;

import java.util.List;
import java.util.Map;

/**
 * 打包单Service接口
 *
 * @author YRT
 * @date 2023-11-07
 */
public interface IOutPackageService extends IServicePlus<OutPackage, OutPackageVo, OutPackageBo> {

  /**
   * 生成一次性费用
   *
   * @param map 入参
   * @return R
   */
  R<Void> createBill(Map<String, Object> map);


  /**
   * 生成一次性费用
   *
   * @param orderId 出库单ID
   * @return R
   */
  List<OutPackage> selectByOrderId(Long orderId);
}

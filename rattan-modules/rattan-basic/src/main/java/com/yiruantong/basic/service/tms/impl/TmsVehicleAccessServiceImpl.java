package com.yiruantong.basic.service.tms.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.tms.TmsVehicleAccess;
import com.yiruantong.basic.domain.tms.bo.TmsVehicleAccessBo;
import com.yiruantong.basic.domain.tms.vo.TmsVehicleAccessVo;
import com.yiruantong.basic.mapper.tms.TmsVehicleAccessMapper;
import com.yiruantong.basic.service.tms.ITmsVehicleAccessService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.AuditEnum;
import com.yiruantong.common.core.enums.in.InOrderStatusEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.DateUtils;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.satoken.utils.LoginHelper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 车辆出入信息Service业务层处理
 *
 * @author YRT
 * @date 2023-11-03
 */
@RequiredArgsConstructor
@Service
public class TmsVehicleAccessServiceImpl extends ServiceImplPlus<TmsVehicleAccessMapper, TmsVehicleAccess, TmsVehicleAccessVo, TmsVehicleAccessBo> implements ITmsVehicleAccessService {

  public R<Void> multiAuditing(List<Long> ids) {
    for (long vehicleAccessId : ids) {
      LambdaQueryWrapper<TmsVehicleAccess> vehicleAccessLambdaQueryWrapper = new LambdaQueryWrapper<>();
      vehicleAccessLambdaQueryWrapper.eq(TmsVehicleAccess::getVehicleAccessId, vehicleAccessId);
      TmsVehicleAccess tmsVehicleAccess = this.getOne(vehicleAccessLambdaQueryWrapper);

      if (ObjectUtil.isEmpty(tmsVehicleAccess)) {
        throw new ServiceException("未获取到单据");
      }
      Byte auditing = Convert.toByte(tmsVehicleAccess.getAuditing());
      if (ObjectUtil.isNotNull(auditing) && !auditing.equals(AuditEnum.AUDIT.getId())) {
        throw new ServiceException(tmsVehicleAccess.getOutLoadCode() + "只有新建的单子才允许审核");
      }
      //修改数据
      LambdaUpdateWrapper<TmsVehicleAccess> lambda = new UpdateWrapper<TmsVehicleAccess>().lambda();
      lambda.set(TmsVehicleAccess::getAuditDate, DateUtils.getNowDate())
        .set(TmsVehicleAccess::getAuditor, LoginHelper.getNickname())
        .set(TmsVehicleAccess::getAuditing, Convert.toLong(2))
        .set(TmsVehicleAccess::getOrderStatus, InOrderStatusEnum.SUCCESS.getName())
        .eq(TmsVehicleAccess::getVehicleAccessId, vehicleAccessId);
      this.update(lambda);//提交
    }
    return R.ok("审核成功");
  }
}

package com.yiruantong.outbound.service.out.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.in.InOrderTypeEnum;
import com.yiruantong.common.core.enums.system.RabbitmqTypeEnum;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.mybatis.enums.QueryTypeEnum;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;
import com.yiruantong.outbound.domain.out.OutPackage;
import com.yiruantong.outbound.domain.out.bo.OutPackageBo;
import com.yiruantong.outbound.domain.out.vo.OutPackageVo;
import com.yiruantong.outbound.mapper.out.OutPackageMapper;
import com.yiruantong.outbound.service.out.IOutPackageService;
import com.yiruantong.system.service.task.ITaskQueueService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 打包单Service业务层处理
 *
 * @author YRT
 * @date 2023-11-07
 */
@RequiredArgsConstructor
@Service
public class OutPackageServiceImpl extends ServiceImplPlus<OutPackageMapper, OutPackage, OutPackageVo, OutPackageBo> implements IOutPackageService {
  private final ITaskQueueService taskQueueService;

  //#region 加载列表前事件
  @Override
  public void beforePageQuery(PageQuery pageQuery) {
    //#region 列表之定义查询
    pageQuery.getQueryBoList().stream().filter(f -> f.getQueryType() == QueryTypeEnum.CUSTOM).forEach(f -> {
      if (f.getColumn().equals("productCode")) {
        f.setQueryType(QueryTypeEnum.EXISTS);
        f.setValues(StringUtils.format("SELECT package_Id FROM out_package_detail d WHERE d.package_Id=out_package.package_Id AND d.product_code='{}'", f.getValues()));
      }
      //如果还有其他查询条件 则继续加else if

    });
    super.beforePageQuery(pageQuery);
    //#endregion
  }
  //#endregion

  //#region 生成一次性费用

  /**
   * 生成一次性费用
   *
   * @param map 入参
   * @return R
   */
  @Override
  public R<Void> createBill(Map<String, Object> map) {
    Long[] ids = Convert.toLongArray(map.get("ids"));

    boolean isCreated = false;
    for (long enterId : ids) {
      // 入库单信息
      OutPackage outPackage = this.getById(enterId);
      Assert.isFalse(StringUtils.equals(outPackage.getOrderType(), InOrderTypeEnum.NO_BILL_FLASH.getName()), "一键闪入不允许撤销操作");
      Assert.isFalse(ObjectUtil.isNull(outPackage), "未获取到入库单");

      if (StrUtil.isNotEmpty(outPackage.getFeeItemIds())) {
        //调用RabbitMQ
        RabbitReceiverDto rabbitReceiverDto = new RabbitReceiverDto();
        rabbitReceiverDto.setRabbitmqType(RabbitmqTypeEnum.OUT_FINISHED_GENERATING_BILL); //类别
        rabbitReceiverDto.setBillId(outPackage.getPackageId());
        rabbitReceiverDto.setBillCode(outPackage.getPackageCode());
        rabbitReceiverDto.setSourceCode(outPackage.getOrderCode());
        rabbitReceiverDto.setSourceId("" + outPackage.getOrderId());
        taskQueueService.createTask(rabbitReceiverDto);
        isCreated = true;
      }
    }

    return isCreated ? R.ok("一次性费用项生成成功！") : R.fail("当前出库单没有设置费用项，不需要生成");
  }

  @Override
  public List<OutPackage> selectByOrderId(Long orderId) {
    LambdaQueryWrapper<OutPackage> packageLambdaQueryWrapper = new LambdaQueryWrapper<>();
    packageLambdaQueryWrapper.eq(OutPackage::getOrderId, orderId);

    return this.list(packageLambdaQueryWrapper);
  }
  //#endregion
}

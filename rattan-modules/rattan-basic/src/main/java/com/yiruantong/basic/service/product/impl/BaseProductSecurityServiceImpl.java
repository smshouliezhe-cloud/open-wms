package com.yiruantong.basic.service.product.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.product.BaseProductSecurity;
import com.yiruantong.basic.domain.product.BaseProductSecurityDetail;
import com.yiruantong.basic.domain.product.BaseProductSecurityHistory;
import com.yiruantong.basic.domain.product.bo.BaseProductSecurityBo;
import com.yiruantong.basic.domain.product.vo.BaseProductSecurityVo;
import com.yiruantong.basic.mapper.product.BaseProductSecurityMapper;
import com.yiruantong.basic.service.product.IBaseProductSecurityDetailService;
import com.yiruantong.basic.service.product.IBaseProductSecurityHistoryService;
import com.yiruantong.basic.service.product.IBaseProductSecurityService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.BaseProductSecurityEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 防伪标签Service业务层处理
 *
 * @author YRT
 * @date 2024-04-25
 */
@RequiredArgsConstructor
@Service
public class BaseProductSecurityServiceImpl extends ServiceImplPlus<BaseProductSecurityMapper, BaseProductSecurity, BaseProductSecurityVo, BaseProductSecurityBo> implements IBaseProductSecurityService {


  private final IBaseProductSecurityDetailService baseProductSecurityDetailService;
  private final IBaseProductSecurityHistoryService baseProductSecurityHistoryService;

  /**
   * 生成防伪码
   *
   * @param map 参数
   * @return 返回查询列表数据
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> createSecurity(Map<String, Object> map) {

    BaseProductSecurity baseProductSecurity = new BaseProductSecurity();
    baseProductSecurity.setProductId(Convert.toLong(map.get("productId")));
    baseProductSecurity.setProductCode(Convert.toStr(map.get("productCode")));
    baseProductSecurity.setProductName(Convert.toStr(map.get("productName")));
    baseProductSecurity.setProductModel(Convert.toStr(map.get("productModel")));
    baseProductSecurity.setUnit(Convert.toStr(map.get("unit")));
    baseProductSecurity.setBatchNumber(Convert.toStr(map.get("batchNumber")));
    baseProductSecurity.setBatchCode(Convert.toStr(map.get("batchCode")));
    baseProductSecurity.setProduceEnterPrise(Convert.toStr(map.get("produceEnterprise")));
    baseProductSecurity.setOriginPlace(Convert.toStr(map.get("originPlace")));
    baseProductSecurity.setQuantity(Convert.toLong(map.get("quantity")));
    baseProductSecurity.setRemark(Convert.toStr(map.get("remark")));
    baseProductSecurity.setProductImageUrl(Convert.toStr(map.get("productImageUrl")));

    this.save(baseProductSecurity);

    var qty = 0L;

    while (Convert.toLong(map.get("quantity")) > qty) {

      BaseProductSecurityDetail securityDetail = new BaseProductSecurityDetail();

      // 生成一个随机UUID
      UUID uuid = UUID.randomUUID();

      // 将UUID转换为字符串
      String guid = uuid.toString();
      securityDetail.setSecurityId(baseProductSecurity.getSecurityId());
      securityDetail.setSecurityCode(guid);
      securityDetail.setSecurityStatus(BaseProductSecurityEnum.NEWED.getName());

      baseProductSecurityDetailService.save(securityDetail);
      qty++;
    }

    return R.ok("操作成功");
  }


  /**
   * 作废
   *
   * @param map 参数
   * @return 返回查询列表数据
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> cancel(Map<String, Object> map) {
    Long[] _ids = Convert.toLongArray(map.get("securityIds"));

    for (long securityId : _ids) {
      LambdaQueryWrapper<BaseProductSecurityDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
      detailLambdaQueryWrapper.eq(BaseProductSecurityDetail::getSecurityId, securityId);
      detailLambdaQueryWrapper.ne(BaseProductSecurityDetail::getSecurityStatus, BaseProductSecurityEnum.NEWED.getName());
      List<BaseProductSecurityDetail> productSecurityDetails = baseProductSecurityDetailService.list(detailLambdaQueryWrapper);

      if (ObjectUtil.isNotEmpty(productSecurityDetails)) {
        throw new ServiceException("作废失败，明细状态不是新建！");
      }


      LambdaUpdateWrapper<BaseProductSecurityDetail> updateWrapper = new LambdaUpdateWrapper<>();
      updateWrapper.set(BaseProductSecurityDetail::getSecurityStatus, BaseProductSecurityEnum.VOIDED.getName());
      updateWrapper.eq(BaseProductSecurityDetail::getSecurityId, securityId);
      baseProductSecurityDetailService.update(updateWrapper);
    }

    return R.ok("作废成功");
  }


  /**
   * 明细作废
   *
   * @param map 参数
   * @return 返回查询列表数据
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> detailCancel(Map<String, Object> map) {
    Long[] securityDetailIds = Convert.toLongArray(map.get("securityDetailIds"));

    LambdaQueryWrapper<BaseProductSecurityDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
    detailLambdaQueryWrapper.in(BaseProductSecurityDetail::getSecurityDetailId, securityDetailIds);
    detailLambdaQueryWrapper.ne(BaseProductSecurityDetail::getSecurityStatus, BaseProductSecurityEnum.NEWED.getName());
    List<BaseProductSecurityDetail> productSecurityDetails = baseProductSecurityDetailService.list(detailLambdaQueryWrapper);

    if (ObjectUtil.isNotEmpty(productSecurityDetails)) {
      throw new ServiceException("[" + productSecurityDetails.stream().map(BaseProductSecurityDetail::getSecurityCode).collect(Collectors.joining(",")) + "]作废失败，明细状态不是新建！");
    }

    LambdaUpdateWrapper<BaseProductSecurityDetail> updateWrapper = new LambdaUpdateWrapper<>();
    updateWrapper.set(BaseProductSecurityDetail::getSecurityStatus, BaseProductSecurityEnum.VOIDED.getName());
    updateWrapper.in(BaseProductSecurityDetail::getSecurityDetailId, securityDetailIds);
    baseProductSecurityDetailService.update(updateWrapper);

    return R.ok("操作成功");
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean addSecurity(Long orderId, String orderCode, List<String> snList) {
    //判断当前防伪码是否已经之前保存过
    LambdaQueryWrapper<BaseProductSecurityHistory> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(BaseProductSecurityHistory::getBillId, orderId)
      .in(BaseProductSecurityHistory::getSecurityCode, snList);
    List<BaseProductSecurityHistory> historyList = baseProductSecurityHistoryService.list(queryWrapper);
    if (ObjectUtil.isNotNull(historyList) && B.isEqual(historyList.size(), snList)) {
      return true;
    }
    //判断当前防伪码 是否被使用过
    LambdaQueryWrapper<BaseProductSecurityDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
    detailLambdaQueryWrapper.ne(BaseProductSecurityDetail::getSecurityStatus, BaseProductSecurityEnum.VOIDED.getName())
      .in(BaseProductSecurityDetail::getSecurityCode, snList);
    List<BaseProductSecurityDetail> details = baseProductSecurityDetailService.list(detailLambdaQueryWrapper);
    if (ObjectUtil.isNotNull(details) && B.isGreater(details.size())) {
      throw new ServiceException("防伪码【" + CollUtil.join(details.stream().map(item -> item.getSecurityCode()).toList(), ",") + "】已使用,请重新获取");
    }
    //添加轨迹
    for (String sn : snList){
      baseProductSecurityHistoryService.addHistory(sn,orderId,orderCode);
    }



    return true;
  }


}

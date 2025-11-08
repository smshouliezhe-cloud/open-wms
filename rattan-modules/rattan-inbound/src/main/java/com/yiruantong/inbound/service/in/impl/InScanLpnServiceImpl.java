package com.yiruantong.inbound.service.in.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.domain.storage.BasePlate;
import com.yiruantong.basic.service.storage.IBasePlateService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.enums.base.PlateTypeEnum;
import com.yiruantong.common.core.enums.base.UsingEnum;
import com.yiruantong.common.core.enums.in.InShelveStatusEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.DateUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.inbound.domain.in.InShelve;
import com.yiruantong.inbound.domain.in.InShelveDetail;
import com.yiruantong.inbound.domain.in.bo.InScanOrderBo;
import com.yiruantong.inbound.service.in.IInScanLpnService;
import com.yiruantong.inbound.service.in.IInScanOrderService;
import com.yiruantong.inbound.service.in.IInShelveDetailService;
import com.yiruantong.inbound.service.in.IInShelveService;
import com.yiruantong.inventory.domain.helper.RecommendPositionDto;
import com.yiruantong.inventory.service.rule.IRecommendPositionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class InScanLpnServiceImpl implements IInScanLpnService {
  private final IBasePlateService basePlateService;
  private final IInScanOrderService inScanOrderService;
  private final IInShelveService inShelveService;
  private final IInShelveDetailService inShelveDetailService;
  private final IRecommendPositionService recommendPositionService;


  @Override
  public R<Void> scanPlateInSave(InScanOrderBo inScanOrderBo) {
    return inScanOrderService.normalScanSave(inScanOrderBo);
  }


  //#region LPN号扫描上架 -- 获取数据

  /**
   * - LPN号扫描上架 -- 获取数据
   *
   * @param map 前段传入数据
   * @return 返回查询数据
   */
  @Override
  public List<Map<String, Object>> getShelveInfoByLpnCode(Map<String, Object> map) {
    String caseNumber = Convert.toStr(map.get(("caseNumber")));
    Assert.isFalse(StringUtils.isEmpty(caseNumber), "LPN上架单不能为空");
    List<String> states = new ArrayList<>();
    states.add(InShelveStatusEnum.SHELVING.getName());
    states.add(InShelveStatusEnum.WAITING.getName());
    states.add(InShelveStatusEnum.PARTIAL_FINISHED.getName());

    MPJLambdaWrapper<InShelveDetail> enterDetailMPJLambdaWrapper = new MPJLambdaWrapper<InShelveDetail>()
      .selectAll(InShelveDetail.class)
      .select(BaseProduct::getRelationCode, BaseProduct::getRelationCode2, BaseProduct::getRelationCode3, BaseProduct::getRelationCode4, BaseProduct::getRelationCode5, BaseProduct::getMiddleBarcode, BaseProduct::getMiddleUnitConvert, BaseProduct::getBigBarcode)
      .innerJoin(InShelve.class, InShelve::getShelveId, InShelveDetail::getShelveId)
      .innerJoin(BaseProduct.class, BaseProduct::getProductId, InShelveDetail::getProductId)
      .in(InShelve::getShelveStatus, states)
      .eq(InShelveDetail::getCaseNumber, caseNumber)
      .ne(InShelveDetail::getShelveStatus, InShelveStatusEnum.FINISHED.getName())
      .apply(" (t.quantity-IFNULL(t.shelved_quantity,0))>0");

    List<Map<String, Object>> maps = inShelveDetailService.selectJoinMaps(enterDetailMPJLambdaWrapper);
    Assert.isFalse(B.isEqual(maps.size()), "未找到对应的LPN上架单明细数据！");
    Long shelveId = Convert.toLong(maps.get(0).get("shelveId"));
    InShelve inShelveInfo = inShelveService.getById(shelveId);
    Assert.isFalse(B.isEqual(maps.size()), "LPN上架单不存在");

    for (var detail : maps) {
      RecommendPositionDto recommendPsoitionDto = BeanUtil.copyProperties(inShelveInfo, RecommendPositionDto.class);// 复制数据
      recommendPsoitionDto.setInQty(Convert.toBigDecimal(detail.get("quantity")));
      recommendPsoitionDto.setPlateCode(Convert.toStr(detail.get("plateCode")));
      recommendPsoitionDto.setBatchNumber(Convert.toStr(detail.get("batchNumber")));
      recommendPsoitionDto.setProductId(Convert.toLong(detail.get("productId")));
      recommendPsoitionDto.setProductSpec(Convert.toStr(detail.get("productSpec")));
      recommendPsoitionDto.setWeight(Convert.toBigDecimal(detail.get("weight")));
      recommendPsoitionDto.setUnitCube(Convert.toBigDecimal(detail.get("unitCube")));
      String positionName = recommendPositionService.getRecommendPosition(recommendPsoitionDto);
      detail.put("positionName", positionName);
      detail.put("shelveCode", inShelveInfo.getShelveCode());
      detail.put("shelveId", inShelveInfo.getShelveId());
      detail.put("storageId", inShelveInfo.getStorageId());
      detail.put("storageName", inShelveInfo.getStorageName());

      detail.put("consignorId", inShelveInfo.getConsignorId());
      detail.put("consignorCode", inShelveInfo.getConsignorCode());
      detail.put("consignorName", inShelveInfo.getConsignorName());

      detail.put("providerId", inShelveInfo.getProviderId());
      detail.put("providerCode", inShelveInfo.getProviderCode());
      detail.put("providerShortName", inShelveInfo.getProviderShortName());
    }

    //更新这个上架单为当前用户所有
    LambdaUpdateWrapper<InShelve> shelveLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    shelveLambdaUpdateWrapper.set(InShelve::getUserId, LoginHelper.getUserId())
      .set(InShelve::getNickName, LoginHelper.getNickname())
      .set(InShelve::getShelveStatus, InShelveStatusEnum.SHELVING.getName())
      .eq(InShelve::getShelveId, inShelveInfo.getShelveId());
    if (ObjectUtil.isEmpty(inShelveInfo.getStartDate())) {
      shelveLambdaUpdateWrapper.set(InShelve::getStartDate, DateUtils.getNowDate());
    }
    inShelveService.update(shelveLambdaUpdateWrapper);
    return maps;
  }
  //#endregion
}

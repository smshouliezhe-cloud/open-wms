package com.yiruantong.inbound.liteflow.InReturnAuditingCmp;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yiruantong.basic.domain.outbound.SortingRuleExtendVo;
import com.yiruantong.basic.domain.storage.BasePosition;
import com.yiruantong.basic.service.outbound.ISortingRuleExtendService;
import com.yiruantong.basic.service.storage.IBasePositionService;
import com.yiruantong.common.core.enums.base.HolderSourceTypeEnum;
import com.yiruantong.common.core.enums.base.SortingStatusEnum;
import com.yiruantong.common.core.enums.in.InReturnActionEnum;
import com.yiruantong.common.core.enums.in.InReturnEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.DateUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.inbound.domain.service.InReturn;
import com.yiruantong.inbound.domain.service.InReturnDetail;
import com.yiruantong.inbound.liteflow.Context.InReturnAuditingContext;
import com.yiruantong.inbound.service.service.IInReturnDetailService;
import com.yiruantong.inbound.service.service.IInReturnService;
import com.yiruantong.inbound.service.service.IInReturnStatusHistoryService;
import com.yiruantong.inventory.domain.base.dto.CommonDetailDto;
import com.yiruantong.inventory.domain.base.dto.CommonMainDto;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryComposeVo;
import com.yiruantong.inventory.service.base.IInventoryBaseService;
import com.yiruantong.inventory.service.base.IInventoryCommonService;
import com.yiruantong.inventory.service.core.ICoreInventoryHolderService;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@LiteflowComponent(id = "returnAuditingCmp", name = "2.退货单审核")
@RequiredArgsConstructor
public class ReturnAuditingCmp extends NodeComponent implements IInventoryBaseService {
  private final IInventoryCommonService inventoryCommonService;
  private final IInReturnStatusHistoryService inReturnStatusHistoryService;
  private final IInReturnService  inReturnService;
  private final ICoreInventoryHolderService coreInventoryHolderService;
  private final IInReturnDetailService inReturnDetailService;
  private final ISortingRuleExtendService sortingRuleExtendService;

  private final IBasePositionService basePositionService;
  @Override
  public void process() {
    InReturnAuditingContext ctx = this.getRequestData();
    InReturn returnInfo =ctx.getInReturn();
    List<InReturnDetail> detailList = ctx.getDetailList();
    inventoryCommonService.setBizService(this);

    //#region     分配库存
    // 构建DTO数据 - 主表
    CommonMainDto commonMainDto = BeanUtil.copyProperties(returnInfo, CommonMainDto.class);
    commonMainDto.setMainId(returnInfo.getReturnId());
    commonMainDto.setMainCode(returnInfo.getReturnCode());

    // 构建DTO数据 - 明细集合
    List<CommonDetailDto> commonDetailDtoList = detailList.stream().map(m -> {
      CommonDetailDto detailDto = BeanUtil.copyProperties(m, CommonDetailDto.class);
      detailDto.setMainId(m.getReturnId()); // 主表ID
      detailDto.setDetailId(m.getReturnDetailId()); // 明细ID
      detailDto.setInQuantity(m.getReturnQuantity());  // 入库数量
      detailDto.setOutQuantity(m.getReturnQuantity()); // 出库数量
      detailDto.setPositionNameIn(m.getPositionName()); // 入库货位
      detailDto.setPositionNameOut(m.getPositionName());  // 出库货位
      detailDto.setStorageId(commonMainDto.getStorageId());
      detailDto.setStorageName(commonMainDto.getStorageName());
      detailDto.setConsignorId(commonMainDto.getConsignorId());
      detailDto.setConsignorCode(commonMainDto.getConsignorCode());
      detailDto.setConsignorName(commonMainDto.getConsignorName());
      detailDto.setBatchNumber(null);
      return detailDto;
    }).toList();
    // 分拣库存
    HolderSourceTypeEnum holderSourceTypeEnum = HolderSourceTypeEnum.RETURN_ORDER;
    if (ObjectUtil.isNotNull(returnInfo.getOrderType()) && returnInfo.getOrderType().equals(HolderSourceTypeEnum.QUALITY_INSPECTION_RETURN.getName())) {
      holderSourceTypeEnum = HolderSourceTypeEnum.QUALITY_INSPECTION_RETURN;
    }
    var sortingResult = inventoryCommonService.sorting(commonMainDto, commonDetailDtoList, holderSourceTypeEnum);
    // 不是完全分拣成功的，清空占位
    if (!sortingResult.isResult()) {
      coreInventoryHolderService.clearHolder(List.of(holderSourceTypeEnum), returnInfo.getReturnId());
      //添加轨迹
      inReturnStatusHistoryService.addHistoryInfo(returnInfo, InReturnActionEnum.SUCCESS, InReturnEnum.NEWED, InReturnEnum.NEWED, "订单缺货");

      //修改退货单申请单状态
      LambdaUpdateWrapper<InReturnDetail> wrapperDetail = new LambdaUpdateWrapper<>();
      wrapperDetail.set(InReturnDetail::getSortingStatus, SortingStatusEnum.NONE.getId())
        .eq(InReturnDetail::getReturnId, returnInfo.getReturnId());
      inReturnDetailService.update(wrapperDetail);

      //修改退货单申请单状态
      LambdaUpdateWrapper<InReturn> wrapper = new LambdaUpdateWrapper<>();
      wrapper.set(InReturn::getSortingStatus, SortingStatusEnum.NONE.getId())
        .eq(InReturn::getReturnId, returnInfo.getReturnId());
      inReturnService.update(wrapper);

    }
    //提示信息
    Assert.isFalse(!sortingResult.isResult(), sortingResult.getMsg());

    //#endregion

    //修改退货单申请单状态
    LambdaUpdateWrapper<InReturn> wrapper = new LambdaUpdateWrapper<>();
    wrapper.set(InReturn::getAuditing, 2L)
      .set(InReturn::getAuditDate, DateUtils.getNowDate())
      .set(InReturn::getAuditor, LoginHelper.getNickname())
      .set(InReturn::getReturnStatus, InReturnEnum.SUCCESS.getName())
      .eq(InReturn::getReturnId, returnInfo.getReturnId());
    inReturnService.update(wrapper);

    //添加轨迹
    inReturnStatusHistoryService.addHistoryInfo(returnInfo, InReturnActionEnum.SUCCESS, InReturnEnum.NEWED, InReturnEnum.SUCCESS, "");


    detailList = inReturnDetailService.selectListByMainId(returnInfo.getReturnId());
    // 入库单状态处理
    if (detailList.stream().allMatch(f -> NumberUtil.equals(f.getSortingStatus(), Convert.toByte(2)))) {
      // 完全上架
      inReturnService.updateAllocateStatus(returnInfo, SortingStatusEnum.ASSIGNED);

    } else if (
      detailList.stream().anyMatch(f -> B.isGreater(f.getLackStorage()) && B.isLess(f.getLackStorage(), f.getReturnQuantity()))) {
      // 部分上架
      inReturnService.updateAllocateStatus(returnInfo, SortingStatusEnum.LACK);
    }

  }


  //#region 更新缺货数量 updateLackStorage
  @Override
  public void updateLackStorage(List<CommonDetailDto> detailList) {
    InReturn inReturn = inReturnService.getById(detailList.get(0).getMainId());
    for (var detail : detailList) {
      HolderSourceTypeEnum holderSourceTypeEnum = HolderSourceTypeEnum.RETURN_ORDER;
      if (ObjectUtil.isNotNull(inReturn.getOrderType()) && inReturn.getOrderType().equals(HolderSourceTypeEnum.QUALITY_INSPECTION_RETURN.getName())) {
        holderSourceTypeEnum = HolderSourceTypeEnum.QUALITY_INSPECTION_RETURN;
      }

      // 获取占位
      BigDecimal placeholderStorage = coreInventoryHolderService.getPlaceholderStorage(detail.getMainId(), detail.getDetailId(), holderSourceTypeEnum);
      BigDecimal lackStorage = B.sub(detail.getOutQuantity(), placeholderStorage); // 缺货数量
      if (B.isLess(lackStorage)) lackStorage = BigDecimal.ZERO;

      detail.setLackStorage(lackStorage);
      // 更新缺货数量
      LambdaUpdateWrapper<InReturnDetail> wrapper = new LambdaUpdateWrapper<>();
      wrapper
        .set(InReturnDetail::getLackStorage, lackStorage)
        // 已分配
        .set(B.isLessOrEqual(lackStorage), InReturnDetail::getSortingStatus, SortingStatusEnum.ASSIGNED.getId())
        // 部分缺货
        .set(B.isGreater(lackStorage) && B.isGreater(detail.getOutQuantity(), lackStorage), InReturnDetail::getSortingStatus, SortingStatusEnum.PARTIAL_ASSIGNED.getId())
        // 缺货
        .set(B.isEqual(detail.getOutQuantity(), lackStorage), InReturnDetail::getSortingStatus, SortingStatusEnum.LACK.getId())
        .eq(InReturnDetail::getReturnDetailId, detail.getDetailId());
      inReturnDetailService.update(wrapper);
    }
  }
  //#endregion


  //#region 自定义分拣过滤器
  @Override
  public List<CoreInventoryComposeVo> customSortingFilter(CommonMainDto mainInfo, CommonDetailDto detailInfo, List<CoreInventoryComposeVo> inventoryList) {

    final List<SortingRuleExtendVo> sortingRuleList = sortingRuleExtendService.selectRuleList(InReturnEnum.QUALITY_INSPECTION_RETURN.getName(), mainInfo.getMainId(), detailInfo.getDetailId());

    for (var sortingRule : sortingRuleList) {
      if (ObjectUtil.isNotEmpty(sortingRule)) {
        // 指定货位
        if (StringUtils.isNotEmpty(sortingRule.getPositionName())) {
          inventoryList = inventoryList.stream().filter(f -> ObjectUtil.equals(f.getPositionName(), sortingRule.getPositionName())).toList();
          if (inventoryList.isEmpty()) continue;
          else {
            BasePosition position = basePositionService.getByName(mainInfo.getStorageId(), sortingRule.getPositionName());

            Assert.isFalse(ObjectUtil.isEmpty(position), "商品编号：" + sortingRule.getProductCode() + "指定货位未找到");
//            rulePositionTypeList.add(position.getPositionType());
          }
        }

        // 指定拍号
        if (StringUtils.isNotEmpty(sortingRule.getPlateCode())) {
          inventoryList = inventoryList.stream().filter(f -> ObjectUtil.equals(f.getPlateCode(), sortingRule.getPlateCode())).toList();
          if (inventoryList.isEmpty()) continue;
        }

        // 指定批次号
        if (StringUtils.isNotEmpty(sortingRule.getBatchNumber())) {
          inventoryList = inventoryList.stream().filter(f -> ObjectUtil.equals(f.getBatchNumber(), sortingRule.getBatchNumber())).toList();
          if (inventoryList.isEmpty()) continue;
        }

        // 指定商品规格
        if (StringUtils.isNotEmpty(sortingRule.getProductSpec())) {
          inventoryList = inventoryList.stream().filter(f -> ObjectUtil.equals(f.getProductSpec(), sortingRule.getProductSpec())).toList();
          if (inventoryList.isEmpty()) continue;
        }

        // 指定唯一码
        if (StringUtils.isNotEmpty(sortingRule.getSingleSignCode())) {
          inventoryList = inventoryList.stream().filter(f -> ObjectUtil.equals(f.getSingleSignCode(), sortingRule.getSingleSignCode())).toList();
          if (inventoryList.isEmpty()) continue;
        }

        // 指定项目号
        if (StringUtils.isNotEmpty(sortingRule.getProjectCode())) {
          inventoryList = inventoryList.stream().filter(f -> ObjectUtil.equals(f.getProjectCode(), sortingRule.getProjectCode())).toList();
          if (inventoryList.isEmpty()) continue;
        }

        // 指定箱号
        if (StringUtils.isNotEmpty(sortingRule.getCaseNumber())) {
          inventoryList = inventoryList.stream().filter(f -> ObjectUtil.equals(f.getCaseNumber(), sortingRule.getCaseNumber())).toList();
          if (inventoryList.isEmpty()) continue;
        }

        // 指定库存ID
        if (ObjectUtil.isNotEmpty(sortingRule.getInventoryId())) {
          inventoryList = inventoryList.stream().filter(f -> ObjectUtil.equals(f.getInventoryId(), sortingRule.getInventoryId())).toList();
          if (inventoryList.isEmpty()) continue;
        }

        // 生产日期
        if (ObjectUtil.isNotEmpty(sortingRule.getProduceDate()) && ObjectUtil.isEmpty(sortingRule.getProduceDateGt())) {
          SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
          inventoryList = inventoryList.stream().filter(f -> ObjectUtil.isNotEmpty(f.getProduceDate())).filter(f -> ObjectUtil.equals(formatter.format(f.getProduceDate()), formatter.format(sortingRule.getProduceDate()))).toList();
          if (inventoryList.isEmpty()) continue;
        }

        List<SortingRuleExtendVo> nonEmptyProduceDateGt = sortingRuleList.stream()
          .filter(k -> StringUtils.isNotEmpty(Convert.toStr(k.getProduceDateGt())))
          .toList();
        if (ObjectUtil.isNotEmpty(sortingRule.getProduceDateGt())) {

          inventoryList = inventoryList.stream()
            .filter(f -> nonEmptyProduceDateGt.stream()
              .anyMatch(k -> {
                LocalDate date1 = f.getProduceDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate date2 = k.getProduceDateGt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                return date1.equals(date2) || date1.isAfter(date2);
              }))
            .collect(Collectors.toList());
        }


      }

    }


    return inventoryList;
  }
  //#endregion
}

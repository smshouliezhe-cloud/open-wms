package com.yiruantong.composite.liteflow.QuickOut;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.yiruantong.basic.domain.product.BaseProvider;
import com.yiruantong.basic.service.product.IBaseProviderService;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.base.SortingStatusEnum;
import com.yiruantong.common.core.enums.inventory.StorageEnterStatusEnum;
import com.yiruantong.common.core.enums.inventory.StorageOuterStatusEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.inventory.domain.operation.StorageEnter;
import com.yiruantong.inventory.domain.operation.StorageEnterDetail;
import com.yiruantong.inventory.domain.operation.StorageOuter;
import com.yiruantong.inventory.domain.operation.StorageOuterDetail;
import com.yiruantong.inventory.domain.operation.vo.StorageEnterDetailVo;
import com.yiruantong.inventory.service.operation.IStorageEnterDetailService;
import com.yiruantong.inventory.service.operation.IStorageEnterService;
import com.yiruantong.inventory.service.operation.IStorageOuterDetailService;
import com.yiruantong.inventory.service.operation.IStorageOuterService;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.domain.out.bo.OutScanDetailBo;
import com.yiruantong.outbound.domain.out.bo.OutScanMainBo;
import com.yiruantong.outbound.liteflow.Context.QuickOutContext;
import com.yiruantong.outbound.service.out.IOutOrderDetailService;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@LiteflowComponent(id = "qutOrderToStorageOuterCmp", name = "4.出库单数量溢出处理(湘钢)")
@RequiredArgsConstructor
public class OutOrderToStorageOuterCmp extends NodeComponent {
  private final IStorageEnterDetailService storageEnterDetailService;
  private final IStorageOuterService storageOuterService;
  private final IStorageOuterDetailService storageOuterDetailService;
  private final IOutOrderDetailService outOrderDetailService;

  /**
   * 如果 实出数量 < 预计出库数量， 则需要 同客户通商品 判断是否是需要根据其他入库单补数据
   */
  @Override
  public void process() {
    QuickOutContext ctx = this.getContextBean(QuickOutContext.class);

    OutScanMainBo outScanMainBo = ctx.getOutScanMainBo();
    OutOrder outOrder = ctx.getOutOrder();
    var list = outScanMainBo.getDataList().stream().filter(item -> B.isGreater(item.getQuantityInvoiced(), item.getFinishedQuantity())).toList();
    if (ObjectUtil.isNotNull(list) && B.isGreater(list.size())) {
      //生成其他出库单
      StorageOuter storageOuter = new StorageOuter();
      BeanUtil.copyProperties(outOrder, storageOuter);
      storageOuter.setOuterId(null);
      storageOuter.setOuterCode(DBUtils.getCodeRegular(MenuEnum.MENU_1600));
      storageOuter.setSourceCode(outOrder.getOrderCode());
      storageOuter.setSourceId(outOrder.getOrderId());
      storageOuter.setSourceType("出库单溢出");
      storageOuter.setTotalOuterQuantity(BigDecimal.ZERO);
      storageOuter.setCreateBy(null);
      storageOuter.setCreateByName(null);
      storageOuter.setCreateTime(null);
      storageOuter.setAuditing(null);
      storageOuter.setSortingStatus(SortingStatusEnum.NONE.getId());
      storageOuter.setOuterStatus(StorageOuterStatusEnum.NEWED.getName());
      storageOuter.setOrderType("出库单溢出");

      storageOuterService.save(storageOuter);

      for (var scanInfo : ctx.getOutScanMainBo().getDataList()) {
        if (B.isGreater(scanInfo.getQuantityInvoiced(), scanInfo.getFinishedQuantity())) {
          // 查询当前 客户 商品 是否还有溢出数量
          MPJLambdaWrapper<StorageEnterDetail> enterMPJLambdaWrapper = new MPJLambdaWrapper<>();
          enterMPJLambdaWrapper.eq(StorageEnter::getConsignorId, outOrder.getConsignorId())
            .eq(StorageEnterDetail::getProductId, scanInfo.getProductId())
            .apply("enter_quantity > IFNULL(JSON_EXTRACT(t.expand_fields, '$.deductedQuantity'),0)")
            .innerJoin(StorageEnter.class, StorageEnter::getEnterId, StorageEnter::getEnterId);
          List<StorageEnterDetailVo> storageEnterDetailVos = storageEnterDetailService.selectList(enterMPJLambdaWrapper);

          //找到了 已经溢出的商品，
          if (ObjectUtil.isNotNull(storageEnterDetailVos) && B.isGreater(storageEnterDetailVos.size())) {


            //生成其他出库单明细信息
            for (var detailInfo : storageEnterDetailVos) {
              StorageOuterDetail storageOuterDetail = new StorageOuterDetail();
              BeanUtil.copyProperties(detailInfo, storageOuterDetail);
              storageOuterDetail.setOuterId(storageOuter.getOuterId());

              //查看差值多少
              BigDecimal quantity = B.sub(scanInfo.getQuantityInvoiced(), scanInfo.getFinishedQuantity());

              //查看当前明细
              BigDecimal remainQuantity = B.sub(detailInfo.getEnterQuantity(), Convert.toBigDecimal(detailInfo.getExpandFields().get("deductedQuantity")));

              // 出库数量
              BigDecimal outerQuantity = BigDecimal.ZERO;
              // 剩余数量 (已抵扣数量)
              BigDecimal deductedQuantity = BigDecimal.ZERO;
              //如果溢出数量>= 差值，则
              if (B.isGreaterOrEqual(remainQuantity, quantity)) {
                outerQuantity = quantity;
                // 溢出数量 - 差值数量  = 剩余数量
                deductedQuantity = B.sub(remainQuantity, quantity);
              } else {
                outerQuantity = remainQuantity;
                deductedQuantity = remainQuantity;
              }
              storageOuterDetail.setOuterQuantity(outerQuantity);
              storageOuterDetailService.save(storageOuterDetail);

              deductedQuantity = B.add(deductedQuantity, Convert.toBigDecimal(detailInfo.getExpandFields().get("deductedQuantity")));
              // 回更其它入库单
              LambdaUpdateWrapper<StorageEnterDetail> enterDetailLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
              enterDetailLambdaUpdateWrapper
                .setSql("expand_fields = json_set(expand_fields,'$.deductedQuantity', '" + deductedQuantity + "')")
                .eq(StorageEnterDetail::getEnterDetailId, detailInfo.getEnterDetailId());
              storageEnterDetailService.update(enterDetailLambdaUpdateWrapper);

              //修改出库单明细数据
              LambdaUpdateWrapper<OutOrderDetail> outOrderDetailLambdaUpdateWrapper =new LambdaUpdateWrapper<>();
              outOrderDetailLambdaUpdateWrapper.set(OutOrderDetail::getQuantityOrder,B.add(scanInfo.getFinishedQuantity(), outerQuantity))
                .setSql("expand_fields = json_set(expand_fields,'$.outerQuantity', '" + deductedQuantity + "')")
                .eq(OutOrderDetail::getOrderDetailId,scanInfo.getOrderDetailId());
              outOrderDetailService.update(outOrderDetailLambdaUpdateWrapper);

              //修改实际出库数量
              scanInfo.setFinishedQuantity(B.add(scanInfo.getFinishedQuantity(), outerQuantity));
              Map<String, Object> expandFields = scanInfo.getExpandFields();
              expandFields.put("outerQuantity",deductedQuantity);
              scanInfo.setExpandFields(expandFields);
            }

            // 调用其他入库单审核功能
            storageOuterService.multiAuditing(new ArrayList<>(Arrays.asList(storageOuter.getOuterId())));
          }
        }
      }
    }
  }

}


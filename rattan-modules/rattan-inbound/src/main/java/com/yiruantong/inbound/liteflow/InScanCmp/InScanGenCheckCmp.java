package com.yiruantong.inbound.liteflow.InScanCmp;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.DateUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.inbound.domain.in.*;
import com.yiruantong.inbound.domain.in.bo.InScanOrderBo;
import com.yiruantong.inbound.domain.in.bo.InScanOrderDetailBo;
import com.yiruantong.inbound.liteflow.Context.InScanContext;
import com.yiruantong.inbound.service.in.IInQualityCheckDetailService;
import com.yiruantong.inbound.service.in.IInQualityCheckService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@LiteflowComponent(id = "inScanGenCheckCmp", name = "4.生成质检单")
@RequiredArgsConstructor
public class InScanGenCheckCmp extends NodeComponent {
  private final IInQualityCheckService inQualityCheckService;
  private final IInQualityCheckDetailService inQualityCheckDetailService;

  @Override
  public void process() {
    InScanContext ctx = this.getContextBean(InScanContext.class);
    InScanOrderBo inScanOrderBo = ctx.getInScanOrderBo();
    InOrder inOrder = ctx.getInOrder();
    InEnter inEnter = ctx.getInEnter();
    Assert.isFalse(ObjectUtil.isNull(inEnter), "必须先生成入库单，才能生成质检单");

    //#region  生成质检数据
    InQualityCheck inQualityCheck = null;
    if (ctx.isInGenerateQualityCheck() && Objects.equals(inOrder.getIsChecking(), EnableEnum.ENABLE.getId())) {
      String qualityCheckCode = DBUtils.getCodeRegular(MenuEnum.MENU_1641, LoginHelper.getTenantId()); // 质检单号
      inQualityCheck = new InQualityCheck();

      BeanUtil.copyProperties(inOrder, inQualityCheck);
      inQualityCheck.setQualityCheckCode(qualityCheckCode);
      inQualityCheck.setAuditor(null);
      inQualityCheck.setAuditDate(null);
      inQualityCheck.setAuditing(null);
      inQualityCheck.setSourceCode(inEnter.getEnterCode());
      inQualityCheck.setSourceId(inEnter.getEnterId().toString());
      inQualityCheck.setOrderCode(inOrder.getOrderCode());
      inQualityCheck.setOrderId(inOrder.getOrderId());
      inQualityCheck.setEnterCode(inEnter.getEnterCode());
      inQualityCheck.setEnterId(inEnter.getEnterId());
      inQualityCheck.setOrderId(inOrder.getOrderId());
      inQualityCheck.setCreateTime(DateUtils.getNowDate());
      inQualityCheck.setCreateBy(LoginHelper.getUserId());
      inQualityCheck.setCreateByName(LoginHelper.getNickname());
      inQualityCheck.setSourceType("入库单");
      inQualityCheckService.save(inQualityCheck); // 保存质检单
    }
    //#endregion

    ctx.setInQualityCheck(inQualityCheck);
    genDetail(ctx, inQualityCheck);
  }

  /**
   * 生成质检单明细
   *
   * @param cxt
   */
  private void genDetail(InScanContext cxt, InQualityCheck inQualityCheck) {
    InEnter inEnter = cxt.getInEnter();
    InOrder inOrder = cxt.getInOrder();
    List<InScanOrderDetailBo> dataList = cxt.getInScanOrderBo().getDataList();

    //用于存放 报废出库 入库明细信息
    List<InEnterDetail> invalidateDetails = new ArrayList<>();
    // 明细处理
    for (InScanOrderDetailBo inScanOrderDetailBo : dataList) {
      // 获取入库单明细
      InEnterDetail inEnterDetail = cxt.getInEnterDetailList().stream().filter(x -> StringUtils.equals(x.getProductCode(), inScanOrderDetailBo.getProductCode())).findFirst().orElse(null);
      Assert.isFalse(ObjectUtil.isNull(inEnterDetail), "入库明细不存在，生成质检单明细失败");

      BigDecimal finishedQuantity = inScanOrderDetailBo.getFinishedQuantity(); // 扫描完成的数量
      Assert.isFalse(B.isLessOrEqual(finishedQuantity), "扫描数量不能小于0");

      //#region 生成质检单明细
      if (cxt.isInGenerateQualityCheck() && Objects.equals(inOrder.getIsChecking(), EnableEnum.ENABLE.getId())) {
        InQualityCheckDetail inQualityCheckDetail = new InQualityCheckDetail();
        BeanUtil.copyProperties(inEnterDetail, inQualityCheckDetail);
        if (inQualityCheck != null) {
          inQualityCheckDetail.setQualityCheckId(inQualityCheck.getQualityCheckId());
        }
        inQualityCheckDetail.setCheckQuantity(finishedQuantity);
        inQualityCheckDetail.setQuantity(finishedQuantity);
        inQualityCheckDetail.setQualifiedRate(BigDecimal.valueOf(100));

        inQualityCheckDetailService.save(inQualityCheckDetail); // 保存质检明细
      }
      //#endregion
    }
    //#endregion
  }

}

package com.yiruantong.outbound.controller.out;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.domain.out.bo.OutOrderDetailBo;
import com.yiruantong.outbound.domain.out.bo.OutScanDetailBo;
import com.yiruantong.outbound.domain.out.vo.OutOrderDetailComposeVo;
import com.yiruantong.outbound.domain.out.vo.OutOrderDetailVo;
import com.yiruantong.outbound.mapper.out.OutOrderDetailMapper;
import com.yiruantong.outbound.service.out.IOutOrderDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 销售订单明细
 *
 * @author YiRuanTong
 * @date 2023-10-19
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/outbound/out/orderDetail")
public class OutOrderDetailController extends AbstractController<OutOrderDetailMapper, OutOrderDetail, OutOrderDetailVo, OutOrderDetailBo> {
  private final IOutOrderDetailService outOrderDetailService;

  //#region 明细订单拆分（查询单据）
  @PostMapping(value = "/selectDetailSplitList")
  public R<List<OutOrderDetailComposeVo>> selectDetailSplitList(@RequestBody Map<String, Object> map) {
    return R.ok(outOrderDetailService.selectDetailSplitList(map));
  }
  //#endregion

  //#region 拆分订单（确认）
  @PostMapping(value = "/splitOrder")
  public R<Void> splitOrder(@RequestBody Map<String, Object> map, LoginUser loginUser) {
    return R.ok(outOrderDetailService.splitOrder(map, loginUser));
  }
  //#endregion

  /**
   * 出库订单明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/selectOutOrderDetailComposeList")
  public TableDataInfo<OutOrderDetailComposeVo> selectOutOrderDetailComposeList(@RequestBody PageQuery pageQuery) {
    return outOrderDetailService.selectOutOrderDetailComposeList(pageQuery);
  }


  /**
   * 出库单装卸任务功能保存
   *
   * @param dataList 要保存的信息
   * @return 返回查询列表数据
   */
  @PostMapping("/saveDetailTask")
  public R<Void> saveDetailTask(@RequestBody List<OutScanDetailBo> dataList) {
    return outOrderDetailService.saveDetailTask(dataList);
  }


}

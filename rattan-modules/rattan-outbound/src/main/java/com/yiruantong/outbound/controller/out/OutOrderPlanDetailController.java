package com.yiruantong.outbound.controller.out;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.outbound.domain.out.OutOrderPlanDetail;
import com.yiruantong.outbound.domain.out.vo.OutOrderPlanDetailVo;
import com.yiruantong.outbound.domain.out.bo.OutOrderPlanDetailBo;
import com.yiruantong.outbound.domain.out.vo.OutPlanDetailComposeVo;
import com.yiruantong.outbound.mapper.out.OutOrderPlanDetailMapper;
import com.yiruantong.outbound.service.out.IOutOrderPlanDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 出库计划单明细
 *
 * @author YiRuanTong
 * @date 2023-10-19
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/outbound/out/orderPlanDetail")
public class OutOrderPlanDetailController extends AbstractController<OutOrderPlanDetailMapper, OutOrderPlanDetail, OutOrderPlanDetailVo, OutOrderPlanDetailBo> {
  private final IOutOrderPlanDetailService outOrderPlanDetailService;

  /**
   * 出库计划明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/selectOutPlanDetailComposeList")
  public TableDataInfo<OutPlanDetailComposeVo> selectOutPlanDetailComposeList(@RequestBody PageQuery pageQuery) {
    return outOrderPlanDetailService.selectOutPlanDetailComposeList(pageQuery);
  }
}

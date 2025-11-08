package com.yiruantong.inbound.controller.in;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inbound.domain.in.InOrderPlanDetail;
import com.yiruantong.inbound.domain.in.vo.InOrderPlanDetailComposeVo;
import com.yiruantong.inbound.domain.in.vo.InOrderPlanDetailVo;
import com.yiruantong.inbound.domain.in.bo.InOrderPlanDetailBo;
import com.yiruantong.inbound.mapper.in.InOrderPlanDetailMapper;
import com.yiruantong.inbound.service.in.IInOrderPlanDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 收货计划单明细
 *
 * @author YiRuanTong
 * @date 2023-10-14
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inbound/in/orderPlanDetail")
public class InOrderPlanDetailController extends AbstractController<InOrderPlanDetailMapper, InOrderPlanDetail, InOrderPlanDetailVo, InOrderPlanDetailBo> {

  public final IInOrderPlanDetailService inOrderPlanDetailService;

  /**
   * 查询入库计划数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/selectPlanDetailComposeList")
  public TableDataInfo<InOrderPlanDetailComposeVo> selectPlanDetailComposeList(@RequestBody PageQuery pageQuery) {
    return inOrderPlanDetailService.selectPlanDetailComposeList(pageQuery);
  }
}

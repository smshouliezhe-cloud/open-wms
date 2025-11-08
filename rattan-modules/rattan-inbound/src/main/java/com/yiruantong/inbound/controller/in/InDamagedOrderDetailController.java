package com.yiruantong.inbound.controller.in;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inbound.domain.in.InDamagedOrderDetail;
import com.yiruantong.inbound.domain.in.vo.InDamagedDetailComposeVo;
import com.yiruantong.inbound.domain.in.vo.InDamagedOrderDetailVo;
import com.yiruantong.inbound.domain.in.bo.InDamagedOrderDetailBo;
import com.yiruantong.inbound.mapper.in.InDamagedOrderDetailMapper;
import com.yiruantong.inbound.service.in.IInDamagedOrderDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 残品入库单明细
 *
 * @author YiRuanTong
 * @date 2023-10-16
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inbound/in/damagedOrderDetail")
public class InDamagedOrderDetailController extends AbstractController<InDamagedOrderDetailMapper, InDamagedOrderDetail, InDamagedOrderDetailVo, InDamagedOrderDetailBo> {
  public final IInDamagedOrderDetailService inDamagedOrderDetailService;

  /**
   * 残品入库计划数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/selectDamagedDetailComposeList")
  public TableDataInfo<InDamagedDetailComposeVo> selectDamagedDetailComposeList(@RequestBody PageQuery pageQuery) {
    return inDamagedOrderDetailService.selectDamagedDetailComposeList(pageQuery);
  }
}

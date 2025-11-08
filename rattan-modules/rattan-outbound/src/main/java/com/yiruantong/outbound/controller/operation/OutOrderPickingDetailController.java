package com.yiruantong.outbound.controller.operation;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.outbound.domain.operation.OutOrderPickingDetail;
import com.yiruantong.outbound.domain.operation.vo.OutOrderPickingDetailComposeVo;
import com.yiruantong.outbound.domain.operation.vo.OutOrderPickingDetailVo;
import com.yiruantong.outbound.domain.operation.bo.OutOrderPickingDetailBo;
import com.yiruantong.outbound.domain.out.vo.OutOrderDetailComposeVo;
import com.yiruantong.outbound.mapper.operation.OutOrderPickingDetailMapper;
import com.yiruantong.outbound.service.operation.IOutOrderPickingDetailService;
import com.yiruantong.outbound.service.out.IOutPackageDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单拣货查询明细
 *
 * @author YRT
 * @date 2023-12-01
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/outbound/operation/orderPickingDetail")
public class OutOrderPickingDetailController extends AbstractController<OutOrderPickingDetailMapper, OutOrderPickingDetail, OutOrderPickingDetailVo, OutOrderPickingDetailBo> {
  private final IOutOrderPickingDetailService outOrderPickingDetailService;
  /**
   * 拣货下架明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/selectPickingDetailComposeList")
  public TableDataInfo<OutOrderPickingDetailComposeVo> selectPickingDetailComposeList(@RequestBody PageQuery pageQuery) {
    return outOrderPickingDetailService.selectPickingDetailComposeList(pageQuery);
  }
}

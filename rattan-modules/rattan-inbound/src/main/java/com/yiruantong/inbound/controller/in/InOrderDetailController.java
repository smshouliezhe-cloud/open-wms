package com.yiruantong.inbound.controller.in;

import cn.hutool.core.convert.Convert;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inbound.domain.in.InOrderDetail;
import com.yiruantong.inbound.domain.in.bo.InOrderDetailBo;
import com.yiruantong.inbound.domain.in.vo.InOrderDetailComposeVo;
import com.yiruantong.inbound.domain.in.vo.InOrderDetailVo;
import com.yiruantong.inbound.mapper.in.InOrderDetailMapper;
import com.yiruantong.inbound.service.in.IInOrderDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 预到货单明细
 *
 * @author YiRuanTong
 * @date 2023-10-14
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inbound/in/orderDetail")
public class InOrderDetailController extends AbstractController<InOrderDetailMapper, InOrderDetail, InOrderDetailVo, InOrderDetailBo> {
  private final IInOrderDetailService inOrderDetailService;

  /**
   * 预到货单明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/selectInOrderDetailComposeList")
  public TableDataInfo<InOrderDetailComposeVo> selectInOrderDetailComposeList(@RequestBody PageQuery pageQuery) {
    return inOrderDetailService.selectInOrderDetailComposeList(pageQuery);
  }

  /**
   * 预到货单明细查询数据
   *
   * @param map 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/getOrderDetails")
  public List<InOrderDetail> getOrderDetails(@RequestBody Map<String, Object> map) {
    return inOrderDetailService.selectListByMainId(Convert.toLong(map.get("orderId")));
  }

  /**
   * 预到货单明细查询数据
   *
   * @param map 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/getQuickEnterList")
  public R<List<Map<String, Object>>> getQuickEnterList(@RequestBody Map<String, Object> map) {
    List<Long> ids = new ArrayList<>(Arrays.asList(Convert.toLong(map.get("orderId"))));
    return inOrderDetailService.getQuickEnterList(ids);
  }

  /**
   * 预到货单明细查询数据
   *
   * @param ids 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping(value = "/getQuickEnterLists/{ids}")
  public R<List<Map<String, Object>>> getQuickEnterLists(@PathVariable List<Long> ids) {
    return inOrderDetailService.getQuickEnterLists(ids);
  }
}

package com.yiruantong.inbound.controller.service;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inbound.domain.service.InReturnDetail;
import com.yiruantong.inbound.domain.service.vo.InReturnDetailComposeVo;
import com.yiruantong.inbound.domain.service.vo.InReturnDetailVo;
import com.yiruantong.inbound.domain.service.bo.InReturnDetailBo;
import com.yiruantong.inbound.mapper.service.InReturnDetailMapper;
import com.yiruantong.inbound.service.service.IInReturnDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 退货管理明细单
 *
 * @author YiRuanTong
 * @date 2023-10-22
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inbound/service/returnDetail")
public class InReturnDetailController extends AbstractController<InReturnDetailMapper, InReturnDetail, InReturnDetailVo, InReturnDetailBo> {
  private final IInReturnDetailService inReturnDetailController;

  /**
   * 到货退货明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/selectInReturnDetailComposeList")
  public TableDataInfo<InReturnDetailComposeVo> selectInReturnDetailComposeList(@RequestBody PageQuery pageQuery) {
    return inReturnDetailController.selectInReturnDetailComposeList(pageQuery);
  }
}

package com.yiruantong.inbound.controller.in;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inbound.domain.in.InEnterDetail;
import com.yiruantong.inbound.domain.in.vo.InEnterDetailComposeVo;
import com.yiruantong.inbound.domain.in.vo.InEnterDetailVo;
import com.yiruantong.inbound.domain.in.bo.InEnterDetailBo;
import com.yiruantong.inbound.domain.in.vo.InOrderDetailComposeVo;
import com.yiruantong.inbound.mapper.in.InEnterDetailMapper;
import com.yiruantong.inbound.service.in.IInEnterDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 入库管理明细
 *
 * @author YiRuanTong
 * @date 2023-10-17
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inbound/in/enterDetail")
public class InEnterDetailController extends AbstractController<InEnterDetailMapper, InEnterDetail, InEnterDetailVo, InEnterDetailBo> {
  private final IInEnterDetailService inEnterDetailService;

  /**
   * 入库记录明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/selectInEnterDetailComposeList")
  public TableDataInfo<InEnterDetailComposeVo> selectInEnterDetailComposeList(@RequestBody PageQuery pageQuery) {
    return inEnterDetailService.selectInEnterDetailComposeList(pageQuery);
  }
}

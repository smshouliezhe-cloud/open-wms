package com.yiruantong.inbound.controller.in;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inbound.domain.in.InShelveDetail;
import com.yiruantong.inbound.domain.in.vo.InEnterDetailComposeVo;
import com.yiruantong.inbound.domain.in.vo.InShelveDetailComposeVo;
import com.yiruantong.inbound.domain.in.vo.InShelveDetailVo;
import com.yiruantong.inbound.domain.in.bo.InShelveDetailBo;
import com.yiruantong.inbound.mapper.in.InShelveDetailMapper;
import com.yiruantong.inbound.service.in.IInEnterDetailService;
import com.yiruantong.inbound.service.in.IInShelveDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品上架明细
 *
 * @author YiRuanTong
 * @date 2023-10-18
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inbound/in/shelveDetail")
public class InShelveDetailController extends AbstractController<InShelveDetailMapper, InShelveDetail, InShelveDetailVo, InShelveDetailBo> {
  private final IInShelveDetailService inShelveDetailService;

  /**
   * 上架记录明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/selectInShelveDetailComposeList")
  public TableDataInfo<InShelveDetailComposeVo> selectInShelveDetailComposeList(@RequestBody PageQuery pageQuery) {
    return inShelveDetailService.selectInShelveDetailComposeList(pageQuery);
  }
}

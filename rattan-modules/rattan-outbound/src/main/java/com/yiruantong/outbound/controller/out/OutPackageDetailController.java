package com.yiruantong.outbound.controller.out;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.outbound.domain.out.OutPackageDetail;
import com.yiruantong.outbound.domain.out.vo.OutOrderDetailComposeVo;
import com.yiruantong.outbound.domain.out.vo.OutPackageDetailComposeVo;
import com.yiruantong.outbound.domain.out.vo.OutPackageDetailVo;
import com.yiruantong.outbound.domain.out.bo.OutPackageDetailBo;
import com.yiruantong.outbound.mapper.out.OutPackageDetailMapper;
import com.yiruantong.outbound.service.out.IOutPackageDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 打包单明细
 *
 * @author YRT
 * @date 2023-11-07
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/outbound/out/packageDetail")
public class OutPackageDetailController extends AbstractController<OutPackageDetailMapper, OutPackageDetail, OutPackageDetailVo, OutPackageDetailBo> {
private final IOutPackageDetailService outPackageDetailService;
  /**
   * 打包出库明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/selectPackageDetailComposeList")
  public TableDataInfo<OutPackageDetailComposeVo> selectPackageDetailComposeList(@RequestBody PageQuery pageQuery) {
    return outPackageDetailService.selectPackageDetailComposeList(pageQuery);
  }
}

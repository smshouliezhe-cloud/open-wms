package com.yiruantong.inventory.controller.plate;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.plate.BasePlateOutDetail;
import com.yiruantong.inventory.domain.plate.bo.BasePlateOutDetailBo;
import com.yiruantong.inventory.domain.plate.vo.BasePlateOutDetailComposeVo;
import com.yiruantong.inventory.domain.plate.vo.BasePlateOutDetailVo;
import com.yiruantong.inventory.mapper.plate.BasePlateOutDetailMapper;
import com.yiruantong.inventory.service.plate.IBasePlateOutDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 容器借出明细
 *
 * @author YRT
 * @date 2023-12-21
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/plate/plateOutDetail")
public class BasePlateOutDetailController extends AbstractController<BasePlateOutDetailMapper, BasePlateOutDetail, BasePlateOutDetailVo, BasePlateOutDetailBo> {
  private final IBasePlateOutDetailService basePlateOutDetailService;
  /**
   * 获取容器借出明细
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/getBasePlateOutDetailCompose")
  public TableDataInfo<BasePlateOutDetailComposeVo> getBasePlateOutDetailCompose(@RequestBody PageQuery pageQuery) {
    return basePlateOutDetailService.getBasePlateOutDetailCompose(pageQuery);
  }






}

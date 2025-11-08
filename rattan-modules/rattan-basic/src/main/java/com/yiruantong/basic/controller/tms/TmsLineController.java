package com.yiruantong.basic.controller.tms;

import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.base.bo.GetListBo;
import com.yiruantong.basic.domain.tms.TmsLine;
import com.yiruantong.basic.domain.tms.bo.TmsLineBo;
import com.yiruantong.basic.domain.tms.vo.TmsLineVo;
import com.yiruantong.basic.mapper.tms.TmsLineMapper;
import com.yiruantong.basic.service.tms.impl.TmsLineServiceImpl;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 线路管理
 *
 * @author YRT
 * @date 2023-12-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/tms/line")
public class TmsLineController extends AbstractController<TmsLineMapper, TmsLine, TmsLineVo, TmsLineBo> {
  private final TmsLineServiceImpl tmsLineService;

  /**
   * 车辆停用
   *
   * @param ids 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping(value = "/multiAuditing")
  public R<Void> multiAuditing(@RequestBody List<Long> ids) {
    return tmsLineService.multiAuditing(ids);
  }

  /**
   * 下拉框查询
   *
   * @param getListBo 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/getList")
  public R<List<Map<String, Object>>> getList(@RequestBody GetListBo getListBo) {
    List<Map<String, Object>> list = tmsLineService.getList(getListBo);
    return R.ok(list);
  }

}

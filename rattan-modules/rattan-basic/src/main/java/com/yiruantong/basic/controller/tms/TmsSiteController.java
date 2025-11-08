package com.yiruantong.basic.controller.tms;

import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.base.bo.GetListBo;
import com.yiruantong.basic.domain.tms.TmsSite;
import com.yiruantong.basic.domain.tms.bo.TmsSiteBo;
import com.yiruantong.basic.domain.tms.vo.TmsSiteVo;
import com.yiruantong.basic.mapper.tms.TmsSiteMapper;
import com.yiruantong.basic.service.tms.ITmsSiteService;
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
 * 网点管理
 *
 * @author YRT
 * @date 2024-03-08
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/tms/site")
public class TmsSiteController extends AbstractController<TmsSiteMapper, TmsSite, TmsSiteVo, TmsSiteBo> {
  private final ITmsSiteService tmsSiteService;

  /**
   * 下拉框查询
   *
   * @param getListBo 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/getList")
  public R<List<Map<String, Object>>> getList(@RequestBody GetListBo getListBo) {
    List<Map<String, Object>> list = tmsSiteService.getList(getListBo);
    return R.ok(list);
  }
}

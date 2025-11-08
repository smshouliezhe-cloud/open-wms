package com.yiruantong.basic.controller.tms;

import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.base.bo.GetListBo;
import com.yiruantong.basic.domain.tms.BaseContainer;
import com.yiruantong.basic.domain.tms.bo.BaseContainerBo;
import com.yiruantong.basic.domain.tms.vo.BaseContainerVo;
import com.yiruantong.basic.mapper.tms.BaseContainerMapper;
import com.yiruantong.basic.service.tms.IBaseContainerService;
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
 * 集装箱信息
 *
 * @author YRT
 * @date 2025-01-19
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/tms/container")
public class BaseContainerController extends AbstractController<BaseContainerMapper, BaseContainer, BaseContainerVo, BaseContainerBo> {
  private final IBaseContainerService baseContainerService;

  /**
   * 查询商品信息
   *
   * @param getListBo 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/getList")
  public R<List<Map<String, Object>>> getList(@RequestBody GetListBo getListBo) {
    List<Map<String, Object>> list = baseContainerService.getList(getListBo);
    return R.ok(list);
  }
}

package com.yiruantong.basic.controller.base;

import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.base.BaseExpressCorp;
import com.yiruantong.basic.domain.base.bo.BaseExpressCorpBo;
import com.yiruantong.basic.domain.base.vo.BaseExpressCorpVo;
import com.yiruantong.basic.mapper.base.BaseExpressCorpMapper;
import com.yiruantong.basic.service.base.IBaseExpressCorpService;
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
 * 快递管理
 *
 * @author YRT
 * @date 2023-08-26
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/base/express-corp")
public class BaseExpressCorpController extends AbstractController<BaseExpressCorpMapper, BaseExpressCorp, BaseExpressCorpVo, BaseExpressCorpBo> {
  private final IBaseExpressCorpService baseExpressCorpService;

  //#region 获取快递公司
  @PostMapping("/getList")
  public R<List<Map<String, Object>>> getList(@RequestBody Map<String,Object> map){
    List<Map<String,Object>> list  = baseExpressCorpService.getList(map);
    return R.ok(list);
  }
  //#endregion

  /**
   * 获取快递
   */
  @PostMapping("/getExpressCorp")
  public R<Map<String, Object>> getExpressCorp(@RequestBody Map<String, Object> map) {
    return baseExpressCorpService.getExpressCorp(map);
  }
}

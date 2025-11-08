package com.yiruantong.outbound.controller.out;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.dto.QueryBo;
import com.yiruantong.common.web.core.BaseController;
import com.yiruantong.outbound.service.out.IOutPrintService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 打包单
 *
 * @author YRT
 * @date 2023-11-07
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/outbound/out/print")
public class OutPrintController extends BaseController {
  private final IOutPrintService outPrintService;

  /**
   * 拣货下架 - 获取波次明细数据
   *
   * @param queryBoList 查询条件
   * @return 返回保存结果
   */
  @PostMapping("/printOutOrderLabel")
  public R<Map<String, Object>> printOutOrderLabel(@RequestBody List<QueryBo> queryBoList) {
    return outPrintService.printOutOrderLabel(queryBoList);
  }
}

package com.yiruantong.inventory.controller.plate;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.plate.BasePlateReturnFactory;
import com.yiruantong.inventory.domain.plate.bo.BasePlateReturnFactoryBo;
import com.yiruantong.inventory.domain.plate.vo.BasePlateReturnFactoryVo;
import com.yiruantong.inventory.mapper.plate.BasePlateReturnFactoryMapper;
import com.yiruantong.inventory.service.plate.IBasePlateReturnFactoryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 容器返厂单
 *
 * @author YRT
 * @date 2024-03-05
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/plate/plateReturnFactory")
public class BasePlateReturnFactoryController extends AbstractController<BasePlateReturnFactoryMapper, BasePlateReturnFactory, BasePlateReturnFactoryVo, BasePlateReturnFactoryBo> {
  private final IBasePlateReturnFactoryService basePlateReturnFactoryService;

  /**
   * 审核
   *
   * @param ids 前端传递参数
   * @return
   */
  @PostMapping("/multiAuditing")
  public R<Void> multiAuditing(@RequestBody List<Long> ids) {
    return basePlateReturnFactoryService.multiAuditing(ids);
  }
}

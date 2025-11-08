package com.yiruantong.basic.controller.storage;

import com.yiruantong.basic.service.tms.IBaseVehicleService;
import com.yiruantong.common.core.domain.R;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.basic.domain.storage.BasePlatform;
import com.yiruantong.basic.domain.storage.vo.BasePlatformVo;
import com.yiruantong.basic.domain.storage.bo.BasePlatformBo;
import com.yiruantong.basic.mapper.storage.BasePlatformMapper;
import com.yiruantong.basic.service.storage.IBasePlatformService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 月台管理
 *
 * @author YRT
 * @date 2024-03-09
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/storage/platform")
public class BasePlatformController extends AbstractController<BasePlatformMapper, BasePlatform, BasePlatformVo, BasePlatformBo> {
  private final IBasePlatformService basePlatformService;
  /**
   * 查询月台
   *
   * @param map 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/getList")
  public R<List<Map<String, Object>>> getList(@RequestBody Map<String, Object> map) {
    List<Map<String, Object>> list = basePlatformService.getList(map);
    return R.ok(list);
  }
}

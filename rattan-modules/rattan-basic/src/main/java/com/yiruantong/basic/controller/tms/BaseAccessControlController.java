package com.yiruantong.basic.controller.tms;

import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.tms.BaseAccessControl;
import com.yiruantong.basic.domain.tms.bo.BaseAccessControlBo;
import com.yiruantong.basic.domain.tms.vo.BaseAccessControlVo;
import com.yiruantong.basic.mapper.tms.BaseAccessControlMapper;
import com.yiruantong.basic.service.tms.IBaseAccessControlService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.idempotent.annotation.RepeatSubmit;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 门禁信息
 *
 * @author YRT
 * @date 2024-12-26
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/tms/accessControl")
public class BaseAccessControlController extends AbstractController<BaseAccessControlMapper, BaseAccessControl, BaseAccessControlVo, BaseAccessControlBo> {


  private final IBaseAccessControlService baseAccessControlService;

  /**
   * 新增数据
   */
  @Log(title = "新增数据", businessType = BusinessType.INSERT)
  @RepeatSubmit()
  @PostMapping("/add")
  public R<Map<String, Object>> add(@RequestBody BaseAccessControlBo bo) {
    return baseAccessControlService.add(bo);
  }
}

package com.yiruantong.system.controller.decorate;

import com.yiruantong.system.domain.decorate.SysPage;
import com.yiruantong.system.domain.decorate.bo.SysPageBo;
import com.yiruantong.system.domain.decorate.vo.SysPageVo;
import com.yiruantong.system.mapper.decorate.SysPageMapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 页面装修
 *
 * @author YRT
 * @date 2024-01-23
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/decorate/page")
public class SysPageController extends AbstractController<SysPageMapper, SysPage, SysPageVo, SysPageBo> {
}

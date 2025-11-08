package com.yiruantong.system.controller.magic;

import com.yiruantong.system.domain.magic.MagicApiFile;
import com.yiruantong.system.domain.magic.bo.MagicApiFileBo;
import com.yiruantong.system.domain.magic.vo.MagicApiFileVo;
import com.yiruantong.system.mapper.magic.MagicApiFileMapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * magic api 接口
 *
 * @author YRT
 * @date 2024-11-10
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/magic/apiFile")
public class MagicApiFileController extends AbstractController<MagicApiFileMapper, MagicApiFile, MagicApiFileVo, MagicApiFileBo> {
}

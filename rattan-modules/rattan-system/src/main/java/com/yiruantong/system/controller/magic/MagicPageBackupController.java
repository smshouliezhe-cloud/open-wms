package com.yiruantong.system.controller.magic;

import com.yiruantong.system.domain.magic.MagicPageBackup;
import com.yiruantong.system.domain.magic.bo.MagicPageBackupBo;
import com.yiruantong.system.domain.magic.vo.MagicPageBackupVo;
import com.yiruantong.system.mapper.magic.MagicPageBackupMapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 设计器备份
 *
 * @author YRT
 * @date 2024-11-20
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/magic/pageBackup")
public class MagicPageBackupController extends AbstractController<MagicPageBackupMapper, MagicPageBackup, MagicPageBackupVo, MagicPageBackupBo> {
}

package com.yiruantong.system.controller.magic;

import com.yiruantong.system.domain.magic.MagicApiBackup;
import com.yiruantong.system.domain.magic.bo.MagicApiBackupBo;
import com.yiruantong.system.domain.magic.vo.MagicApiBackupVo;
import com.yiruantong.system.mapper.magic.MagicApiBackupMapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * magic api 备份
 *
 * @author YRT
 * @date 2024-11-10
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/magic/apiBackup")
public class MagicApiBackupController extends AbstractController<MagicApiBackupMapper, MagicApiBackup, MagicApiBackupVo, MagicApiBackupBo> {
}

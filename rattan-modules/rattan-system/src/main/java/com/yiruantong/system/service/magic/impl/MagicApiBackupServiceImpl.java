package com.yiruantong.system.service.magic.impl;

import com.yiruantong.system.domain.magic.MagicApiBackup;
import com.yiruantong.system.domain.magic.bo.MagicApiBackupBo;
import com.yiruantong.system.domain.magic.vo.MagicApiBackupVo;
import com.yiruantong.system.mapper.magic.MagicApiBackupMapper;
import com.yiruantong.system.service.magic.IMagicApiBackupService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;

/**
 * magic api 备份Service业务层处理
 *
 * @author YRT
 * @date 2024-11-10
 */
@RequiredArgsConstructor
@Service
public class MagicApiBackupServiceImpl extends ServiceImplPlus<MagicApiBackupMapper, MagicApiBackup, MagicApiBackupVo, MagicApiBackupBo> implements IMagicApiBackupService {
}

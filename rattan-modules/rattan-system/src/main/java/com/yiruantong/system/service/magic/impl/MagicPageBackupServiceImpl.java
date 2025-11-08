package com.yiruantong.system.service.magic.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yiruantong.system.domain.magic.MagicPageBackup;
import com.yiruantong.system.domain.magic.bo.MagicPageBackupBo;
import com.yiruantong.system.domain.magic.vo.MagicPageBackupVo;
import com.yiruantong.system.mapper.magic.MagicPageBackupMapper;
import com.yiruantong.system.service.magic.IMagicPageBackupService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;

/**
 * 设计器备份Service业务层处理
 *
 * @author YRT
 * @date 2024-11-20
 */
@RequiredArgsConstructor
@Service
public class MagicPageBackupServiceImpl extends ServiceImplPlus<MagicPageBackupMapper, MagicPageBackup, MagicPageBackupVo, MagicPageBackupBo> implements IMagicPageBackupService {
  @Override
  public MagicPageBackup getLastOne() {
    LambdaQueryWrapper<MagicPageBackup> backupLambdaQueryWrapper = new LambdaQueryWrapper<>();
    backupLambdaQueryWrapper.orderByDesc(MagicPageBackup::getBackupId);

    return this.getOnly(backupLambdaQueryWrapper);
  }
}

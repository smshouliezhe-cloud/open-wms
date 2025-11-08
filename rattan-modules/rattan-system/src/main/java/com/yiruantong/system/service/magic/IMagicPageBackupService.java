package com.yiruantong.system.service.magic;

import com.yiruantong.system.domain.magic.MagicPageBackup;
import com.yiruantong.system.domain.magic.bo.MagicPageBackupBo;
import com.yiruantong.system.domain.magic.vo.MagicPageBackupVo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

/**
 * 设计器备份Service接口
 *
 * @author YRT
 * @date 2024-11-20
 */
public interface IMagicPageBackupService extends IServicePlus<MagicPageBackup, MagicPageBackupVo, MagicPageBackupBo> {
  /**
   * 获取最后一条
   *
   * @return
   */
  MagicPageBackup getLastOne();
}

package com.yiruantong.system.service.magic.impl;

import com.yiruantong.system.domain.magic.MagicApiFile;
import com.yiruantong.system.domain.magic.bo.MagicApiFileBo;
import com.yiruantong.system.domain.magic.vo.MagicApiFileVo;
import com.yiruantong.system.mapper.magic.MagicApiFileMapper;
import com.yiruantong.system.service.magic.IMagicApiFileService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;

/**
 * magic api 接口Service业务层处理
 *
 * @author YRT
 * @date 2024-11-10
 */
@RequiredArgsConstructor
@Service
public class MagicApiFileServiceImpl extends ServiceImplPlus<MagicApiFileMapper, MagicApiFile, MagicApiFileVo, MagicApiFileBo> implements IMagicApiFileService {
}

package com.yiruantong.basic.service.tms.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.basic.domain.tms.bo.TmsClientLineDetailBo;
import com.yiruantong.basic.domain.tms.vo.TmsClientLineDetailVo;
import com.yiruantong.basic.domain.tms.TmsClientLineDetail;
import com.yiruantong.basic.mapper.tms.TmsClientLineDetailMapper;
import com.yiruantong.basic.service.tms.ITmsClientLineDetailService;

/**
 * 客户线路关系明细Service业务层处理
 *
 * @author YRT
 * @date 2024-03-08
 */
@RequiredArgsConstructor
@Service
public class TmsClientLineDetailServiceImpl extends ServiceImplPlus<TmsClientLineDetailMapper, TmsClientLineDetail, TmsClientLineDetailVo, TmsClientLineDetailBo> implements ITmsClientLineDetailService {
}

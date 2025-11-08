package com.yiruantong.inventory.service.plate;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.plate.BasePlateClient;
import com.yiruantong.inventory.domain.plate.vo.BasePlateClientVo;
import com.yiruantong.inventory.domain.plate.bo.BasePlateClientBo;

/**
 * 客户容器管理Service接口
 *
 * @author YRT
 * @date 2023-12-21
 */
public interface IBasePlateClientService extends IServicePlus<BasePlateClient, BasePlateClientVo, BasePlateClientBo> {
}

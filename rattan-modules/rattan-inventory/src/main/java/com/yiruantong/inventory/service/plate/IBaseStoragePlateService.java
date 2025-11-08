package com.yiruantong.inventory.service.plate;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.plate.BaseStoragePlate;
import com.yiruantong.inventory.domain.plate.vo.BaseStoragePlateVo;
import com.yiruantong.inventory.domain.plate.bo.BaseStoragePlateBo;

/**
 * 仓库容器查询Service接口
 *
 * @author YRT
 * @date 2024-03-06
 */
public interface IBaseStoragePlateService extends IServicePlus<BaseStoragePlate, BaseStoragePlateVo, BaseStoragePlateBo> {
}

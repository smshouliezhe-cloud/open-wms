package com.yiruantong.basic.service.consignor;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.basic.domain.consignor.BaseConsignorStoreAddress;
import com.yiruantong.basic.domain.consignor.vo.BaseConsignorStoreAddressVo;
import com.yiruantong.basic.domain.consignor.bo.BaseConsignorStoreAddressBo;

import java.util.List;
import java.util.Map;

/**
 * 收货人管理Service接口
 *
 * @author YRT
 * @date 2024-03-12
 */
public interface IBaseConsignorStoreAddressService extends IServicePlus<BaseConsignorStoreAddress, BaseConsignorStoreAddressVo, BaseConsignorStoreAddressBo> {
    List<Map<String, Object>> getList(Map<String, Object> map);
}

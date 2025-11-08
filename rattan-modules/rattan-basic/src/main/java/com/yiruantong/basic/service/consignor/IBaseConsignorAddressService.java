package com.yiruantong.basic.service.consignor;

import com.yiruantong.basic.domain.base.bo.GetListBo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.basic.domain.consignor.BaseConsignorAddress;
import com.yiruantong.basic.domain.consignor.vo.BaseConsignorAddressVo;
import com.yiruantong.basic.domain.consignor.bo.BaseConsignorAddressBo;

import java.util.List;
import java.util.Map;

/**
 * 货主地址管理Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-13
 */
public interface IBaseConsignorAddressService extends IServicePlus<BaseConsignorAddress, BaseConsignorAddressVo, BaseConsignorAddressBo> {
  /**
   * 查询发货人信息
   *
   * @param getListBo@return 返回查询列表数据
   */
  List<Map<String, Object>> getList(GetListBo getListBo);
}

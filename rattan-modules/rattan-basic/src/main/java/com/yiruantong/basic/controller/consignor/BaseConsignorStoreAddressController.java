package com.yiruantong.basic.controller.consignor;

import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.consignor.BaseConsignorStoreAddress;
import com.yiruantong.basic.domain.consignor.bo.BaseConsignorStoreAddressBo;
import com.yiruantong.basic.domain.consignor.vo.BaseConsignorStoreAddressVo;
import com.yiruantong.basic.mapper.consignor.BaseConsignorStoreAddressMapper;
import com.yiruantong.basic.service.consignor.IBaseConsignorStoreAddressService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 收货人管理
 *
 * @author YRT
 * @date 2024-03-12
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/consignor/consignorStoreAddress")
public class BaseConsignorStoreAddressController extends AbstractController<BaseConsignorStoreAddressMapper, BaseConsignorStoreAddress, BaseConsignorStoreAddressVo, BaseConsignorStoreAddressBo> {
  private final IBaseConsignorStoreAddressService BaseConsignorStoreAddressService;
  /**
   * 查询发货人信息
   *
   * @param map 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/getList")
  public R<List<Map<String, Object>>> getList(@RequestBody Map<String, Object> map) {
    List<Map<String, Object>> list = BaseConsignorStoreAddressService.getList(map);
    return R.ok(list);
  }
}

package com.yiruantong.basic.controller.consignor;

import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.base.bo.GetListBo;
import com.yiruantong.basic.domain.consignor.BaseConsignorAddress;
import com.yiruantong.basic.domain.consignor.bo.BaseConsignorAddressBo;
import com.yiruantong.basic.domain.consignor.vo.BaseConsignorAddressVo;
import com.yiruantong.basic.mapper.consignor.BaseConsignorAddressMapper;
import com.yiruantong.basic.service.consignor.IBaseConsignorAddressService;
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
 * 货主地址管理
 *
 * @author YiRuanTong
 * @date 2023-10-13
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/consignor/consignorAddress")
public class BaseConsignorAddressController extends AbstractController<BaseConsignorAddressMapper, BaseConsignorAddress, BaseConsignorAddressVo, BaseConsignorAddressBo> {
  private final IBaseConsignorAddressService BaseConsignorAddressService;

  /**
   * 查询发货人信息
   *
   * @param getListBo 查询参数
   * @return 返回查询列表数据
   */
  @PostMapping("/getList")
  public R<List<Map<String, Object>>> getList(@RequestBody GetListBo getListBo) {
    List<Map<String, Object>> list = BaseConsignorAddressService.getList(getListBo);

    return R.ok(list);
  }
}

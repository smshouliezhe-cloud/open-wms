package com.yiruantong.basic.controller.consignor;

import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.consignor.BaseConsignorContract;
import com.yiruantong.basic.domain.consignor.bo.BaseConsignorContractBo;
import com.yiruantong.basic.domain.consignor.vo.BaseConsignorContractVo;
import com.yiruantong.basic.mapper.consignor.BaseConsignorContractMapper;
import com.yiruantong.basic.service.consignor.IBaseConsignorContractService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 货主合同
 *
 * @author YiRuanTong
 * @date 2023-10-13
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/consignor/consignorContract")
public class BaseConsignorContractController extends AbstractController<BaseConsignorContractMapper, BaseConsignorContract, BaseConsignorContractVo, BaseConsignorContractBo> {
  private final IBaseConsignorContractService baseConsignorContractService;

  /**
   * 货主信息修改状态
   *
   * @param map
   * @return
   */
  @RequestMapping("/contractAlter")
  public R<Void> contractAlter(@RequestBody Map<String, Object> map) {
    return baseConsignorContractService.contractAlter(map);
  }
}

package com.yiruantong.basic.controller.client;

import com.yiruantong.basic.domain.client.BaseClientContract;
import com.yiruantong.basic.domain.client.bo.BaseClientContractBo;
import com.yiruantong.basic.domain.client.bo.ContractStatusBo;
import com.yiruantong.basic.domain.client.vo.BaseClientContractVo;
import com.yiruantong.basic.mapper.client.BaseClientContractMapper;
import com.yiruantong.basic.service.client.IBaseClientContractService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.AbstractController;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户合同
 *
 * @author YRT
 * @date 2024-05-13
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/client/clientContract")
public class BaseClientContractController extends AbstractController<BaseClientContractMapper, BaseClientContract, BaseClientContractVo, BaseClientContractBo> {
  private final IBaseClientContractService baseClientContractService;

  /**
   * 紧急度调整
   *
   * @param
   */
  @PostMapping("/contractStatus")
  @Transactional(rollbackFor = Exception.class)
  public R<Void> contractStatus(@RequestBody ContractStatusBo statusBo) {
    return baseClientContractService.contractStatus(statusBo);
  }
}

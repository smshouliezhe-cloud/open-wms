package com.yiruantong.basic.controller.tms;

import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.tms.BaseDriverContract;
import com.yiruantong.basic.domain.tms.bo.BaseDriverContractBo;
import com.yiruantong.basic.domain.tms.vo.BaseDriverContractVo;
import com.yiruantong.basic.mapper.tms.BaseDriverContractMapper;
import com.yiruantong.basic.service.tms.IBaseDriverContractService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 司机合同管理
 *
 * @author YRT
 * @date 2023-11-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/tms/driverContract")
public class BaseDriverContractController extends AbstractController<BaseDriverContractMapper, BaseDriverContract, BaseDriverContractVo, BaseDriverContractBo> {

  private final IBaseDriverContractService baseDriverContractService;

//  /*
//   * 审核
//   * */
//  @PostMapping("/multiAuditing")
//  public R<Void> multiAuditing(@RequestBody List<Long> ids) {
//    return baseDriverContractService.multiAuditing(ids);
//  }

  /*
   * 签署状态更新
   * */
  @PostMapping("/updateSign")
  public R<Void> updateSign(@RequestBody Map<String, Object> map) {
    return baseDriverContractService.updateSign(map);
  }
}

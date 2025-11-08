package com.yiruantong.basic.controller.client;

import cn.hutool.core.convert.Convert;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.base.bo.GetListBo;
import com.yiruantong.basic.domain.client.BaseClient;
import com.yiruantong.basic.domain.client.bo.BaseClientBo;
import com.yiruantong.basic.domain.client.vo.BaseClientVo;
import com.yiruantong.basic.mapper.client.BaseClientMapper;
import com.yiruantong.basic.service.client.IBaseClientService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.idempotent.annotation.RepeatSubmit;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 客户信息
 *
 * @author YRT
 * @date 2023-10-26
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/client/client")
public class BaseClientController extends AbstractController<BaseClientMapper, BaseClient, BaseClientVo, BaseClientBo> {
  private final IBaseClientService baseClientService;

  /**
   * 查询客户
   *
   * @param map 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/getList")
  public R<List<Map<String, Object>>> getList(@RequestBody GetListBo getListBo) {
    List<Map<String, Object>> list = baseClientService.getList(getListBo);
    return R.ok(list);
  }

  /**
   * 查询客户
   *
   * @param map 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/getClientInfo")
  public R<BaseClient> getClientInfo(@RequestBody Map<String, Object> map) {
    Long clientId = Convert.toLong(map.get("clientId"));
    BaseClient clientInfo = baseClientService.getClientInfo(clientId);
    return R.ok(clientInfo);
  }

  /**
   * 新增数据
   */
  @Log(title = "新增数据", businessType = BusinessType.INSERT)
  @RepeatSubmit()
  @PostMapping("/add")
  public R<Map<String, Object>> add(@Validated(AddGroup.class) @RequestBody BaseClientBo bo) {
    return baseClientService.add(bo);
  }
}

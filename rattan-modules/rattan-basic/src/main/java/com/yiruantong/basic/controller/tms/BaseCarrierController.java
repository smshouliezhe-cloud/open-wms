package com.yiruantong.basic.controller.tms;

import cn.dev33.satoken.secure.BCrypt;
import com.yiruantong.basic.domain.base.bo.GetListBo;
import com.yiruantong.basic.domain.tms.BaseCarrier;
import com.yiruantong.basic.domain.tms.bo.BaseCarrierBo;
import com.yiruantong.basic.domain.tms.bo.BaseCarrierPasswordBo;
import com.yiruantong.basic.domain.tms.vo.BaseCarrierVo;
import com.yiruantong.basic.mapper.tms.BaseCarrierMapper;
import com.yiruantong.basic.service.tms.IBaseCarrierService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.idempotent.annotation.RepeatSubmit;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.web.core.AbstractController;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 承运商管理
 *
 * @author YRT
 * @date 2024-03-08
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/tms/carrier")
public class BaseCarrierController extends AbstractController<BaseCarrierMapper, BaseCarrier, BaseCarrierVo, BaseCarrierBo> {
  private final IBaseCarrierService baseCarrierService;

  /**
   * 查询承运商
   *
   * @param map 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/getList")
  public R<List<Map<String, Object>>> getList(@RequestBody Map<String, Object> map) {
    List<Map<String, Object>> list = baseCarrierService.getList(map);
    return R.ok(list);
  }

  /**
   * 查询承运商
   *
   * @param getListBo 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/getListNew")
  public R<List<Map<String, Object>>> getListNew(@RequestBody GetListBo getListBo) {
    List<Map<String, Object>> list = baseCarrierService.getListNew(getListBo);
    return R.ok(list);
  }

  /**
   * 新增数据
   */
  @Log(title = "新增数据", businessType = BusinessType.INSERT)
  @RepeatSubmit()
  @PostMapping("/add")
  public R<Map<String, Object>> add(@Validated(AddGroup.class) @RequestBody BaseCarrierBo bo) {
    return baseCarrierService.add(bo);
  }

  /**
   * 重置其他用户密码
   *
   * @param bo 新旧密码
   */
  @Log(title = "修改货主信息", businessType = BusinessType.UPDATE)
  @PostMapping("/updatePwd")
  public R<Void> updatePwd(@Validated @RequestBody BaseCarrierPasswordBo bo) {
    BaseCarrier baseCarrier = baseCarrierService.getById(bo.getCarrierId());
    String password = baseCarrier.getPassword();
    if (BCrypt.checkpw(bo.getNewPassword(), password)) {
      return R.fail("新密码不能与旧密码相同");
    }

    if (baseCarrierService.resetUserPwd(baseCarrier.getCarrierId(), BCrypt.hashpw(bo.getNewPassword())) > 0) {
      return R.ok();
    }
    return R.fail("修改密码异常，请联系管理员");
  }
}

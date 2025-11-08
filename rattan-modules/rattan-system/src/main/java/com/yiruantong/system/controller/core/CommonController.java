package com.yiruantong.system.controller.core;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.yiruantong.system.service.core.ICommonService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.user.UserCacheEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.NumberUtils;
import com.yiruantong.common.mybatis.core.domain.bo.LoadTreeBo;
import com.yiruantong.common.mybatis.core.dto.QueryBo;
import com.yiruantong.common.mybatis.enums.DataTypeEnum;
import com.yiruantong.common.mybatis.enums.QueryTypeEnum;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.web.core.BaseController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/core/common")
public class CommonController extends BaseController {

  private final ICommonService commonService;

  /**
   * 获取下拉框集合
   */
  @PostMapping("/loadDropDown")
  public R<Map<String, List<LinkedHashMap<String, Object>>>> loadDropDown(@RequestBody List<QueryBo> queryBoList) {
    final Map<String, List<LinkedHashMap<String, Object>>> sysDropdownVos = commonService.loadDropDown(queryBoList);

    return R.ok(sysDropdownVos);
  }

  /**
   * 获取下拉框集合
   */
  @PostMapping("/loadDropDownById/{id}")
  public R<List<LinkedHashMap<String, Object>>> loadDropDownById(@PathVariable Long id) {
    Assert.isFalse(ObjectUtil.isNull(id), "下拉框ID不能为空！");

    List<QueryBo> queryBoList = new ArrayList<>();
    QueryBo queryBo = new QueryBo();
    queryBo.setQueryType(QueryTypeEnum.EQ);
    queryBo.setDataType(DataTypeEnum.LONG);
    queryBo.setColumn("dropdownId");
    queryBo.setValues(id.toString());
    queryBoList.add(queryBo);
    final Map<String, List<LinkedHashMap<String, Object>>> sysDropdownVos = commonService.loadDropDown(queryBoList);
    return R.ok(sysDropdownVos.get("dropdown" + id));
  }

  /**
   * 获取PC权限点集合
   */
  @PostMapping("/getAuthNode")
  public R<Map<String, Object>> getAuthNode(@RequestBody Map<String, Object> params) {
    return R.ok(commonService.getAuthNode(params));
  }

  /**
   * 获取AMIS权限点集合
   */
  @PostMapping("/getAuthNodeAmis/{menuId}")
  public R<Map<String, Boolean>> getAuthNodeAmis(@PathVariable Long menuId) {
    return R.ok(commonService.getAuthNodeAmis(menuId));
  }

  /**
   * 获取APP权限点集合
   */
  @PostMapping("/getAuthNodeApp")
  public R<Map<String, Object>> getAuthNodeApp(@RequestBody Map<String, Object> params) {
    return R.ok(commonService.getAuthNodeApp(params));
  }

  /**
   * 获取APP权限点集合
   */
  @PostMapping("/getAppBigModules")
  public R<List<Map<String, Object>>> getAppBigModules(@RequestBody Map<String, Object> params) {
    return R.ok(commonService.getAppBigModules(params));
  }

  /**
   * 获取导入消息
   */
  @PostMapping("/getMsg")
  public R<List<String>> getMsg(@RequestBody Map<String, Object> params) {
    return R.ok(commonService.getMsg(params));
  }

  /**
   * 查询tree结构数据
   */
  @SaIgnore // 忽略接口鉴权
  @PostMapping("/loadTreeNode")
  public R<List<Map<String, Object>>> loadTreeNode(@RequestBody LoadTreeBo loadTreeBo) {
    List<Map<String, Object>> mapList = commonService.loadTreeNode(loadTreeBo);
    // 兼容amis框架
    mapList.forEach(item -> {
      item.put("refer", B.isEqual(item.get("hasChild"), NumberUtils.ONE));
    });

    return R.ok(mapList);
  }

  /**
   * 查询tree结构数据
   */
  @PostMapping("/loadTreeNodeAll")
  public R<List<Map<String, Object>>> loadTreeNodeAll(@RequestBody LoadTreeBo loadTreeBo) {
    List<Map<String, Object>> mapList = commonService.loadTreeNodeAll(loadTreeBo);

    return R.ok(mapList);
  }

  /**
   * 设置用户级别缓存
   */
  @PostMapping("/setUserCache/{userCacheEnum}")
  public R<Void> setUserCache(@PathVariable UserCacheEnum userCacheEnum, @RequestBody Map<String, Object> data) {
    LoginHelper.setUserCache(userCacheEnum, data);
    return R.ok();
  }

  /**
   * 获取用户级别缓存
   */
  @PostMapping("/getUserCache/{userCacheEnum}")
  public R<Map<String, Object>> getUserCache(@PathVariable UserCacheEnum userCacheEnum) {
    return R.ok(LoginHelper.getUserCache(userCacheEnum));
  }

  /**
   * 获取编码
   */
  @PostMapping("/getCodeRegular/{menuId}")
  public R<Map<String, Object>> getCodeRegular(@PathVariable int menuId) {
    String code = DBUtils.getCodeRegular(MenuEnum.getEnumById(menuId));
    Map<String, Object> map = new HashMap<>();
    map.put("code", code);
    return R.ok(map);
  }
}

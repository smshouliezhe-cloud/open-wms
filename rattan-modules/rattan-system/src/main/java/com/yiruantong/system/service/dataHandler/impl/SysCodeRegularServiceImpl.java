package com.yiruantong.system.service.dataHandler.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.yiruantong.system.domain.dataHandler.SysCodeRegular;
import com.yiruantong.system.domain.dataHandler.bo.SysCodeRegularBo;
import com.yiruantong.system.domain.dataHandler.vo.SysCodeRegularVo;
import com.yiruantong.system.mapper.dataHandler.SysCodeRegularMapper;
import com.yiruantong.system.service.dataHandler.ISysCodeRegularService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.constant.TenantConstants;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.service.ITenantInitService;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.NumberUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.domain.bo.SaveEditorBo;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.tenant.helper.TenantHelper;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * 单据编码规则Service业务层处理
 *
 * @author YRT
 * @date 2023-07-08
 */
@RequiredArgsConstructor
@Service
public class SysCodeRegularServiceImpl extends ServiceImplPlus<SysCodeRegularMapper, SysCodeRegular, SysCodeRegularVo, SysCodeRegularBo>
  implements ISysCodeRegularService, ITenantInitService {
  @Override
  public R<Map<String, Object>> copyEditor(SaveEditorBo<SysCodeRegularBo> saveEditorBo) {
    if (ObjectUtil.isEmpty(saveEditorBo.getIdValue())) {
      return R.fail("复制单据ID不存在");
    }

    SysCodeRegular dataInfo = this.getById(saveEditorBo.getIdValue());

    // 保存主表
    dataInfo.setRegularId(null);
    this.save(dataInfo);
    Map<String, Object> resultMap = new HashMap<>();
    resultMap.put("regularId", dataInfo.getRegularId());
    resultMap.put("menuName", dataInfo.getMenuName());

    return R.ok("复制成功", resultMap);
  }

  @Override
  public void tenantCopy(Long fromPackageId, String targetTenantId, Long targetUserId) {
    TenantHelper.enableIgnore();
    LambdaQueryWrapper<SysCodeRegular> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(SysCodeRegular::getTenantId, targetTenantId);
    // 判断已经存在值，不允许重复复制
    if (this.exists(lambdaQueryWrapper)) {
      return;
    }

    // 获取拷贝数据源
    String fromTenantId;
    lambdaQueryWrapper = new LambdaQueryWrapper<>();
    if (NumberUtils.equals(fromPackageId, TenantConstants.PACKAGE_ID_1)) {
      fromTenantId = TenantConstants.DEFAULT_TENANT_ID;
    } else {
      fromTenantId = fromPackageId + '-' + TenantConstants.DEFAULT_TENANT_ID;
    }
    lambdaQueryWrapper.eq(SysCodeRegular::getTenantId, fromTenantId);
    List<SysCodeRegular> regularList = this.list(lambdaQueryWrapper);

    // 目标数据
    List<SysCodeRegular> newRegularList = regularList.stream().peek(m -> {
      m.setTenantId(targetTenantId);
    }).toList();

    BiConsumer<Number, Number> consumer = getNumberBiConsumer(newRegularList);
    consumer.accept(BigDecimal.ZERO, BigDecimal.ZERO);

    TenantHelper.disableIgnore();
  }

  @NotNull
  private BiConsumer<Number, Number> getNumberBiConsumer(List<SysCodeRegular> newRegularList) {
    var the = this;
    return new BiConsumer<>() {
      final BiConsumer<Number, Number> self = this; // 使用局部变量引用

      /**
       * BiConsumer构造函数
       *
       * @param parentId    原父级ID
       * @param newParentId 新父级ID
       */
      @Override
      public void accept(Number parentId, Number newParentId) {
        var dataList = newRegularList.stream().filter(f -> B.isEqual(f.getParentId(), parentId)).toList();
        for (var item : dataList) {
          Long id = item.getRegularId();
          item.setParentId(Convert.toLong(newParentId));
          item.setRegularId(null);
          the.save(item);
          // 使用局部变量self而不是this，递归执行
          self.accept(id, item.getRegularId());
        }
      }
    };
  }

  @Override
  public R<List<Map<String, Object>>> searchTree(String filterText) {
    Assert.isFalse(StringUtils.isEmpty(filterText), "搜索关键词不能为空");

    LambdaQueryWrapper<SysCodeRegular> menuLambdaQueryWrapper = new LambdaQueryWrapper<>();
    menuLambdaQueryWrapper.like(SysCodeRegular::getMenuName, filterText).last("limit 10");
    List<SysCodeRegular> regularList = this.list(menuLambdaQueryWrapper);

    List<Map<String, Object>> mapList = new ArrayList<>();
    for (var regular : regularList) {
      String menuName = regular.getMenuName();
      Function<Long, String> parentNode = getParentNode();
      String parentNameAll = parentNode.apply(regular.getParentId());
      parentNameAll = parentNameAll + "&gt;" + menuName;

      Map<String, Object> resultMap = new HashMap<>();
      resultMap.put("regularId", regular.getRegularId());
      resultMap.put("menuName", regular.getMenuName());
      resultMap.put("parentNameAll", parentNameAll);
      mapList.add(resultMap);
    }


    return R.ok(mapList);
  }

  @NotNull
  private Function<Long, String> getParentNode() {
    var the = this;
    return new Function<>() {
      final Function<Long, String> self = this; // 使用局部变量引用

      /**
       * Function构造函数
       *
       * @param parentId    父级ID
       */
      @Override
      public String apply(Long parentId) {
        LambdaQueryWrapper<SysCodeRegular> sysMenuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysMenuLambdaQueryWrapper.eq(SysCodeRegular::getRegularId, parentId);
        SysCodeRegular sysCodeRegular = the.getById(parentId);
        String parentName = StringUtils.EMPTY;
        if (ObjectUtil.isNotNull(sysCodeRegular)) {
          String _parentName = self.apply(sysCodeRegular.getParentId()); // 递归获取父级菜单
          parentName = _parentName + (StringUtils.isNotEmpty(_parentName) ? "&gt;" : "") + sysCodeRegular.getMenuName();
        }
        return parentName;
      }
    };
  }
}

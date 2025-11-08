package com.yiruantong.system.service.core.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yiruantong.system.domain.core.SysPrintTemplate;
import com.yiruantong.system.domain.core.bo.SysPrintTemplateBo;
import com.yiruantong.system.domain.core.vo.SysPrintTemplateVo;
import com.yiruantong.system.mapper.core.SysPrintTemplateMapper;
import com.yiruantong.system.service.core.ISysPrintTemplateService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.constant.TenantConstants;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.service.ITenantInitService;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.NumberUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.domain.bo.SaveDataBo;
import com.yiruantong.common.mybatis.core.domain.bo.SaveEditorBo;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.tenant.helper.TenantHelper;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

/**
 * 系统打印模板Service业务层处理
 *
 * @author YRT
 * @date 2023-11-14
 */
@RequiredArgsConstructor
@Service
public class SysPrintTemplateServiceImpl extends ServiceImplPlus<SysPrintTemplateMapper, SysPrintTemplate, SysPrintTemplateVo, SysPrintTemplateBo> implements ISysPrintTemplateService, ITenantInitService {
  @Override
  public R<Void> savePrintTemplate(SysPrintTemplateBo bo) {
    var printInfo = this.getById(bo.getPrintTemplateId());
    if (ObjectUtil.isNotNull(printInfo)) {
      printInfo.setVueData(bo.getVueData());
      printInfo.setTemplateName(bo.getTemplateName());
      printInfo.setTemplateType(bo.getTemplateType());
      printInfo.setMenuId(bo.getMenuId());
      this.updateById(printInfo);
    } else {
      printInfo = new SysPrintTemplate();
      printInfo.setVueData(bo.getVueData());
      printInfo.setTemplateName(bo.getTemplateName());
      printInfo.setTemplateType(bo.getTemplateType());
      printInfo.setMenuId(bo.getMenuId());
      this.save(printInfo);
    }
    return R.ok("保存成功");
  }

  /**
   * 获取打印模板
   */
  @Override
  public R<List<SysPrintTemplateVo>> getTemplateListByMenuId(Map<String, Long> map) {
    Long menuId = map.get("menuId");

    try {
      LambdaQueryWrapper<SysPrintTemplate> printTemplateLambdaQueryWrapper = new LambdaQueryWrapper<>();
      printTemplateLambdaQueryWrapper.eq(SysPrintTemplate::getMenuId, menuId);
      var dataList = this.selectList(printTemplateLambdaQueryWrapper);

      return R.ok(dataList);
    } catch (Exception error) {
      return R.fail("获取打印模板失败");
    }
  }

  @Override
  public R<Map<String, Object>> copyEditor(SaveEditorBo<SysPrintTemplateBo> saveEditorBo) {
    if (ObjectUtil.isEmpty(saveEditorBo.getIdValue())) {
      return R.fail("复制单据ID不存在");
    }

    SysPrintTemplate mainInfo = this.getById(saveEditorBo.getIdValue());
    // 保存主表
    mainInfo.setPrintTemplateId(null);
    String templateName = mainInfo.getTemplateName() + "-复制";
    String newTemplateName = Optional.of(saveEditorBo).map(SaveEditorBo::getData).map(SaveDataBo::getMaster).map(SysPrintTemplateBo::getTemplateName).orElse(null);
    if (StringUtils.isNotEmpty(newTemplateName)) templateName = newTemplateName;
    mainInfo.setTemplateName(templateName);
    this.save(mainInfo);

    return R.ok("复制成功");
  }

  /**
   * 租户数据拷贝
   *
   * @param fromPackageId  来源套餐ID
   * @param targetTenantId 目标租户ID
   * @param targetUserId   目标用户ID
   */
  @Override
  public void tenantCopy(Long fromPackageId, String targetTenantId, Long targetUserId) {
    TenantHelper.enableIgnore();
    LambdaQueryWrapper<SysPrintTemplate> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(SysPrintTemplate::getTenantId, targetTenantId);
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
    lambdaQueryWrapper.eq(SysPrintTemplate::getTenantId, fromTenantId);
    List<SysPrintTemplate> regularList = this.list(lambdaQueryWrapper);

    // 目标数据
    List<SysPrintTemplate> newList = regularList.stream().peek(m -> {
      m.setTenantId(targetTenantId);
    }).toList();

    BiConsumer<Number, Number> consumer = getNumberBiConsumer(newList);
    consumer.accept(BigDecimal.ZERO, BigDecimal.ZERO);

    TenantHelper.disableIgnore();
  }

  @NotNull
  private BiConsumer<Number, Number> getNumberBiConsumer(List<SysPrintTemplate> newList) {
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
        var dataList = newList.stream().filter(f -> B.isEqual(f.getParentId(), parentId)).toList();
        for (var item : dataList) {
          Long id = item.getPrintTemplateId();
          item.setParentId(Convert.toLong(newParentId));
          item.setPrintTemplateId(null);
          the.save(item);
          // 使用局部变量self而不是this，递归执行
          self.accept(id, item.getPrintTemplateId());
        }
      }
    };
  }
}

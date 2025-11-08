package com.yiruantong.system.service.magic.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yiruantong.system.domain.core.SysMenu;
import com.yiruantong.system.domain.magic.MagicPage;
import com.yiruantong.system.domain.magic.MagicPageBackup;
import com.yiruantong.system.domain.magic.bo.MagicPageBo;
import com.yiruantong.system.domain.magic.vo.MagicPageUpdateVo;
import com.yiruantong.system.domain.magic.vo.MagicPageVo;
import com.yiruantong.system.mapper.magic.MagicPageMapper;
import com.yiruantong.system.service.core.ISysMenuService;
import com.yiruantong.system.service.magic.IMagicPageBackupService;
import com.yiruantong.system.service.magic.IMagicPageService;
import lombok.RequiredArgsConstructor;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import com.yiruantong.common.core.constant.Constants;
import com.yiruantong.common.core.constant.TenantConstants;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.BaseBo;
import com.yiruantong.common.core.domain.model.BaseVo;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.service.ITenantInitService;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.NumberUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.json.utils.JsonUtils;
import com.yiruantong.common.mybatis.core.domain.bo.SaveEditorBo;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.tenant.helper.TenantHelper;
import com.yiruantong.generator.domain.GenTable;
import com.yiruantong.generator.domain.GenTableColumn;
import com.yiruantong.generator.service.IGenTableService;
import com.yiruantong.generator.util.VelocityInitializer;
import com.yiruantong.generator.util.VelocityUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * 页面开发Service业务层处理
 *
 * @author YRT
 * @date 2024-11-10
 */
@RequiredArgsConstructor
@Service
public class MagicPageServiceImpl extends ServiceImplPlus<MagicPageMapper, MagicPage, MagicPageVo, MagicPageBo> implements IMagicPageService, ITenantInitService {
  private final IMagicPageBackupService magicPageBackupService;
  private final IGenTableService genTableService;
  private final ISysMenuService sysMenuService;

  //#region beforeSaveEditor
  @Override
  public void beforeSaveEditor(SaveEditorBo<MagicPageBo> saveEditorBo) {
    Long pageId = saveEditorBo.getData().getMaster().getPageId();
    String pageCode = saveEditorBo.getData().getMaster().getPageCode();
    Assert.isFalse(StringUtils.isBlank(pageCode), "编号不能为空！");

    // 排重，编号已存在，不允许重复
    LambdaUpdateWrapper<MagicPage> pageLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    pageLambdaUpdateWrapper.eq(MagicPage::getPageCode, pageCode)
      .ne(ObjectUtil.isNotNull(pageId), MagicPage::getPageId, pageId);
    Assert.isFalse(this.exists(pageLambdaUpdateWrapper), "编号已存在，不允许重复！");

    super.beforeSaveEditor(saveEditorBo);
  }
  //#endregion

  //#region MagicPageUpdateVo
  @Override
  @Transactional(rollbackFor = Exception.class)
  public MagicPageUpdateVo updatePageschema(BaseBo baseBo) {
    MagicPageBackup lastOneBackup = magicPageBackupService.getLastOne();
    // 根据编号保存数据
    MagicPageVo magicPageVo = this.selectByCode(baseBo.getId().toString()).getData();

    boolean isUpdateVersion = false;
    // 备份数据
    if (ObjectUtil.isNull(lastOneBackup) || !StringUtils.equals(lastOneBackup.getPageschema(), baseBo.getCode())) {
      MagicPageBackup pageBackup = new MagicPageBackup();
      BeanUtil.copyProperties(magicPageVo, pageBackup);
      pageBackup.setVersion(NumberUtils.ifNullDefault(pageBackup.getVersion(), 1L));
      magicPageBackupService.save(pageBackup);
      isUpdateVersion = true;
    }

    LambdaUpdateWrapper<MagicPage> pageLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    pageLambdaUpdateWrapper
      .set(MagicPage::getPageschema, baseBo.getCode())
      .set(isUpdateVersion, MagicPage::getVersion, NumberUtils.ifNullDefault(magicPageVo.getVersion(), 1L) + 1)
      .eq(MagicPage::getPageId, magicPageVo.getPageId());
    this.update(pageLambdaUpdateWrapper);

    MagicPageUpdateVo vo = new MagicPageUpdateVo();
    vo.setPageId(magicPageVo.getPageId());
    vo.setPageschema(baseBo.getCode());
    return vo;
  }

  @Override
  public BaseVo generatorCode(Long pageId, String code) {
    MagicPage magicPage = this.getById(pageId);
    Assert.isFalse(ObjectUtil.isNull(magicPage.getTableId()), "表ID不能为空！");
    Assert.isFalse(ObjectUtil.isNull(magicPage.getMenuId()), "菜单ID不能为空！");

    return generatorCodeByTableId(magicPage.getTableId(), magicPage.getMenuId(), code);
  }

  @Override
  public BaseVo generatorCodeByTableId(Long tableId, Long menuId, String code) {
    GenTable table = genTableService.selectGenTableById(tableId);
    SysMenu sysMenu = sysMenuService.getById(menuId);
    Assert.isFalse(ObjectUtil.isNull(table), "表ID不正确！");
    Assert.isFalse(ObjectUtil.isNull(sysMenu), "菜单ID不正确！");

    // 设置主键列信息
    setPkColumn(table);
    VelocityInitializer.initVelocity();
    VelocityContext context = VelocityUtils.prepareContext(table);
    context.put("pageMenuId", menuId);
    String template = StrUtil.format("vm/json/{}.vm", code);

    // 渲染模板
    StringWriter sw = new StringWriter();
    Template tpl = Velocity.getTemplate(template, Constants.UTF8);
    tpl.merge(context, sw);

    BaseVo baseVo = new BaseVo();
    try {
      baseVo.setCode(sw.toString());
    } catch (Exception e) {
      throw new ServiceException("渲染模板失败，表名：" + table.getTableName());
    }

    return baseVo;
  }

  /**
   * 设置主键列信息
   *
   * @param table 业务表信息
   */
  private void setPkColumn(GenTable table) {
    for (GenTableColumn column : table.getColumns()) {
      if (column.isPk()) {
        table.setPkColumn(column);
        break;
      }
    }
    if (ObjectUtil.isNull(table.getPkColumn())) {
      table.setPkColumn(table.getColumns().get(0));
    }

  }
  //#endregion

  //#region updatePageJson
  @Override
  public R<Void> updatePageJson(Long pageId, String code) {
    BaseVo baseVo = this.generatorCode(pageId, code);
    MagicPageBo magicPageBo = new MagicPageBo();
    magicPageBo.setPageId(pageId);
    magicPageBo.setPageschema(baseVo.getCode());
    this.update(magicPageBo);

    return R.ok("更新成功");
  }

  @Override
  public R<MagicPageVo> selectByCode(String code) {
    LambdaUpdateWrapper<MagicPage> pageLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    pageLambdaUpdateWrapper.eq(MagicPage::getPageCode, code);

    return R.ok(this.selectOne(pageLambdaUpdateWrapper));
  }

  @Override
  public R<Map<String, Object>> getSchema(String code) {
    LambdaQueryWrapper<MagicPage> pageLambdaUpdateWrapper = new LambdaQueryWrapper<>();
    pageLambdaUpdateWrapper
      .select(MagicPage::getPageschema)
      .eq(MagicPage::getPageCode, code);
    MagicPage magicPage = this.getOnly(pageLambdaUpdateWrapper);
    if (ObjectUtil.isNull(magicPage)) {
      return R.fail(code + "模板不存在！");
    }

    JSONObject jsonObject = JsonUtils.parseObj(magicPage.getPageschema());
    return R.ok(jsonObject);
  }
  //#endregion


  //#region tenantCopy

  /**
   * 租户数据拷贝
   *
   * @param fromPackageId  来源套餐ID
   * @param targetTenantId 目标租户ID
   * @param targetUserId   目标用户ID
   */
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void tenantCopy(Long fromPackageId, String targetTenantId, Long targetUserId) {
    TenantHelper.enableIgnore();
    LambdaQueryWrapper<MagicPage> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(MagicPage::getTenantId, targetTenantId);
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
    lambdaQueryWrapper.eq(MagicPage::getTenantId, fromTenantId);
    List<MagicPage> regularList = this.list(lambdaQueryWrapper);

    // 目标数据
    List<MagicPage> newList = regularList.stream().peek(m -> {
      m.setTenantId(targetTenantId);
    }).toList();

    BiConsumer<Number, Number> consumer = getNumberBiConsumer(fromTenantId, newList);
    consumer.accept(BigDecimal.ZERO, BigDecimal.ZERO);

    TenantHelper.disableIgnore();
  }

  @NotNull
  private BiConsumer<Number, Number> getNumberBiConsumer(String fromTenantId, List<MagicPage> newList) {
    var the = this;
    return new BiConsumer<>() {
      final BiConsumer<Number, Number> self = this; // 使用局部变量引用

      /**
       * BiConsumer构造函数
       *
       * @param parentId    原父级ID
       * @param newParentId 新父级ID
       */
      @Transactional(rollbackFor = Exception.class)
      @Override
      public void accept(Number parentId, Number newParentId) {
        var dataList = newList.stream().filter(f -> B.isEqual(f.getParentId(), parentId)).toList();
        for (var item : dataList) {
          Long id = item.getPageId();
          item.setParentId(Convert.toLong(newParentId));
          item.setPageId(null);
          // 从母版中复制时，需要设置自定义ID值
          if (StringUtils.equals(fromTenantId, TenantConstants.DEFAULT_TENANT_ID)) {
            item.setPageCode(id.toString());
          }
          the.save(item);

          // 使用局部变量self而不是this，递归执行
          self.accept(id, item.getPageId());
        }
      }
    };
  }
  //#endregion

  @Override
  public R<Map<String, Object>> copyEditor(SaveEditorBo<MagicPageBo> saveEditorBo) {
    if (CollUtil.isEmpty(saveEditorBo.getIdValueList())) {
      return R.fail("复制单据ID不存在");
    }

    for (Long idValue : saveEditorBo.getIdValueList()) {
      MagicPage sysPage = this.getById(idValue);

      // 保存主表
      sysPage.setPageId(null);
      this.save(sysPage);
      sysPage.setPageCode(sysPage.getPageId().toString());
      this.updateById(sysPage);
    }

    return R.ok("复制成功");
  }
}

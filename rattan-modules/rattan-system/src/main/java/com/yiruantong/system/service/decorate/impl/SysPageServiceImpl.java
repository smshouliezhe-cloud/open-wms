package com.yiruantong.system.service.decorate.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yiruantong.system.domain.decorate.SysPage;
import com.yiruantong.system.domain.decorate.bo.SysPageBo;
import com.yiruantong.system.domain.decorate.vo.SysPageVo;
import com.yiruantong.system.mapper.decorate.SysPageMapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.constant.TenantConstants;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.service.ITenantInitService;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.NumberUtils;
import com.yiruantong.common.mybatis.core.domain.bo.SaveEditorBo;
import com.yiruantong.common.mybatis.core.dto.QueryBo;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.mybatis.enums.DataTypeEnum;
import com.yiruantong.common.mybatis.enums.QueryTypeEnum;
import com.yiruantong.common.tenant.helper.TenantHelper;
import com.yiruantong.system.service.decorate.ISysPageService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

/**
 * 页面装修Service业务层处理
 *
 * @author YRT
 * @date 2024-01-23
 */
@RequiredArgsConstructor
@Service
public class SysPageServiceImpl extends ServiceImplPlus<SysPageMapper, SysPage, SysPageVo, SysPageBo> implements ISysPageService, ITenantInitService {
  private final SysPageMapper sysPageMapper;

  /**
   * 分页查询前事件
   *
   * @param pageQuery 加载参数
   */
  @Override
  public void beforePageQuery(PageQuery pageQuery) {
    Optional.ofNullable(pageQuery).map(PageQuery::getOtherParams).map(m -> m.get("currentpageId")).ifPresent(pageId -> {
      Long currentpageId = Convert.toLong(pageId);
      // 根据当前部门ID获取所有子孙ID，包含自己
      var childrenId = sysPageMapper.getChildrenId(currentpageId);
      // 加载部门条件
      var queryBo = new QueryBo();
      queryBo.setQueryType(QueryTypeEnum.IN);
      queryBo.setColumn("pageId");
      queryBo.setValues(childrenId);
      queryBo.setDataType(DataTypeEnum.LONG);
      pageQuery.addQueryBo(queryBo);
    });
  }

  @Override
  public R<Map<String, Object>> copyEditor(SaveEditorBo<SysPageBo> saveEditorBo) {
    if (ObjectUtil.isEmpty(saveEditorBo.getIdValue())) {
      return R.fail("复制单据ID不存在");
    }

    SysPage sysPage = this.getById(saveEditorBo.getIdValue());

    // 保存主表
    sysPage.setPageId(null);
    this.save(sysPage);

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
    LambdaQueryWrapper<SysPage> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(SysPage::getTenantId, targetTenantId);
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
    lambdaQueryWrapper.eq(SysPage::getTenantId, fromTenantId);
    List<SysPage> regularList = this.list(lambdaQueryWrapper);

    // 目标数据
    List<SysPage> newList = regularList.stream().peek(m -> {
      m.setTenantId(targetTenantId);
    }).toList();

    BiConsumer<Number, Number> consumer = getNumberBiConsumer(newList);
    consumer.accept(BigDecimal.ZERO, BigDecimal.ZERO);

    TenantHelper.disableIgnore();
  }

  @NotNull
  private BiConsumer<Number, Number> getNumberBiConsumer(List<SysPage> newList) {
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
          Long id = item.getPageId();
          item.setParentId(Convert.toLong(newParentId));
          item.setPageId(null);
          the.save(item);
          // 使用局部变量self而不是this，递归执行
          self.accept(id, item.getPageId());
        }
      }
    };
  }

}

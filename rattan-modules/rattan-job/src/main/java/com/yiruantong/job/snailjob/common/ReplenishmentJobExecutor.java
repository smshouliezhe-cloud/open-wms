package com.yiruantong.job.snailjob.common;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.aizuda.snailjob.client.job.core.annotation.JobExecutor;
import com.aizuda.snailjob.client.job.core.dto.JobArgs;
import com.aizuda.snailjob.client.model.ExecuteResult;
import com.aizuda.snailjob.common.core.util.JsonUtil;
import com.aizuda.snailjob.common.log.SnailJobLog;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.constant.TenantConstants;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.json.utils.JsonUtils;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.composite.domain.inventory.Bo.StorageLowerReplenishmentBo;
import com.yiruantong.composite.service.inventory.IStorageLowerReplenishmentService;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryReplenishmentVo;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import com.yiruantong.system.service.core.ISysConfigService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * 补货自动作业
 *
 * @author xietb
 * @date 2024-07-15
 */
@Component
@RequiredArgsConstructor
@JobExecutor(name = "ReplenishmentJobExecutor")
public class ReplenishmentJobExecutor {
  final private IStorageLowerReplenishmentService storageLowerReplenishmentService;
  final private ICoreInventoryService coreInventoryService;
  private final ISysConfigService sysConfigService;

  public ExecuteResult jobExecute(JobArgs jobArgs) {
    boolean auto_toReplenishment = sysConfigService.getConfigBool("auto_toReplenishment"); // 自动生成补货单
    if (!auto_toReplenishment) {
      return ExecuteResult.success("未开启自动生成补货");
    }

    SnailJobLog.LOCAL.info("LOCAL ReplenishmentJobExecutor. jobArgs:{}", JsonUtil.toJsonString(jobArgs));
    SnailJobLog.REMOTE.info("REMOTE ReplenishmentJobExecutor. jobArgs:{}", JsonUtil.toJsonString(jobArgs));

    try {
      String jobParams = Optional.ofNullable(jobArgs.getArgsStr()).orElse("{}");
      String tenantId = JsonUtils.parseObj(jobParams).getStr("tenantId");
      if (StrUtil.isEmpty(tenantId)) tenantId = TenantConstants.DEFAULT_TENANT_ID;

      LoginUser loginUser = new LoginUser();
      loginUser.setAdministrator(true);
      loginUser.setUserId(0L);
      loginUser.setNickname("snailjob");
      loginUser.setRoleId(1L);
      loginUser.setTenantId(tenantId);

      // 可以生成的补货单候选数据
      List<StorageLowerReplenishmentBo> storageReplenishmentBoList;
      PageQuery pageQuery = new PageQuery();
      pageQuery.setMenuId(MenuEnum.MENU_2235.getId());
      pageQuery.setPageIndex(1);
      pageQuery.setPageSize(5000);
      pageQuery.setTableName("core_inventory");
      pageQuery.setIsAsc("desc");
      pageQuery.setListMethod("selectInventoryReplenishmentList");
      pageQuery.setOrderByColumn("inventoryId");
      pageQuery.setSumColumnNames(List.of());

      final TableDataInfo<CoreInventoryReplenishmentVo> replenishmentVoTableDataInfo = coreInventoryService.selectInventoryReplenishmentList(pageQuery);
      // 过滤掉以生成的库存
      final List<CoreInventoryReplenishmentVo> replenishmentVoList = replenishmentVoTableDataInfo.getRows().stream().filter(x -> B.isEqual(x.getIsCreateReplenishment(), 0)).toList();
      storageReplenishmentBoList = BeanUtil.copyToList(replenishmentVoList, StorageLowerReplenishmentBo.class);

      storageLowerReplenishmentService.toReplenishment(loginUser, storageReplenishmentBoList);
      return ExecuteResult.success("库存调度执行成功");
    } catch (Exception exception) {
      return ExecuteResult.failure(exception, "库存调度执行失败，" + exception.getMessage());
    }
  }
}

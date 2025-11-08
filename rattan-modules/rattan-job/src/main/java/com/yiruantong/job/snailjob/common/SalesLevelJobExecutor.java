package com.yiruantong.job.snailjob.common;

import cn.hutool.core.util.StrUtil;
import com.aizuda.snailjob.client.job.core.annotation.JobExecutor;
import com.aizuda.snailjob.client.job.core.dto.JobArgs;
import com.aizuda.snailjob.client.model.ExecuteResult;
import com.aizuda.snailjob.common.core.util.JsonUtil;
import com.aizuda.snailjob.common.log.SnailJobLog;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.constant.TenantConstants;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.json.utils.JsonUtils;
import com.yiruantong.composite.service.basic.IBaseConsignorCompositeService;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author xietb
 * @date 2024-07-15
 */
@Component
@RequiredArgsConstructor
@JobExecutor(name = "SalesLevelJobExecutor")
public class SalesLevelJobExecutor {
  final private IBaseConsignorCompositeService baseConsignorCompositeService;

  public ExecuteResult jobExecute(JobArgs jobArgs) {
    SnailJobLog.LOCAL.info("LOCAL testJobExecutor. jobArgs:{}", JsonUtil.toJsonString(jobArgs));
    SnailJobLog.REMOTE.info("REMOTE testJobExecutor. jobArgs:{}", JsonUtil.toJsonString(jobArgs));

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
      baseConsignorCompositeService.matchGrade(loginUser);

      return ExecuteResult.success("销售等级匹配执行成功");
    } catch (Exception exception) {
      return ExecuteResult.failure(exception, "销售等级匹配执行失败，" + exception.getMessage());
    }
  }
}

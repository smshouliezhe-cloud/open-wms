package com.yiruantong.job.snailjob.common;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.aizuda.snailjob.client.job.core.annotation.JobExecutor;
import com.aizuda.snailjob.client.job.core.dto.JobArgs;
import com.aizuda.snailjob.client.model.ExecuteResult;
import com.aizuda.snailjob.common.core.util.JsonUtil;
import com.aizuda.snailjob.common.log.SnailJobLog;
import com.yiruantong.common.core.constant.TenantConstants;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.json.utils.JsonUtils;
import com.yiruantong.system.service.task.ITaskConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author xietb
 * @date 2024-07-15
 */
@Component
@RequiredArgsConstructor
@JobExecutor(name = "TaskConfigJobExecutor")
public class TaskConfigJobExecutor {
  final private ITaskConfigService taskConfigService;

  public ExecuteResult jobExecute(JobArgs jobArgs) {
    SnailJobLog.LOCAL.info("LOCAL testJobExecutor. jobArgs:{}", JsonUtil.toJsonString(jobArgs));
    SnailJobLog.REMOTE.info("REMOTE testJobExecutor. jobArgs:{}", JsonUtil.toJsonString(jobArgs));

    try {
      String jobParams = Optional.ofNullable(jobArgs).map(JobArgs::getArgsStr).orElse("{}");

      String tenantId = JsonUtils.parseObj(jobParams).getStr("tenantId");
      if (StrUtil.isEmpty(tenantId)) tenantId = TenantConstants.DEFAULT_TENANT_ID;
      String configIds = JsonUtils.parseObj(jobParams).getStr("configIds");
      if (StrUtil.isEmpty(configIds)) {
        return ExecuteResult.success("没有可执行的数据");
      }

      LoginUser loginUser = new LoginUser();
      loginUser.setAdministrator(true);
      loginUser.setUserId(1L);
      loginUser.setNickname("snailjob");
      loginUser.setRoleId(1L);
      loginUser.setTenantId(tenantId);
      List<Long> configIdList = Convert.toList(Long.class, StrUtil.split(configIds, ','));
      taskConfigService.autoPush(configIdList, loginUser);

      return ExecuteResult.success("ERP任务推送执行成功");
    } catch (Exception exception) {
      return ExecuteResult.failure(exception, "ERP任务推送执行失败，" + exception.getMessage());
    }
  }
}

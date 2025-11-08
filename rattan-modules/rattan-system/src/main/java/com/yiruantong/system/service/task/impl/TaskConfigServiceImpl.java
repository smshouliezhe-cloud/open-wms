package com.yiruantong.system.service.task.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yiruantong.common.core.domain.model.LoginBody;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.user.GrantTypeEnum;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.encrypt.utils.EncryptUtils;
import com.yiruantong.common.json.utils.JsonUtils;
import com.yiruantong.common.mybatis.core.dto.QueryBo;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.mybatis.enums.DataTypeEnum;
import com.yiruantong.common.mybatis.enums.QueryTypeEnum;
import com.yiruantong.common.redis.utils.RedisUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.tenant.helper.TenantHelper;
import com.yiruantong.system.domain.task.TaskConfig;
import com.yiruantong.system.domain.task.TaskLog;
import com.yiruantong.system.domain.task.bo.TaskConfigBo;
import com.yiruantong.system.domain.task.vo.TaskConfigVo;
import com.yiruantong.system.mapper.task.TaskConfigMapper;
import com.yiruantong.system.service.task.ITaskConfigService;
import com.yiruantong.system.service.task.ITaskLogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 任务配置Service业务层处理
 *
 * @author YRT
 * @date 2024-12-16
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class TaskConfigServiceImpl extends ServiceImplPlus<TaskConfigMapper, TaskConfig, TaskConfigVo, TaskConfigBo> implements ITaskConfigService {
  private final ITaskLogService taskLogService;

  @Value("${api-decrypt.publicKey}")
  private String publicKey;

  @Override
  public TableDataInfo<Map<String, Object>> getPushPageList(HttpServletRequest request, HttpServletResponse response, PageQuery pageQuery) {
    String authorization = request.getHeader("Authorization");
    String clientId = request.getHeader("Clientid");
    return getPushPageList(authorization, clientId, pageQuery);
  }

  @Override
  public TableDataInfo<Map<String, Object>> getPushPageList(String authorization, String clientid, PageQuery pageQuery) {
    Long configId = Convert.toLong(pageQuery.getOtherParams().get("configId"));
    TaskConfig taskConfig = this.getById(configId);
    if (ObjectUtil.isNull(taskConfig)) {
      TableDataInfo<Map<String, Object>> tableDataInfo = new TableDataInfo<>();
      tableDataInfo.setResult(true);
      tableDataInfo.setCode(200);
      tableDataInfo.setTotal(0);
      tableDataInfo.setRows(new ArrayList<>());
      return tableDataInfo;
    }

    // 发送POST请求获取查询数据
    String url = "http://127.0.0.1:7852" + taskConfig.getPrefixRouter();
    Map<String, List<String>> headerMap = new HashMap<>();
    headerMap.put("Authorization", List.of(authorization));
    headerMap.put("Clientid", List.of(clientid));

    pageQuery.setMenuId(taskConfig.getMenuId().intValue());
    pageQuery.setIsAsc(taskConfig.getOrderbyType()); // 设置排序方式
    pageQuery.setOrderByColumn(taskConfig.getOrderbyField());


    // 设置查询条件
    if (ObjectUtil.isNotNull(taskConfig.getPushWhere())) {
      List<QueryBo> queryBoList = new ArrayList<>();
      for (var where : taskConfig.getPushWhere().entrySet()) {
        QueryBo queryBo = new QueryBo();
        queryBo.setColumn(where.getKey());
        queryBo.setQueryType(QueryTypeEnum.EQ);
        queryBo.setDataType(DataTypeEnum.STRING);
        queryBo.setValues(Convert.toStr(where.getValue()));
        queryBoList.add(queryBo);
      }
      pageQuery.setQueryBoList(queryBoList);
    }
    String postResult = HttpUtil.createPost(url).header(headerMap).body(JsonUtils.toJsonString(pageQuery)).execute().body();
    TableDataInfo<Map<String, Object>> tableDataInfo = new TableDataInfo<>();
    tableDataInfo = JsonUtils.parseObject(postResult, tableDataInfo.getClass());

    // 字段转换
    for (var row : tableDataInfo.getRows()) {
      row.put("billId", row.get(taskConfig.getIdField()));
      row.put("billCode", row.get(taskConfig.getCodeField()));
      row.put("orderStatus", row.get(taskConfig.getStatusField()));
      // 获取状态日志
      LambdaUpdateWrapper<TaskLog> logLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
      logLambdaUpdateWrapper
        .eq(TaskLog::getBillId, row.get(taskConfig.getIdField()))
        .eq(TaskLog::getModuleName, taskConfig.getModuleName())
        .orderByDesc(TaskLog::getConfigId);
      TaskLog taskLog = taskLogService.getOne(logLambdaUpdateWrapper);
      if (ObjectUtil.isNotNull(taskLog)) {
        row.put("pushCount", taskLog.getPushCount());
        row.put("pushDate", taskLog.getPushDate());
        row.put("pushState", taskLog.getPushState());
        row.put("pushType", taskLog.getPushType());
        row.put("resultMsg", taskLog.getResultMsg());
      }
    }

    return tableDataInfo;
  }

  /**
   * 模拟手动推送
   *
   * @param clientId
   */
  private String getAuthorization(LoginUser loginUser, String clientId) {
    String key = "getAuthorization";
    String authorization = RedisUtils.getCacheObject(key);
    if (StrUtil.isNotEmpty(authorization)) {
      return authorization;
    }

    // 发送POST请求获取查询数据
    String url = "http://127.0.0.1:7852/auth/loginSso";
    LoginBody loginBody = new LoginBody();
    loginBody.setTenantId(loginUser.getTenantId());
    loginBody.setClientId(clientId);
    loginBody.setUsername("admin");
    loginBody.setPhoneNumber("18600336433");
    loginBody.setGrantType(GrantTypeEnum.PASSWORD.getType());

    // 生成一个 AES 密钥
    String aesKey = RandomUtil.randomString(32);
    String encodedString = EncryptUtils.encryptByBase64(aesKey); // 加密base64
    String encryptKey = EncryptUtils.encryptByRsa(encodedString, publicKey);
    String body = EncryptUtils.encryptByAes(JsonUtils.toJsonString(loginBody), aesKey); // 加密内容
    String resultBody = HttpUtil.createPost(url)
      .header("encrypt-key", encryptKey)
      .header("Content-Type", "application/json;charset=UTF-8")
      .body(body).execute().body();

    authorization = Convert.toStr(JsonUtils.getByPath(JsonUtils.parseObj(resultBody), "data.access_token"));
    RedisUtils.setCacheObject(key, authorization, Duration.ofMinutes(100));
    return authorization;
  }

  /**
   * 模拟手动推送
   *
   * @param authorization
   * @param clientId
   * @param taskConfig
   * @param ids
   */
  private void autoPushUrl(String authorization, String clientId, TaskConfig taskConfig, List<Long> ids) {
    if (CollUtil.isEmpty(ids)) {
      return;
    }

    // 发送POST请求获取查询数据
    String url = "http://127.0.0.1:7852" + taskConfig.getPushApi() + "/" + taskConfig.getConfigId() + "/" + StringUtils.join(ids, ",");
    Map<String, List<String>> headerMap = new HashMap<>();
    headerMap.put("Authorization", List.of(authorization));
    headerMap.put("Clientid", List.of(clientId));
    final HttpResponse execute = HttpUtil.createPost(url).header(headerMap).body("{}").execute();
    log.info("自动推送结果：" + execute.body());
  }

  /**
   * 自动化推送
   *
   * @param configIds 配置ID
   * @param loginUser
   */
  @Async
  @Override
  public void autoPush(List<Long> configIds, LoginUser loginUser) {
    LoginHelper.setLoginUser(loginUser);
    TenantHelper.setDynamic(loginUser.getTenantId()); // 在异步线程里面需要手动设置当前租户ID，缓存用到

    String clientId = "e5cd7e4891bf95d1d19206ce24a7b32e";
    String authorization = "Bearer " + getAuthorization(loginUser, clientId);
    for (Long configId : configIds) {
      TaskConfig taskConfig = this.getById(configId);
      PageQuery pageQuery = new PageQuery();
      Map<String, Object> otherParams = new HashMap<>();
      otherParams.put("configId", configId);
      pageQuery.setOtherParams(otherParams);

      // 获取到推送数据
      TableDataInfo<Map<String, Object>> pushDataInfo = getPushPageList(authorization, clientId, pageQuery);
      var ids = pushDataInfo.getRows().stream().map(m -> m.get(taskConfig.getIdField())).map(Convert::toLong).toList();
      // 在日志表中检查是否已经推送
      final List<Long> notPushIdList = taskLogService.findNotPushIdList(configId, ids);

      autoPushUrl(authorization, clientId, taskConfig, notPushIdList);
    }
  }
}

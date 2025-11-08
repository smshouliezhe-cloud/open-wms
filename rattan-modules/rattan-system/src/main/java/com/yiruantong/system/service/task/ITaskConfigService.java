package com.yiruantong.system.service.task;

import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.system.domain.task.TaskConfig;
import com.yiruantong.system.domain.task.bo.TaskConfigBo;
import com.yiruantong.system.domain.task.vo.TaskConfigVo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

/**
 * 任务配置Service接口
 *
 * @author YRT
 * @date 2024-12-16
 */
public interface ITaskConfigService extends IServicePlus<TaskConfig, TaskConfigVo, TaskConfigBo> {
  /**
   * 获取通用数据
   *
   * @param request
   * @param response
   * @param pageQuery
   * @return
   */
  TableDataInfo<Map<String, Object>> getPushPageList(HttpServletRequest request, HttpServletResponse response, PageQuery pageQuery);

  /**
   * 获取通用数据
   *
   * @param authorization
   * @param clientid
   * @param pageQuery
   * @return
   */
  TableDataInfo<Map<String, Object>> getPushPageList(String authorization, String clientid, PageQuery pageQuery);

  /**
   * 自动化推送
   *
   * @param configIds 配置ID
   * @param loginUser
   */
  void autoPush(List<Long> configIds, LoginUser loginUser);
}

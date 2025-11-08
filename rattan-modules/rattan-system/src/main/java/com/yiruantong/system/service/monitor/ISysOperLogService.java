package com.yiruantong.system.service.monitor;

import com.yiruantong.system.domain.monitor.SysOperLog;
import com.yiruantong.system.domain.monitor.bo.SysOperLogBo;
import com.yiruantong.system.domain.monitor.vo.SysOperLogVo;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.List;

/**
 * 操作日志 服务层
 *
 * @author YiRuanTong
 */
public interface ISysOperLogService extends IServicePlus<SysOperLog, SysOperLogVo, SysOperLogBo> {

  TableDataInfo<SysOperLogVo> selectPageOperLogList(SysOperLogBo operLog, PageQuery pageQuery);

  /**
   * 新增操作日志
   *
   * @param bo 操作日志对象
   */
  void insertOperlog(SysOperLogBo bo);

  /**
   * 查询系统操作日志集合
   *
   * @param operLog 操作日志对象
   * @return 操作日志集合
   */
  List<SysOperLogVo> selectOperLogList(SysOperLogBo operLog);

  /**
   * 批量删除系统操作日志
   *
   * @param operIds 需要删除的操作日志ID
   * @return 结果
   */
  int deleteOperLogByIds(Long[] operIds);

  /**
   * 查询操作日志详细
   *
   * @param operId 操作ID
   * @return 操作日志对象
   */
  SysOperLogVo selectOperLogById(Long operId);

  /**
   * 清空操作日志
   */
  void cleanOperLog();

  /**
   * 更新备注
   */
  void updateRemark(String key, String remark);
}

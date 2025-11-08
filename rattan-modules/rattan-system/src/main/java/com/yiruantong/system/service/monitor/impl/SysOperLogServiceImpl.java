package com.yiruantong.system.service.monitor.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiruantong.system.domain.core.vo.SysMenuVo;
import com.yiruantong.system.domain.monitor.SysOperLog;
import com.yiruantong.system.domain.monitor.bo.SysOperLogBo;
import com.yiruantong.system.domain.monitor.vo.SysOperLogVo;
import com.yiruantong.system.mapper.core.SysMenuMapper;
import com.yiruantong.system.mapper.monitor.SysOperLogMapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.utils.MapstructUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.core.utils.ip.AddressUtils;
import com.yiruantong.common.json.utils.JsonUtils;
import com.yiruantong.common.log.event.OperLogEvent;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.system.service.monitor.ISysOperLogService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 操作日志 服务层处理
 *
 * @author YiRuanTong
 */
@RequiredArgsConstructor
@Service
public class SysOperLogServiceImpl extends ServiceImplPlus<SysOperLogMapper, SysOperLog, SysOperLogVo, SysOperLogBo> implements ISysOperLogService {

  private final SysOperLogMapper baseMapper;
  private final SysMenuMapper sysMenuMapper;

  /**
   * 操作日志记录
   *
   * @param operLogEvent 操作日志事件
   */
  @Async
  @EventListener
  public void recordOper(OperLogEvent operLogEvent) {
    SysOperLogBo operLog = MapstructUtils.convert(operLogEvent, SysOperLogBo.class);
    String operParam = operLogEvent.getOperParam();
    if (StringUtils.isNotEmpty(operParam)) {
      final Dict dict = JsonUtils.parseMap(operParam);
      if (ObjectUtil.isNotNull(dict)) {
        Long menuId = Convert.toLong(dict.get("menuId"));
        String tableName = Convert.toStr(dict.get("tableName"));
        final SysMenuVo sysMenuVo = sysMenuMapper.selectVoById(menuId);
        operLog.setMenuId(menuId);
        if (ObjectUtil.isNotNull(sysMenuVo)) {
          operLog.setMenuName(sysMenuVo.getMenuName());
        }
        operLog.setTableName(tableName);
      }
    }
    // 远程查询操作地点
    operLog.setOperLocation(AddressUtils.getRealAddressByIP(operLog.getOperIp()));
    insertOperlog(operLog);
  }

  @Override
  public TableDataInfo<SysOperLogVo> selectPageOperLogList(SysOperLogBo operLog, PageQuery pageQuery) {
    Map<String, Object> params = operLog.getParams();
    LambdaQueryWrapper<SysOperLog> lqw = new LambdaQueryWrapper<SysOperLog>()
      .like(StringUtils.isNotBlank(operLog.getTitle()), SysOperLog::getTitle, operLog.getTitle())
      .eq(operLog.getBusinessType() != null && operLog.getBusinessType() > 0,
        SysOperLog::getBusinessType, operLog.getBusinessType())
      .func(f -> {
        if (ArrayUtil.isNotEmpty(operLog.getBusinessTypes())) {
          f.in(SysOperLog::getBusinessType, Arrays.asList(operLog.getBusinessTypes()));
        }
      })
      .eq(operLog.getStatus() != null,
        SysOperLog::getStatus, operLog.getStatus())
      .like(StringUtils.isNotBlank(operLog.getOperName()), SysOperLog::getOperName, operLog.getOperName())
      .between(params.get("beginTime") != null && params.get("endTime") != null,
        SysOperLog::getOperTime, params.get("beginTime"), params.get("endTime"));
    if (StringUtils.isBlank(pageQuery.getOrderByColumn())) {
      pageQuery.setOrderByColumn("oper_id");
      pageQuery.setIsAsc("desc");
    }
    Page<SysOperLogVo> page = baseMapper.selectVoPage(pageQuery.build(), lqw);
    return TableDataInfo.build(page);
  }

  /**
   * 新增操作日志
   *
   * @param bo 操作日志对象
   */
  @Override
  public void insertOperlog(SysOperLogBo bo) {
    SysOperLog operLog = MapstructUtils.convert(bo, SysOperLog.class);
    operLog.setOperTime(new Date());
    baseMapper.insert(operLog);
  }

  /**
   * 查询系统操作日志集合
   *
   * @param operLog 操作日志对象
   * @return 操作日志集合
   */
  @Override
  public List<SysOperLogVo> selectOperLogList(SysOperLogBo operLog) {
    Map<String, Object> params = operLog.getParams();
    return baseMapper.selectVoList(new LambdaQueryWrapper<SysOperLog>()
      .like(StringUtils.isNotBlank(operLog.getTitle()), SysOperLog::getTitle, operLog.getTitle())
      .eq(operLog.getBusinessType() != null && operLog.getBusinessType() > 0,
        SysOperLog::getBusinessType, operLog.getBusinessType())
      .func(f -> {
        if (ArrayUtil.isNotEmpty(operLog.getBusinessTypes())) {
          f.in(SysOperLog::getBusinessType, Arrays.asList(operLog.getBusinessTypes()));
        }
      })
      .eq(operLog.getStatus() != null && operLog.getStatus() > 0,
        SysOperLog::getStatus, operLog.getStatus())
      .like(StringUtils.isNotBlank(operLog.getOperName()), SysOperLog::getOperName, operLog.getOperName())
      .between(params.get("beginTime") != null && params.get("endTime") != null,
        SysOperLog::getOperTime, params.get("beginTime"), params.get("endTime"))
      .orderByDesc(SysOperLog::getOperId));
  }

  /**
   * 批量删除系统操作日志
   *
   * @param operIds 需要删除的操作日志ID
   * @return 结果
   */
  @Override
  public int deleteOperLogByIds(Long[] operIds) {
    return baseMapper.deleteBatchIds(Arrays.asList(operIds));
  }

  /**
   * 查询操作日志详细
   *
   * @param operId 操作ID
   * @return 操作日志对象
   */
  @Override
  public SysOperLogVo selectOperLogById(Long operId) {
    return baseMapper.selectVoById(operId);
  }

  /**
   * 清空操作日志
   */
  @Override
  public void cleanOperLog() {
    baseMapper.delete(new LambdaQueryWrapper<>());
  }

  @Override
  public void updateRemark(String key, String remark) {
    LambdaUpdateWrapper<SysOperLog> logininforLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    logininforLambdaUpdateWrapper.set(SysOperLog::getRemark, remark)
      .eq(SysOperLog::getOperKey, key);
    this.update(logininforLambdaUpdateWrapper);
  }
}

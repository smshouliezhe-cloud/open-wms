package com.yiruantong.system.service.core;

import com.yiruantong.system.domain.core.SysConfig;
import com.yiruantong.system.domain.core.bo.SysConfigBo;
import com.yiruantong.system.domain.core.vo.SysConfigVo;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.List;
import java.util.Map;

/**
 * 参数配置 服务层
 *
 * @author YiRuanTong
 */
public interface ISysConfigService extends IServicePlus<SysConfig, SysConfigVo, SysConfigBo> {


  TableDataInfo<SysConfigVo> selectPageConfigList(SysConfigBo config, PageQuery pageQuery);

  /**
   * 查询参数配置信息
   *
   * @param configId 参数配置ID
   * @return 参数配置信息
   */
  SysConfigVo selectConfigById(Long configId);

  /**
   * 根据key查询参数配置信息
   *
   * @param configKey 参数配置ID
   * @return 参数配置信息
   */
  SysConfigVo selectOneByKey(String configKey);

  /**
   * 根据key查询参数配置信息
   *
   * @param configKeys 参数配置ID集合
   * @return 参数配置信息
   */
  List<SysConfigVo> selectOneByKeys(List<String> configKeys);

  /**
   * 根据键名查询参数配置信息
   *
   * @param configKey 参数键名
   * @return 参数键值
   */
  String selectConfigByKey(String configKey);

  /**
   * 获取注册开关
   *
   * @return true开启，false关闭
   */
  boolean registerEnabled();

  /**
   * 查询参数配置列表
   *
   * @param config 参数配置信息
   * @return 参数配置集合
   */
  List<SysConfigVo> selectConfigList(SysConfigBo config);

  /**
   * 新增参数配置
   *
   * @param bo 参数配置信息
   * @return 结果
   */
  String insertConfig(SysConfigBo bo);

  /**
   * 修改参数配置
   *
   * @param bo 参数配置信息
   * @return 结果
   */
  String updateConfig(SysConfigBo bo);

  /**
   * 批量删除参数信息
   *
   * @param configIds 需要删除的参数ID
   */
  void deleteConfigByIds(Long[] configIds);

  /**
   * 重置参数缓存数据
   */
  void resetConfigCache();

  /**
   * 校验参数键名是否唯一
   *
   * @param config 参数信息
   * @return 结果
   */
  boolean checkConfigKeyUnique(SysConfigBo config);

  /**
   * 根据参数 keys 获取参数值，多个值采用都好分隔
   *
   * @param configKeys 参数 keys
   * @return 参数值集合
   */
  List<Map<String, Object>> getConfigValues(String configKeys);

  /**
   * 获取参数boolean值
   *
   * @param configKey 参数参数名称
   * @return 结果
   */
  boolean getConfigBool(String configKey);

  /**
   * 保存参数
   *
   * @param map 参数集合
   * @return 保存结果
   */
  public R<List<SysConfigVo>> saveParams(Map<String, Object> map);
}

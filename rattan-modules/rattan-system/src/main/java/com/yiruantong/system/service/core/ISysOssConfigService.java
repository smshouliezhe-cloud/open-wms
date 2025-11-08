package com.yiruantong.system.service.core;

import com.yiruantong.system.domain.core.SysOssConfig;
import com.yiruantong.system.domain.core.bo.SysOssConfigBo;
import com.yiruantong.system.domain.core.vo.SysOssConfigVo;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.Collection;

/**
 * 对象存储配置Service接口
 *
 * @author YiRuanTong
 * @author 孤舟烟雨
 * @date 2021-08-13
 */
public interface ISysOssConfigService extends IServicePlus<SysOssConfig, SysOssConfigVo, SysOssConfigBo> {

  /**
   * 初始化OSS配置
   */
  void init();

  /**
   * 查询单个
   */
  SysOssConfigVo queryById(Long ossConfigId);

  /**
   * 查询列表
   */
  TableDataInfo<SysOssConfigVo> queryPageList(SysOssConfigBo bo, PageQuery pageQuery);


  /**
   * 根据新增业务对象插入对象存储配置
   *
   * @param bo 对象存储配置新增业务对象
   * @return
   */
  Boolean insertByBo(SysOssConfigBo bo);

  /**
   * 根据编辑业务对象修改对象存储配置
   *
   * @param bo 对象存储配置编辑业务对象
   * @return
   */
  Boolean updateByBo(SysOssConfigBo bo);

  /**
   * 校验并删除数据
   *
   * @param ids     主键集合
   * @param isValid 是否校验,true-删除前校验,false-不校验
   * @return
   */
  Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

  /**
   * 启用停用状态
   */
  int updateOssConfigStatus(SysOssConfigBo bo);

}

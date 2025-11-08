package com.yiruantong.system.service.tenant;

import com.yiruantong.system.domain.tenant.SysClient;
import com.yiruantong.system.domain.tenant.bo.SysClientBo;
import com.yiruantong.system.domain.tenant.vo.SysClientVo;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.Collection;
import java.util.List;

/**
 * 客户端管理Service接口
 *
 * @author YiRuanTong
 * @date 2023-06-18
 */
public interface ISysClientService extends IServicePlus<SysClient, SysClientVo, SysClientBo> {

  /**
   * 查询客户端管理
   */
  SysClientVo queryById(Long id);

  /**
   * 查询客户端信息基于客户端id
   */
  SysClient queryByClientId(String clientId);

  /**
   * 查询客户端管理列表
   */
  TableDataInfo<SysClientVo> queryPageList(SysClientBo bo, PageQuery pageQuery);

  /**
   * 查询客户端管理列表
   */
  List<SysClientVo> queryList(SysClientBo bo);

  /**
   * 新增客户端管理
   */
  Boolean insertByBo(SysClientBo bo);

  /**
   * 修改客户端管理
   */
  Boolean updateByBo(SysClientBo bo);

  /**
   * 修改状态
   */
  int updateUserStatus(Long id, Byte status);

  /**
   * 校验并批量删除客户端管理信息
   */
  Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

}

package com.yiruantong.basic.service.storage;

import com.yiruantong.basic.domain.storage.BaseStorage;
import com.yiruantong.basic.domain.storage.bo.BaseStorageBo;
import com.yiruantong.basic.domain.storage.vo.BaseStorageVo;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.List;
import java.util.Map;

/**
 * 仓库管理Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-18
 */
public interface IBaseStorageService extends IServicePlus<BaseStorage, BaseStorageVo, BaseStorageBo> {
  /**
   * 通用 - 查询仓库
   *
   * @param map 查询条件
   * @return 返回查询结果
   */
  List<Map<String, Object>> getList(Map<String, Object> map);

  /**
   * 根据仓库名称，带出目的地值（所属网点）
   *
   * @param map 查询条件
   * @return 返回查询列表数据
   */
  BaseStorage changePlaceDestination(Map<String, Object> map);

  /**
   * 根据编号查询仓库信息
   *
   * @param code 仓库编号
   * @return R
   */
  BaseStorage getByCode(String code);

  /**
   * 根据名称查询仓库信息
   *
   * @param name 仓库名称
   * @return R
   */
  BaseStorage getByName(String name);

  /**
   * 新增仓库信息
   *
   * @param bo 仓库信息
   * @return R
   */
  R<Map<String, Object>> add(BaseStorageBo bo);
}

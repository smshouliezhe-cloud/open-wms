package com.yiruantong.basic.service.storage;

import com.yiruantong.basic.domain.storage.BasePosition;
import com.yiruantong.basic.domain.storage.bo.BasePositionBo;
import com.yiruantong.basic.domain.storage.bo.BasePositionSearchBo;
import com.yiruantong.basic.domain.storage.bo.PositionConfigSaveBo;
import com.yiruantong.basic.domain.storage.vo.BasePositionVo;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.PositionTypeEnum;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.List;
import java.util.Map;

/**
 * 货位管理Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-18
 */
public interface IBasePositionService extends IServicePlus<BasePosition, BasePositionVo, BasePositionBo> {
  /**
   * 通用 - 查询货位列表
   *
   * @param positionSearchBo 查询条件
   * @return 返回查询结果
   */
  List<Map<String, Object>> getList(BasePositionSearchBo positionSearchBo);

  /**
   * 查询货位
   */
  List<Map<String, Object>> getPositionList(BasePositionSearchBo positionSearchBo);

  /**
   * 根据货位名称获取货位是否可用
   *
   * @param storageId    仓库ID
   * @param positionName 货位名称
   * @return 返回boolean，是否可用
   */
  boolean existEnableByName(Long storageId, String positionName);

  /**
   * 根据货位名称获取货位是否可用，根据货位类型
   *
   * @param storageId            仓库ID
   * @param positionName         货位名称
   * @param positionTypeEnumList 货位类型
   * @return 返回boolean，是否可用
   */
  boolean existEnableByName(Long storageId, String positionName, List<PositionTypeEnum> positionTypeEnumList);

  /**
   * 根据货位名称获取货位信息
   *
   * @param positionName 货位名称
   * @return 返回货位信息
   */
  BasePosition getByName(Long storageId, String positionName);

  /**
   * 获取下架理货位
   *
   * @param storageId 仓库ID
   */
  List<BasePosition> getOffPositionList(Long storageId);

  /**
   * 获取库区
   *
   * @param map
   * @return
   */
  List<Map<String, Object>> selectAreaCodeList(Map<String, Object> map);

  /**
   * 获取货架
   *
   * @param map
   * @return
   */
  List<Map<String, Object>> selectShelveCodeList(Map<String, Object> map);

  R<List<String>> getShelveCodes(Map<String, Object> map);

  R<List<String>> getChannelCodes(Map<String, Object> map);

  /**
   * save-保存库区
   *
   * @param positionConfigSaveBo 查询条件
   * @return 返回查询数据
   */
  R<Void> saveArea(PositionConfigSaveBo positionConfigSaveBo);

  /**
   * 锁定货位
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  R<Void> lockPosition(Map<String, Object> map);

  /**
   * 解锁货位
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  R<Void> unlockPosition(Map<String, Object> map);

  /**
   * 是否混放
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  R<Void> isMixProductPosition(Map<String, Object> map);

  /**
   * 是否可用
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  R<Void> enablePosition(Map<String, Object> map);

  /**
   * 最低库存
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  R<Void> submitminCapacity(Map<String, Object> map);

  /**
   * loadPositionData 根据仓库和货位获得货位数据
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  R<List<Map<String, Object>>> loadPositionData(Map<String, Object> map);

  /**
   * add
   *
   * @param bo 条件
   * @return 返回查询数据
   */
  R<Map<String, Object>> add(BasePositionBo bo);
}

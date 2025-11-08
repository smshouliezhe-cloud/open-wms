package com.yiruantong.basic.service.storage;

import com.yiruantong.basic.domain.storage.BasePlate;
import com.yiruantong.basic.domain.storage.api.ApiBasePlateBo;
import com.yiruantong.basic.domain.storage.bo.BasePlateBo;
import com.yiruantong.basic.domain.storage.bo.BasePlateListBo;
import com.yiruantong.basic.domain.storage.vo.BasePlateVo;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.UsingEnum;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.system.domain.dataHandler.vo.SysParamValueVo;

import java.util.List;
import java.util.Map;

/**
 * 容器管理Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-18
 */
public interface IBasePlateService extends IServicePlus<BasePlate, BasePlateVo, BasePlateBo> {
  /**
   * 更新托盘使用状态
   *
   * @param storageId 仓库ID
   * @param plateCode 托盘号
   * @param usingEnum 使用状态
   */
  void updatePlateStatus(Long storageId, String plateCode, UsingEnum usingEnum);

  /**
   * 生成容器号
   *
   * @param basePlateListBo
   * @return
   */
  R<Void> createPlantCode(BasePlateListBo basePlateListBo);

  /**
   * 查询容器规格
   *
   * @param map 查询条件
   * @return 返回查询列表数据
   */
  List<Map<String, Object>> getPlateSpec(Map<String, Object> map);

  /**
   * 查询容器类型
   *
   * @param map 查询条件
   * @return 返回查询列表数据
   */
  List<SysParamValueVo> getPlateType(Map<String, Object> map);

  /**
   * 查询容器重量体积
   *
   * @param map 查询条件
   * @return 返回查询列表数据
   */
  BasePlate getWeightCube(Map<String, Object> map);

  /**
   * 根据容器名称获取容器信息
   *
   * @param plateName 容器名称
   * @return 返回货位信息
   */
  BasePlate getByName(String plateName);

  /**
   * 新增数据
   */
  R<Map<String, Object>> add(ApiBasePlateBo bo);

  /**
   * 查询托盘列表
   * @param map
   * @return
   */

  List<BasePlate> getList(Map<String, Object> map);
}

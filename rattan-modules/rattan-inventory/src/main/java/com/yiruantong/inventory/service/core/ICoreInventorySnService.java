package com.yiruantong.inventory.service.core;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.PositionTypeEnum;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.core.CoreInventorySn;
import com.yiruantong.inventory.domain.core.bo.CoreInventorySnBo;
import com.yiruantong.inventory.domain.core.vo.CoreInventorySnVo;
import com.yiruantong.inventory.domain.core.vo.CoreSnComposeVo;

import java.util.List;

/**
 * 库存明细SNService接口
 *
 * @author YiRuanTong
 * @date 2023-10-21
 */
public interface ICoreInventorySnService extends IServicePlus<CoreInventorySn, CoreInventorySnVo, CoreInventorySnBo> {
  /**
   * 根据SN号获取SN信息
   *
   * @param storageId 仓库ID
   * @param sn        sn号
   * @return
   */
  CoreInventorySn getByCode(Long storageId, String sn);

  /**
   * 获取SN组合信息
   */
  TableDataInfo<CoreSnComposeVo> selectSnComposeList(PageQuery pageQuery);

  /**
   * 根据给定SN集合(逗号分隔)，返回无效的SN集合
   *
   * @param storageId 仓库ID
   * @param sns       逗号分隔的SN集合
   * @return List<String>
   */
  List<String> getInvalidSnList(Long storageId, String sns);

  /**
   * 根据给定SN集合，返回无效的SN集合
   *
   * @param storageId   仓库ID
   * @param querySnList SN集合
   * @return List<String>
   */
  List<String> getInvalidSnList(Long storageId, List<String> querySnList);

  /**
   * 根据给定SN集合，返回无效的SN集合
   *
   * @param storageId   仓库ID
   * @param querySnList SN集合
   * @return List<String>
   */
  List<String> getInvalidSnList(Long storageId, List<String> querySnList, List<PositionTypeEnum> positionTypeEnumList);

  /**
   * 根据给定SN集合(逗号分隔)，返回有效的SN集合
   *
   * @param storageId 仓库ID
   * @param sns       逗号分隔的SN集合
   * @return List<CoreInventorySn>
   */
  List<CoreInventorySn> getValidSnList(Long storageId, String sns);

  /**
   * 根据给定SN集合，返回有效的SN集合
   *
   * @param storageId   仓库ID
   * @param querySnList SN集合
   * @return
   */
  List<CoreInventorySn> getValidSnList(Long storageId, List<String> querySnList);

  /**
   * 根据库存ID返回SN集合
   *
   * @param inventoryId 库存ID
   * @return List<CoreInventorySnVo>
   */
  R<List<CoreInventorySnVo>> selectListByInventoryId(long inventoryId);

  /**
   * 获取有效SN信息
   *
   * @param storageId            仓库ID
   * @param consignorId          货主ID
   * @param productid            商品ID
   * @param querySnList          SN集合
   * @param positionTypeEnumList 货位类型
   * @return CoreSnComposeVo集合
   */
  List<CoreSnComposeVo> selectSnComposeList(Long storageId, Long consignorId, Long productid, List<String> querySnList, List<PositionTypeEnum> positionTypeEnumList);

  /**
   * 更新SN不可用
   *
   * @param storageId   仓库ID
   * @param querySnList SN集合
   * @param inventoryId 库存ID
   * @return boolean
   */
  boolean updateInvalid(Long storageId, List<String> querySnList, Long inventoryId);

  boolean updateInvalid(Long storageId, List<String> querySnList);

  /**
   * 更新SN可用
   *
   * @param storageId   仓库ID
   * @param querySnList SN集合
   * @return boolean
   */
  boolean updateValid(Long storageId, List<String> querySnList, Long inventoryId);

  boolean updateValid(Long storageId, List<String> querySnList);
}

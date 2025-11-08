package com.yiruantong.inventory.service.operation;

import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.operation.StorageConsignorTransferDetail;
import com.yiruantong.inventory.domain.operation.vo.StorageConsignorTransferDetailComposeVo;
import com.yiruantong.inventory.domain.operation.vo.StorageConsignorTransferDetailVo;
import com.yiruantong.inventory.domain.operation.bo.StorageConsignorTransferDetailBo;

import java.util.List;

/**
 * 货位转移明细Service接口
 *
 * @author YRT
 * @date 2023-10-24
 */
public interface IStorageConsignorTransferDetailService extends IServicePlus<StorageConsignorTransferDetail, StorageConsignorTransferDetailVo, StorageConsignorTransferDetailBo> {
  /**
   * 根据主表ID获取明细集合
   *
   * @param id 主表ID
   * @return 返回明细集合
   */
  List<StorageConsignorTransferDetail> selectListByMainId(Long id);

  /**
   * 货主过户明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  TableDataInfo<StorageConsignorTransferDetailComposeVo> selectConsignorTransferDetailComposeList(PageQuery pageQuery);
}

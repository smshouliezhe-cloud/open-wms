package com.yiruantong.inventory.service.operation;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.operation.StorageCheckDetail;
import com.yiruantong.inventory.domain.operation.vo.StorageCheckDetailComposeVo;
import com.yiruantong.inventory.domain.operation.vo.StorageCheckDetailVo;
import com.yiruantong.inventory.domain.operation.bo.StorageCheckDetailBo;

import java.util.List;
import java.util.Map;

/**
 * 盘点单明细Service接口
 *
 * @author YRT
 * @date 2023-10-24
 */
public interface IStorageCheckDetailService extends IServicePlus<StorageCheckDetail, StorageCheckDetailVo, StorageCheckDetailBo> {
  /**
   * 盘点单明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  TableDataInfo<StorageCheckDetailComposeVo> selectStorageCheckDetailComposeList(PageQuery pageQuery);

  /**
   * 根据主表ID获取明细集合
   *
   * @param mainId
   * @return 返回明细集合
   */
  List<StorageCheckDetail> selectListByMainId(Long mainId);

  R<List<Map<String, Object>>> pdaCheckDetailList(Map<String, Object> map);

  R<Void> pdaCheckSave(Map<String, Object> map);
}

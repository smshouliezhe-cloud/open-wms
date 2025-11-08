package com.yiruantong.inventory.service.operation;

import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.operation.StorageAssembleEnterDetail;
import com.yiruantong.inventory.domain.operation.vo.StorageAssembleEnterDetailComposeVo;
import com.yiruantong.inventory.domain.operation.vo.StorageAssembleEnterDetailVo;
import com.yiruantong.inventory.domain.operation.bo.StorageAssembleEnterDetailBo;

/**
 * 商品拆装单入库明细Service接口
 *
 * @author YRT
 * @date 2023-10-24
 */
public interface IStorageAssembleEnterDetailService extends IServicePlus<StorageAssembleEnterDetail, StorageAssembleEnterDetailVo, StorageAssembleEnterDetailBo> {
  /**
   * 商品拆装单入库明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  TableDataInfo<StorageAssembleEnterDetailComposeVo> selectAssembleEnterDetailComposeList(PageQuery pageQuery);
}

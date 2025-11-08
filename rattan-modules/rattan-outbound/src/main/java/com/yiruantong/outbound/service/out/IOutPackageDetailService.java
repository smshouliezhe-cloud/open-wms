package com.yiruantong.outbound.service.out;

import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.outbound.domain.out.OutPackageDetail;
import com.yiruantong.outbound.domain.out.bo.OutPackageDetailBo;
import com.yiruantong.outbound.domain.out.vo.OutPackageDetailComposeVo;
import com.yiruantong.outbound.domain.out.vo.OutPackageDetailVo;

import java.util.List;

/**
 * 打包单明细Service接口
 *
 * @author YRT
 * @date 2023-11-07
 */
public interface IOutPackageDetailService extends IServicePlus<OutPackageDetail, OutPackageDetailVo, OutPackageDetailBo> {
  /**
   * 根据主表ID获取明细集合
   *
   * @param mainId 主表ID
   * @return 返回明细集合
   */
  List<OutPackageDetail> selectListByMainId(Long mainId);

  /**
   * 打包出库明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  TableDataInfo<OutPackageDetailComposeVo> selectPackageDetailComposeList(PageQuery pageQuery);
}

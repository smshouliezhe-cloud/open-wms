package com.yiruantong.basic.service.product;

import com.yiruantong.basic.domain.product.BaseProductSecurityDetail;
import com.yiruantong.basic.domain.product.ProductSecurity;
import com.yiruantong.basic.domain.product.bo.BaseProductSecurityDetailBo;
import com.yiruantong.basic.domain.product.vo.BaseProductSecurityComposeVo;
import com.yiruantong.basic.domain.product.vo.BaseProductSecurityDetailVo;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

/**
 * 防伪标签明细Service接口
 *
 * @author YRT
 * @date 2024-04-25
 */
public interface IBaseProductSecurityDetailService extends IServicePlus<BaseProductSecurityDetail, BaseProductSecurityDetailVo, BaseProductSecurityDetailBo> {
  /**
   * 防伪码明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  TableDataInfo<BaseProductSecurityComposeVo> selectBaseProductSecurityDetail(PageQuery pageQuery);

  R<ProductSecurity> getProductSecurity(ProductSecurity productSecurity);
}

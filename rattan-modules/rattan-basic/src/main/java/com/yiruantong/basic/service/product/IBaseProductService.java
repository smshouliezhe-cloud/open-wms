package com.yiruantong.basic.service.product;

import com.yiruantong.basic.domain.base.bo.GetListBo;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.domain.product.bo.BaseProductBo;
import com.yiruantong.basic.domain.product.vo.BaseProductVo;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.List;
import java.util.Map;

/**
 * 商品基础信息Service接口
 *
 * @author YRT
 * @date 2023-10-15
 */
public interface IBaseProductService extends IServicePlus<BaseProduct, BaseProductVo, BaseProductBo> {
  /**
   * 根据编号获取商品信息
   *
   * @param productCode
   * @return 返回实体商品信息
   */
  BaseProduct getByCode(String productCode);

  /**
   * 根据条码获取商品信息
   *
   * @param productModel
   * @return 返回实体商品信息
   */
  BaseProduct getByModel(String productModel);

  /**
   * 根据名称获取商品信息
   *
   * @param productName
   * @return 返回实体商品信息
   */
  BaseProduct getByName(String productName);

  /**
   * 根据条码和货主获取商品信息
   *
   * @param productCode   商品编号
   * @param consignorName 货主名称
   * @return 返回实体商品信息
   */
  BaseProduct getByCodeAndConsignor(String productCode, String consignorName);

  /**
   * 根据条码获取商品信息列表
   *
   * @param productModel
   * @return 返回实体商品信息
   */
  List<BaseProduct> selectByModel(String productModel);

  /**
   * 通用 - 查询商品列表
   *
   * @param getListBo 查询条件
   * @return 返回查询结果
   */
  List<Map<String, Object>> getList(GetListBo getListBo);

  /**
   * 反审
   *
   * @param ids
   * @return
   */
  R<Void> reAudit(Long[] ids);

  /**
   * 新增数据
   *
   * @param bo
   * @return
   */
  R<Map<String, Object>> add(BaseProductBo bo);

  /**
   * 根据条码和名称获取商品信息
   *
   * @param productCode   商品编号
   * @param productName 商品名称
   * @return 返回实体商品信息
   */
  BaseProduct getByCodeAndName(String productCode, String productName);
}

package com.yiruantong.basic.service.base;

import com.yiruantong.basic.domain.base.BaseConsignor;
import com.yiruantong.basic.domain.base.BaseConsignorCheckInRecord;
import com.yiruantong.basic.domain.base.bo.BaseConsignorAddBo;
import com.yiruantong.basic.domain.base.bo.BaseConsignorBo;
import com.yiruantong.basic.domain.base.bo.GetListBo;
import com.yiruantong.basic.domain.base.vo.BaseConsignorVo;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.dto.QueryBo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.List;
import java.util.Map;

/**
 * 货主信息Service接口
 *
 * @author YRT
 * @date 2023-08-31
 */
public interface IBaseConsignorService extends IServicePlus<BaseConsignor, BaseConsignorVo, BaseConsignorBo> {
  /**
   * 通用查询
   *
   * @param getListBo@return 返回查询结果
   */
  List<Map<String, Object>> getList(GetListBo getListBo);

  /**
   * 重置用户密码
   *
   * @param userId   用户ID
   * @param password 密码
   * @return 结果
   */
  int resetUserPwd(Long userId, String password);

  /**
   * 根据名称查询货主信息
   *
   * @param name 货主名称
   * @return R
   */
  BaseConsignor getByName(String name);

  /**
   * 根据编号查询货主信息
   *
   * @param code 货主名称
   * @return R
   */
  BaseConsignor getByCode(String code);

  /**
   * 新增货主信息
   *
   * @param bo 货主信息
   * @return R
   */
  R<Map<String, Object>> add(BaseConsignorAddBo bo);

  /**
   * 修改货主资料
   *
   * @param bo 货主信息
   * @return R
   */
  R<Map<String, Object>> updateInfo(BaseConsignorBo bo);

  /**
   * 获取门店信息和明细信息
   *
   * @param queryBoList 前端参数
   */
  R<Map<String, Object>> getConsignorData(List<QueryBo> queryBoList);


  /**
   * 添加门店打卡记录
   *
   * @param bo 货主信息
   * @return R
   */
  R<Map<String, Object>> addCheckInRecord(BaseConsignorCheckInRecord bo);

  /**
   * 获取门店打开记录
   *
   * @param consignorName 前端参数
   */
  R<Map<String, Object>> getCheckInRecord(String consignorName);
}

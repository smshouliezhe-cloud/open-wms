package com.yiruantong.inbound.service.in;

import com.yiruantong.common.core.enums.in.InShelveStatusEnum;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inbound.domain.in.InShelve;
import com.yiruantong.inbound.domain.in.vo.InShelveVo;
import com.yiruantong.inbound.domain.in.bo.InShelveBo;

import java.util.Map;

/**
 * 商品上架Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-18
 */
public interface IInShelveService extends IServicePlus<InShelve, InShelveVo, InShelveBo> {
  /**
   * 上架单编号
   *
   * @param shelveCode
   * @return 返回上架单信息
   */
  InShelve getByCode(String shelveCode);

  /**
   * 根据入库单ID获取上架单信息
   *
   * @param enterId
   * @return 返回上架单信息
   */
  InShelve getByEnterId(Long enterId);

  /**
   * 上架单状态
   *
   * @param shelveId
   * @return 返回上架单信息
   */
  boolean updateShelveStatus(Long shelveId, InShelveStatusEnum status);


  /**
   * 强制上架完成
   *
   * @param map 前端参数
   */
  R<Void> forceShelve(Map<String, Object> map);

  /**
   * 取消上架记录查询的上架人
   *
   * @param map 前端参数
   */
  R<Void> cancelNickName(Map<String, Object> map);

  R<Void> receiveTask(Map<String, Object> map);
}

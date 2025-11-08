package com.yiruantong.outbound.domain.service.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * 换货管理组合对象 out_exchange
 *
 * @author YiRuanTong
 * @date 2023-10-23
 */
@Data
public class OutExchangeComposeVo extends OutExchangeVo implements Serializable {


  List<OutExchangeOuterDetailVo> outerDetails;

  List<OutExchangeEnterDetailVo> enterDetails;
}

package com.yiruantong.inbound.service.in;

import com.yiruantong.common.core.domain.R;

import java.util.Map;

public interface IInShelveCancelService {


  /**
   * 取消上架
   * @param map
   * @return
   */
  R<Void> cancelShelve(Map<String, Object> map);
}

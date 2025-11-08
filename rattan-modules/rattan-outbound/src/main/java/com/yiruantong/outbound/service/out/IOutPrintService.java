package com.yiruantong.outbound.service.out;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.dto.QueryBo;

import java.util.List;
import java.util.Map;

/**
 * 出库打印服务类
 */
public interface IOutPrintService {
  /**
   * 出库标签打印
   *
   * @param queryBoList 查询条件
   * @return 打印数据Map结构
   */
  R<Map<String, Object>> printOutOrderLabel(List<QueryBo> queryBoList);
}

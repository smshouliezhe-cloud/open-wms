package com.yiruantong.common.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.excel.core.DefaultExcelListener;

import java.util.HashMap;
import java.util.Map;

/**
 * 导入通用解析处理器
 *
 * @author xtb
 */
public class ImportCommonListener extends DefaultExcelListener<Map<String, Object>> {
  //用于存储表头的信息
  private Map<Integer, String> headMap = new HashMap<>();

  public ImportCommonListener() {
    // 显示使用构造函数，否则将导致空指针
    super(true);
  }

  @Override
  public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
    if (context.readSheetHolder().getHeadRowNumber() > 1) {
      // 多行表头
      headMap.entrySet().forEach(entry -> {
        int key = entry.getKey();
        String value = entry.getValue();
        if (StringUtils.isNotEmpty(value)) {
          this.headMap.put(key, value);
        }
      });
    } else {
      this.headMap = headMap;
    }
  }

  @Override
  public void invoke(Map<String, Object> data, AnalysisContext context) {
    //把表头和值放入Map
    HashMap<String, Object> paramsMap = new HashMap<>();
    for (int i = 0; i < data.size(); i++) {
      String key = headMap.get(i);
      Object value = data.get(i);
      //将表头作为map的key，每行每个单元格的数据作为map的value
      paramsMap.put(key, value);
    }

    // 添加到处理结果中
    this.getExcelResult().getList().add(paramsMap);
  }
}

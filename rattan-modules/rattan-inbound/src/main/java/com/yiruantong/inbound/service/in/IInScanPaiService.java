package com.yiruantong.inbound.service.in;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.inbound.domain.in.bo.InScanOrderBo;

import java.util.List;
import java.util.Map;

public interface IInScanPaiService {


  List<Map<String, Object>> getPlateCodeData(Map<String, Object> map);

  R<Void> enterPaiSave(InScanOrderBo inScanOrderBo);

  List<Map<String, Object>> getShelvePlateData(Map<String, Object> maps);
}

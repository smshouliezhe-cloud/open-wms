package com.yiruantong.composite.service.inventory;

import com.yiruantong.common.core.domain.R;

import java.util.Map;

public interface IInventoryAttributeService {

  R<Void> attributeConvert(Map<String, Object> map);
}

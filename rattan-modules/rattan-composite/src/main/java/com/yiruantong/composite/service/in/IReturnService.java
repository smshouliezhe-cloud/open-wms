package com.yiruantong.composite.service.in;

import com.yiruantong.common.core.domain.R;

import java.util.Map;

public interface IReturnService {

  R<Void> toOutOrder(Map<String, Object> map);
}

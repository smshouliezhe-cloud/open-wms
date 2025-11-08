package com.yiruantong.common.json.handler;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.yiruantong.common.core.enums.base.BaseEnum;

import java.io.IOException;

/**
 * BaseEnum 序列化
 */
public class BaseEnumSerializer extends JsonSerializer<BaseEnum> {
  @Override
  public void serialize(BaseEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    gen.writeNumber(value.getCode());
    gen.writeStringField(gen.getOutputContext().getCurrentName() + "Text", value.getCode());
  }
}

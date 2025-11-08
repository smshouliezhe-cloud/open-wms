package com.yiruantong.common.json.handler;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import com.yiruantong.common.core.enums.base.BaseEnum;
import com.yiruantong.common.core.utils.StringUtils;
import org.springframework.beans.BeanUtils;

import java.io.IOException;

/**
 * BaseEnum反序列化
 */
@Slf4j
public class BaseEnumDeserializer extends JsonDeserializer<Enum> {
  @Override
  public Enum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    JsonNode node = p.getCodec().readTree(p);
    String currentName = p.currentName();
    Object currentValue = p.getCurrentValue();
    Class findPropertyType = BeanUtils.findPropertyType(currentName, currentValue.getClass());
    String asText = node.asText();
    if (StringUtils.isBlank(asText) || (StringUtils.isNotBlank(asText) && asText.equals("0"))) {
      return null;
    }

    if (BaseEnum.class.isAssignableFrom(findPropertyType)) {
      BaseEnum valueOf = null;
      if (StringUtils.isNotBlank(asText)) {
        valueOf = BaseEnum.getEnum(findPropertyType, asText);
      }
      if (valueOf != null) {
        return (Enum) valueOf;
      }

    }

    return Enum.valueOf(findPropertyType, asText);
  }
}

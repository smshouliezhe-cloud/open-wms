package com.yiruantong.common.json.handler;

import cn.hutool.core.convert.Convert;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Date;

/**
 * BaseEnum反序列化
 */
@Slf4j
public class DateDeserializer extends JsonDeserializer<Date> {
  @Override
  public Date deserialize(JsonParser p, DeserializationContext context) throws IOException {
    String date = p.getText();
    return Convert.toDate(date);
  }
}

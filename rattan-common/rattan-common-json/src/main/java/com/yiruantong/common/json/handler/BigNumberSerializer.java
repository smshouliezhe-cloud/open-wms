package com.yiruantong.common.json.handler;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.ser.std.NumberSerializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 大数字序列化处理器
 * 处理超出 JavaScript 安全整数范围(±2^53-1)的数字
 */
@JacksonStdImpl
public class BigNumberSerializer extends NumberSerializer {

  public static final BigNumberSerializer INSTANCE = new BigNumberSerializer(Number.class);

  // JavaScript 安全整数范围
  private static final BigInteger JS_MAX_SAFE_INTEGER = new BigInteger("9007199254740991");
  private static final BigInteger JS_MIN_SAFE_INTEGER = new BigInteger("-9007199254740991");

  public BigNumberSerializer(Class<? extends Number> rawType) {
    super(rawType);
  }

  @Override
  public void serialize(Number value, JsonGenerator gen, SerializerProvider provider) throws IOException {
    if (value == null) {
      gen.writeNull();
      return;
    }

    // 处理不同数字类型
    if (value instanceof BigInteger) {
      handleBigInteger((BigInteger) value, gen);
    } else if (value instanceof BigDecimal) {
      handleBigDecimal((BigDecimal) value, gen);
    } else {
      handleRegularNumber(value, gen);
    }
  }

  private void handleBigInteger(BigInteger value, JsonGenerator gen) throws IOException {
    if (value.compareTo(JS_MIN_SAFE_INTEGER) >= 0 &&
      value.compareTo(JS_MAX_SAFE_INTEGER) <= 0) {
      gen.writeNumber(value.longValue());
    } else {
      gen.writeString(value.toString());
    }
  }

  private void handleBigDecimal(BigDecimal value, JsonGenerator gen) throws IOException {
    // 尝试转换为整数处理
    try {
      BigInteger intValue = value.toBigIntegerExact();
      handleBigInteger(intValue, gen);
    } catch (ArithmeticException e) {
      // 有小数部分，直接作为字符串处理
      gen.writeString(value.toString());
    }
  }

  private void handleRegularNumber(Number value, JsonGenerator gen) throws IOException {
    long longValue = value.longValue();
    double doubleValue = value.doubleValue();

    // 检查是否为整数且在安全范围内
    if (longValue == doubleValue &&
      longValue >= JS_MIN_SAFE_INTEGER.longValue() &&
      longValue <= JS_MAX_SAFE_INTEGER.longValue()) {
      gen.writeNumber(longValue);
    } else {
      gen.writeString(value.toString());
    }
  }
}

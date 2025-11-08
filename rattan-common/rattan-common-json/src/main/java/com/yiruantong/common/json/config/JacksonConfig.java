package com.yiruantong.common.json.config;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.yiruantong.common.core.enums.base.BaseEnum;
import com.yiruantong.common.json.handler.BaseEnumDeserializer;
import com.yiruantong.common.json.handler.BaseEnumSerializer;
import com.yiruantong.common.json.handler.BigNumberSerializer;
import com.yiruantong.common.json.handler.DateDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

/**
 * jackson 配置
 *
 * @author YiRuanTong
 */
@Slf4j
@AutoConfiguration(before = JacksonAutoConfiguration.class)
public class JacksonConfig {

  @Bean
  public Jackson2ObjectMapperBuilderCustomizer customizer() {
    return builder -> {
      // 全局配置序列化返回 JSON 处理
      JavaTimeModule javaTimeModule = new JavaTimeModule();
      javaTimeModule.addSerializer(Long.class, BigNumberSerializer.INSTANCE);
      javaTimeModule.addSerializer(Long.TYPE, BigNumberSerializer.INSTANCE);
      javaTimeModule.addSerializer(BigInteger.class, BigNumberSerializer.INSTANCE);
//      javaTimeModule.addSerializer(BigDecimal.class, ToStringSerializer.instance);
      javaTimeModule.addSerializer(BigDecimal.class, BigNumberSerializer.INSTANCE);
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
      javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));

      builder.modules(javaTimeModule);
      builder.timeZone(TimeZone.getDefault());
      log.info("初始化 jackson 配置");
    };
  }

  @Bean
  public Jackson2ObjectMapperBuilderCustomizer jacksonObjectMapperCustomization() {
    BaseEnumSerializer baseEnumSerializer = new BaseEnumSerializer();
    BaseEnumDeserializer baseEnumDeserializer = new BaseEnumDeserializer();
    DateDeserializer dateDeserializer = new DateDeserializer();
    return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.timeZone(TimeZone.getDefault()).
      serializerByType(BaseEnum.class, baseEnumSerializer).
      deserializerByType(Enum.class, baseEnumDeserializer).
      deserializerByType(Date.class, dateDeserializer);
  }
}

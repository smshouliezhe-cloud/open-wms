package com.yiruantong.common.translation.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yiruantong.common.translation.annotation.TranslationType;
import com.yiruantong.common.translation.core.TranslationInterface;
import com.yiruantong.common.translation.core.handler.TranslationBeanSerializerModifier;
import com.yiruantong.common.translation.core.handler.TranslationHandler;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 翻译模块配置类
 *
 * @author YiRuanTong
 */
@Slf4j
@AutoConfiguration
public class TranslationConfig {

  @Autowired
  private List<TranslationInterface<?>> list;

  @Autowired
  private ObjectMapper objectMapper;

  /**
   * PostConstruct 注释用于在依赖关系注入完成之后需要执行的方法上，以执行任何初始化。此方法必须在将类放入服务之前调用。支持依赖关系注入的所有类都必须支持此注释。
   * 即使类没有请求注入任何资源，用 PostConstruct 注释的方法也必须被调用。只有一个方法可以用此注释进行注释。
   * 应用 PostConstruct 注释的方法必须遵守以下所有标准：该方法不得有任何参数，除非是在 EJB 拦截器 (interceptor) 的情况下，根据 EJB 规范的定义，
   * 在这种情况下它将带有一个 InvocationContext 对象 ；该方法的返回类型必须为 void；该方法不得抛出已检查异常；应用 PostConstruct 的方法可以是 public、protected、package private
   * 或 private；除了应用程序客户端之外，该方法不能是 static；该方法可以是 final；如果该方法抛出未检查异常，那么不得将类放入服务中，除非是能够处理异常并可从中恢复的 EJB。
   */
  @PostConstruct
  public void init() {
    Map<String, TranslationInterface<?>> map = new HashMap<>(list.size());
    for (TranslationInterface<?> trans : list) {
      if (trans.getClass().isAnnotationPresent(TranslationType.class)) {
        TranslationType annotation = trans.getClass().getAnnotation(TranslationType.class);
        map.put(annotation.type(), trans);
      } else {
        log.warn(trans.getClass().getName() + " 翻译实现类未标注 TranslationType 注解!");
      }
    }
    TranslationHandler.TRANSLATION_MAPPER.putAll(map);
    // 设置 Bean 序列化修改器
    objectMapper.setSerializerFactory(objectMapper.getSerializerFactory().withSerializerModifier(new TranslationBeanSerializerModifier()));
  }

}

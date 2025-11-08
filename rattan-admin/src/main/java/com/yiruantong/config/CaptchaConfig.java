package com.yiruantong.config;


import com.xingyuv.captcha.properties.AjCaptchaProperties;
import com.xingyuv.captcha.service.CaptchaCacheService;
import com.xingyuv.captcha.service.impl.CaptchaServiceFactory;
import com.yiruantong.web.service.CaptchaCacheServiceRedisImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class CaptchaConfig {

  @Autowired
  StringRedisTemplate redisTemplate;

  @Bean(name = "AjCaptchaCacheService")
  @Primary
  public CaptchaCacheService captchaCacheService(AjCaptchaProperties config) {
    //缓存类型redis/local/....
    CaptchaCacheService ret = CaptchaServiceFactory.getCache(config.getCacheType().name());
    if (ret instanceof CaptchaCacheServiceRedisImpl) {
      ((CaptchaCacheServiceRedisImpl) ret).setStringRedisTemplate(redisTemplate);
    }
    return ret;
  }
}

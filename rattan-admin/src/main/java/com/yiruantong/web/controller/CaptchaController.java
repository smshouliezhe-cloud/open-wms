package com.yiruantong.web.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.generator.CodeGenerator;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.xingyuv.captcha.model.common.ResponseModel;
import com.xingyuv.captcha.model.vo.CaptchaVO;
import com.xingyuv.captcha.service.CaptchaService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.yiruantong.common.core.constant.Constants;
import com.yiruantong.common.core.constant.GlobalConstants;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.utils.ServletUtils;
import com.yiruantong.common.core.utils.SpringUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.core.utils.reflect.ReflectUtils;
import com.yiruantong.common.mail.config.properties.MailProperties;
import com.yiruantong.common.mail.utils.MailUtils;
import com.yiruantong.common.redis.utils.RedisUtils;
import com.yiruantong.common.web.config.properties.CaptchaProperties;
import com.yiruantong.common.web.enums.CaptchaType;
import org.dromara.sms4j.api.SmsBlend;
import org.dromara.sms4j.api.entity.SmsResponse;
import org.dromara.sms4j.core.factory.SmsFactory;
import com.yiruantong.web.domain.vo.CaptchaVo;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.LinkedHashMap;

/**
 * 验证码操作处理
 *
 * @author YiRuanTong
 */
@SaIgnore
@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
public class CaptchaController {

  private final CaptchaProperties captchaProperties;
  private final MailProperties mailProperties;

  @Resource
  private CaptchaService captchaService;

  public static String getRemoteId(HttpServletRequest request) {
    String xfwd = request.getHeader("X-Forwarded-For");
    String ip = getRemoteIpFromXfwd(xfwd);
    String ua = request.getHeader("user-agent");
    if (StringUtils.isNotBlank(ip)) {
      return ip + ua;
    }
    return request.getRemoteAddr() + ua;
  }

  private static String getRemoteIpFromXfwd(String xfwd) {
    if (StringUtils.isNotBlank(xfwd)) {
      String[] ipList = xfwd.split(",");
      return StringUtils.trim(ipList[0]);
    }
    return null;
  }

  /**
   * 短信验证码
   *
   * @param phoneNumber 用户手机号
   */
  @PostMapping("/resource/sms/code/{phoneNumber}")
  public R<Void> smsCode(@NotBlank(message = "{user.phoneNumber.not.blank}") @PathVariable String phoneNumber) {
    String key = GlobalConstants.CAPTCHA_CODE_KEY + phoneNumber;
    String code = RandomUtil.randomNumbers(6);
    RedisUtils.setCacheObject(key, code, Duration.ofMinutes(Constants.CAPTCHA_EXPIRATION));
    // 验证码模板id 自行处理 (查数据库或写死均可)
    String templateId = "SMS_222625035";
    LinkedHashMap<String, String> map = new LinkedHashMap<>(1);
    map.put("code", code);
    log.info("验证码：" + code);
    SmsBlend smsBlend = SmsFactory.getSmsBlend("config1");
    SmsResponse smsResponse = smsBlend.sendMessage(phoneNumber, templateId, map);
    if (!smsResponse.isSuccess()) {
      log.error("验证码短信发送异常 => {}", smsResponse);
      return R.fail(smsResponse.getData().toString());
    }
    return R.ok();
  }

  /**
   * 邮箱验证码
   *
   * @param email 邮箱
   */
  @PostMapping("/resource/email/code/{email}")
  public R<Void> emailCode(@NotBlank(message = "{user.email.not.blank}") @PathVariable String email) {
    if (!mailProperties.getEnabled()) {
      return R.fail("当前系统没有开启邮箱功能！");
    }
    String key = GlobalConstants.CAPTCHA_CODE_KEY + email;
    String code = RandomUtil.randomNumbers(6);
    RedisUtils.setCacheObject(key, code, Duration.ofMinutes(Constants.CAPTCHA_EXPIRATION));
    try {
      MailUtils.sendText(email, "登录验证码", "您本次验证码为：" + code + "，有效性为" + Constants.CAPTCHA_EXPIRATION + "分钟，请尽快填写。");
    } catch (Exception e) {
      log.error("验证码短信发送异常 => {}", e.getMessage());
      return R.fail(e.getMessage());
    }
    return R.ok();
  }

  /**
   * 生成验证码
   */
  @GetMapping("/auth/code")
  public R<CaptchaVo> getCode() {
    CaptchaVo captchaVo = new CaptchaVo();
    boolean captchaEnabled = captchaProperties.getEnable();
    if (!captchaEnabled) {
      captchaVo.setCaptchaEnabled(false);
      return R.ok(captchaVo);
    }
    // 保存验证码信息
    String uuid = IdUtil.simpleUUID();
    String verifyKey = GlobalConstants.CAPTCHA_CODE_KEY + uuid;
    String uniqueIdKey = GlobalConstants.CAPTCHA_CODE_KEY + ServletUtils.getUniqueId();
    // 生成验证码
    CaptchaType captchaType = captchaProperties.getType();
    boolean isMath = CaptchaType.MATH == captchaType;
    Integer length = isMath ? captchaProperties.getNumberLength() : captchaProperties.getCharLength();
    CodeGenerator codeGenerator = ReflectUtils.newInstance(captchaType.getClazz(), length);
    AbstractCaptcha captcha = SpringUtils.getBean(captchaProperties.getCategory().getClazz());
    captcha.setGenerator(codeGenerator);
    captcha.createCode();
    String code = captcha.getCode();
    if (isMath) {
      ExpressionParser parser = new SpelExpressionParser();
      Expression exp = parser.parseExpression(StringUtils.remove(code, "="));
      code = exp.getValue(String.class);
    }
    RedisUtils.setCacheObject(verifyKey, code, Duration.ofMinutes(Constants.CAPTCHA_EXPIRATION));
    captchaVo.setUuid(uuid);
    captchaVo.setImg(captcha.getImageBase64());

    // 是否需要验证码
    Integer checkNum = RedisUtils.getCacheObject(uniqueIdKey);
    captchaVo.setIsCheck(ObjectUtil.isNotNull(checkNum) && checkNum >= 2);

    return R.ok(captchaVo);
  }

  /**
   * 生成滑块验证码
   *
   * @param data    验证参数
   * @param request 请求request
   * @return 返回验证码
   */
  @PostMapping("/captcha/get")
  public ResponseModel get(@RequestBody CaptchaVO data, HttpServletRequest request) {
    assert request.getRemoteHost() != null;
    data.setBrowserInfo(getRemoteId(request));
    return captchaService.get(data);
  }

  /**
   * 验证验证码
   *
   * @param data    验证参数
   * @param request 请求request
   * @return 返回验证结果
   */
  @PostMapping("/captcha/check")
  public ResponseModel check(@RequestBody CaptchaVO data, HttpServletRequest request) {
    data.setBrowserInfo(getRemoteId(request));
    return captchaService.check(data);
  }

  //  @PostMapping("/verify")
  public ResponseModel verify(@RequestBody CaptchaVO data, HttpServletRequest request) {
    return captchaService.verification(data);
  }

}

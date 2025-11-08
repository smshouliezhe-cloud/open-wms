package com.yiruantong.common.security.config;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.router.SaRouterStaff;
import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.yiruantong.common.core.utils.ServletUtils;
import com.yiruantong.common.core.utils.SpringUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.security.config.properties.SecurityProperties;
import com.yiruantong.common.security.handler.AllUrlHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 权限安全配置
 *
 * @author YiRuanTong
 */

@Slf4j
@AutoConfiguration
@EnableConfigurationProperties(SecurityProperties.class)
@RequiredArgsConstructor
public class SecurityConfig implements WebMvcConfigurer {

  private final SecurityProperties securityProperties;

  /**
   * 注册sa-token的拦截器
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    // 注册路由拦截器，自定义验证规则
    registry.addInterceptor(new SaInterceptor(handler -> {
        String url = ServletUtils.getRequest().getHeader("Referer");
        if (StringUtils.contains(url, "/login")) {
          // 注册页面不验证权限
          SaRouter.match("/system/core/oss/upload").check(SaRouterStaff::stop).stop();
        }
        AllUrlHandler allUrlHandler = SpringUtils.getBean(AllUrlHandler.class);
        // 登录验证 -- 排除多个路径
        SaRouter
          // 获取所有的
          .match(allUrlHandler.getUrls())
          // 对未排除的路径进行检查
          .check(() -> {
            // 检查是否登录 是否有token
            StpUtil.checkLogin();

            // 检查 header 与 param 里的 clientid 与 token 里的是否一致
            String headerCid = ServletUtils.getRequest().getHeader(LoginHelper.CLIENT_ID);
            String paramCid = ServletUtils.getParameter(LoginHelper.CLIENT_ID);
            String clientId = StpUtil.getExtra(LoginHelper.CLIENT_ID).toString();
            if (!StringUtils.equalsAny(clientId, headerCid, paramCid)) {
              // token 无效
              throw NotLoginException.newInstance(StpUtil.getLoginType(),
                "-100", "客户端ID与Token不匹配",
                StpUtil.getTokenValue());
            }

            // 验证签名，API接口的需要做签名验证
            LoginHelper.checkSign();

            // 有效率影响 用于临时测试
            // if (log.isDebugEnabled()) {
            //     log.debug("剩余有效时间: {}", StpUtil.getTokenTimeout());
            //     log.debug("临时有效时间: {}", StpUtil.getTokenActivityTimeout());
            // }

          });
      })).addPathPatterns("/**")
      // 排除不需要拦截的路径
      .excludePathPatterns(securityProperties.getExcludes());
  }

}

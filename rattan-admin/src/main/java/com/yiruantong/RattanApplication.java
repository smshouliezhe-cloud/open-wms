package com.yiruantong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 启动程序
 *
 * @author YiRuanTong
 */

@EnableAsync // 启用异步线程操作
@SpringBootApplication
public class RattanApplication {

  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(RattanApplication.class);
    application.setApplicationStartup(new BufferingApplicationStartup(2048));
    application.run(args);
    System.out.println("(♥◠‿◠)ﾉﾞ  Rattan-Vue-Plus启动成功   ლ(´ڡ`ლ)ﾞ");
  }

}

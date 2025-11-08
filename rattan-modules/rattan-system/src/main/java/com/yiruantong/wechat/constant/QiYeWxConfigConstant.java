package com.yiruantong.wechat.constant;

import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * 常量类，数据来自于application.yml的配置，在代码中使用
 *
 * @author xtb
 * @date 2025/03/14
 */
@Data
@ToString
@Component
public class QiYeWxConfigConstant {
  public String corpid = "ww44d1a794728c54ce";
  public String corpsecret = "fLpE6H3F3N794GiiHfGNzzl0IP_R_tw8lKUGvIbXDf0";
  public Long agentId = 1000074L;
  public String callableToken;
  public String callableEncodingAesKey;
}

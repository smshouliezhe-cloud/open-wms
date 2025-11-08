package com.yiruantong.wechat.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.ChineseDate;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.redis.utils.RedisUtils;
import com.yiruantong.wechat.constant.QiYeWxConfigConstant;
import com.yiruantong.wechat.domain.QyWxImageTextMessage;
import com.yiruantong.wechat.domain.TextMessage;
import com.yiruantong.wechat.domain.TextMessageContent;
import com.yiruantong.wechat.enums.QyWxClickEnum;
import com.yiruantong.wechat.enums.QyWxColorEnum;
import com.yiruantong.wechat.enums.TypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * 企业微信消息推送工具类
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class QyWxHelper {
  private static final String SEND_MESSAGE_URL = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token={}";
  private final String WX_ACCESS_TOKEN_KEY = "WX_ACCESS_TOKEN_KEY";
  private final QiYeWxConfigConstant qiYeWxConfigConstant;

  public String getQyWxAccessToken() {
    String accessToken = RedisUtils.getCacheObject(WX_ACCESS_TOKEN_KEY);
    if (StrUtil.isNotEmpty(accessToken)) {
      return accessToken;
    }

    //封装请求数据
    String params = "corpid=" + qiYeWxConfigConstant.getCorpid() + "&corpsecret=" + qiYeWxConfigConstant.getCorpsecret();
    //发送GET请求
    String sendGet = HttpUtil.get("https://qyapi.weixin.qq.com/cgi-bin/gettoken?" + params);
    // 解析相应内容（转换成json对象）
    JSONObject jsonObject = JSONUtil.parseObj(sendGet);
    log.info("微信token响应结果=" + jsonObject);
    //拿到access-token
    accessToken = StrUtil.toStringOrNull(jsonObject.get("access_token"));

    // 缓存accessToken
    RedisUtils.setCacheObject(WX_ACCESS_TOKEN_KEY, accessToken, Duration.ofSeconds(7100));

    return accessToken;
  }

  public void sendTextMessage(String toUser, String content) {
    String accessToken = getQyWxAccessToken();
    TextMessage textMessage = new TextMessage();
    TextMessageContent textMessageContent = new TextMessageContent();
    textMessageContent.setContent(content);
    textMessage.setText(textMessageContent);
    textMessage.setTouser(toUser);
    textMessage.setAgentid(qiYeWxConfigConstant.getAgentId());
    textMessage.setSafe(0);
    textMessage.setMsgtype("text");
    textMessage.setEnable_id_trans(0);
    textMessage.setDuplicate_check_interval(1800);
    String requestBody = JSONUtil.toJsonStr(textMessage);
    log.info("requestBody=" + requestBody + ", accessToken=" + accessToken);

    HttpRequest request = HttpUtil.createPost(StringUtils.format(SEND_MESSAGE_URL, accessToken));
    request.body(requestBody);
    // 发送请求并获取响应
    HttpResponse response = request.execute();

    // 处理响应
    System.out.println(response.body());
    JSONObject parse = JSONUtil.parseObj(response.body());
    if (StringUtils.equals(Convert.toStr(parse.get("errcode")), "40014")) {
      // invalid access_token
      RedisUtils.deleteObject(WX_ACCESS_TOKEN_KEY);
    }
  }

  public QyWxImageTextMessage createImageTextMessage() {
    QyWxImageTextMessage imageTextMessage = QyWxImageTextMessage.builder()
      .agentid(qiYeWxConfigConstant.getAgentId())
      .touser("@all").toparty("@all").totag("@all")
      .msgtype(TypeEnum.template_card.name())
      .agentid(qiYeWxConfigConstant.getAgentId())
      .enable_id_trans(0).enable_duplicate_check(0).duplicate_check_interval(1800)
      .build();

    QyWxImageTextMessage.TemplateCardBean templateCardBean = QyWxImageTextMessage.TemplateCardBean.builder()
      .card_type(TypeEnum.news_notice.name())
      .task_id(IdUtil.getSnowflakeNextIdStr())
      .build();

    templateCardBean.setSource(QyWxImageTextMessage.TemplateCardBean.SourceBean.builder()
      .icon_url(StrUtil.format("https://dailybing.com/api/v1/{}zh-cnFHD1360", DateUtil.format(DateUtil.yesterday(), DatePattern.PURE_DATE_PATTERN)))
      .desc("♥是爱蛋娃的一天哦!️♥")
      .desc_color(QyWxColorEnum.BLACK.getColor())
      .build());
    templateCardBean.setAction_menu(QyWxImageTextMessage.TemplateCardBean.ActionMenuBean.builder()
      .desc("操作菜单")
      .action_list(CollUtil.newArrayList(
        QyWxImageTextMessage.TemplateCardBean.ActionMenuBean.ActionListBean.builder()
          .text("欢迎归来呀")
          .key("welcome_back")
          .build(),
        QyWxImageTextMessage.TemplateCardBean.ActionMenuBean.ActionListBean.builder()
          .text("再见啦")
          .key("good_bye")
          .build()
      )).build());
    ChineseDate chineseDate = new ChineseDate(DateUtil.date());
    templateCardBean.setMain_title(QyWxImageTextMessage.TemplateCardBean.MainTitleBean.builder()
      .title(StrUtil.format("{} {} {}"
        , DateUtil.format(DateUtil.date(), DatePattern.CHINESE_DATE_PATTERN)
        , DateUtil.thisDayOfWeekEnum().toChinese()
        , chineseDate.getChineseMonthName() + chineseDate.getChineseDay() + StrUtil.SPACE + chineseDate.getFestivals()
      ))
      .build());
    templateCardBean.setCard_image(QyWxImageTextMessage.TemplateCardBean.CardImageBean.builder()
      .url(StrUtil.format("https://dailybing.com/api/v1/{}zh-cnFHD1360", DateUtil.format(DateUtil.date(), DatePattern.PURE_DATE_PATTERN)))
      .aspect_ratio(1.3)
      .build());
    templateCardBean.setImage_text_area(
      QyWxImageTextMessage.TemplateCardBean.ImageTextAreaBean.builder()
        .type(QyWxClickEnum.URL.getClick())
        .url("http://dawaxiaowo.online/Love.html")
        .image_url(StrUtil.format("https://dailybing.com/api/v1/{}zh-cnFHD1360", DateUtil.format(DateUtil.offsetDay(DateUtil.date(), -2), DatePattern.PURE_DATE_PATTERN)))
        .build());

    templateCardBean.setCard_action(QyWxImageTextMessage.TemplateCardBean.CardActionBean.builder()
      .type(QyWxClickEnum.URL.getClick())
      .url("http://dawaxiaowo.online/Love.html")
      .build()
    );

    imageTextMessage.setTemplate_card(templateCardBean);
    return imageTextMessage;
  }

}

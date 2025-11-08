package com.yiruantong.wechat.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 企业微信图文消息发送
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2024/11/15
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class QyWxImageTextMessage {

  /**
   * touser : @all
   */
  private String touser;

  /**
   * toparty : @all
   */
  private String toparty;

  /**
   * totag : @all
   */
  private String totag;

  /**
   * msgtype : template_card 此时固定发生模板图文消息
   */
  private String msgtype;

  /**
   * agentid : 应用ID
   */
  private Long agentid;

  /**
   * 消息正文
   */
  private TemplateCardBean template_card;

  /**
   * 是否开启id转译，0表示否，1表示是，默认0
   */
  private int enable_id_trans;

  /**
   * 是否开启重复消息检查，0表示否，1表示是，默认0
   */
  private int enable_duplicate_check;

  /**
   * 是否重复消息检查的时间间隔，默认1800s，最大不超过4小时
   */
  private int duplicate_check_interval;

  @Data
  @Builder
  @AllArgsConstructor
  public static class TemplateCardBean {

    /**
     * 模板卡片类型，图文展示型卡片此处填写 "news_notice"
     */
    private String card_type;
    /**
     * 卡片第一行（标题）
     */
    private SourceBean source;
    /**
     * 卡片右上角更多操作按钮
     */
    private ActionMenuBean action_menu;
    /**
     * 任务id，同一个应用任务id不能重复，只能由数字、字母和“_-@”组成，最长128字节，填了action_menu字段的话本字段必填
     */
    private String task_id;
    /**
     * 卡片第二列内容
     */
    private MainTitleBean main_title;
    /**
     * 第四列，引用文献样式，即上下排版
     */
    private QuoteAreaBean quote_area;
    /**
     * 第四列，左图右文样式，news_notice类型的卡片，card_image和image_text_area两者必填一个字段，不可都不填
     */
    private ImageTextAreaBean image_text_area;
    /**
     * 卡片顶部大图样式
     */
    private CardImageBean card_image;
    /**
     * 整体卡片的点击跳转事件，news_notice必填本字段
     */
    private CardActionBean card_action;
    /**
     * 卡片二级垂直内容，该字段可为空数组，但有数据的话需确认对应字段是否必填，列表长度不超过4
     */
    private List<VerticalContentListBean> vertical_content_list;
    /**
     * 二级标题+文本列表，该字段可为空数组，但有数据的话需确认对应字段是否必填，列表长度不超过6
     */
    private List<HorizontalContentListBean> horizontal_content_list;

    @Data
    @Builder
    @AllArgsConstructor
    public static class SourceBean {

      /**
       * 图片的url，来源图片的尺寸建议为72*72
       */
      private String icon_url;
      /**
       * 图片的描述，建议不超过20个字，（支持id转译）
       */
      private String desc;
      /**
       * 文字的颜色，目前支持：0(默认) 灰色，1 黑色，2 红色，3 绿色
       */
      private int desc_color;

    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class ActionMenuBean {
      /**
       * 更多操作界面的描述
       */
      private String desc;
      /**
       * 操作列表，列表长度取值范围为 [1, 3]
       */
      private List<ActionListBean> action_list;

      @Data
      @Builder
      @AllArgsConstructor
      public static class ActionListBean {
        /**
         * text : 操作的描述文案 (不再接收消息)
         */
        private String text;
        /**
         * key : 操作key值，用户点击后，会产生回调事件将本参数作为EventKey返回，回调事件会带上该key值，最长支持1024字节，不可重复
         */
        private String key;

      }
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class MainTitleBean {
      /**
       * 一级标题，建议不超过36个字，（支持id转译）
       */
      private String title;
      /**
       * 标题辅助信息，建议不超过44个字，（支持id转译）
       */
      private String desc;

    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class QuoteAreaBean {
      /**
       * 引用文献样式区域点击事件，0或不填代表没有点击事件，1 代表跳转url，2 代表跳转小程序
       */
      private int type;
      /**
       * 点击跳转的url，quote_area.type是1时必填
       */
      private String url;
      /**
       * 点击跳转的小程序的appid，必须是与当前应用关联的小程序，quote_area.type是2时必填
       */
      private String appid;
      /**
       * 点击跳转的小程序的pagepath，quote_area.type是2时选填
       */
      private String pagepath;
      /**
       * 引用文献样式的标题
       */
      private String title;
      /**
       * 引用文献样式的引用文案
       */
      private String quote_text;
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class ImageTextAreaBean {
      /**
       * 左图右文样式区域点击事件，0或不填代表没有点击事件，1 代表跳转url，2 代表跳转小程序
       */
      private int type;
      /**
       * 点击跳转的url，image_text_area.type是1时必填
       */
      private String url;
      /**
       * 点击跳转的小程序的appid，必须是与当前应用关联的小程序，quote_area.type是2时必填
       */
      private String appid;
      /**
       * 点击跳转的小程序的pagepath，quote_area.type是2时选填
       */
      private String pagepath;
      /**
       * 左图右文样式的标题
       */
      private String title;
      /**
       * 左图右文样式的描述
       */
      private String desc;
      /**
       * 左图右文样式的图片url
       */
      private String image_url;
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class CardImageBean {
      /**
       * 图片的url
       */
      private String url;
      /**
       * 图片的宽高比，宽高比要小于2.25，大于1.3，不填该参数默认1.3
       */
      private double aspect_ratio;
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class CardActionBean {
      /**
       * 跳转事件类型，1 代表跳转url，2 代表打开小程序。news_notice卡片模版中该字段取值范围为[1,2]
       */
      private int type;
      /**
       * 跳转事件的url，card_action.type是1时必填
       */
      private String url;
      /**
       * 点击跳转的小程序的appid，必须是与当前应用关联的小程序，quote_area.type是2时必填
       */
      private String appid;
      /**
       * 点击跳转的小程序的pagepath，quote_area.type是2时选填
       */
      private String pagepath;
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class VerticalContentListBean {
      /**
       * 卡片二级标题，建议不超过38个字
       */
      private String title;
      /**
       * 二级普通文本，建议不超过160个字
       */
      private String desc;
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class HorizontalContentListBean {
      /**
       * 链接类型，0或不填代表不是链接，1 代表跳转url，2 代表下载附件，3 代表点击跳转成员详情
       */
      private int type;
      /**
       * 二级标题，建议不超过5个字
       */
      private String keyname;
      /**
       * 二级文本，如果horizontal_content_list.type是2，该字段代表文件名称（要包含文件类型），建议不超过30个字，（支持id转译）
       */
      private String value;
      /**
       * 链接跳转的url，horizontal_content_list.type是1时必填
       */
      private String url;
      /**
       * 附件的media_id，horizontal_content_list.type是2时必填
       */
      private String media_id;
      /**
       * 成员详情的userid，horizontal_content_list.type是3时必填
       */
      private String userid;

    }
  }
}

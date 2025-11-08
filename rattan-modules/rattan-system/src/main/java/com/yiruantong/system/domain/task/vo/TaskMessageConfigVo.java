package com.yiruantong.system.domain.task.vo;

  import java.util.Date;
  import com.fasterxml.jackson.annotation.JsonFormat;
import com.yiruantong.system.domain.task.TaskMessageConfig;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yiruantong.common.excel.annotation.ExcelDictFormat;
import com.yiruantong.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;
import java.util.Map;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * 消息推送配置视图对象 task_message_config
 *
 * @author YRT
 * @date 2025-03-23
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TaskMessageConfig.class)
public class TaskMessageConfigVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

      /**
       * 推送配置ID
       */
      @ExcelProperty(value = "推送配置ID")
    private Long messageConfigId;

      /**
       * 平台标识
       */
      @ExcelProperty(value = "平台标识")
    private String platform;

      /**
       * appKey
       */
      @ExcelProperty(value = "appKey")
    private String appKey;

      /**
       * appSecret
       */
      @ExcelProperty(value = "appSecret")
    private String appSecret;

      /**
       * 平台认证Token
       */
      @ExcelProperty(value = "平台认证Token")
    private String token;

      /**
       * Token过期时间
       */
      @ExcelProperty(value = "Token过期时间")
    private String tokenExpireAt;

      /**
       * 平台API地址
       */
      @ExcelProperty(value = "平台API地址")
    private String apiUrl;

      /**
       * 消息模板
       */
      @ExcelProperty(value = "消息模板")
    private String msgTemplate;

      /**
       * 重试策略
       */
      @ExcelProperty(value = "重试策略")
    private Map<String, Object> retryPolicy;

      /**
       * 回调URL
       */
      @ExcelProperty(value = "回调URL")
    private String callbackUrl;

      /**
       * 配置状态
       */
      @ExcelProperty(value = "配置状态")
    private Byte enable;

      /**
       * 扩展字段
       */
      @ExcelProperty(value = "扩展字段")
    private Map<String, Object> expandFields;

      /**
       * 备注
       */
      @ExcelProperty(value = "备注")
    private String remark;

      /**
       * 创建人
       */
      @ExcelProperty(value = "创建人")
    private String createByName;

      /**
       * 创建时间
       */
      @ExcelProperty(value = "创建时间")
    private Date createTime;

      /**
       * 修改人
       */
      @ExcelProperty(value = "修改人")
    private String updateByName;

      /**
       * 修改时间
       */
      @ExcelProperty(value = "修改时间")
    private Date updateTime;

      /**
       * 删除时间
       */
      @ExcelProperty(value = "删除时间")
    private Date deleteTime;

      /**
       * 删除人id
       */
      @ExcelProperty(value = "删除人id")
    private Long deleteBy;

      /**
       * 删除人
       */
      @ExcelProperty(value = "删除人")
    private String deleteByName;

      /**
       * 父级ID
       */
      @ExcelProperty(value = "父级ID")
    private Long parentId;

      /**
       * 排序号
       */
      @ExcelProperty(value = "排序号")
    private Long orderNum;

  
}

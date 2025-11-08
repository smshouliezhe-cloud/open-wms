package com.yiruantong.system.domain.task.bo;

import com.yiruantong.system.domain.task.TaskMessageConfig;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Map;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 消息推送配置业务对象 task_message_config
 *
 * @author YRT
 * @date 2025-03-23
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TaskMessageConfig.class, reverseConvertGenerate = false)
public class TaskMessageConfigBo extends BaseEntity {

      /**
       * 推送配置ID
       */
        @NotNull(message = "推送配置ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long messageConfigId;

      /**
       * 平台标识
       */
        @NotBlank(message = "平台标识不能为空", groups = { AddGroup.class, EditGroup.class })
    private String platform;

      /**
       * appKey
       */
        @NotBlank(message = "appKey不能为空", groups = { AddGroup.class, EditGroup.class })
    private String appKey;

      /**
       * appSecret
       */
        @NotBlank(message = "appSecret不能为空", groups = { AddGroup.class, EditGroup.class })
    private String appSecret;

      /**
       * 平台认证Token
       */
        @NotBlank(message = "平台认证Token不能为空", groups = { AddGroup.class, EditGroup.class })
    private String token;

      /**
       * Token过期时间
       */
        @NotBlank(message = "Token过期时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private String tokenExpireAt;

      /**
       * 平台API地址
       */
        @NotBlank(message = "平台API地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private String apiUrl;

      /**
       * 消息模板
       */
        @NotBlank(message = "消息模板不能为空", groups = { AddGroup.class, EditGroup.class })
    private String msgTemplate;

      /**
       * 重试策略
       */
        @NotNull(message = "重试策略不能为空", groups = { AddGroup.class, EditGroup.class })
    private Map<String, Object> retryPolicy;

      /**
       * 回调URL
       */
        @NotBlank(message = "回调URL不能为空", groups = { AddGroup.class, EditGroup.class })
    private String callbackUrl;

      /**
       * 配置状态
       */
        @NotNull(message = "配置状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private Byte enable;

      /**
       * 扩展字段
       */
        @NotNull(message = "扩展字段不能为空", groups = { AddGroup.class, EditGroup.class })
    private Map<String, Object> expandFields;

      /**
       * 备注
       */
        @NotBlank(message = "备注不能为空", groups = { AddGroup.class, EditGroup.class })
    private String remark;

      /**
       * 删除时间
       */
        @NotNull(message = "删除时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date deleteTime;

      /**
       * 删除人id
       */
        @NotNull(message = "删除人id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long deleteBy;

      /**
       * 删除人
       */
        @NotBlank(message = "删除人不能为空", groups = { AddGroup.class, EditGroup.class })
    private String deleteByName;

      /**
       * 父级ID
       */
        @NotNull(message = "父级ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long parentId;

      /**
       * 排序号
       */
        @NotNull(message = "排序号不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long orderNum;

  
}

package com.yiruantong.system.domain.task.bo;

import com.yiruantong.system.domain.task.TaskMessage;
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
 * 消息推送业务对象 task_message
 *
 * @author YRT
 * @date 2025-03-23
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TaskMessage.class, reverseConvertGenerate = false)
public class TaskMessageBo extends BaseEntity {

      /**
       * 消息ID
       */
        @NotNull(message = "消息ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long messageId;

      /**
       * 平台标识
       */
        @NotBlank(message = "平台标识不能为空", groups = { AddGroup.class, EditGroup.class })
    private String platform;

      /**
       * 消息类型
       */
        @NotBlank(message = "消息类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String msgType;

      /**
       * 消息内容
       */
        @NotBlank(message = "消息内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String content;

      /**
       * 接收者信息
       */
        @NotBlank(message = "接收者信息不能为空", groups = { AddGroup.class, EditGroup.class })
    private String receiver;

      /**
       * 发送状态
       */
        @NotBlank(message = "发送状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private String status;

      /**
       * 重试次数
       */
        @NotNull(message = "重试次数不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long retryCount;

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

  
}

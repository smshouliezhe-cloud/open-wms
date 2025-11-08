package com.yiruantong.system.domain.task.vo;

  import java.util.Date;
  import com.fasterxml.jackson.annotation.JsonFormat;
import com.yiruantong.system.domain.task.TaskMessage;
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
 * 消息推送视图对象 task_message
 *
 * @author YRT
 * @date 2025-03-23
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TaskMessage.class)
public class TaskMessageVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

      /**
       * 消息ID
       */
      @ExcelProperty(value = "消息ID")
    private Long messageId;

      /**
       * 平台标识
       */
      @ExcelProperty(value = "平台标识")
    private String platform;

      /**
       * 消息类型
       */
      @ExcelProperty(value = "消息类型")
    private String msgType;

      /**
       * 消息内容
       */
      @ExcelProperty(value = "消息内容")
    private String content;

      /**
       * 接收者信息
       */
      @ExcelProperty(value = "接收者信息")
    private String receiver;

      /**
       * 发送状态
       */
      @ExcelProperty(value = "发送状态")
    private String status;

      /**
       * 重试次数
       */
      @ExcelProperty(value = "重试次数")
    private Long retryCount;

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

  
}

package com.yiruantong.basic.domain.common.vo;

  import java.math.BigDecimal;
import com.yiruantong.basic.domain.common.CommonAttachment;
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
 * 模块附件视图对象 common_attachment
 *
 * @author YRT
 * @date 2025-03-08
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CommonAttachment.class)
public class CommonAttachmentVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

      /**
       * 附件ID
       */
      @ExcelProperty(value = "附件ID")
    private Long attachmentId;

      /**
       * 文件名
       */
      @ExcelProperty(value = "文件名")
    private String fileName;

      /**
       * 原名
       */
      @ExcelProperty(value = "原名")
    private String originalName;

      /**
       * 文件后缀名
       */
      @ExcelProperty(value = "文件后缀名")
    private String fileSuffix;

      /**
       * URL地址
       */
      @ExcelProperty(value = "URL地址")
    private String url;

      /**
       * md5值
       */
      @ExcelProperty(value = "md5值")
    private String md5key;

      /**
       * 文件大小
       */
      @ExcelProperty(value = "文件大小")
    private BigDecimal fileSize;

      /**
       * 备注
       */
      @ExcelProperty(value = "备注")
    private String remark;

      /**
       * 创建时间
       */
      @ExcelProperty(value = "创建时间")
    private Date createTime;

      /**
       * 更新时间
       */
      @ExcelProperty(value = "更新时间")
    private Date updateTime;

      /**
       * 服务商
       */
      @ExcelProperty(value = "服务商")
    private String service;

      /**
       * 创建人
       */
      @ExcelProperty(value = "创建人")
    private String createByName;

      /**
       * 修改人
       */
      @ExcelProperty(value = "修改人")
    private String updateByName;

      /**
       * 单据类型
       */
      @ExcelProperty(value = "单据类型")
    private String billType;

      /**
       * 单据id
       */
      @ExcelProperty(value = "单据id")
    private Long billId;

      /**
       * 单号
       */
      @ExcelProperty(value = "单号")
    private String billCode;


}

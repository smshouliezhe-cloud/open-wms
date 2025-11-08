package com.yiruantong.basic.domain.common.vo;

import com.yiruantong.basic.domain.common.CommonComment;
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
 * 页面单据评论视图对象 common_comment
 *
 * @author YRT
 * @date 2025-03-08
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CommonComment.class)
public class CommonCommentVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

      /**
       * 评论ID
       */
      @ExcelProperty(value = "评论ID")
    private Long commentId;

      /**
       * 评论内容
       */
      @ExcelProperty(value = "评论内容")
    private String content;

      /**
       * 回复
       */
      @ExcelProperty(value = "回复")
    private Map<String, Object> reply;

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

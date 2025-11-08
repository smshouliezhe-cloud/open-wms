package com.yiruantong.basic.domain.common.vo;

  import java.util.Date;
  import com.fasterxml.jackson.annotation.JsonFormat;
import com.yiruantong.basic.domain.common.CommonOperationLog;
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
 * 业务操作日志视图对象 common_operation_log
 *
 * @author YRT
 * @date 2025-03-15
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CommonOperationLog.class)
public class CommonOperationLogVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

      /**
       * 历史ID
       */
      @ExcelProperty(value = "历史ID")
    private Long operationId;

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

      /**
       * 状态类型
       */
      @ExcelProperty(value = "状态类型")
    private String statusType;

      /**
       * 操作类型
       */
      @ExcelProperty(value = "操作类型")
    private String operationType;

      /**
       * 变更前状态
       */
      @ExcelProperty(value = "变更前状态")
    private String fromStatus;

      /**
       * 变更后状态
       */
      @ExcelProperty(value = "变更后状态")
    private String toStatus;

      /**
       * 状态级别
       */
      @ExcelProperty(value = "状态级别")
    private String statusLevel;

      /**
       * 排序号
       */
      @ExcelProperty(value = "排序号")
    private Long orderNum;

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
       * 模块ID
       */
      @ExcelProperty(value = "模块ID")
    private Long menuId;


}

package com.yiruantong.system.domain.dataHandler.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yiruantong.system.domain.dataHandler.SysCodeRegular;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;


/**
 * 单据编码规则视图对象 sys_code_regular
 *
 * @author YRT
 * @date 2023-12-03
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysCodeRegular.class)
public class SysCodeRegularVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ExcelProperty(value = "ID")
  private Long regularId;

  /**
   * 父级ID
   */
  @ExcelProperty(value = "父级ID")
  private Long parentId;

  /**
   * 模块类别ID
   */
  @ExcelProperty(value = "模块类别ID")
  private Long menuId;

  /**
   * 模块ID
   */
  @ExcelProperty(value = "模块ID")
  private String menuName;

  /**
   * 模块名称
   */
  @ExcelProperty(value = "模块名称")
  private String code;

  /**
   * SQL语句
   */
  @ExcelProperty(value = "SQL语句")
  private String regularSql;

  /**
   * 是否启用
   */
  @ExcelProperty(value = "是否启用")
  private Long enable;

  /**
   * 创建人
   */
  @ExcelProperty(value = "创建人")
  private String createByName;

  /**
   * 更新人
   */
  @ExcelProperty(value = "更新人")
  private String updateByName;

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


}

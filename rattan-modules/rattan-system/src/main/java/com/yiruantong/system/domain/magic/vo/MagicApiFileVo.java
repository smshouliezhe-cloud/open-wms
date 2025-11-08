package com.yiruantong.system.domain.magic.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yiruantong.system.domain.magic.MagicApiFile;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * magic api 接口视图对象 magic_api_file
 *
 * @author YRT
 * @date 2024-11-10
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = MagicApiFile.class)
public class MagicApiFileVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 路径
   */
  @ExcelProperty(value = "路径")
  private String filePath;

  /**
   * 内容
   */
  @ExcelProperty(value = "内容")
  private String fileContent;

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

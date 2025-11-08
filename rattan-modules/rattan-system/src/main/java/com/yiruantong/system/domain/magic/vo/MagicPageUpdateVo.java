package com.yiruantong.system.domain.magic.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yiruantong.system.domain.magic.MagicPage;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 页面开发视图对象 magic_page
 *
 * @author YRT
 * @date 2024-11-10
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = MagicPage.class)
public class MagicPageUpdateVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 主键
   */
  @ExcelProperty(value = "主键")
  private Long pageId;

  /**
   * 页面json
   */
  @ExcelProperty(value = "页面json")
  private String pageschema;
}

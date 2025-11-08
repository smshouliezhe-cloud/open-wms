package com.yiruantong.system.domain.dataHandler;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.common.mybatis.core.domain.TenantEntity;

import java.io.Serial;
import java.util.Map;

/**
 * 单据编码规则对象 sys_code_regular
 *
 * @author YRT
 * @date 2023-12-03
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_code_regular", autoResultMap = true)
public class SysCodeRegular extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @TableId(value = "regular_Id")
  private Long regularId;

  /**
   * 父级ID
   */
  private Long parentId;

  /**
   * 模块类别ID
   */
  private Long menuId;

  /**
   * 模块ID
   */
  private String menuName;

  /**
   * 模块名称
   */
  private String code;

  /**
   * SQL语句
   */
  private String regularSql;

  /**
   * 是否启用
   */
  private Long enable;

  /**
   * 排序号
   */
  private Long orderNum;

  /**
   * 扩展字段
   */
  @TableField(value = "expand_fields", typeHandler = JacksonTypeHandler.class)
  private Map<String, Object> expandFields;

  /**
   * 备注
   */
  private String remark;


}

package com.yiruantong.generator.domain.vo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.generator.domain.GenTable;
import com.yiruantong.generator.domain.GenTableColumn;

import java.io.Serializable;
import java.util.List;

/**
 * 业务表 gen_table
 *
 * @author YiRuanTong
 */
@Data
@AutoMapper(target = GenTable.class)
public class GenTableVo extends BaseEntity implements Serializable {

  /**
   * 编号
   */
  private Long tableId;

  /**
   * 表名称
   */
  @NotBlank(message = "表名称不能为空")
  private String tableName;

  /**
   * 表描述
   */
  @NotBlank(message = "表描述不能为空")
  private String tableComment;

  /**
   * 关联父表的表名
   */
  private String subTableName;

  /**
   * 本表关联父表的外键名
   */
  private String subTableFkName;

  /**
   * 实体类名称(首字母大写)
   */
  @NotBlank(message = "实体类名称不能为空")
  private String className;

  /**
   * 使用的模板（crud单表操作 tree树表操作 sub主子表操作）
   */
  private String tplCategory;

  /**
   * 生成包路径
   */
  @NotBlank(message = "生成包路径不能为空")
  private String packageName;

  /**
   * 生成模块名
   */
  @NotBlank(message = "生成模块名不能为空")
  private String moduleName;

  /**
   * 生成业务名
   */
  @NotBlank(message = "生成业务名不能为空")
  private String businessName;

  /**
   * 生成功能名
   */
  @NotBlank(message = "生成功能名不能为空")
  private String functionName;

  /**
   * 生成作者
   */
  @NotBlank(message = "作者不能为空")
  private String functionAuthor;

  /**
   * 生成代码方式（0zip压缩包 1自定义路径）
   */
  private String genType;

  /**
   * 生成路径（不填默认项目路径）
   */
  private String genPath;

  /**
   * 主键字段
   */
  private String keyName;

  /**
   * 编码字段
   */
  private String codeRegular;

  /**
   * 连接字段
   */
  private String linkColumn;

  /**
   * 排序字段
   */
  private String orderBy;

  /**
   * 前端字段
   */
  private String webRouter;

  /**
   * 后端字段
   */
  private String prefixRouter;

  /**
   * 主键信息
   */
  private GenTableColumn pkColumn;

  /**
   * 表列信息
   */
  @Valid
  private List<GenTableColumn> columns;

  /**
   * 其它生成选项
   */
  private String options;

  /**
   * 父级ID
   */
  private Long parentId;

  /**
   * UI JSON数据
   */
  private String jsonData;
  /**
   * 树编码字段
   */
  private String treeCode;

  /**
   * 树父编码字段
   */
  private String treeParentCode;

  /**
   * 树名称字段
   */
  private String treeName;

  /**
   * 菜单id列表
   */
  private List<Long> menuIds;

  /**
   * 上级菜单ID字段
   */
  private String parentMenuId;

  /**
   * 上级菜单名称字段
   */
  private String parentMenuName;

  /**
   * 排序号
   */
  private Long orderNum;

  /**
   * do扩展属性
   */
  private String expandAttributesDo;

  /**
   * do扩展import
   */
  private String expandImportsDo;

  /**
   * bo扩展属性
   */
  private String expandAttributesBo;

  /**
   * bo扩展import
   */
  private String expandImportsBo;

  /**
   * vo扩展属性
   */
  private String expandAttributesVo;

  /**
   * vo扩展import
   */
  private String expandImportsVo;
}

package com.yiruantong.inventory.domain.operation.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.common.mybatis.core.dto.QueryBo;
import com.yiruantong.inventory.domain.operation.StorageCheck;

import java.io.Serializable;
import java.util.List;


/**
 * 盘点单视图对象 storage_check
 *
 * @author YRT
 * @date 2023-10-24
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = StorageCheck.class)
public class CreateStorageCheckVo implements Serializable {

  /**
   * 仓库ID
   */
  @ExcelProperty(value = "仓库ID")
  private Long storageId;

  /**
   * 仓库名称
   */
  @ExcelProperty(value = "仓库名称")
  private String storageName;

  /**
   * 货主ID
   */
  @ExcelProperty(value = "货主ID")
  private Long consignorId;

  /**
   * 货主名称
   */
  @ExcelProperty(value = "货主名称")
  private String consignorName;

  /**
   * 库区
   */
  @ExcelProperty(value = "库区")
  private String areaCode;

  /**
   * 通道
   */
  @ExcelProperty(value = "通道")
  private String channelCode;

  /**
   * 盘点类型
   */
  @ExcelProperty(value = "盘点类型")
  private String checkType;

  /**
   * 是否盲盘
   */
  @ExcelProperty(value = "是否盲盘")
  private Long checked;

  /**
   * 差异天数
   */
  @ExcelProperty(value = "差异天数")
  private Long diffDate;

  /**
   * 库存ID集合
   */
  @ExcelProperty(value = "库存ID集合")
  private String inventoryIds;

  /**
   * 页面条件
   */
  @ExcelProperty(value = "页面条件")
  private List<QueryBo> allWhere;

}

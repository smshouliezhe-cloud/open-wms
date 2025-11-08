package com.yiruantong.inventory.domain.operation.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/*
 * @description: 货位转移明细查询对象
 */
@Data
public class StoragePositionTransferDetailComposeVo extends StoragePositionTransferDetailVo implements Serializable {
  /**
   * 货位转移单号
   */
  @ExcelProperty(value = "货位转移单号")
  private String transferCode;

  /**
   * 仓库名称
   */
  @ExcelProperty(value = "仓库名称")
  private String storageName;



  /**
   * 经手人
   */
  @ExcelProperty(value = "经手人")
  private String nickName;



  /**
   * 转移状态
   */
  @ExcelProperty(value = "转移状态")
  private String tansfterStatus;

  /**
   * 分拣日期
   */
  @ExcelProperty(value = "分拣日期")
  private Date sortingDate;


}

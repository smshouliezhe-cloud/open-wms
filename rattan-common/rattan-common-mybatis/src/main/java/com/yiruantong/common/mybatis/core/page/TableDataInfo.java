package com.yiruantong.common.mybatis.core.page;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 表格分页数据对象
 *
 * @author YiRuanTong
 */
@Data
@NoArgsConstructor
public class TableDataInfo<T> implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 总记录数
   */
  private long total;

  /**
   * 表名
   */
  private String tableName;

  /**
   * 列表数据
   */
  private List<T> rows;

  /**
   * 表格脚本合计
   */
  private List<Map<String, Object>> footer;

  /**
   * 消息状态码
   */
  private int code;

  /**
   * 是否成功
   */
  private boolean result;

  /**
   * 消息内容
   */
  private String msg;

  /**
   * 分页
   *
   * @param list  列表数据
   * @param total 总记录数
   */
  public TableDataInfo(List<T> list, long total) {
    this.rows = list;
    this.total = total;
  }

  public static <T> TableDataInfo<T> build(IPage<T> page) {
    TableDataInfo<T> rspData = new TableDataInfo<>();
    rspData.setResult(true);
    rspData.setCode(HttpStatus.HTTP_OK);
    rspData.setMsg("查询成功");

    rspData.setRows(page.getRecords());
    rspData.setTotal(page.getTotal());
    List<Map<String, Object>> mapList = new ArrayList<>();
    rspData.setFooter(mapList);

    return rspData;
  }

  public static <T> TableDataInfo<T> build(List<T> list) {
    TableDataInfo<T> rspData = new TableDataInfo<>();
    rspData.setResult(true);
    rspData.setCode(HttpStatus.HTTP_OK);
    rspData.setMsg("查询成功");

    rspData.setRows(list);
    rspData.setTotal(list.size());
    List<Map<String, Object>> mapList = new ArrayList<>();
    rspData.setFooter(mapList);

    return rspData;
  }

  public static <T> TableDataInfo<T> build() {
    TableDataInfo<T> rspData = new TableDataInfo<>();
    rspData.setResult(true);
    rspData.setCode(HttpStatus.HTTP_OK);
    rspData.setMsg("查询成功");
    return rspData;
  }
}

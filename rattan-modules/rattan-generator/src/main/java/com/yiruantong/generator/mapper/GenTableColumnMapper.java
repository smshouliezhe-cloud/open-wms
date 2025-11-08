package com.yiruantong.generator.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.yiruantong.generator.domain.vo.GenTableColumnVo;
import org.apache.ibatis.annotations.Param;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;
import com.yiruantong.generator.domain.GenTableColumn;

import java.util.List;

/**
 * 业务字段 数据层
 *
 * @author YiRuanTong
 */
@InterceptorIgnore(dataPermission = "true", tenantLine = "true")
public interface GenTableColumnMapper extends BaseMapperPlus<GenTableColumn, GenTableColumnVo> {
  /**
   * 根据表名称查询列信息
   *
   * @param tableName 表名称
   * @param dataName  数据源名称
   * @return 列信息
   */
  @DS("#dataName")
  List<GenTableColumn> selectDbTableColumnsByName(@Param("tableName") String tableName, String dataName);

}

package com.yiruantong.system.service.dataHandler;

import com.yiruantong.system.domain.dataHandler.SysImport;
import com.yiruantong.system.domain.dataHandler.bo.SysImportBo;
import com.yiruantong.system.domain.dataHandler.vo.SysImportVo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 导入设置Service接口
 *
 * @author YRT
 * @date 2023-08-05
 */
public interface ISysImportService extends IServicePlus<SysImport, SysImportVo, SysImportBo> {
  /**
   * 修改key
   */
  void setKey(String key);

  /**
   * 是否存在错误
   */
  boolean isError();

  /**
   * 修改错误状态
   *
   * @param isExistError
   */
  void setError(boolean isExistError);

  /**
   * 导入通用校验
   *
   * @param excelDataList 导入数据集合
   * @param importId      导入ID
   * @param request       请求request参数
   * @return
   */
  R<Void> commonCheck(List<Map<String, Object>> excelDataList, Long importId, HttpServletRequest request, LoginUser loginUser);

  /**
   * 写入带颜色的字体
   * 格式化文本, {} 表示占位符<br>
   * 此方法只是简单将占位符 {} 按照顺序替换为参数<br>
   * 如果想输出 {} 使用 \\转义 { 即可，如果想输出 {} 之前的 \ 使用双转义符 \\\\ 即可<br>
   * 例：<br>
   * 通常使用：format("this is {} for {}", "a", "b") -> this is a for b<br>
   * 转义{}： format("this is \\{} for {}", "a", "b") -> this is {} for a<br>
   * 转义\： format("this is \\\\{} for {}", "a", "b") -> this is \a for b<br>
   *
   * @param template 文本模板，被替换的部分用 {} 表示
   * @param params
   */
  void writeMsg(String template, Color color, Object... params);

  /**
   * 写入普通字体
   * 格式化文本, {} 表示占位符<br>
   * 此方法只是简单将占位符 {} 按照顺序替换为参数<br>
   * 如果想输出 {} 使用 \\转义 { 即可，如果想输出 {} 之前的 \ 使用双转义符 \\\\ 即可<br>
   * 例：<br>
   * 通常使用：format("this is {} for {}", "a", "b") -> this is a for b<br>
   * 转义{}： format("this is \\{} for {}", "a", "b") -> this is {} for a<br>
   * 转义\： format("this is \\\\{} for {}", "a", "b") -> this is \a for b<br>
   *
   * @param template
   * @param params
   */
  void writeMsg(String template, Object... params);

  /**
   * 写入红色字体
   * 格式化文本, {} 表示占位符<br>
   * 此方法只是简单将占位符 {} 按照顺序替换为参数<br>
   * 如果想输出 {} 使用 \\转义 { 即可，如果想输出 {} 之前的 \ 使用双转义符 \\\\ 即可<br>
   * 例：<br>
   * 通常使用：format("this is {} for {}", "a", "b") -> this is a for b<br>
   * 转义{}： format("this is \\{} for {}", "a", "b") -> this is {} for a<br>
   * 转义\： format("this is \\\\{} for {}", "a", "b") -> this is \a for b<br>
   *
   * @param template
   * @param params
   */
  void writeMsgRed(String template, Object... params);

  /**
   * 写入蓝色字体
   * 格式化文本, {} 表示占位符<br>
   * 此方法只是简单将占位符 {} 按照顺序替换为参数<br>
   * 如果想输出 {} 使用 \\转义 { 即可，如果想输出 {} 之前的 \ 使用双转义符 \\\\ 即可<br>
   * 例：<br>
   * 通常使用：format("this is {} for {}", "a", "b") -> this is a for b<br>
   * 转义{}： format("this is \\{} for {}", "a", "b") -> this is {} for a<br>
   * 转义\： format("this is \\\\{} for {}", "a", "b") -> this is \a for b<br>
   *
   * @param template
   * @param params
   */
  void writeMsgBlue(String template, Object... params);

  /**
   * 写入消息结束
   *
   * @param template 文本模板，被替换的部分用 {} 表示
   * @param params
   */
  void writeEnd(String template, Object... params);

  /**
   * 写入消息结束
   */
  void writeEnd();

  /**
   * isAssert断言
   *
   * @param expression       为true时，输出消息
   * @param errorMsgTemplate 消息模板
   * @param params           消息参数
   * @throws Throwable
   */
  void isAssert(boolean expression, String errorMsgTemplate, Object... params) throws IllegalArgumentException;

  /**
   * 获取分组
   *
   * @param excelDataList
   * @param importId
   * @return
   */
  List<Map<String, Object>> getGroupList(List<Map<String, Object>> excelDataList, Long importId);

  /**
   * 获取分组明细
   *
   * @param excelDataList
   * @param groupInfo
   * @param importId
   * @return
   */
  List<Map<String, Object>> getGroupDetails(List<Map<String, Object>> excelDataList, Map<String, Object> groupInfo, Long importId);

  /**
   * 下载导入模板
   *
   * @param response
   */
  void importTemplate(HttpServletResponse response, Long importId) throws IOException;

  /**
   * 根据自定义ID或者主键ID获取导入信息
   *
   * @param importId 自定义ID
   * @return SysImport实体
   */
  SysImport getInfoByCustomImportId(Long importId);

  /**
   * 搜索菜单
   *
   * @param filterText 查询参数
   * @return
   */
  R<List<Map<String, Object>>> searchTree(String filterText);

  /**
   * 获取扩展字段
   *
   * @param row 当前导入行数据
   * @return map
   */
  Map<String, Object> getExpandFields(Map<String, Object> row);
}

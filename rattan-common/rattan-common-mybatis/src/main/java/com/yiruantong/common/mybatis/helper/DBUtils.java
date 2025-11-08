package com.yiruantong.common.mybatis.helper;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.session.SqlSession;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.utils.DateUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Component
@Slf4j
public class DBUtils {

  private static final String PREFIX_LOG = "【自定义DB工具】";
  private SqlSessionTemplate sqlSessionTemplate;

  public DBUtils(SqlSessionTemplate sqlSessionTemplate) {
    this.sqlSessionTemplate = sqlSessionTemplate;
  }

  public List<LinkedHashMap<String, Object>> selectList(String sql) {
    log.info(PREFIX_LOG + "执行查询sql:" + sql);
    List<LinkedHashMap<String, Object>> list = new ArrayList<LinkedHashMap<String, Object>>();
    PreparedStatement pst = null;
    SqlSession session = getSqlSession();
    ResultSet result = null;
    try {
      pst = session.getConnection().prepareStatement(sql);
      result = pst.executeQuery();
      ResultSetMetaData md = result.getMetaData(); // 获得结果集结构信息,元数据
      int columnCount = md.getColumnCount(); // 获得列数
      while (result.next()) {
        LinkedHashMap<String, Object> rowData = new LinkedHashMap<String, Object>();
        for (int i = 1; i <= columnCount; i++) {
          String colName = StringUtils.toCamelCase(md.getColumnLabel(i));
          rowData.put(colName, result.getObject(i));
        }
        list.add(rowData);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (pst != null) {
        try {
          pst.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      closeSqlSession(session);
    }
    return list;
  }

  /**
   * 执行sql语句，并返回是否执行成功
   *
   * @param sql
   * @return
   */
  public boolean executeSql(String sql) {
    log.info(PREFIX_LOG + "执行查询sql:" + sql);
    PreparedStatement pst = null;
    SqlSession session = getSqlSession();
    boolean result = false;
    try {
      pst = session.getConnection().prepareStatement(sql);
      result = pst.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (pst != null) {
        try {
          pst.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      closeSqlSession(session);
    }
    return result;
  }

  /**
   * 获取sqlSession
   *
   * @return
   */
  public SqlSession getSqlSession() {
    return SqlSessionUtils.getSqlSession(
      sqlSessionTemplate.getSqlSessionFactory(),
      sqlSessionTemplate.getExecutorType(),
      sqlSessionTemplate.getPersistenceExceptionTranslator());
  }

  /**
   * 关闭sqlSession
   *
   * @param session
   */
  public void closeSqlSession(SqlSession session) {
    SqlSessionUtils.closeSqlSession(session, sqlSessionTemplate.getSqlSessionFactory());
  }

  /**
   * 获取编码规则
   *
   * @param menuEnum 模块ID
   * @return 返回单据编码
   */
  public static String getCodeRegular(MenuEnum menuEnum, String tenantId) {
    int menuId = menuEnum.getId();
    if (StringUtils.isEmpty(tenantId)) {
      tenantId = LoginHelper.getTenantId();
    }
    String sql = new SQL()
      .SELECT("code, regular_sql")
      .FROM("sys_code_regular")
      .WHERE("menu_id = {0}")
      .toString();
    final Map<String, Object> sysCodeRegular = SqlRunner.db().selectOne(sql, menuId);
    if (ObjectUtil.isEmpty(sysCodeRegular)) {
      return "";
    }

    String code = ObjectUtil.toString(sysCodeRegular.get("code"));
    sql = ObjectUtil.toString(sysCodeRegular.get("regularSql"));
    sql = sql.replace("{tenant_id}", tenantId);
    sql = sql.replace("{date}", DateUtils.getDate());
    Map<String, Object> dataInfo = SqlRunner.db().selectOne(sql);

    final String lastCode = Optional.ofNullable(dataInfo).map(Map::values)
      .map(Collection::stream).flatMap(Stream::findFirst).orElse("").toString();

    int lastNumberLength = 0; // 尾部自增的长度
    String lastRegular = "";

    Pattern p = Pattern.compile("(\\[[^\\]]*\\])");
    Matcher m = p.matcher(code);
    while (m.find()) {
      var regular = m.group().substring(1, m.group().length() - 1); // 编码规则，yyyy, MM, dd
      if (StringUtils.isNumeric(regular)) {
        lastNumberLength = NumberUtil.parseInt(regular);
        lastRegular = "[" + regular + "]";
      } else {
        SimpleDateFormat dateFormat = new SimpleDateFormat(regular);
        String formattValue = dateFormat.format(new Date());

        code = code.replace("[" + regular + "]", formattValue);
      }
    }
    int lastNumber = 0; // 尾部的数值
    if (StrUtil.isNotEmpty(lastCode)) {
      var subNumber = StrUtil.subSufByLength(lastCode, lastNumberLength);
      lastNumber = NumberUtil.parseInt(subNumber);
    }
    String increaseNum = StrUtil.padPre(String.valueOf(lastNumber + 1), lastNumberLength, '0');
    code = code.replace(lastRegular, increaseNum);

    return code;
  }

  public static String getCodeRegular(MenuEnum menuEnum) {
    return getCodeRegular(menuEnum, null);
  }

  /**
   * 递归获取自己及子级ID集合，逗号分割
   *
   * @param tableName   表名
   * @param keyName     主键名
   * @param rootIdValue 根节点值
   */
  public static List<String> getSelfAndChildId(String tableName, String keyName, String rootIdValue) {
    String sql = """
      WITH RECURSIVE sub_categories AS (
          SELECT {1}, tenant_id
          FROM {0}
          WHERE {1} = {2} -- 替换为具体的父级分类ID
          UNION ALL
          SELECT c.{1}, c.tenant_id
          FROM {0} c
          INNER JOIN sub_categories sc ON sc.{1} = c.parent_id
      )
      SELECT GROUP_CONCAT({1} SEPARATOR '','') AS ids FROM sub_categories;
      """;
    sql = MessageFormat.format(sql, tableName, keyName, rootIdValue);
    final Map<String, Object> result = SqlRunner.db().selectOne(sql);
    return StringUtils.splitList(Convert.toStr(result.get("ids")));
  }
}

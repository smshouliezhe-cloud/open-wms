package com.yiruantong.common.core.utils;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author liuyaowei
 * @date 2023-02-09 15:43
 * @desc
 */
public class NumberUtils extends NumberUtil {
  /**
   * 数字0
   */
  public static final long ZERO = 0L;
  /**
   * 数字1
   */
  public static final long ONE = 1L;

  private static boolean isMatch(String regex, String orginal) {
    if (orginal == null || orginal.trim().equals("")) {
      return false;
    }
    Pattern pattern = Pattern.compile(regex);
    Matcher isNum = pattern.matcher(orginal);
    return isNum.matches();
  }

  /**
   * 校验是否是正整数
   *
   * @param orginal
   * @return
   */
  public static boolean isPositiveInteger(String orginal) {
    return isMatch("^\\+{0,1}[1-9]\\d*", orginal);
  }

  /**
   * 校验是否是正整数
   *
   * @param orginal
   * @return
   */
  public static boolean isPositiveInteger(Number orginal) {
    return isMatch("^\\+{0,1}[1-9]\\d*", Convert.toStr(orginal));
  }

  /**
   * 校验是否是负整数
   *
   * @param orginal
   * @return
   */
  public static boolean isNegativeInteger(String orginal) {
    return isMatch("^-[1-9]\\d*", orginal);
  }

  /**
   * 整数
   *
   * @param orginal
   * @return
   */
  public static boolean isWholeNumber(String orginal) {
    return isMatch("[+-]{0,1}0", orginal) || isPositiveInteger(orginal) || isNegativeInteger(orginal);
  }

  /**
   * 正数
   *
   * @param orginal
   * @return
   */
  public static boolean isPositiveDecimal(String orginal) {
    return isMatch("\\+{0,1}[0]\\.[1-9]*|\\+{0,1}[1-9]\\d*\\.\\d*", orginal);
  }

  /**
   * 负数
   *
   * @param orginal
   * @return
   */
  public static boolean isNegativeDecimal(String orginal) {
    return isMatch("^-[0]\\.[1-9]*|^-[1-9]\\d*\\.\\d*", orginal);
  }

  /**
   * 小数
   *
   * @param orginal
   * @return
   */
  public static boolean isDecimal(String orginal) {
    return isMatch("[-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+", orginal);
  }

  /**
   * 实数
   *
   * @param orginal
   * @return
   */
  public static boolean isRealNumber(String orginal) {
    return isWholeNumber(orginal) || isDecimal(orginal);
  }

  /**
   * 如果为空默认值
   *
   * @param originVal  原值
   * @param defaultVal 默认值
   * @return
   */
  public static Long ifNullDefault(Long originVal, Long defaultVal) {
    return ObjectUtil.isNull(originVal) ? defaultVal : originVal;
  }

  /**
   * 如果为空默认值
   *
   * @param originVal 原值
   * @return
   */
  public static Long ifNullDefault(Long originVal) {
    return ifNullDefault(originVal, 0L);
  }

  /**
   * 如果为空默认值
   *
   * @param originVal 原值
   * @return
   */
  public static int ifNullDefault(Integer originVal) {
    return ifNullDefault(Convert.toLong(originVal), 0L).intValue();
  }
}

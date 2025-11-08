package com.yiruantong.common.core.utils;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Optional;

/**
 * BigDecimal工具，B为BigDecimalUtils缩写
 *
 * @author xtb
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class B {

  /**
   * 返回结果
   */
  private BigDecimal result;

  private B(BigDecimal result) {
    this.result = Optional.ofNullable(result).orElse(BigDecimal.ZERO);
  }

  /**
   * 功能描述: 构造链 <br/>
   */
  public static B chain(BigDecimal bd) {
    return new B(bd);
  }

  /**
   * 功能描述: 构造链 <br/>
   */
  public static B chain(String bd) {
    isNumber(bd);
    return new B(new BigDecimal(bd));
  }

  /**
   * 功能描述: 构造链 <br/>
   */
  public static B chain(long bd) {
    return new B(BigDecimal.valueOf(bd));
  }

  /**
   * 功能描述: 构造链 <br/>
   */
  public static B chain(double bd) {
    return new B(BigDecimal.valueOf(bd));
  }

  /**
   * 判断是否数字
   */
  private static void isNumber(String v) {
    if (!NumberUtil.isNumber(v)) {
      throw new NumberFormatException("非数字入参，请输入有效的数字");
    }
  }

  /**
   * 比较大小
   *
   * @param x
   * @param y
   * @return
   */
  public static int compare(BigDecimal x, BigDecimal y) {
    x = Optional.ofNullable(x).orElse(BigDecimal.ZERO);
    y = Optional.ofNullable(y).orElse(BigDecimal.ZERO);

    return x.compareTo(y);
  }

  /**
   * 大于
   *
   * @param bigNum1
   * @param bigNum2
   * @return
   */
  public static boolean isGreater(Number bigNum1, Number bigNum2) {
    BigDecimal bigDecimal1 = Convert.toBigDecimal(bigNum1);
    BigDecimal bigDecimal2 = Convert.toBigDecimal(bigNum2);
    bigDecimal1 = Optional.ofNullable(bigDecimal1).orElse(BigDecimal.ZERO);
    bigDecimal2 = Optional.ofNullable(bigDecimal2).orElse(BigDecimal.ZERO);
    return bigDecimal1.compareTo(bigDecimal2) > 0;
  }

  /**
   * 大于0
   *
   * @param bigNum1
   * @return
   */
  public static boolean isGreater(BigDecimal bigNum1) {
    bigNum1 = Optional.ofNullable(bigNum1).orElse(BigDecimal.ZERO);
    return bigNum1.compareTo(BigDecimal.ZERO) > 0;
  }

  /**
   * 大于0
   *
   * @param bigNum1
   * @return
   */
  public static boolean isGreater(Number bigNum1) {
    return isGreater(Convert.toBigDecimal(bigNum1));
  }

  /**
   * 大于等于
   *
   * @param bigNum1
   * @param bigNum2
   * @return
   */
  public static boolean isGreaterOrEqual(BigDecimal bigNum1, BigDecimal bigNum2) {
    bigNum1 = Optional.ofNullable(bigNum1).orElse(BigDecimal.ZERO);
    bigNum2 = Optional.ofNullable(bigNum2).orElse(BigDecimal.ZERO);
    return bigNum1.compareTo(bigNum2) >= 0;
  }

  /**
   * 大于等于0
   *
   * @param bigNum1
   * @return
   */
  public static boolean isGreaterOrEqual(BigDecimal bigNum1) {
    bigNum1 = Optional.ofNullable(bigNum1).orElse(BigDecimal.ZERO);
    return bigNum1.compareTo(BigDecimal.ZERO) >= 0;
  }

  /**
   * 小于
   *
   * @param bigNum1
   * @param bigNum2
   * @return
   */
  public static boolean isLess(Number bigNum1, Number bigNum2) {
    return isLess(Convert.toBigDecimal(bigNum1), Convert.toBigDecimal(bigNum2));
  }

  /**
   * 小于
   *
   * @param bigNum1
   * @param bigNum2
   * @return
   */
  public static boolean isLess(BigDecimal bigNum1, BigDecimal bigNum2) {
    bigNum1 = Optional.ofNullable(bigNum1).orElse(BigDecimal.ZERO);
    bigNum2 = Optional.ofNullable(bigNum2).orElse(BigDecimal.ZERO);
    return bigNum1.compareTo(bigNum2) < 0;
  }

  /**
   * 小于
   *
   * @param bigNum1
   * @return
   */
  public static boolean isLess(BigDecimal bigNum1) {
    bigNum1 = Optional.ofNullable(bigNum1).orElse(BigDecimal.ZERO);
    return bigNum1.compareTo(BigDecimal.ZERO) < 0;
  }

  /**
   * 小于等于
   *
   * @param bigNum1
   * @param bigNum2
   * @return
   */
  public static boolean isLessOrEqual(BigDecimal bigNum1, BigDecimal bigNum2) {
    bigNum1 = Optional.ofNullable(bigNum1).orElse(BigDecimal.ZERO);
    bigNum2 = Optional.ofNullable(bigNum2).orElse(BigDecimal.ZERO);
    return bigNum1.compareTo(bigNum2) <= 0;
  }

  /**
   * 小于等于
   *
   * @param bigNum1
   * @param bigNum2
   * @return
   */
  public static boolean isLessOrEqual(Number bigNum1, Number bigNum2) {
    return isLessOrEqual(Convert.toBigDecimal(bigNum1), Convert.toBigDecimal(bigNum2));
  }

  /**
   * 小于等于0
   *
   * @param bigNum1 BigDecimal类型
   * @return boolean
   */
  public static boolean isLessOrEqual(BigDecimal bigNum1) {
    bigNum1 = Optional.ofNullable(bigNum1).orElse(BigDecimal.ZERO);
    return bigNum1.compareTo(BigDecimal.ZERO) <= 0;
  }

  /**
   * 小于等于0
   *
   * @param bigNum1 数字类型
   * @return boolean
   */
  public static boolean isLessOrEqual(Number bigNum1) {
    BigDecimal bigNum = Optional.ofNullable(bigNum1).map(Convert::toBigDecimal).orElse(BigDecimal.ZERO);
    return bigNum.compareTo(BigDecimal.ZERO) <= 0;
  }

  /**
   * 等于
   *
   * @param bigNum1
   * @param bigNum2
   * @return
   */
  public static boolean isEqual(BigDecimal bigNum1, BigDecimal bigNum2) {
    bigNum1 = Optional.ofNullable(bigNum1).orElse(BigDecimal.ZERO);
    bigNum2 = Optional.ofNullable(bigNum2).orElse(BigDecimal.ZERO);
    return bigNum1.compareTo(bigNum2) == 0;
  }

  /**
   * 等于0
   *
   * @param bigNum1
   * @return
   */
  public static boolean isEqual(BigDecimal bigNum1) {
    bigNum1 = Optional.ofNullable(bigNum1).orElse(BigDecimal.ZERO);
    return bigNum1.compareTo(BigDecimal.ZERO) == 0;
  }

  /**
   * 等于
   *
   * @param bigNum1
   * @param bigNum2
   * @return
   */
  public static boolean isEqual(Number bigNum1, Number bigNum2) {
    return NumberUtil.equals(Convert.toBigDecimal(bigNum1), Convert.toBigDecimal(bigNum2));
  }

  /**
   * 等于
   *
   * @param bigNum1 Object对象
   * @param bigNum2 Object对象
   * @return
   */
  public static boolean isEqual(Object bigNum1, Object bigNum2) {
    return NumberUtil.equals(Convert.toBigDecimal(bigNum1), Convert.toBigDecimal(bigNum2));
  }

  /**
   * 等于0
   *
   * @param bigNum1
   * @return
   */
  public static boolean isEqual(Number bigNum1) {
    return isEqual(Convert.toBigDecimal(bigNum1));
  }

  /**
   * 通常用于boolean开关，等于1
   *
   * @param bigNum1 布尔开关值
   * @return boolean
   */
  public static boolean isOk(Number bigNum1) {
    return isEqual(Convert.toBigDecimal(bigNum1), NumberUtils.ONE);
  }

  /**
   * 通常用于boolean开关，等于0
   *
   * @param bigNum1 布尔开关值
   * @return boolean
   */
  public static boolean isFail(Number bigNum1) {
    return isEqual(Convert.toBigDecimal(bigNum1), NumberUtils.ZERO);
  }

  /**
   * 字符串等于
   *
   * @param str1 字符串1
   * @param str2 字符串2
   * @return
   */
  public static boolean isEqual(String str1, String str2) {
    return StringUtils.equals(str1, str2);
  }

  /**
   * null值转0
   *
   * @param bigNum1
   * @return
   */
  public static BigDecimal nullToZero(BigDecimal bigNum1) {
    return NumberUtil.nullToZero(bigNum1);
  }
  /****************** 以下为实例方法 ***********************/
  /**
   * 加
   */
  public static BigDecimal add(BigDecimal bigNum1, BigDecimal bigNum2) {
    bigNum1 = Optional.ofNullable(bigNum1).orElse(BigDecimal.ZERO);
    bigNum2 = Optional.ofNullable(bigNum2).orElse(BigDecimal.ZERO);
    return NumberUtil.add(bigNum1, bigNum2);
  }

  /**
   * 加
   */
  public static BigDecimal add(Long longNum1, Long longNum2) {
    longNum1 = Optional.ofNullable(longNum1).orElse(0L);
    longNum2 = Optional.ofNullable(longNum2).orElse(0L);
    return NumberUtil.add(longNum1, longNum2);
  }

  /**
   * 加
   */
  public static BigDecimal add(Number longNum1, Number longNum2) {
    longNum1 = Optional.ofNullable(longNum1).orElse(0L);
    longNum2 = Optional.ofNullable(longNum2).orElse(0L);
    return NumberUtil.add(longNum1, longNum2);
  }

  /**
   * 批量加
   */
  public static BigDecimal adds(Number... values) {
    return NumberUtil.add(values);
  }

  /**
   * 减
   */
  public static BigDecimal sub(BigDecimal bigNum1, BigDecimal bigNum2, BigDecimal... bigNumList) {
    bigNum1 = Optional.ofNullable(bigNum1).orElse(BigDecimal.ZERO);
    bigNum2 = Optional.ofNullable(bigNum2).orElse(BigDecimal.ZERO);
    bigNum2 = NumberUtil.sub(bigNum1, bigNum2);
    for (BigDecimal n : bigNumList) {
      bigNum2 = NumberUtil.sub(bigNum2, n);
    }
    return bigNum2;
  }

  /**
   * 减
   */
  public static BigDecimal sub(Object bigNum1, Object bigNum2) {
    return NumberUtil.sub(Convert.toBigDecimal(bigNum1), Convert.toBigDecimal(bigNum2));
  }

  /**
   * 批量减
   */
  public static BigDecimal subs(Number... values) {
    return NumberUtil.sub(values);
  }

  /**
   * 乘
   */
  public static BigDecimal mul(BigDecimal bigNum1, BigDecimal bigNum2) {
    bigNum1 = Optional.ofNullable(bigNum1).orElse(BigDecimal.ZERO);
    bigNum2 = Optional.ofNullable(bigNum2).orElse(BigDecimal.ZERO);
    return NumberUtil.mul(bigNum1, bigNum2);
  }

  /**
   * 乘
   */
  public static BigDecimal mul(Object bigNum1, Object bigNum2) {
    return NumberUtil.mul(Convert.toBigDecimal(bigNum1), Convert.toBigDecimal(bigNum2));
  }

  /**
   * 批量乘
   */
  public static BigDecimal muls(Number... values) {
    return NumberUtil.mul(Arrays.stream(values).map(m -> ObjectUtil.defaultIfNull(m, 0)).toArray(Number[]::new));
  }

  /**
   * 批量乘
   */
  public static BigDecimal muls(Object... values) {
    return B.muls(Arrays.stream(values).map(Convert::toNumber).toArray(Number[]::new));
  }

  /**
   * 除
   */
  public static BigDecimal div(BigDecimal bigNum1, BigDecimal bigNum2) {
    bigNum1 = Optional.ofNullable(bigNum1).orElse(BigDecimal.ZERO);
    bigNum2 = Optional.ofNullable(bigNum2).orElse(BigDecimal.ZERO);
    if (isEqual(bigNum2)) return BigDecimal.ZERO;

    return NumberUtil.div(bigNum1, bigNum2);
  }

  /**
   * 除
   */
  public static BigDecimal div(Object bigNum1, Object bigNum2) {
    return NumberUtil.div(Convert.toBigDecimal(bigNum1), Convert.toBigDecimal(bigNum2));
  }

  /**
   * 批量除
   */
  public static BigDecimal divs(Number... values) {
    if (!ArrayUtil.isEmpty(values) && !ArrayUtil.hasNull(values)) {
      Number value = values[0];
      BigDecimal result = NumberUtil.toBigDecimal(value.toString());

      for (int i = 1; i < values.length; ++i) {
        value = values[i];
        result = result.divide(NumberUtil.toBigDecimal(value.toString()));
      }

      return result;
    } else {
      return BigDecimal.ZERO;
    }
  }

  /**
   * 除数，并向上取整
   */
  public static int ceilDiv(BigDecimal bigNum1, BigDecimal bigNum2) {
    bigNum1 = Optional.ofNullable(bigNum1).orElse(BigDecimal.ZERO);
    bigNum2 = Optional.ofNullable(bigNum2).orElse(BigDecimal.ZERO);
    if (isEqual(bigNum2)) return 1;

    return div(bigNum1, bigNum2).setScale(0, RoundingMode.UP).intValue();
  }

  /**
   * 小数位数处理
   */
  public static BigDecimal round(BigDecimal number, int scale) {
    return NumberUtil.round(number, scale);
  }

  /**
   * 小数位数处理
   */
  public static BigDecimal round(BigDecimal number, int scale, RoundingMode roundingMode) {
    return NumberUtil.round(number, scale, roundingMode);
  }

  /**
   * 取大于等于当前值的最小整数，向上取整 <br />例如：1.4返回1，1.5返回1，1.6返回1
   */
  public static BigDecimal floor(BigDecimal number) {
    return NumberUtil.round(number, 0, RoundingMode.FLOOR);
  }

  /**
   * 取小于等于当前值的最大整数，向上取整 <br /> 例如：1.4返回2，1.5返回2，1.6返回2
   */
  public static BigDecimal ceiling(BigDecimal number) {
    return NumberUtil.round(number, 0, RoundingMode.CEILING);
  }

  /**
   * 如果为null值默认为0
   */
  public static BigDecimal ifNullDefaultZero(BigDecimal value) {
    return ObjectUtil.isNull(value) ? BigDecimal.ZERO : value;
  }


  /**
   * 加
   */
  public B add(BigDecimal v) {
    this.result = result.add(Optional.ofNullable(v).orElse(BigDecimal.ZERO));
    return this;
  }

  /**
   * 加
   */
  public B add(String v) {
    isNumber(v);
    this.result = result.add(new BigDecimal(v));
    return this;
  }

  /**
   * 加
   */
  public B add(String... vs) {
    this.result = result.add(NumberUtil.add(vs));
    return this;
  }

  /**
   * 加
   */
  public B add(int v1) {
    this.result = result.add(new BigDecimal(Integer.toString(v1)));
    return this;
  }

  /**
   * 加
   */
  public B add(float v1) {
    this.result = result.add(new BigDecimal(Float.toString(v1)));
    return this;
  }

  /**
   * 加
   */
  public B add(double v1) {
    this.result = result.add(new BigDecimal(Double.toString(v1)));
    return this;
  }

  /**
   * 加
   */
  public B add(int v1, int v2) {
    this.result = result.add(NumberUtil.add(new Number[]{v1, v2}));
    return this;
  }

  /**
   * 加
   */
  public B add(Integer v1) {
    this.result = result.add(new BigDecimal(v1));
    return this;
  }

  /**
   * 加
   */
  public B add(Float v1) {
    this.result = result.add(new BigDecimal(v1));
    return this;
  }

  /**
   * 加
   */
  public B add(Double v1) {
    this.result = result.add(new BigDecimal(v1));
    return this;
  }

  /**
   * 加
   */
  public B add(Float v1, Float v2) {
    this.result = result.add(NumberUtil.add(new Number[]{v1, v2}));
    return this;
  }

  /**
   * 加
   */
  public B add(Double v1, Double v2) {
    this.result = result.add(NumberUtil.add(new Number[]{v1, v2}));
    return this;
  }

  /**
   * 加
   */
  public B add(Integer... vs) {
    this.result = result.add(NumberUtil.add(vs));
    return this;
  }

  /**
   * 加
   */
  public B add(Float... vs) {
    this.result = result.add(NumberUtil.add(vs));
    return this;
  }

  /**
   * 加
   */
  public B add(Double... vs) {
    this.result = result.add(NumberUtil.add(vs));
    return this;
  }

  /**
   * 加
   */
  public B add(Number... vs) {
    this.result = result.add(NumberUtil.add(vs));
    return this;
  }

  /**
   * 减
   */
  public B sub(BigDecimal v) {
    this.result = result.subtract(Optional.ofNullable(v).orElse(BigDecimal.ZERO));
    return this;
  }

  /**
   * 减
   */
  public B sub(String v) {
    isNumber(v);
    this.result = result.subtract(new BigDecimal(v));
    return this;
  }

  /**
   * 减
   */
  public B sub(int v) {
    this.result = result.subtract(new BigDecimal(Integer.toString(v)));
    return this;
  }

  /**
   * 减
   */
  public B sub(float v) {
    this.result = result.subtract(new BigDecimal(Float.toString(v)));
    return this;
  }

  /**
   * 减
   */
  public B sub(double v) {
    this.result = result.subtract(new BigDecimal(Double.toString(v)));
    return this;
  }

  /**
   * 减
   */
  public B sub(Integer v) {
    this.result = result.subtract(new BigDecimal(v));
    return this;
  }

  /**
   * 减
   */
  public B sub(Float v) {
    this.result = result.subtract(new BigDecimal(v));
    return this;
  }

  /**
   * 减
   */
  public B sub(Double v) {
    this.result = result.subtract(new BigDecimal(v));
    return this;
  }

  /**
   * 乘
   */
  public B mul(BigDecimal v) {
    this.result = result.multiply(Optional.ofNullable(v).orElse(BigDecimal.ZERO));
    return this;
  }

  /**
   * 乘
   */
  public B mul(String v) {
    isNumber(v);
    this.result = result.multiply(new BigDecimal(v));
    return this;
  }

  /**
   * 乘
   */
  public B mul(int v) {
    this.result = result.multiply(new BigDecimal(Integer.toString(v)));
    return this;
  }

  /**
   * 乘
   */
  public B mul(float v) {
    this.result = result.multiply(new BigDecimal(Float.toString(v)));
    return this;
  }

  /**
   * 乘
   */
  public B mul(double v) {
    this.result = result.multiply(new BigDecimal(Double.toString(v)));
    return this;
  }

  /**
   * 乘
   */
  public B mul(Integer v) {
    this.result = result.multiply(new BigDecimal(v));
    return this;
  }

  /**
   * 乘
   */
  public B mul(Float v) {
    this.result = result.multiply(new BigDecimal(v));
    return this;
  }

  /**
   * 乘
   */
  public B mul(Double v) {
    this.result = result.multiply(new BigDecimal(v));
    return this;
  }

  /**
   * 除
   */
  public B div(BigDecimal v) {
    this.result = result.divide(Optional.ofNullable(v).orElse(BigDecimal.ZERO));
    return this;
  }

  /**
   * 除
   */
  public B div(String v) {
    isNumber(v);
    this.result = result.divide(new BigDecimal(v));
    return this;
  }

  /**
   * 除
   */
  public B div(int v) {
    this.result = result.divide(new BigDecimal(Integer.toString(v)));
    return this;
  }

  /**
   * 除
   */
  public B div(float v) {
    this.result = result.divide(new BigDecimal(Float.toString(v)));
    return this;
  }

  /**
   * 除
   */
  public B div(double v) {
    this.result = result.divide(new BigDecimal(Double.toString(v)));
    return this;
  }

  /**
   * 除
   */
  public B div(Integer v) {
    this.result = result.divide(new BigDecimal(v));
    return this;
  }

  /**
   * 除
   */
  public B div(Float v) {
    this.result = result.divide(new BigDecimal(v));
    return this;
  }

  /**
   * 除
   */
  public B div(Double v) {
    this.result = result.divide(new BigDecimal(v));
    return this;
  }

  /**
   * 除 带精度 四舍五入
   */
  public B div(String v, int scale) {
    isNumber(v);
    this.result = result.divide(new BigDecimal(v), scale, RoundingMode.HALF_UP);
    return this;
  }

  /**
   * 除 带精度 四舍五入
   */
  public B div(int v, int scale) {
    this.result = result.divide(new BigDecimal(Integer.toString(v)), scale, RoundingMode.HALF_UP);
    return this;
  }

  /**
   * 除 带精度 四舍五入
   */
  public B div(float v, int scale) {
    this.result = result.divide(new BigDecimal(Float.toString(v)), scale, RoundingMode.HALF_UP);
    return this;
  }

  /**
   * 除 带精度 四舍五入
   */
  public B div(double v, int scale) {
    this.result = result.divide(new BigDecimal(Double.toString(v)), scale, RoundingMode.HALF_UP);
    return this;
  }

  /**
   * 除 带精度 四舍五入
   */
  public B div(Integer v, int scale) {
    this.result = result.divide(new BigDecimal(v), scale, RoundingMode.HALF_UP);
    return this;
  }

  /**
   * 除 带精度 四舍五入
   */
  public B div(Float v, int scale) {
    this.result = result.divide(new BigDecimal(v), scale, RoundingMode.HALF_UP);
    return this;
  }

  /**
   * 除 带精度 四舍五入
   */
  public B div(Double v, int scale) {
    this.result = result.divide(new BigDecimal(v), scale, RoundingMode.HALF_UP);
    return this;
  }

  /**
   * 除 带精度 保留小数模式
   */
  public B div(BigDecimal div, int scale, RoundingMode roundingMode) {
    this.result = result.divide(div, scale, roundingMode);
    return this;
  }

  /**
   * 精度
   */
  public B scale(int scale) {
    this.result = result.setScale(scale);
    return this;
  }

  /**
   * 精度 保留小数模式
   */
  public B scale(int scale, RoundingMode roundingMode) {
    this.result = result.setScale(scale, roundingMode);
    return this;
  }

  /**
   * 获取最终结果
   */
  public BigDecimal get() {
    return result;
  }
}

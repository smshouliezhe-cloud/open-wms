package com.yiruantong.common.security.utils;

import com.yiruantong.common.core.exception.UtilException;
import com.yiruantong.common.core.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 密码强度校验
 */
public class PasswordUtils {
  /**
   * 校验密码强度
   *
   * @param password 密码原文
   * @return 密码强度分值，总分100分
   */
  public static double calculateStrength(String password) {
    double score = 0;
    int minLength = 6;

    // 检查不能是连续的数字或字母
    if (!validateChar(password)) {
      throw new UtilException("不能包含连续的数字或字母，键盘连续字符也不可以！");
    }

    // 密码长度
    if (StringUtils.isEmpty(password) || password.length() < minLength) {
      throw new UtilException(StringUtils.format("密码至少{}位！", minLength));
    }

    // 根据密码长度增加分值，最高加20分，密码至少8位长度
    double lenScore = password.length() * 2.5;
    if (lenScore >= 20) {
      lenScore = 20;
    }
    score += lenScore;

    // 正则表达式：包含大写字母
    String consecutiveUppercaseRegex = ".*[A-Z].*";
    // 正则表达式：包含小写字母
    String consecutiveLowercaseRegex = ".*[a-z].*";
    // 正则表达式：包含数字
    String consecutiveNumbersRegex = ".*\\d+.*";
    // 正则表达式：包含特殊字符
    String specialCharactersRegex = ".*[\\W_].*";
    // 正则表达式：包含2个特殊字符
    String specialCharactersRegex2 = ".*(.*[\\W_].*[\\W_].*).*";

    // 包含大写字母
    if (password.matches(consecutiveUppercaseRegex)) {
      score += 20;
    }
    // 包含小写字母
    if (password.matches(consecutiveLowercaseRegex)) {
      score += 20;
    }

    // 包含数字
    if (password.matches(consecutiveNumbersRegex)) {
      score += 20;
    }

    // 密码包含至少两个特殊字符
    if (password.matches(specialCharactersRegex2)) {
      score += 20;
    } else if (password.matches(specialCharactersRegex)) {
      // 包含一个特殊字符
      score += 10;
    }

    return score;
  }

  private static List<String> getCharAll() {
    //不能连续字符（如123、abc）连续3位或3位以上
    /*
      键盘字符表(小写)
      非shift键盘字符表
     */
    List<List<String>> charTable1 = new ArrayList<>();
    charTable1.add(List.of("`", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "-", "=", ""));
    charTable1.add(List.of("", "q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "[", "]", "\\"));
    charTable1.add(List.of("", "a", "s", "d", "f", "g", "h", "j", "k", "l", ";", "\"", "", ""));
    charTable1.add(List.of("", "z", "x", "c", "v", "b", "n", "m", ",", ".", "/", "", "", ""));

    /*
      shift键盘的字符表
     */
    List<List<String>> charTable2 = new ArrayList<>();
    charTable2.add(List.of("~", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "_", "+", ""));
    charTable2.add(List.of("", "q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "{", "}", "|"));
    charTable2.add(List.of("", "a", "s", "d", "f", "g", "h", "j", "k", "l", ":", "\"", "", ""));
    charTable2.add(List.of("", "z", "x", "c", "v", "b", "n", "m", "<", ">", "?", "", "", ""));

    // 所有键盘组合值
    List<String> charAll = new ArrayList<>();
    // 横向所有的值(正序和反序)-
    charTable1.addAll(charTable2);
    charTable1.forEach((item) -> {
      String str = StringUtils.join(item, "");
      charAll.add(str);
      charAll.add(StringUtils.reverse(str));
    });

    // 反斜杠纵向所有的值(正序和反序)\
    for (var i = 0; i < charTable1.get(0).size(); i++) {
      List<String> sb1 = new ArrayList<>();
      List<String> sb2 = new ArrayList<>();
      int finalI = i;
      charTable1.forEach((item) -> {
        sb1.add(item.get(finalI));
      });
      int finalI1 = i;
      charTable2.forEach((item) -> {
        sb2.add(item.get(finalI1));
      });
      // 去除长度小于3的值
      if (StringUtils.join(sb1, "").length() >= 3) {
        charAll.add(StringUtils.join(sb1, ""));
        charAll.add(StringUtils.reverse(StringUtils.join(sb1, "")));
      }
      if (StringUtils.join(sb1, "").length() >= 3) {
        charAll.add(StringUtils.join(sb2, ""));
        charAll.add(StringUtils.reverse(StringUtils.join(sb2, "")));
      }
    }

    // 正斜杠纵向所有的值(正序和反序)/
    for (var i = 0; i < charTable1.get(0).size(); i++) {
      List<String> sb1 = new ArrayList<>();
      List<String> sb2 = new ArrayList<>();
      AtomicInteger index = new AtomicInteger();
      int finalI = i;
      charTable1.forEach((item) -> {
        int currentIndex = index.getAndIncrement();
        if (finalI - currentIndex >= 0) {
          sb1.add(item.get(finalI - currentIndex));
        }
      });
      index.set(0);
      int finalI1 = i;
      charTable2.forEach((item) -> {
        int currentIndex = index.getAndIncrement();
        if (finalI1 - currentIndex >= 0) sb2.add(item.get(finalI1 - currentIndex));
      });
      // 去除长度小于3的值
      if (StringUtils.join(sb1, "").length() >= 3) {
        charAll.add(StringUtils.join(sb1, ""));
        charAll.add(StringUtils.reverse(StringUtils.join(sb1, "")));
      }
      if (StringUtils.join(sb1, "").length() >= 3) {
        charAll.add(StringUtils.join(sb2, ""));
        charAll.add(StringUtils.reverse(StringUtils.join(sb2, "")));
      }
    }
    charAll.add("abcdefghijklmnopqrstuvwxyz");
    return charAll;
  }

  // 校验键盘连续性和连续重复
  private static boolean validateChar(String val) {
    List<String> charAll = getCharAll();
    String password = val.toLowerCase();
    // 密码3位比对
    for (var i = 0; i < password.length() - 2; i++) {
      String n1 = StringUtils.substring(password, i, i + 1);
      String n2 = StringUtils.substring(password, i + 1, i + 2);
      String n3 = StringUtils.substring(password, i + 2, i + 3);
      // 判断重复字符
      if (Objects.equals(n1, n2) && Objects.equals(n1, n3)) {
        return false;
        // 判断键盘连续字符
      } else if (
        charAll.stream().anyMatch((item) -> item.contains(n1 + n2 + n3))
      ) {
        return false;
      }
    }
    return true;
  }
}

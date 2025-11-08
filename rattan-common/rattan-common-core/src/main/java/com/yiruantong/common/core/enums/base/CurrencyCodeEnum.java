package com.yiruantong.common.core.enums.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 币种枚举
 */
@Getter
@AllArgsConstructor
public enum CurrencyCodeEnum {
  USD("USD", "$", "United States Dollar", "美元"),
  EUR("EUR", "€", "Euro", "欧元"),
  JPY("JPY", "¥", "Japanese Yen", "日元"),
  GBP("GBP", "£", "British Pound Sterling", "英镑"),
  AUD("AUD", "$", "Australian Dollar", "澳元"), // 注意：澳元也使用$符号，但通常有一个不同的前缀或上下文来区分
  CAD("CAD", "$", "Canadian Dollar", "加元"),   // 同上，加元也使用$符号
  CHF("CHF", "CHF", "Swiss Franc", "瑞士法郎"), // 注意：CHF在这里既是缩写也是符号，因为瑞士法郎没有特定的广泛认可的符号（除了缩写本身）
  CNY("CNY", "¥", "Chinese Yuan", "人民币"),    // 人民币在中文环境中也常使用¥符号，尽管它与日元符号相同，但上下文会区分它们
  ;


  private final String id;
  private final String code;
  private final String enName;
  private final String cnName;

  public static CurrencyCodeEnum matchingEnum(String id) {
    for (CurrencyCodeEnum i : values()) {
      if (i.getId().equals(id)) {
        return i;
      }
    }
    return null;
  }
}

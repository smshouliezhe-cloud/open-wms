package com.yiruantong.common.core.exception.base;

import java.io.Serial;

/**
 * 通用信息异常类
 *
 * @author rattan
 */
public class CommonException extends BaseException {

  @Serial
  private static final long serialVersionUID = 1L;

  public CommonException(String code, Object... args) {
    super("common", code, args, null);
  }
}

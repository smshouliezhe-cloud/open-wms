package com.yiruantong.common.core.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.common.core.utils.MessageUtils;
import com.yiruantong.common.core.utils.StringUtils;

import java.io.Serial;

/**
 * 业务异常
 *
 * @author rattan
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public final class ServiceException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 错误码
   */
  private Integer code;

  /**
   * 错误提示
   */
  private String message;

  /**
   * 错误明细，内部调试错误
   */
  private String detailMessage;

  /**
   * 错误码对应的参数
   */
  private Object[] args;

  public ServiceException(String message) {
    this.message = message;
  }

  public ServiceException(String message, Object[] args) {
    this.message = message;
    this.args = args;
  }

  public ServiceException(String message, Integer code) {
    this.message = message;
    this.code = code;
  }

  public String getDetailMessage() {
    return detailMessage;
  }

  public Integer getCode() {
    return code;
  }

  public ServiceException setMessage(String message) {
    this.message = message;
    return this;
  }

  public ServiceException setDetailMessage(String detailMessage) {
    this.detailMessage = detailMessage;
    return this;
  }

  @Override
  public String getMessage() {
    String msg = null;
    if (!StringUtils.isEmpty(this.message)) {
      msg = MessageUtils.message(this.message, args);
    }
    if (msg == null) {
      msg = this.message;
    }
    return msg;
  }
}

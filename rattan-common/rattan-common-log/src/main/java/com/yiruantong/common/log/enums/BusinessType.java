package com.yiruantong.common.log.enums;

/**
 * 业务操作类型
 *
 * @author rattan
 */
public enum BusinessType {
  /**
   * 其它
   */
  OTHER,

  /**
   * 新增
   */
  INSERT,

  /**
   * 修改
   */
  UPDATE,

  /**
   * 删除
   */
  DELETE,

  /**
   * 授权
   */
  GRANT,

  /**
   * 导出
   */
  EXPORT,

  /**
   * 导入
   */
  IMPORT,

  /**
   * 强退
   */
  FORCE,

  /**
   * 生成代码
   */
  GENCODE,

  /**
   * 保存
   */
  SAVE,

  /**
   * 审核
   */
  AUDIT,

  /**
   * 反审核
   */
  reAudit,

  /**
   * 分拣
   */
  SORTING,

  /**
   * 终止
   */
  STOP,

  /**
   * 开启
   */
  OPEN,

  /**
   * 清空数据
   */
  CLEAN,

  /**
   * 用户申请APP
   */
  USER_APP,

  /**
   * 修改手机号
   */
  MODIFY_USER_PHONE_NUMBER,

  /**
   * 修改email
   */
  MODIFY_USER_EMAIL,

  /**
   * 推送数据
   */
  PUSH,

  /**
   * 取消出库单
   */
  CANCEL,

  /**
   * 取消出库单
   */
  RETURN,

  /**
   * 回更接口
   */
  RETURN_RESULT,
}

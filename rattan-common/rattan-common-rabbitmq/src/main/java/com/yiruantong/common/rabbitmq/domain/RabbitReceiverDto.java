package com.yiruantong.common.rabbitmq.domain;

import cn.hutool.core.util.ObjectUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.system.RabbitmqActionEnum;
import com.yiruantong.common.core.enums.system.RabbitmqTypeEnum;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * rabbitmq数据结构
 */
@Data
@NoArgsConstructor
public class RabbitReceiverDto implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 数据类型
   */
  private RabbitmqTypeEnum rabbitmqType;
  /**
   * websocket sessionId
   */
  private String webSocketSessionId;
  /**
   * 模块ID
   */
  private Long menuId;
  /**
   * 模块名称
   */
  private String menuName;
  /**
   * 单据ID
   */
  private Long billId;
  /**
   * 单据号
   */
  private String billCode;
  /**
   * 消息对了任务单ID，注意：不是审核流程任务ID
   */
  private Long taskId;
  /**
   * 用户信息
   */
  private LoginUser loginUser;
  /**
   * 创建时间
   */
  private Date createDate;
  /**
   * 来源ID
   */
  private String sourceId;
  /**
   * 来源单号
   */
  private String sourceCode;
  /**
   * 根单据ID
   */
  private Long rootId;
  /**
   * 根单据号
   */
  private String rootCode;
  /**
   * 执行动作
   */
  private RabbitmqActionEnum action;
  /**
   * 任务名称
   */
  private String taskName;
  /**
   * 备注
   */
  private String remark;
  /**
   * 部署ID
   */
  private String deployId;
  /**
   * 其他参数
   */
  private Map<String, Object> otherField;
  /**
   * 执行步骤
   */
  private List<DoStepDto> doStepList;

  /**
   * 是否为最后一步
   *
   * @return
   */
  public boolean isLastStep() {
    if (ObjectUtil.isNull(this.doStepList)) {
      return true;
    }

    return this.doStepList.get(this.doStepList.size() - 1).getRabbitmqType() == this.rabbitmqType;
  }
}

package com.yiruantong.system.service.task;

import com.yiruantong.system.domain.task.WcsTask;
import com.yiruantong.system.domain.task.bo.WcsTaskBo;
import com.yiruantong.system.domain.task.vo.WcsTaskVo;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

/**
 * WCS接口Service接口
 *
 * @author YRT
 * @date 2024-10-04
 */
public interface IWcsTaskService extends IServicePlus<WcsTask, WcsTaskVo, WcsTaskBo> {

  /**
   * 推送数据
   *
   * @param ids ID集合
   * @return R
   */
  R<Map<String, Object>> push(@PathVariable List<Long> ids);
}

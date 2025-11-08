package com.yiruantong.basic.service.tms;

import com.yiruantong.basic.domain.tms.BaseDriver;
import com.yiruantong.basic.domain.tms.bo.BaseDriverBo;
import com.yiruantong.basic.domain.tms.vo.BaseDriverVo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.List;
import java.util.Map;

/**
 * 司机管理Service接口
 *
 * @author YRT
 * @date 2023-11-03
 */
public interface IBaseDriverService extends IServicePlus<BaseDriver, BaseDriverVo, BaseDriverBo> {
  /**
   * 获取司机下拉框信息
   *
   * @param map 查询条件
   * @return 返回保存结果
   */
  List<Map<String, Object>> getDriverNameList(Map<String, Object> map);


  /**
   * 通用 - 查询司机
   *
   * @param map 查询条件
   * @return 返回查询结果
   */
  List<Map<String, Object>> getList(Map<String, Object> map);

  /**
   * 重置用户密码
   *
   * @param userId   用户ID
   * @param password 密码
   * @return 结果
   */
  int resetUserPwd(Long userId, String password);

  BaseDriver getByName(String driverName);
}

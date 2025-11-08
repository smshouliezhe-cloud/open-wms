package com.yiruantong.composite.service.basic;

public interface IBaseStorageAreaCompositeService {
  /**
   * 删除前事件
   * @param Ids
   * @return
   */
  int deleteByIds(Long[] Ids);
}

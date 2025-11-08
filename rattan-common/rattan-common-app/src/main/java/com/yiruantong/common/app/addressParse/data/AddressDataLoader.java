package com.yiruantong.common.app.addressParse.data;


import com.yiruantong.common.app.addressParse.assets.Address;

import java.util.List;

/**
 * 地址数据加载器
 *
 * @author I6view
 */
public interface AddressDataLoader {

  /**
   * 加载地址数据
   *
   * @return Address List
   */
  List<Address> loadData();

}

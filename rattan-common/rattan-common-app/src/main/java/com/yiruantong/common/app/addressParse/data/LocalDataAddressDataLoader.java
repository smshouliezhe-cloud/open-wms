package com.yiruantong.common.app.addressParse.data;


import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;
import com.yiruantong.common.app.addressParse.assets.Address;

import java.nio.charset.StandardCharsets;
import java.util.List;


/**
 * 加载本地地址数据->resources/areaData.json
 *
 * @author I6view
 */
public class LocalDataAddressDataLoader implements AddressDataLoader {

  private final List<Address> addressList;

  public LocalDataAddressDataLoader() {
    this.addressList = JSONUtil.toList(ResourceUtil.readStr("areaData.json", StandardCharsets.UTF_8), Address.class);
  }

  @Override
  public List<Address> loadData() {
    return addressList;
  }

}

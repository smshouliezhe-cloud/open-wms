package com.yiruantong.common.app.addressParse;


import com.yiruantong.common.app.addressParse.assets.AddressInfo;
import com.yiruantong.common.app.addressParse.assets.UserInfo;
import com.yiruantong.common.app.addressParse.data.AddressDataLoader;
import com.yiruantong.common.app.addressParse.data.LocalDataAddressDataLoader;

import java.io.Serializable;

/**
 * SmartAgent
 *
 * @author I6view
 */
public class SmartAgent implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = -6937633457650992278L;
  private static volatile AddressDataLoader addressDataLoader = null;
  private final String userAddressString;
  private final SmartParse smartParse;

  public SmartAgent(String userAddressString) {
    this.userAddressString = userAddressString;
    if (addressDataLoader == null) {
      synchronized (SmartAgent.class) {
        if (addressDataLoader == null) {
          addressDataLoader = new LocalDataAddressDataLoader();
        }
      }
    }
    smartParse = new SmartParse(addressDataLoader);
  }

  /**
   * SmartAgent
   *
   * @param userAddressString userAddressString
   * @return SmartAgent
   */
  public static SmartAgent parseUserAddressString(String userAddressString) {
    return new SmartAgent(userAddressString);
  }

  /**
   * addressInfo
   *
   * @return addressInfo
   */
  public AddressInfo getAddressInfo() {
    return smartParse.parseAddressInfo(userAddressString);
  }

  /**
   * userInfo
   *
   * @return userInfo
   */
  public UserInfo getUserInfo() {
    return smartParse.parseUserInfo(userAddressString);
  }
}

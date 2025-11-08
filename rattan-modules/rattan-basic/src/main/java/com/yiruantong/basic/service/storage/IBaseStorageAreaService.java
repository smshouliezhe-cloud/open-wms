package com.yiruantong.basic.service.storage;

import com.yiruantong.basic.domain.storage.BaseStorageArea;
import com.yiruantong.basic.domain.storage.bo.BaseStorageAreaBo;
import com.yiruantong.basic.domain.storage.bo.SvgBo;
import com.yiruantong.basic.domain.storage.vo.BaseStorageAreaVo;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.List;
import java.util.Map;

/**
 * 库区管理Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-18
 */
public interface IBaseStorageAreaService extends IServicePlus<BaseStorageArea, BaseStorageAreaVo, BaseStorageAreaBo> {
  R<List<String>> getAreaCodes(Map<String, Object> map);

  R<List<BaseStorageArea>> getAreaList(Map<String, Object> map);

  BaseStorageArea getStorageAreaInfo(Long storageId, String areaCode);

  BaseStorageArea getByCode(String areaCode);


  /**
   * 加载货架数据
   *
   * @param storageId 仓库ID
   * @param areaCode  库区编号
   * @return 库区信息
   */
  BaseStorageArea loadShelveList(Long storageId, String areaCode);

  R<Map<String, Object>> add(BaseStorageAreaBo bo);

  R<Void> saveSvg(SvgBo svg);
}

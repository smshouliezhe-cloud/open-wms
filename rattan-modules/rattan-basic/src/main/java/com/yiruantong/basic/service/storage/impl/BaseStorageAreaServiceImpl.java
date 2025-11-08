package com.yiruantong.basic.service.storage.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.storage.BaseStorage;
import com.yiruantong.basic.domain.storage.BaseStorageArea;
import com.yiruantong.basic.domain.storage.bo.BaseStorageAreaBo;
import com.yiruantong.basic.domain.storage.bo.SvgBo;
import com.yiruantong.basic.domain.storage.vo.BaseStorageAreaVo;
import com.yiruantong.basic.mapper.storage.BaseStorageAreaMapper;
import com.yiruantong.basic.service.storage.IBaseStorageAreaService;
import com.yiruantong.basic.service.storage.IBaseStorageService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 库区管理Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-18
 */
@RequiredArgsConstructor
@Service
public class BaseStorageAreaServiceImpl extends ServiceImplPlus<BaseStorageAreaMapper, BaseStorageArea, BaseStorageAreaVo, BaseStorageAreaBo> implements IBaseStorageAreaService {
  private final IBaseStorageService baseStorageService;

  @Override
  public R<List<String>> getAreaCodes(Map<String, Object> map) {
    String storageName = Convert.toStr(map.get("storageName"));


    LambdaQueryWrapper<BaseStorageArea> baseStorageAreaLqw = new LambdaQueryWrapper<>();
    baseStorageAreaLqw.select(BaseStorageArea::getAreaCode)
      .eq(BaseStorageArea::getStorageName, storageName)
      .isNotNull(BaseStorageArea::getAreaCode);
    List<BaseStorageArea> storageAreas = this.getBaseMapper().selectList(baseStorageAreaLqw);
    storageAreas = storageAreas.stream().distinct().toList();

    return R.ok(storageAreas.stream().map(BaseStorageArea::getAreaCode).toList());
  }

  public R<List<BaseStorageArea>> getAreaList(Map<String, Object> map) {
    Long storageId = Convert.toLong(map.get("storageId"));

    LambdaQueryWrapper<BaseStorageArea> baseStorageAreaLqw = new LambdaQueryWrapper<>();
    baseStorageAreaLqw.select(BaseStorageArea::getAreaCode)
      .eq(BaseStorageArea::getStorageId, storageId)
      .isNotNull(BaseStorageArea::getAreaCode);
    List<BaseStorageArea> storageAreas = this.getBaseMapper().selectList(baseStorageAreaLqw);
    storageAreas = storageAreas.stream().distinct().toList();

    return R.ok(storageAreas);
  }

  @Override
  public BaseStorageArea getStorageAreaInfo(Long storageId, String areaCode) {
    LambdaQueryWrapper<BaseStorageArea> baseStorageArea = new LambdaQueryWrapper<>();
    baseStorageArea.eq(BaseStorageArea::getStorageId, storageId)
      .eq(BaseStorageArea::getAreaCode, areaCode)
      .isNotNull(BaseStorageArea::getAreaCode);
    return this.getOne(baseStorageArea);

  }

  @Override
  public BaseStorageArea getByCode(String areaCode) {
    LambdaQueryWrapper<BaseStorageArea> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(BaseStorageArea::getAreaCode, areaCode);
    return this.getOnly(lambdaQueryWrapper);
  }

  @Override
  public BaseStorageArea loadShelveList(Long storageId, String areaCode) {
    LambdaQueryWrapper<BaseStorageArea> baseStorageArea = new LambdaQueryWrapper<>();
    baseStorageArea.eq(BaseStorageArea::getStorageId, storageId)
      .eq(BaseStorageArea::getAreaCode, areaCode)
      .isNotNull(BaseStorageArea::getAreaCode);

    return this.getOne(baseStorageArea);
  }

  //#region add
  @Override
  public R<Map<String, Object>> add(BaseStorageAreaBo bo) {
    try {
      BaseStorage baseStorage = baseStorageService.getByName(bo.getStorageName());
      if (ObjectUtil.isNull(baseStorage)) {
        return R.fail("仓库信息不存在，请先添加仓库信息");
      }

      BaseStorageArea baseStorageArea = this.getByCode(bo.getAreaCode());

      if (ObjectUtil.isNotNull(baseStorageArea)) {
        BeanUtil.copyProperties(bo, baseStorageArea, new CopyOptions().setIgnoreProperties("storageAreaId"));
        this.updateByBo(bo);

        Map<String, Object> result = new HashMap<>();
        result.put("storageAreaId", baseStorageArea.getStorageAreaId());
        result.put("areaCode", baseStorageArea.getAreaCode());
        result.put("storageId", baseStorageArea.getStorageId());
        result.put("storageName", baseStorageArea.getStorageName());

        return R.ok("库区信息更新成功", result);
      }

      baseStorageArea = new BaseStorageArea();
      BeanUtil.copyProperties(bo, baseStorageArea, new CopyOptions().setIgnoreProperties("storageAreaId"));
      baseStorageArea.setStorageId(baseStorage.getStorageId());
      baseStorageArea.setStorageName(baseStorage.getStorageName());
      this.save(baseStorageArea);

      Map<String, Object> result = new HashMap<>();
      result.put("storageAreaId", baseStorageArea.getStorageAreaId());
      result.put("areaCode", baseStorageArea.getAreaCode());
      result.put("storageId", baseStorageArea.getStorageId());
      result.put("storageName", baseStorageArea.getStorageName());

      return R.ok("库区信息保存成功", result);
    } catch (Exception error) {
      return R.fail("库区信息保存失败，" + error.getMessage());
    }
  }

  @Override
  public R<Void> saveSvg(SvgBo svg) {
    BaseStorageArea baseStorageArea = this.loadShelveList(svg.getStorageId(), svg.getAreaCode());
    if (ObjectUtil.isNull(baseStorageArea)) {
      return R.fail("未找到" + svg.getAreaCode() + "库区");
    }
    LambdaUpdateWrapper<BaseStorageArea> updateWrapper = new LambdaUpdateWrapper<>();
    updateWrapper.set(BaseStorageArea::getSvgUrl, svg.getUrl())
      .eq(BaseStorageArea::getStorageAreaId, baseStorageArea.getStorageAreaId());
    this.update(updateWrapper);
    return R.ok("上传成功");
  }
  //#endregion
}

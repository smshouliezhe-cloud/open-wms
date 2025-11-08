package com.yiruantong.system.service.dataHandler.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yiruantong.system.domain.dataHandler.SysExportVueData;
import com.yiruantong.system.domain.dataHandler.bo.SysExportVueDataBo;
import com.yiruantong.system.domain.dataHandler.vo.SysExportVueDataVo;
import com.yiruantong.system.mapper.dataHandler.SysExportVueDataMapper;
import com.yiruantong.system.service.dataHandler.ISysExportVueDataService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.utils.NumberUtils;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 导出视图设置Service业务层处理
 *
 * @author YRT
 * @date 2023-08-05
 */
@RequiredArgsConstructor
@Service
public class SysExportVueDataServiceImpl extends ServiceImplPlus<SysExportVueDataMapper, SysExportVueData, SysExportVueDataVo, SysExportVueDataBo> implements ISysExportVueDataService {

  @Override
  public R<List<SysExportVueDataVo>> selectByExportId(Map<String, Object> map) {
    Long exportId = Convert.toLong(map.get("exportId"));

    LambdaQueryWrapper<SysExportVueData> vueDataLambdaQueryWrapper = new LambdaQueryWrapper<>();
    vueDataLambdaQueryWrapper.eq(SysExportVueData::getExportId, exportId);
    var dataList = this.selectList(vueDataLambdaQueryWrapper);

    return R.ok(dataList);
  }

  @Override
  public R<Void> updateTitle(Map<String, Object> map) {
    String title = Convert.toStr(map.get("title"));
    Long vueDataId = Convert.toLong(map.get("vueDataId"));
    Assert.isFalse(ObjectUtil.isEmpty(title), "修改名称不能为空");
    Assert.isFalse(!NumberUtils.isPositiveInteger(vueDataId), "修改ID必须大于0");

    LambdaUpdateWrapper<SysExportVueData> exportLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    exportLambdaUpdateWrapper.set(SysExportVueData::getVueDataName, title)
      .eq(SysExportVueData::getVueDataId, vueDataId);
    var result = this.update(exportLambdaUpdateWrapper);

    return result ? R.ok() : R.fail();
  }

  @Override
  public R<Map<String, Object>> saveData(Map<String, Object> map) {
    Long exportId = Convert.toLong(map.get("exportId"));
    Long vueDataId = Convert.toLong(map.get("vueDataId"));
    String label = Convert.toStr(map.get("label"));
    String vueData = Convert.toStr(map.get("vueData"));

    Map<String, Object> returnMap = new HashMap<>();
    try {
      var tempInfo = this.getBaseMapper().selectById(vueDataId);
      if (ObjectUtil.isNotEmpty(tempInfo)) {
        LambdaUpdateWrapper<SysExportVueData> dataLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        dataLambdaUpdateWrapper.set(SysExportVueData::getVueData, vueData)
          .set(SysExportVueData::getVueDataName, label)
          .eq(SysExportVueData::getExportId, exportId);
        this.update(dataLambdaUpdateWrapper);

        returnMap.put("vueDataId", tempInfo.getVueDataId());
        return R.ok("修改成功", returnMap);
      } else {
        tempInfo = new SysExportVueData();
        tempInfo.setExportId(exportId);
        tempInfo.setEnable(EnableEnum.ENABLE.getId());
        tempInfo.setVueData(vueData);
        this.save(tempInfo);

        returnMap.put("vueDataId", tempInfo.getVueDataId());
        return R.ok("新增模板成功", returnMap);
      }
    } catch (Exception exception) {
      return R.fail();
    }
  }

}

package com.yiruantong.basic.service.storage.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yiruantong.common.core.enums.base.PlateTypeEnum;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.storage.BasePlate;
import com.yiruantong.basic.domain.storage.api.ApiBasePlateBo;
import com.yiruantong.basic.domain.storage.bo.BasePlateBo;
import com.yiruantong.basic.domain.storage.bo.BasePlateListBo;
import com.yiruantong.basic.domain.storage.vo.BasePlateVo;
import com.yiruantong.basic.mapper.storage.BasePlateMapper;
import com.yiruantong.basic.service.storage.IBasePlateService;
import com.yiruantong.common.core.constant.TenantConstants;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.enums.base.UsingEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.DateUtils;
import com.yiruantong.common.core.utils.SpringUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.system.domain.dataHandler.SysParamValue;
import com.yiruantong.system.domain.dataHandler.vo.SysParamValueVo;
import com.yiruantong.system.service.dataHandler.ISysParamValueService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 容器管理Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-18
 */
@RequiredArgsConstructor
@Service
public class BasePlateServiceImpl extends ServiceImplPlus<BasePlateMapper, BasePlate, BasePlateVo, BasePlateBo> implements IBasePlateService {
  @Override
  public void updatePlateStatus(Long storageId, String plateCode, UsingEnum usingEnum) {
    LambdaUpdateWrapper<BasePlate> plateLambdaQueryWrapper = new LambdaUpdateWrapper<>();
    plateLambdaQueryWrapper
      .set(BasePlate::getIsUsing, usingEnum.getId())
      .eq(BasePlate::getStorageId, storageId)
      .eq(BasePlate::getPlateCode, plateCode);

    this.update(plateLambdaQueryWrapper);
  }

  //#region 生成容器号
  @Override
  public R<Void> createPlantCode(BasePlateListBo basePlateListBo) {
    try {
      String msg = "";

      Long storageId = basePlateListBo.getStorageId();
      String storageName = basePlateListBo.getStorageName();
      String plateType = basePlateListBo.getPlateType();
      String codePrefix = basePlateListBo.getCodePrefix();
      Long generate = basePlateListBo.getGenerate();
      String plateSpec = basePlateListBo.getPlateSpec();


      LambdaQueryWrapper<BasePlate> queryWrapper = new LambdaQueryWrapper<>();
      queryWrapper.like(StringUtils.isNotEmpty(codePrefix), BasePlate::getPlateCode, codePrefix)
        .orderByDesc(BasePlate::getPlateId); // 关键词对编号和名称模糊查询
      queryWrapper.last("limit 1");

      var plateInfo = this.selectOne(queryWrapper);

      int lastCode = 0; // 最后一条编码
      if (ObjectUtil.isNotNull(plateInfo)) {
        lastCode = Convert.toInt(plateInfo.getPlateCode().substring(2));
      }
      for (var index = 0; index < generate; index++) {
        lastCode++;
        var newPlateInfo = new BasePlate();
        var plateCode = TenantConstants.DEFAULT_TENANT_ID + lastCode;
        newPlateInfo.setPlateCode(codePrefix + plateCode.substring(plateCode.length() - 6));
        newPlateInfo.setStorageId(storageId);
        newPlateInfo.setStorageName(storageName);
        newPlateInfo.setPlateType(plateType);
        newPlateInfo.setPlateSpec(plateSpec);
        newPlateInfo.setIsUsing(Convert.toLong(EnableEnum.DISABLE.getId()));
        newPlateInfo.setEnable(Convert.toLong(EnableEnum.ENABLE.getId()));
        newPlateInfo.setCreateBy(LoginHelper.getUserId());
        newPlateInfo.setCreateByName(LoginHelper.getUsername());
        newPlateInfo.setCreateTime(DateUtils.getNowDate());
        this.save(newPlateInfo);
      }

      return R.ok(msg + "容器号生成成功！");
    } catch (Exception e) {
      return R.fail(e.getMessage());
    }
  }

  //#endregion

  //#region 生成容器规格
  @Override
  public List<Map<String, Object>> getPlateSpec(Map<String, Object> map) {
    String plateType = Convert.toStr(map.get("plateType"));

    LambdaQueryWrapper<BasePlate> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(BasePlate::getEnable, EnableEnum.ENABLE.getId()) // 是否可用
      .eq(BasePlate::getPlateType, plateType)
      .isNotNull(BasePlate::getPlateSpec); // 规格不为空

    try {
      List<String> fields = Arrays.asList("plateId", "plateSpec"); // 查询默认字段
      // 自定义查询字段
      if (fields.size() > 0) {
        List<String> finalFields = fields;
        queryWrapper.select(BasePlate.class, s -> finalFields.contains(s.getProperty()));
      }
      var dataList = this.baseMapper.selectMaps(queryWrapper);
      return dataList;
    } catch (Exception error) {
      var msg = "异常错误信息：" + error.getCause();
      throw new ServiceException(msg);
    }
  }

  //#endregion

  //#region 生成容器类型
  @Override
  public List<SysParamValueVo> getPlateType(Map<String, Object> map) {
    ISysParamValueService bean = SpringUtils.getBean(ISysParamValueService.class);

    LambdaQueryWrapper<SysParamValue> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(SysParamValue::getTypeId, 947); // typeid=947

    try {
      var dataList = bean.selectList(queryWrapper);
      return dataList;
    } catch (Exception error) {
      var msg = "异常错误信息：" + error.getCause();
      throw new ServiceException(msg);
    }
  }

  //#endregion

  //#region 查询容器重量体积
  @Override
  public BasePlate getWeightCube(Map<String, Object> map) {
    Long plateId = Convert.toLong(map.get("plateId"));

    LambdaQueryWrapper<BasePlate> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(BasePlate::getEnable, EnableEnum.ENABLE.getId()) // 是否可用
      .eq(BasePlate::getPlateId, plateId);

    try {
      var dataInfo = this.getOne(queryWrapper);
      return dataInfo;
    } catch (Exception error) {
      var msg = "异常错误信息：" + error.getCause();
      throw new ServiceException(msg);
    }
  }
  //#endregion

  /**
   * 根据容器名称获取容器信息
   *
   * @param plateName 货位名称
   * @return 返回货位信息
   */
  @Override
  public BasePlate getByName(String plateName) {
    LambdaQueryWrapper<BasePlate> positionLambdaQueryWrapper = new LambdaQueryWrapper<>();
    positionLambdaQueryWrapper.eq(BasePlate::getPlateName, plateName)
      .eq(BasePlate::getEnable, EnableEnum.ENABLE.getId());
    return this.getOnly(positionLambdaQueryWrapper);
  }

  /**
   * 新增数据
   */
  @Override
  public R<Map<String, Object>> add(ApiBasePlateBo bo) {

    BasePlate basePlate = this.getByName(bo.getPlateName());

    if (ObjectUtil.isNotNull(basePlate)) {
      BeanUtil.copyProperties(bo, basePlate, new CopyOptions().setIgnoreProperties("plateId"));
      this.saveOrUpdate(basePlate);

      Map<String, Object> result = new HashMap<>();
      result.put("plateId", basePlate.getPlateId());
      result.put("plateName", basePlate.getPlateName());

      return R.ok("容器信息更新成功", result);
    }

    basePlate = new BasePlate();
    BeanUtil.copyProperties(bo, basePlate, new CopyOptions().setIgnoreProperties("plateId"));
    this.save(basePlate);

    Map<String, Object> result = new HashMap<>();
    result.put("plateId", basePlate.getPlateId());
    result.put("plateName", basePlate.getPlateName());

    return R.ok("容器信息添加成功", result);

  }
  //#endregion


  //#region  LPN 获取容器信息
  @Override
  public List<BasePlate> getList(Map<String, Object> map) {
    String plateCode = Convert.toStr(map.get("plateCode"));
    LambdaQueryWrapper<BasePlate> basePlateLambdaQueryWrapper = new LambdaQueryWrapper<>();
    basePlateLambdaQueryWrapper.select(BasePlate::getPlateCode, BasePlate::getIsUsing, BasePlate::getPlateId)
      .like(BasePlate::getPlateCode, ObjectUtil.isNotEmpty(plateCode) ? plateCode : "")
      .eq(BasePlate::getIsUsing, UsingEnum.NO_USE.getId())
      .eq(BasePlate::getPlateType, PlateTypeEnum.TURNOVER_BOX.getName())
      .eq(BasePlate::getEnable, EnableEnum.ENABLE.getId())
      .last("limit 500");

    return this.getBaseMapper().selectList(basePlateLambdaQueryWrapper);
  }
  //#endregion
}

package com.yiruantong.basic.service.storage.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.storage.BasePosition;
import com.yiruantong.basic.domain.storage.BaseStorage;
import com.yiruantong.basic.domain.storage.bo.BaseStorageBo;
import com.yiruantong.basic.domain.storage.vo.BaseStorageVo;
import com.yiruantong.basic.mapper.storage.BaseStorageMapper;
import com.yiruantong.basic.service.storage.IBasePositionService;
import com.yiruantong.basic.service.storage.IBaseStorageService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.enums.base.PositionTypeEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.SpringUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.domain.bo.SaveEditorBo;
import com.yiruantong.common.mybatis.core.domain.vo.EditorVo;
import com.yiruantong.common.mybatis.core.service.IDataAuthService;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.satoken.utils.LoginHelper;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 仓库管理Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-18
 */
@RequiredArgsConstructor
@Service
public class BaseStorageServiceImpl extends ServiceImplPlus<BaseStorageMapper, BaseStorage, BaseStorageVo, BaseStorageBo> implements IBaseStorageService {
  //  private final IBasePositionService basePositionService;
  private final IDataAuthService dataAuthService;

  //#region 通用 - 查询仓库

  /**
   * 通用 - 查询仓库
   *
   * @param map 查询条件
   * @return 返回查询结果
   */
  @Override
  public List<Map<String, Object>> getList(Map<String, Object> map) {
    Integer take = Optional.ofNullable(map).map(m -> Convert.toInt(m.get("take"))).orElse(200); // 查询top N，如果为空，默认200
    String name = Convert.toStr(map.get("name"));
    // amis下拉框搜索
    if (StringUtils.isEmpty(name)) {
      name = Convert.toStr(map.get("term"));
    }

    String searchFields = Convert.toStr(map.get("searchFields"));
    LoginUser loginUser = LoginHelper.getLoginUser();
    if (ObjectUtil.isNull(loginUser)) return null;

    QueryWrapper<BaseStorage> queryWrapper = new QueryWrapper<>();
    String finalName = name;
    queryWrapper.lambda().eq(BaseStorage::getEnable, EnableEnum.ENABLE.getId()) // 是否可用
      .and(StringUtils.isNotEmpty(name),
        a -> a.like(StringUtils.isNotEmpty(finalName), BaseStorage::getStorageCode, finalName)
          .or()
          .like(StringUtils.isNotEmpty(finalName), BaseStorage::getStorageName, finalName)
      ); // 关键词对编号和名称模糊查询

    try {
      List<String> fields = Arrays.asList("storageId", "storageCode", "storageName"); // 查询默认字段
      // 自定义查询字段
      if (StringUtils.isNotEmpty(searchFields)) {
        fields = List.of(StringUtils.split(searchFields, ",")); // 查询指定字段
      }
      if ("*".equals(searchFields)) {
        fields = new ArrayList<>(); // 清空，查询所有字段
      }

      // 自定义查询字段
      if (!fields.isEmpty()) {
        List<String> finalFields = fields;
        queryWrapper.select(BaseStorage.class, s -> finalFields.contains(s.getProperty()));
      }
      // 数据权限
      if (!loginUser.isAdministrator()) {
        dataAuthService.getStorageAuth(queryWrapper);
      }
      queryWrapper.lambda().orderByAsc(BaseStorage::getStorageId); // 排序
      queryWrapper.last("limit " + take); // top N

      return this.baseMapper.selectMaps(queryWrapper);
    } catch (Exception error) {
      var msg = "异常错误信息：" + error.getCause();
      throw new ServiceException(msg);
    }
  }


  //#region 保存后事件
  @Override
  public void afterSaveEditor(SaveEditorBo<BaseStorageBo> saveEditorBo, EditorVo<BaseStorageVo> editor) {
    IBasePositionService bean = SpringUtils.getBean(IBasePositionService.class);
    // 是否新建
    boolean isAdd = saveEditorBo.isAdd();
    if (isAdd) {
      BasePosition basePosition = new BasePosition();
      basePosition.setPositionName(PositionTypeEnum.UNLOADING.getName()); // 下架理货位
      basePosition.setPositionType(PositionTypeEnum.UNLOADING.getId()); // 下架理货位
      basePosition.setStorageId(editor.getMaster().getStorageId());
      basePosition.setStorageName(editor.getMaster().getStorageName());
      basePosition.setIsLocked(EnableEnum.DISABLE.getId());
      basePosition.setEnable(EnableEnum.ENABLE.getId());
      basePosition.setIsMixProduct(EnableEnum.ENABLE.getId());
      bean.saveOrUpdate(basePosition);

      BasePosition basePosition2 = new BasePosition();
      basePosition2.setPositionName(PositionTypeEnum.RECEIVING.getName()); // 收货位
      basePosition2.setPositionType(PositionTypeEnum.RECEIVING.getId()); // 收货位
      basePosition2.setStorageId(editor.getMaster().getStorageId());
      basePosition2.setStorageName(editor.getMaster().getStorageName());
      basePosition2.setIsLocked(EnableEnum.DISABLE.getId());
      basePosition2.setEnable(EnableEnum.ENABLE.getId());
      basePosition2.setIsMixProduct(EnableEnum.ENABLE.getId());
      bean.saveOrUpdate(basePosition2);

      BasePosition basePosition3 = new BasePosition();
      basePosition3.setPositionName(PositionTypeEnum.IN_TRANSIT.getName()); // 在途货位
      basePosition3.setPositionType(PositionTypeEnum.VIRTUAL.getId()); // 虚拟货位
      basePosition3.setStorageId(editor.getMaster().getStorageId());
      basePosition3.setStorageName(editor.getMaster().getStorageName());
      basePosition3.setIsLocked(EnableEnum.DISABLE.getId());
      basePosition3.setEnable(EnableEnum.ENABLE.getId());
      basePosition3.setIsMixProduct(EnableEnum.ENABLE.getId());
      bean.saveOrUpdate(basePosition3);
    }
  }
  //#endregion

  /**
   * 根据仓库名称，带出目的地值（所属网点）
   *
   * @param map 查询条件
   * @return 返回查询列表数据
   */
  @Override
  public BaseStorage changePlaceDestination(Map<String, Object> map) {
    String storageName = Convert.toStr(map.get("storageName"));

    LambdaQueryWrapper<BaseStorage> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(BaseStorage::getStorageName, storageName); // 仓库

    try {
      return this.getOne(queryWrapper);
    } catch (Exception error) {
      var msg = "异常错误信息：" + error.getCause();
      throw new ServiceException(msg);
    }
  }
  //#endregion

  //#region getByCode
  @Override
  public BaseStorage getByCode(String code) {
    LambdaQueryWrapper<BaseStorage> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(BaseStorage::getStorageCode, code);
    return this.getOnly(lambdaQueryWrapper);
  }
  //#endregion

  //#region getByName
  @Override
  public BaseStorage getByName(String name) {
    LambdaQueryWrapper<BaseStorage> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(BaseStorage::getStorageName, name);
    return this.getOnly(lambdaQueryWrapper);
  }
  //#endregion

  //#region add
  @Override
  public R<Map<String, Object>> add(BaseStorageBo bo) {
    BaseStorage baseStorage = this.getByCode(bo.getStorageCode());

    if (ObjectUtil.isNotNull(baseStorage)) {
      BeanUtil.copyProperties(bo, baseStorage, new CopyOptions().setIgnoreProperties("storageId"));
      this.updateByBo(bo);

      Map<String, Object> result = new HashMap<>();
      result.put("storageId", baseStorage.getStorageId());
      result.put("storageCode", baseStorage.getStorageCode());
      result.put("storageName", baseStorage.getStorageName());

      return R.ok("仓库信息更新成功", result);
    }

    baseStorage = new BaseStorage();
    BeanUtil.copyProperties(bo, baseStorage, new CopyOptions().setIgnoreProperties("storageId"));
    this.save(baseStorage);

    Map<String, Object> result = new HashMap<>();
    result.put("storageId", baseStorage.getStorageId());
    result.put("storageCode", baseStorage.getStorageCode());
    result.put("storageName", baseStorage.getStorageName());
    return R.ok("仓库信息保存成功", result);

  }
  //#endregion
}

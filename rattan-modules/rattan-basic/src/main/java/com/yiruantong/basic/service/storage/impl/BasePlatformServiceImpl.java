package com.yiruantong.basic.service.storage.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yiruantong.basic.domain.storage.BasePlatform;
import com.yiruantong.basic.domain.storage.bo.BasePlatformBo;
import com.yiruantong.basic.domain.storage.vo.BasePlatformVo;
import com.yiruantong.basic.mapper.storage.BasePlatformMapper;
import com.yiruantong.basic.service.storage.IBasePlatformService;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.base.BasePlatformStatusEnum;
import com.yiruantong.common.core.enums.base.BasePlatformTypeEnum;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.domain.bo.SaveEditorBo;
import com.yiruantong.common.mybatis.core.service.IDataAuthService;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.satoken.utils.LoginHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 月台管理Service业务层处理
 *
 * @author YRT
 * @date 2024-03-09
 */
@RequiredArgsConstructor
@Service
public class BasePlatformServiceImpl extends ServiceImplPlus<BasePlatformMapper, BasePlatform, BasePlatformVo, BasePlatformBo> implements IBasePlatformService {

  private final IDataAuthService dataAuthService;

  /**
   * 通用 - 查询月台
   *
   * @param map 查询条件
   * @return 返回查询结果
   */
  @Override
  public List<Map<String, Object>> getList(Map<String, Object> map) {
    Integer take = Optional.ofNullable(map).map(m -> Convert.toInt(m.get("take"))).orElse(200); // 查询top N，如果为空，默认200
    String name = Convert.toStr(map.get("name"));
    String storageName = Convert.toStr(map.get("storageName"));
    // amis下拉框搜索
    if (StringUtils.isEmpty(name)) {
      name = Convert.toStr(map.get("term"));
    }

    String searchFields = Convert.toStr(map.get("searchFields"));
    LoginUser loginUser = LoginHelper.getLoginUser();
    if (ObjectUtil.isNull(loginUser)) return null;

    QueryWrapper<BasePlatform> queryWrapper = new QueryWrapper<>();
    String finalName = name;
    queryWrapper.lambda().eq(BasePlatform::getEnable, EnableEnum.ENABLE.getId()) // 是否可用
      .and(StringUtils.isNotEmpty(name),
        a -> a.like(StringUtils.isNotEmpty(finalName), BasePlatform::getPlatformName, finalName)
      ).and(StringUtils.isNotEmpty(storageName),
        a -> a.like(StringUtils.isNotEmpty(storageName), BasePlatform::getStorageName, storageName)
      ); // 关键词对编号和名称模糊查询

    try {
      List<String> fields = Arrays.asList("platformId", "platformName", "storageName"); // 查询默认字段
      // 自定义查询字段
      if (StringUtils.isNotEmpty(searchFields)) {
        fields = List.of(StringUtils.split(searchFields, ",")); // 查询指定字段
      }
      if ("*".equals(searchFields)) {
        fields.clear(); // 清空，查询所有字段
      }

      // 自定义查询字段
      if (fields.size() > 0) {
        List<String> finalFields = fields;
        queryWrapper.select(BasePlatform.class, s -> finalFields.contains(s.getProperty()));
      }
      // 数据权限
      if (!loginUser.isAdministrator()) {
        dataAuthService.getProviderAuth(queryWrapper);
      }
      queryWrapper.lambda().orderByAsc(BasePlatform::getPlatformId); // 排序
      queryWrapper.last("limit " + take); // top N

      var dataList = this.baseMapper.selectMaps(queryWrapper);
      return dataList;
    } catch (Exception error) {
      var msg = "异常错误信息：" + error.getCause();
      throw new ServiceException(msg);
    }
  }

  @Override
  public void updateStatus(Long platformId, BasePlatformStatusEnum basePlatformStatusEnum, Date startTime, Date endTime, BasePlatformTypeEnum basePlatformTypeEnum, String truckNo) {
    LambdaUpdateWrapper<BasePlatform> platformLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    platformLambdaUpdateWrapper
      .set(BasePlatform::getPlatStatus, BasePlatformStatusEnum.IN_TASK.getName())
      .set(BasePlatform::getStartTime, startTime)
      .set(BasePlatform::getEndTime, endTime)
      .set(BasePlatform::getPlatformType, basePlatformTypeEnum.getName())
      .set(BasePlatform::getTruckNo, truckNo)
      .eq(BasePlatform::getPlatformId, platformId);
    this.update(platformLambdaUpdateWrapper);
  }


  //#region 套装商品保存前事件
  @Override
  public void beforeSaveEditor(SaveEditorBo<BasePlatformBo> saveEditorBo) {
    LambdaQueryWrapper<BasePlatform> basePlatformBoLambdaQueryWrapper = new LambdaQueryWrapper<>();
    basePlatformBoLambdaQueryWrapper.eq(BasePlatform::getStorageName, saveEditorBo.getData().getMaster().getStorageName())
      .eq(BasePlatform::getPlatformName, saveEditorBo.getData().getMaster().getPlatformName());
    // 如果商品信息Id不为空
    if (ObjectUtil.isNotEmpty(saveEditorBo.getData().getMaster().getPlatformId())) {
      basePlatformBoLambdaQueryWrapper.eq(BasePlatform::getStorageName, saveEditorBo.getData().getMaster().getStorageName())
        .eq(BasePlatform::getPlatformName, saveEditorBo.getData().getMaster().getPlatformName())
        .ne(BasePlatform::getPlatformId, saveEditorBo.getData().getMaster().getPlatformId());
    }
    long count = this.count(basePlatformBoLambdaQueryWrapper);
    if (count > 0) {
      throw new ServiceException("月台名称+仓库名称已存在不允许重复添加，无法添加！");
    }
    super.beforeSaveEditor(saveEditorBo);
  }
  //#endregion

}

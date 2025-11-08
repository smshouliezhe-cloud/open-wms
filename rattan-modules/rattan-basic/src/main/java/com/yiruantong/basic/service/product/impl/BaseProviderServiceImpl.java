package com.yiruantong.basic.service.product.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.product.BaseProvider;
import com.yiruantong.basic.domain.product.bo.BaseProviderBo;
import com.yiruantong.basic.domain.product.vo.BaseProviderVo;
import com.yiruantong.basic.mapper.product.BaseProviderMapper;
import com.yiruantong.basic.service.product.IBaseProviderService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.DateUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.excel.core.ExcelResult;
import com.yiruantong.common.excel.listener.ImportCommonListener;
import com.yiruantong.common.excel.utils.ExcelUtil;
import com.yiruantong.common.mybatis.core.service.IDataAuthService;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.tenant.helper.TenantHelper;
import com.yiruantong.system.service.dataHandler.ISysImportService;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.InputStream;
import java.util.*;

/**
 * 供应商管理Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-15
 */
@RequiredArgsConstructor
@Service
public class BaseProviderServiceImpl extends ServiceImplPlus<BaseProviderMapper, BaseProvider, BaseProviderVo, BaseProviderBo> implements IBaseProviderService {
  private final DataSourceTransactionManager transactionManager;
  private final ISysImportService sysImportService;
  private final IDataAuthService dataAuthService;


  //#region 通用 - 查询供应商

  /**
   * 通用 - 查询供应商
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

    QueryWrapper<BaseProvider> queryWrapper = new QueryWrapper<>();
    String finalName = name;
    queryWrapper.lambda().eq(BaseProvider::getEnable, EnableEnum.ENABLE.getId()) // 是否可用
      .and(StringUtils.isNotEmpty(name),
        a -> a.like(StringUtils.isNotEmpty(finalName), BaseProvider::getProviderCode, finalName)
          .or()
          .like(StringUtils.isNotEmpty(finalName), BaseProvider::getProviderShortName, finalName)
      ); // 关键词对编号和名称模糊查询

    try {
      List<String> fields = Arrays.asList("providerId", "providerCode", "providerShortName", "providerName"); // 查询默认字段
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
        queryWrapper.select(BaseProvider.class, s -> finalFields.contains(s.getProperty()));
      }
      // 数据权限
      if (!loginUser.isAdministrator()) {
        dataAuthService.getProviderAuth(queryWrapper);
      }
      queryWrapper.lambda().orderByAsc(BaseProvider::getProviderId); // 排序
      queryWrapper.last("limit " + take); // top N

      var dataList = this.baseMapper.selectMaps(queryWrapper);
      return dataList;
    } catch (Exception error) {
      var msg = "异常错误信息：" + error.getCause();
      throw new ServiceException(msg);
    }
  }

  @Override
  public BaseProvider getDefaultOne() {
    LambdaQueryWrapper<BaseProvider> providerLambdaQueryWrapper = new LambdaQueryWrapper<>();
    providerLambdaQueryWrapper
      .eq(BaseProvider::getProviderShortName, "默认供应商")
      .last("limit 1");
    return this.getOne(providerLambdaQueryWrapper);
  }

  @Override
  public R<Map<String, Object>> getProviderOne(Map<String, Object> map) {
    LambdaQueryWrapper<BaseProvider> providerLambdaQueryWrapper = new LambdaQueryWrapper<>();
    providerLambdaQueryWrapper
      .last("limit 1");
    var dataList = this.getOne(providerLambdaQueryWrapper);
    Map<String, Object> result = new HashMap<>();
    result.put("dataList", dataList);

    return R.ok("供应商更新成功", result);
  }

  @Override
  public BaseProvider getByShortName(String providerShortName) {
    LambdaQueryWrapper<BaseProvider> providerLambdaQueryWrapper = new LambdaQueryWrapper<>();
    providerLambdaQueryWrapper
      .eq(BaseProvider::getProviderShortName, providerShortName)
      .last("limit 1");
    return this.getOne(providerLambdaQueryWrapper);
  }
  //#endregion

  @Override
  public BaseProvider getByCode(String providerCode) {
    LambdaQueryWrapper<BaseProvider> providerLambdaQueryWrapper = new LambdaQueryWrapper<>();
    providerLambdaQueryWrapper
      .eq(BaseProvider::getProviderCode, providerCode)
      .last("limit 1");
    return this.getOne(providerLambdaQueryWrapper);
  }
  //#endregion

  //#region 货位信息导入数据
  @Async
  @Override
  public void importData(InputStream inputStream, Long importId, HttpServletRequest request, LoginUser loginUser) {
    // 手动开启事务  start
    DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
    definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus transaction = transactionManager.getTransaction(definition);

    TenantHelper.setDynamic(loginUser.getTenantId()); // 在异步线程里面需要手动设置当前租户ID，缓存用到
    LoginHelper.setLoginUser(loginUser);
    String key = request.getParameter("key");
    sysImportService.setKey(key);
    DateTime startDate = DateUtil.date(); // 导入开始时间

    try {
      // 是否开启唯一码
      sysImportService.isAssert(StringUtils.isEmpty(key), "上传key不存在");
      sysImportService.isAssert(ObjectUtil.isEmpty(importId), "未关联模板，不可导入");

      // 处理解析结果
      ExcelResult<Map<String, Object>> excelResult = null;
      excelResult = ExcelUtil.importExcel(inputStream, new ImportCommonListener());
      var dataList = excelResult.getList();

      // 通用验证
      sysImportService.setError(false);
      R<Void> result = sysImportService.commonCheck(dataList, importId, request, loginUser);
      sysImportService.isAssert(!result.isResult(), "导入数据有错误，请处理好重新导入");
      sysImportService.writeMsg("开始导入...");
      //#endregion

      //#region 验证数据正确性
      int i = 1;
      int successCount = 0;
      int updateCount = 0;
      for (var row : dataList) {
        i++;
        final String providerShortName = Convert.toStr(row.get("providerShortName"));
        final String providerCode = Convert.toStr(row.get("providerCode"));

        BaseProvider dataInfo;
        LambdaQueryWrapper<BaseProvider> providerLambdaQueryWrapper = new LambdaQueryWrapper<>();
        providerLambdaQueryWrapper.eq(BaseProvider::getProviderShortName, providerShortName);
        dataInfo = this.getOne(providerLambdaQueryWrapper);

        // 如果不存在，增长品牌
        if (ObjectUtil.isNull(dataInfo)) {
          successCount++;
          dataInfo = new BaseProvider(); // 新建数据
          // 数据拷贝
          BeanUtil.copyProperties(row, dataInfo);
          if (ObjectUtil.isNotEmpty(providerCode)) {
            dataInfo.setProviderCode(providerCode);
          } else {
            dataInfo.setProviderCode(DBUtils.getCodeRegular(MenuEnum.MENU_1762, loginUser.getTenantId()));
          }
          sysImportService.writeMsgBlue("第{}行、{}正在导入新增...", i, dataInfo.getProviderShortName());
        } else {
          BeanUtil.copyProperties(row, dataInfo);
          sysImportService.writeMsgBlue("第{}行、{}正在导入更新...", i, dataInfo.getProviderShortName());
          updateCount++;
        }
        dataInfo.setCreateBy(loginUser.getUserId());
        dataInfo.setCreateByName(loginUser.getNickname());
        dataInfo.setCreateTime(DateUtils.getNowDate());
        this.saveOrUpdate(dataInfo);
      }

      var endDate = DateUtil.date();
      var totalSeconds = DateUtil.between(startDate, endDate, DateUnit.SECOND);
      sysImportService.writeMsgBlue("导入完成，新增{}条", successCount);
      sysImportService.writeMsgBlue("导入完成，更新{}条", updateCount);
      sysImportService.writeMsg("导入成功,共耗时{}秒", totalSeconds);
      transactionManager.commit(transaction); // 手动提交事务
    } catch (Exception exception) {
      sysImportService.writeMsgRed("导入错误：{}", exception);
      transactionManager.rollback(transaction); // 手动回滚事务
    }
    sysImportService.writeEnd(); // 标记结算
  }
  //#endregion

  //#region add
  @Override
  public R<Map<String, Object>> add(BaseProviderBo bo) {
    try {
      BaseProvider baseProvider = this.getByCode(bo.getProviderCode());

      if (ObjectUtil.isNotNull(baseProvider)) {
        BeanUtil.copyProperties(bo, baseProvider, new CopyOptions().setIgnoreProperties("providerId"));
        this.saveOrUpdate(baseProvider);

        Map<String, Object> result = new HashMap<>();
        result.put("providerId", baseProvider.getProviderId());
        result.put("providerCode", baseProvider.getProviderCode());
        result.put("providerShortName", baseProvider.getProviderShortName());

        return R.ok("供应商更新成功", result);
      }

      baseProvider = this.getByShortName(bo.getProviderShortName());

      if (ObjectUtil.isNotNull(baseProvider)) {
        Map<String, Object> result = new HashMap<>();
        result.put("providerId", baseProvider.getProviderId());
        result.put("providerCode", baseProvider.getProviderCode());
        result.put("providerShortName", baseProvider.getProviderShortName());

        return R.ok("供应商已存在，不能重复推送", result);
      }

      baseProvider = new BaseProvider();
      BeanUtil.copyProperties(bo, baseProvider, new CopyOptions().setIgnoreProperties("providerId"));
      this.save(baseProvider);

      Map<String, Object> result = new HashMap<>();
      result.put("providerId", baseProvider.getProviderId());
      result.put("providerCode", baseProvider.getProviderCode());
      result.put("providerName", baseProvider.getProviderName());
      result.put("providerShortName", baseProvider.getProviderShortName());
      return R.ok("供应商信息保存成功", result);
    } catch (Exception error) {
      return R.fail("供应商信息保存失败，" + error.getMessage());
    }
  }
  //#endregion
}

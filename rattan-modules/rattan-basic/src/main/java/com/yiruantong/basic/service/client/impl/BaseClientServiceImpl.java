package com.yiruantong.basic.service.client.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.base.bo.GetListBo;
import com.yiruantong.basic.domain.client.BaseClient;
import com.yiruantong.basic.domain.client.BaseClientAddress;
import com.yiruantong.basic.domain.client.bo.BaseClientBo;
import com.yiruantong.basic.domain.client.vo.BaseClientVo;
import com.yiruantong.basic.mapper.client.BaseClientMapper;
import com.yiruantong.basic.service.client.IBaseClientAddressService;
import com.yiruantong.basic.service.client.IBaseClientService;
import com.yiruantong.basic.service.tms.ITmsLineService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.excel.core.ExcelResult;
import com.yiruantong.common.excel.listener.ImportCommonListener;
import com.yiruantong.common.excel.utils.ExcelUtil;
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
 * 客户信息Service业务层处理
 *
 * @author YRT
 * @date 2023-10-26
 */
@RequiredArgsConstructor
@Service
public class BaseClientServiceImpl extends ServiceImplPlus<BaseClientMapper, BaseClient, BaseClientVo, BaseClientBo> implements IBaseClientService {
  private final DataSourceTransactionManager transactionManager;
  private final ISysImportService sysImportService;
  private final IBaseClientAddressService baseClientAddressService;
  private final ITmsLineService tmsLineService;

  //#region 通用 - 查询客户

  /**
   * 通用 - 查询客户
   *
   * @param getListBo@return 返回查询结果
   */
  @Override
  public List<Map<String, Object>> getList(GetListBo getListBo) {
    Long take = Optional.ofNullable(getListBo).map(m -> getListBo.getTake()).orElse(200L); // 查询top N，如果为空，默认200
    String name = getListBo.getName();
    // amis下拉框搜索
    if (StringUtils.isEmpty(name)) {
      name = getListBo.getTerm();
    }

    String searchFields = Convert.toStr(getListBo.getSearchFields());
    LambdaQueryWrapper<BaseClient> queryWrapper = new LambdaQueryWrapper<>();
    String finalName = name;
    queryWrapper.eq(BaseClient::getEnable, EnableEnum.ENABLE.getId()) // 是否可用
      .and(StringUtils.isNotEmpty(name),
        a -> a.like(StringUtils.isNotEmpty(finalName), BaseClient::getClientCode, finalName)
          .or()
          .like(StringUtils.isNotEmpty(finalName), BaseClient::getClientShortName, finalName)
      ); // 关键词对编号和名称模糊查询

    try {
      List<String> fields = Arrays.asList("clientId", "clientCode", "clientShortName", "clientName"); // 查询默认字段
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
        queryWrapper.select(BaseClient.class, s -> finalFields.contains(s.getProperty()));
      }
      queryWrapper.orderByAsc(BaseClient::getClientId); // 排序
      queryWrapper.last("limit " + take); // top N

      return this.baseMapper.selectMaps(queryWrapper);
    } catch (Exception error) {
      var msg = "异常错误信息：" + error.getCause();
      throw new ServiceException(msg);
    }
  }
  //#endregion

  //#region 通用 - 查询客户

  /**
   * 通用 - 查询客户
   *
   * @param clientId 查询条件
   * @return 返回查询结果
   */
  public BaseClient getClientInfo(Long clientId) {
    LambdaQueryWrapper<BaseClient> shelveLambdaQueryWrapper = new LambdaQueryWrapper<>();
    shelveLambdaQueryWrapper.eq(BaseClient::getClientId, clientId);

    return this.getOne(shelveLambdaQueryWrapper);
  }
  //#endregion

  //#region 根据编号查询客户

  /**
   * 根据编号查询客户
   *
   * @param clientCode 查询条件
   * @return 返回查询结果
   */
  public BaseClient getByCode(String clientCode) {
    LambdaQueryWrapper<BaseClient> shelveLambdaQueryWrapper = new LambdaQueryWrapper<>();
    shelveLambdaQueryWrapper.eq(BaseClient::getClientCode, clientCode);

    return this.getOnly(shelveLambdaQueryWrapper);
  }
  //#endregion

  //#region 根据名称查询客户

  /**
   * 根据名称查询客户
   *
   * @param clientShortName 查询条件
   * @return 返回查询结果
   */
  public BaseClient getByName(String clientShortName) {
    LambdaQueryWrapper<BaseClient> shelveLambdaQueryWrapper = new LambdaQueryWrapper<>();
    shelveLambdaQueryWrapper.eq(BaseClient::getClientShortName, clientShortName);

    return this.getOnly(shelveLambdaQueryWrapper);
  }
  //#endregion

  //#region 客户信息批量导入
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
      Long storageId = Convert.toLong(request.getParameter("storageId"));
      Long consignorId = Convert.toLong(request.getParameter("consignorId"));
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

      sysImportService.isAssert(sysImportService.isError(), "导入数据有错误，请处理好重新导入");

      // 循环处理分组数据，groupList对应的是主表数据
      int i = 1;
      int successCount = 0;
      int updateCount = 0;
      for (var item : dataList) {
        i++;

        final String lineName = Convert.toStr(item.get("lineName")); // 配送路线

//        LambdaQueryWrapper<TmsLine> tmsLineLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        tmsLineLambdaQueryWrapper.eq(TmsLine::getLineName, lineName); // 客户编号
//        var lineInfo = tmsLineService.selectOne(tmsLineLambdaQueryWrapper);
//        sysImportService.isAssert(ObjectUtil.isEmpty(lineInfo), "模板线路不存在，请检查导入模板后重新导入");

        final String clientCode = Convert.toStr(item.get("clientCode"));

        LambdaQueryWrapper<BaseClient> clientLambdaQueryWrapper = new LambdaQueryWrapper<>();
        clientLambdaQueryWrapper.eq(BaseClient::getClientCode, clientCode); // 客户编号
        var dataInfo = this.baseMapper.selectOne(clientLambdaQueryWrapper);

        if (ObjectUtil.isNotEmpty(dataInfo)) {
          BeanUtil.copyProperties(item, dataInfo);
          sysImportService.writeMsgBlue("第{}行、{}正在导入更新...", i, dataInfo.getClientCode());
          updateCount++;
        } else {
          dataInfo = new BaseClient(); // 新建数据
          // 数据拷贝
          BeanUtil.copyProperties(item, dataInfo);
          if (ObjectUtil.isNotEmpty(clientCode)) {
            dataInfo.setClientCode(clientCode);
          } else {
            dataInfo.setClientCode(DBUtils.getCodeRegular(MenuEnum.MENU_2063, loginUser.getTenantId()));
          }
          dataInfo.setCreateBy(loginUser.getUserId());
          dataInfo.setCreateByName(loginUser.getNickname());
          dataInfo.setUserId(loginUser.getUserId());
          dataInfo.setNickName(loginUser.getNickname());
          sysImportService.writeMsgBlue("第{}行、{}正在导入新增...", i, dataInfo.getClientCode());
          successCount++;
        }
        this.saveOrUpdate(dataInfo);

        //根据主表的地址信息添加一条明细信息
        LambdaQueryWrapper<BaseClientAddress> addressLambdaQueryWrapper = new LambdaQueryWrapper<>();
        addressLambdaQueryWrapper.eq(BaseClientAddress::getAddress, dataInfo.getShippingAddress())
          .eq(BaseClientAddress::getClientId, dataInfo.getClientId())
          .last("limit 1");
        BaseClientAddress address = baseClientAddressService.getOne(addressLambdaQueryWrapper);
        if (!ObjectUtil.isNotEmpty(address)) {
          address = new BaseClientAddress();
        }
        BeanUtil.copyProperties(dataInfo, address);
        address.setName(dataInfo.getShippingName());
        address.setAddress(dataInfo.getShippingAddress());
        address.setClientId(dataInfo.getClientId());
        address.setAddressId(null);
        baseClientAddressService.saveOrUpdate(address);

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
  public R<Map<String, Object>> add(BaseClientBo bo) {
    try {
      BaseClient baseClient = this.getByCode(bo.getClientCode());

      if (ObjectUtil.isNull(baseClient)) {
        baseClient = this.getByName(bo.getClientShortName());
      }

      if (ObjectUtil.isNotNull(baseClient)) {
        BeanUtil.copyProperties(bo, baseClient, new CopyOptions().setIgnoreProperties("clientId"));
        this.saveOrUpdate(baseClient);

        Map<String, Object> result = new HashMap<>();
        result.put("clientId", baseClient.getClientId());
        result.put("clientCode", baseClient.getClientCode());
        result.put("clientShortName", baseClient.getClientShortName());
        result.put("clientFullName", baseClient.getClientFullName());

        return R.ok("客户更新成功", result);
      }

      baseClient = new BaseClient();
      BeanUtil.copyProperties(bo, baseClient, new CopyOptions().setIgnoreProperties("clientId"));
      this.save(baseClient);

      Map<String, Object> result = new HashMap<>();
      result.put("clientId", baseClient.getClientId());
      result.put("clientCode", baseClient.getClientCode());
      result.put("clientShortName", baseClient.getClientShortName());
      result.put("clientFullName", baseClient.getClientFullName());
      return R.ok("客户信息保存成功", result);
    } catch (Exception error) {
      return R.fail("客户信息保存失败，" + error.getMessage());
    }
  }

  //#endregion

  @Override
  public BaseClient getConsignorInfo(String consignorName) {
    LambdaQueryWrapper<BaseClient> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(BaseClient::getConsignorName, consignorName);
    return this.getOnly(queryWrapper);
  }
}

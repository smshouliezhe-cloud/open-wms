package com.yiruantong.basic.service.product.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.excel.core.ExcelResult;
import com.yiruantong.common.excel.listener.ImportCommonListener;
import com.yiruantong.common.excel.utils.ExcelUtil;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.tenant.helper.TenantHelper;
import com.yiruantong.system.service.dataHandler.ISysImportService;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.yiruantong.basic.domain.product.bo.BaseBrandBo;
import com.yiruantong.basic.domain.product.vo.BaseBrandVo;
import com.yiruantong.basic.domain.product.BaseBrand;
import com.yiruantong.basic.mapper.product.BaseBrandMapper;
import com.yiruantong.basic.service.product.IBaseBrandService;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.InputStream;
import java.util.Map;

/**
 * 品牌管理Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-15
 */
@RequiredArgsConstructor
@Service
public class BaseBrandServiceImpl extends ServiceImplPlus<BaseBrandMapper, BaseBrand, BaseBrandVo, BaseBrandBo> implements IBaseBrandService {
  private final DataSourceTransactionManager transactionManager;
  private final ISysImportService sysImportService;

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
        final String brandName = Convert.toStr(row.get("brandName"));
        final String brandNameEn = Convert.toStr(row.get("brandNameEn"));
        final String consignorName = Convert.toStr(row.get("consignorName"));
        final String providerShortName = Convert.toStr(row.get("providerShortName"));

        BaseBrand dataInfo;
        LambdaQueryWrapper<BaseBrand> brandLambdaQueryWrapper = new LambdaQueryWrapper<>();
        brandLambdaQueryWrapper.eq(BaseBrand::getBrandName, brandName)
          .eq(BaseBrand::getBrandNameEn, brandNameEn)
          .eq(BaseBrand::getConsignorName, consignorName)
          .eq(BaseBrand::getProviderShortName, providerShortName);
        dataInfo = this.getOne(brandLambdaQueryWrapper);

        // 如果不存在，增长品牌
        if (ObjectUtil.isNull(dataInfo)) {
          successCount++;
          dataInfo = new BaseBrand(); // 新建数据

          // 数据拷贝
          BeanUtil.copyProperties(row, dataInfo);
          dataInfo.setCreateBy(loginUser.getUserId());
          dataInfo.setCreateByName(loginUser.getNickname());
          sysImportService.writeMsgBlue("第{}行、{}正在导入新增...", i, dataInfo.getBrandName());
        } else {
          sysImportService.writeMsgBlue("第{}行、{}正在导入更新...", i, dataInfo.getBrandName());
          updateCount++;
        }

        BeanUtil.copyProperties(row, dataInfo);
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
}

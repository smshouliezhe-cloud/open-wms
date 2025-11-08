package com.yiruantong.basic.service.product.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.product.BaseProductSet;
import com.yiruantong.basic.service.product.IBaseProductService;
import com.yiruantong.basic.service.product.IBaseProductSetService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.utils.NumberUtils;
import com.yiruantong.common.core.utils.SpringUtils;
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
import com.yiruantong.basic.domain.product.bo.BaseProductSetDetailBo;
import com.yiruantong.basic.domain.product.vo.BaseProductSetDetailVo;
import com.yiruantong.basic.domain.product.BaseProductSetDetail;
import com.yiruantong.basic.mapper.product.BaseProductSetDetailMapper;
import com.yiruantong.basic.service.product.IBaseProductSetDetailService;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 商品套装明细Service业务层处理
 *
 * @author YRT
 * @date 2023-12-19
 */
@RequiredArgsConstructor
@Service
public class BaseProductSetDetailServiceImpl extends ServiceImplPlus<BaseProductSetDetailMapper, BaseProductSetDetail, BaseProductSetDetailVo, BaseProductSetDetailBo> implements IBaseProductSetDetailService {
  private final DataSourceTransactionManager transactionManager;
  private final ISysImportService sysImportService;
  private final IBaseProductService baseProductService;

  //#region 套装组合明细导入数据
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
      // 主表id
      Long productSetId = Convert.toLong(request.getParameter("mainId"));
      // 是否开启唯一码
      sysImportService.isAssert(StringUtils.isEmpty(key), "上传key不存在");
      sysImportService.isAssert(ObjectUtil.isEmpty(importId), "未关联模板，不可导入");
      sysImportService.isAssert(ObjectUtil.isEmpty(productSetId), "请先保存主表后再进行导入操作");

      // 处理解析结果
      ExcelResult<Map<String, Object>> excelResult = null;
      excelResult = ExcelUtil.importExcel(inputStream, new ImportCommonListener());
      var dataList = excelResult.getList();

      // 通用验证
      sysImportService.setError(false);
      R<Void> result = sysImportService.commonCheck(dataList, importId, request, loginUser);
      sysImportService.isAssert(!result.isResult(), "导入数据有错误，请处理好重新导入");
      sysImportService.writeMsg("开始导入...");

      // 主表信息
      LambdaQueryWrapper<BaseProductSet> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
      orderLambdaQueryWrapper.eq(BaseProductSet::getProductSetId, productSetId);
      IBaseProductSetService bean = SpringUtils.getBean(IBaseProductSetService.class);
      var baseProductSet = bean.getOne(orderLambdaQueryWrapper);

      int i = 0;
      int successCount = 0;
      int updateCount = 0;
      for (var row : dataList) {
        i++;
        final String productCode = Convert.toStr(row.get("productCode"));
        final BigDecimal splitQuantity = Convert.toBigDecimal(row.get("splitQuantity"));
        sysImportService.isAssert(!NumberUtils.isPositiveInteger(splitQuantity), "{}、产品编号[{}]数量请输入正整数！", i, productCode);
      }
      sysImportService.isAssert(!result.isResult(), "导入数据有错误，请处理好重新导入");


      /*——————————————————————————————————————————————————
       * 处理明细，将明细挂载到主表下
       *——————————————————————————————————————————————————*/
      List<BaseProductSetDetail> baseProductSetDetails = new ArrayList<>();
      for (var detail : dataList) {
        // 商品信息
        var prodInfo = baseProductService.getByModel(Convert.toStr(detail.get("productModel")));

        var detailInfo = new BaseProductSetDetail();
        BeanUtil.copyProperties(prodInfo, detailInfo);
        BeanUtil.copyProperties(detail, detailInfo);
        final BigDecimal salePrice = Convert.toBigDecimal(detail.get("salePrice")); // 销售售价
        final BigDecimal purchasePrice = Convert.toBigDecimal(detail.get("purchasePrice")); // 成本价
        BigDecimal zero = BigDecimal.ZERO;
        if (ObjectUtil.isNull(salePrice)) {
          detailInfo.setSalePrice(zero);
        } else {
          detailInfo.setSalePrice(salePrice);
        }
        if (ObjectUtil.isNull(salePrice)) {
          detailInfo.setPurchasePrice(zero);
        } else {
          detailInfo.setPurchasePrice(purchasePrice);
        }

        //建立关系，设置主表ID
        detailInfo.setProductSetId(baseProductSet.getProductSetId());
        this.saveOrUpdate(detailInfo);
        baseProductSetDetails.add(detailInfo);
        successCount++;
      }
      var salePrices = baseProductSetDetails;
      // 主表求和字段计算
      // 合计销售售价
      var salePrice = baseProductSetDetails.stream().map(BaseProductSetDetail::getSalePrice).reduce(BigDecimal.ZERO, BigDecimal::add);
      // 合计成本价
      var purchasePrice = baseProductSetDetails.stream().map(BaseProductSetDetail::getPurchasePrice).reduce(BigDecimal.ZERO, BigDecimal::add);


      LambdaUpdateWrapper<BaseProductSet> lambda = new UpdateWrapper<BaseProductSet>().lambda();
      lambda.set(BaseProductSet::getSalePrice, salePrice)
        .set(BaseProductSet::getPurchasePrice, purchasePrice)
        .eq(BaseProductSet::getProductSetId, productSetId);
      bean.update(lambda);


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

package com.yiruantong.inventory.service.plate.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.client.BaseClient;
import com.yiruantong.basic.domain.storage.BasePlateProduct;
import com.yiruantong.basic.domain.storage.BaseStorage;
import com.yiruantong.basic.service.client.IBaseClientService;
import com.yiruantong.basic.service.storage.IBasePlateProductService;
import com.yiruantong.basic.service.storage.IBaseStorageService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.base.AuditEnum;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.base.PlateSourceTypeEnum;
import com.yiruantong.common.core.enums.base.PlateStatusEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.SpringUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.excel.core.ExcelResult;
import com.yiruantong.common.excel.listener.ImportCommonListener;
import com.yiruantong.common.excel.utils.ExcelUtil;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.tenant.helper.TenantHelper;
import com.yiruantong.inventory.domain.plate.*;
import com.yiruantong.inventory.domain.plate.bo.BasePlateInBo;
import com.yiruantong.inventory.domain.plate.vo.BasePlateInVo;
import com.yiruantong.inventory.mapper.plate.BasePlateInMapper;
import com.yiruantong.inventory.service.plate.*;
import com.yiruantong.system.service.dataHandler.ISysImportService;
import com.yiruantong.system.service.monitor.ISysOperLogService;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 容器归还主Service业务层处理
 *
 * @author YRT
 * @date 2023-12-21
 */
@RequiredArgsConstructor
@Service
public class BasePlateInServiceImpl extends ServiceImplPlus<BasePlateInMapper, BasePlateIn, BasePlateInVo, BasePlateInBo> implements IBasePlateInService {
  private final IBasePlateInDetailService basePlateInDetailService;
  private final IBaseStoragePlateService baseStoragePlateService;
  private final IBasePlateClientService basePlateClientService;
  private final IBasePlateFlowService basePlateFlowService;
  private final IBasePlateOutService basePlateOutService;
  private final IBasePlateOutDetailService basePlateOutDetailService;
  private final DataSourceTransactionManager transactionManager;
  private final ISysImportService sysImportService;
  private final ISysOperLogService sysOperLogService;
  private final IBaseClientService baseClientService;
  private final IBaseStorageService baseStorageService;
  private final IBasePlateProductService basePlateProductService;

  /**
   * 批量审核
   *
   * @param ids 审核参数
   * @return 返回查询列表数据
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> multiAuditing(List<Long> ids) {
//    Date returnDate = Convert.toDate(ids.get("returnDate"));
    Long[] _ids = Convert.toLongArray(ids);
    IBasePlateInService sortingBean = SpringUtils.getBean(IBasePlateInService.class); // 需要动态获取bean，否则循环冲突

    for (long inId : _ids) {

      LambdaQueryWrapper<BasePlateIn> plateInLambdaQueryWrapper = new LambdaQueryWrapper<>();
      plateInLambdaQueryWrapper.eq(BasePlateIn::getInId, inId);
      var mainInfo = sortingBean.getOne(plateInLambdaQueryWrapper);

      if (ObjectUtil.isNull(mainInfo)) {
        throw new ServiceException("单据为空");
      }

      // 归还单明细
      LambdaQueryWrapper<BasePlateInDetail> queryWrapper = new LambdaQueryWrapper<>();
      queryWrapper.eq(BasePlateInDetail::getInId, mainInfo.getInId());
      var detailList = basePlateInDetailService.selectList(queryWrapper);
      if (ObjectUtil.isNull(detailList)) {
        throw new ServiceException("单据明细为空，请检查");
      }
      for (var item : detailList) {

        BaseStoragePlate dateInfo = new BaseStoragePlate();
        BeanUtil.copyProperties(mainInfo, dateInfo);
        dateInfo.setBillId(mainInfo.getInId());
        dateInfo.setBillCode(mainInfo.getInCode());

        dateInfo.setSourceType(PlateSourceTypeEnum.PLATE_IN.getName());
        dateInfo.setPlateSpec(item.getPlateSpec());
        dateInfo.setPlateType(item.getPlateType());
        dateInfo.setPlateName(item.getPlateName());
        dateInfo.setPlateAttribute(item.getPlateAttribute());
        dateInfo.setEnterQuantity(item.getReturnQty()); // 入库数量
        dateInfo.setUnReturnFactoryQty(item.getReturnQty()); // 未返厂数量
        dateInfo.setReturnFactoryQty(BigDecimal.ZERO); // 已返厂数量
        baseStoragePlateService.save(dateInfo);


        LambdaQueryWrapper<BasePlateOut> outqwp = new LambdaQueryWrapper<>();
        outqwp.eq(BasePlateOut::getCustomerOrderCode, mainInfo.getSourceOutCode())
          .eq(BasePlateOut::getOutId, mainInfo.getOutId());
        var outqwpInfo = basePlateOutService.getOne(outqwp);
        if (ObjectUtil.isNotNull(outqwpInfo)) {
          // 修改明细数据
          LambdaUpdateWrapper<BasePlateOutDetail> plateOutDetailLambda = new UpdateWrapper<BasePlateOutDetail>().lambda();
          plateOutDetailLambda.set(BasePlateOutDetail::getReturnedQty, item.getReturnQty()) // 已归还数量
            .set(BasePlateOutDetail::getUnreturnedQty, B.sub(item.getReturnQty(), item.getReturnQty())) // 未归还数量=借出数量-归还数量
            .eq(BasePlateOutDetail::getPlateType, item.getPlateType())
            .eq(BasePlateOutDetail::getPlateSpec, item.getPlateSpec())
            .eq(BasePlateOutDetail::getOutDetailId, item.getSourceDetailId()) // 明细id
            .eq(BasePlateOutDetail::getOutId, outqwpInfo.getOutId());
          basePlateOutDetailService.update(plateOutDetailLambda);//提交
        }
      }
      //#region 生成流水
      for (var item : detailList) {
        BasePlateFlow basePlateFlow = new BasePlateFlow();
        String flowCode = DBUtils.getCodeRegular(MenuEnum.MENU_1810, LoginHelper.getLoginUser().getTenantId());

        basePlateFlow.setFlowCode(flowCode);
        BeanUtil.copyProperties(mainInfo, basePlateFlow);
        basePlateFlow.setSourceCode(mainInfo.getInCode());
        basePlateFlow.setPlateSpec(item.getPlateSpec());
        basePlateFlow.setPlateType(item.getPlateType());
        basePlateFlow.setPlateName(item.getPlateName());
        basePlateFlow.setSourceType(PlateSourceTypeEnum.PLATE_IN.getName()); // 容器归还
        basePlateFlow.setReturnQty(item.getReturnQty()); // 归还数量
        basePlateFlow.setOuterQty(BigDecimal.ZERO); // 借出数量
        basePlateFlow.setNickName(LoginHelper.getNickname());
        basePlateFlow.setDeptId(LoginHelper.getDeptId());
        basePlateFlow.setDeptName(LoginHelper.getDeptName());
        // 操作前数量：
        // 查询客户容器管理，条件为：客户名称+仓库名称+容器类别+容器规格；容器借出数量，作为流水中的：
        // 借出：操作前数量
        // 归还：操作后数量
        // 客户容器管理
        LambdaQueryWrapper<BasePlateClient> clientLambdaQueryWrapper = new LambdaQueryWrapper<>();
        clientLambdaQueryWrapper.eq(BasePlateClient::getClientCode, mainInfo.getClientCode())
          .eq(BasePlateClient::getClientShortName, mainInfo.getClientShortName())
          .eq(BasePlateClient::getStorageId, mainInfo.getStorageId())
          .eq(BasePlateClient::getPlateType, item.getPlateType())
          .eq(BasePlateClient::getPlateSpec, item.getPlateSpec());
        var plateClientInfo = basePlateClientService.getOne(clientLambdaQueryWrapper);

        // 查询符合条件为：客户名称+仓库名称+容器类别+容器规格的历史流水数据
        LambdaQueryWrapper<BasePlateFlow> flowLambdaQueryWrapper = new LambdaQueryWrapper<>();
        flowLambdaQueryWrapper.eq(BasePlateFlow::getClientCode, mainInfo.getClientCode())
          .eq(BasePlateFlow::getClientShortName, mainInfo.getClientShortName())
          .eq(BasePlateFlow::getStorageId, mainInfo.getStorageId())
          .eq(BasePlateFlow::getPlateType, item.getPlateType())
          .eq(BasePlateFlow::getPlateSpec, item.getPlateSpec());
        var flowList = basePlateFlowService.list(flowLambdaQueryWrapper);

        BigDecimal beforeBalanceQty = BigDecimal.ZERO;
        BigDecimal balanceQty = BigDecimal.ZERO;
        if (!flowList.isEmpty()) {
          beforeBalanceQty = flowList.stream().map(BasePlateFlow::getBeforeBalanceQty).reduce(BigDecimal.ZERO, BigDecimal::add); // 操作前数量合计
          balanceQty = flowList.stream().map(BasePlateFlow::getBalanceQty).reduce(BigDecimal.ZERO, BigDecimal::add); // 操作后数量合计
        }
        if (ObjectUtil.isNull(plateClientInfo)) {
          // 新增客户容器借出数
          plateClientInfo = new BasePlateClient();
          BeanUtil.copyProperties(mainInfo, plateClientInfo);
          plateClientInfo.setPlateType(item.getPlateType());
          plateClientInfo.setPlateSpec(item.getPlateSpec());
          plateClientInfo.setOuterOty(B.adds(item.getReturnQty()));
          basePlateClientService.save(plateClientInfo);

          basePlateFlow.setBeforeBalanceQty(BigDecimal.ZERO); // 操作前数量
          basePlateFlow.setBalanceQty(B.add(item.getReturnQty(), balanceQty)); // 操作后数量
        } else {
          basePlateFlow.setBeforeBalanceQty(plateClientInfo.getOuterOty()); // 操作前数量
          basePlateFlow.setBalanceQty(B.sub(plateClientInfo.getOuterOty(), item.getReturnQty())); // 操作后数量

          // 更新客户容器借出数
          LambdaUpdateWrapper<BasePlateClient> lambda = new UpdateWrapper<BasePlateClient>().lambda();
          lambda.set(BasePlateClient::getOuterOty, B.sub(plateClientInfo.getOuterOty(), item.getReturnQty())) // 容器借出数量减少
            .eq(BasePlateClient::getClientShortName, mainInfo.getClientShortName())
            .eq(BasePlateClient::getStorageId, mainInfo.getStorageId())
            .eq(BasePlateClient::getPlateType, item.getPlateType())
            .eq(BasePlateClient::getPlateSpec, item.getPlateSpec());
          basePlateClientService.update(lambda);//提交
        }
        basePlateFlowService.save(basePlateFlow);
      }
      //#endregion


      // 修改数据
      LambdaUpdateWrapper<BasePlateIn> lambda = new UpdateWrapper<BasePlateIn>().lambda();
      lambda.set(BasePlateIn::getStatusText, AuditEnum.AUDITED_SUCCESS.getName()) // 状态审核成功
        .set(BasePlateIn::getAuditing, AuditEnum.AUDITED_SUCCESS.getId()) // 审核状态审核成功
        .set(BasePlateIn::getAuditDate, new Date()) // 审核时间
        .set(BasePlateIn::getAuditor, LoginHelper.getNickname()) // 审核人
        .eq(BasePlateIn::getInId, inId);
      this.update(lambda);//提交

      //#region 审核成功后根据源借出单号回更借出单已归还数量
      // 根据源借出单号查询借出单
      LambdaQueryWrapper<BasePlateOut> plateOutLambda1 = new LambdaQueryWrapper<>();
      plateOutLambda1.eq(BasePlateOut::getCustomerOrderCode, mainInfo.getSourceOutCode())
        .eq(BasePlateOut::getOutId, mainInfo.getOutId());
      var outInfo = basePlateOutService.getOne(plateOutLambda1);//提交

      if (ObjectUtil.isNull(outInfo)) {
        throw new ServiceException("源借出单号不存在");
      }
      // 修改主表数据
      LambdaUpdateWrapper<BasePlateOut> plateOutLambda = new UpdateWrapper<BasePlateOut>().lambda();
      plateOutLambda.set(BasePlateOut::getTotalReturnedQty, mainInfo.getTotalReturnQty()) // 合计归还数量
        .set(BasePlateOut::getTotalUnreturnedQty, B.sub(outInfo.getTotalOutQty(), mainInfo.getTotalReturnQty())) // 合计未归还数量=借出数量-归还数量
        .eq(BasePlateOut::getOutCode, mainInfo.getSourceOutCode());
      basePlateOutService.update(plateOutLambda);//提交
    }

    return R.ok("审核成功");
  }
  //#endregion

  //#region 容器归还导入数据
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
      Long consignorId = Convert.toLong(request.getParameter("consignorId"));
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
      int i = 0;
      int successCount = 0;

      // 对单据进行分组
      var groupList = sysImportService.getGroupList(dataList, importId);
      for (Map<String, Object> item : groupList) {
        i++;
        sysImportService.writeMsg("正在导入第{}行", i);
        final String clientShortName = Convert.toStr(item.get("clientShortName")); // 客户
        final String storageName = Convert.toStr(item.get("storageName")); // 仓库

        LambdaQueryWrapper<BaseClient> clientLambdaQueryWrapper = new LambdaQueryWrapper<>();
        clientLambdaQueryWrapper.eq(BaseClient::getClientShortName, clientShortName);
        var clientInfo = baseClientService.getOne(clientLambdaQueryWrapper);

        LambdaQueryWrapper<BaseStorage> storageLambdaQueryWrapper = new LambdaQueryWrapper<>();
        storageLambdaQueryWrapper.eq(BaseStorage::getStorageName, storageName);
        var storageInfo = baseStorageService.getOne(storageLambdaQueryWrapper);

        // 获得明细数据
        var detailList = sysImportService.getGroupDetails(dataList, item, importId);

        BasePlateIn orderInfo = new BasePlateIn();
        BeanUtil.copyProperties(item, orderInfo);
        String orderCode = DBUtils.getCodeRegular(MenuEnum.MENU_1812, loginUser.getTenantId());
        orderInfo.setInCode(orderCode);
        if (ObjectUtil.isNotNull(clientInfo)) {
          orderInfo.setPlaceOrigin(clientInfo.getSiteName());
          orderInfo.setPlaceOriginId(clientInfo.getSiteId());
        }
        if (ObjectUtil.isNotNull(storageInfo)) {
          orderInfo.setPlaceDestinationId(storageInfo.getSiteId());
          orderInfo.setPlaceDestination(storageInfo.getSiteName());
        }
        orderInfo.setCreateBy(loginUser.getUserId());
        orderInfo.setCreateByName(loginUser.getNickname());
        orderInfo.setUserId(loginUser.getUserId());
        orderInfo.setNickName(loginUser.getNickname());

        // 保存主表
        this.getBaseMapper().insert(orderInfo);

        /*——————————————————————————————————————————————————
         * 处理明细，将明细挂载到主表下
         *——————————————————————————————————————————————————*/
        List<BasePlateInDetail> orderDetailList = new ArrayList<>();
        for (var detail : detailList) {
          var detailInfo = new BasePlateInDetail();

          final String plateName = Convert.toStr(detail.get("plateName")); // 容器名称
          LambdaQueryWrapper<BasePlateProduct> productLambdaQueryWrapper = new LambdaQueryWrapper<>();
          productLambdaQueryWrapper.eq(BasePlateProduct::getProductName, plateName)
            .last("limit 1");
          var productInfo = basePlateProductService.getOne(productLambdaQueryWrapper);

          if (ObjectUtil.isNotNull(productInfo)) {
            detailInfo.setPlateType(productInfo.getPlateType());
            detailInfo.setPlateSpec(productInfo.getPlateSpec());
            detailInfo.setPlateName(productInfo.getPlateName());
            detailInfo.setWeight(productInfo.getWeight());
            detailInfo.setUnitCube(productInfo.getUnitCube());
            detailInfo.setReturnQty(Convert.toBigDecimal(detail.get("returnQty")));
            detailInfo.setRowWeight(B.mul(productInfo.getWeight(), detailInfo.getReturnQty())); // 小计重量
            detailInfo.setRowCube(B.mul(productInfo.getUnitCube(), detailInfo.getReturnQty())); // 小计体积
          }

          //建立关系，设置主表ID
          detailInfo.setInId(orderInfo.getInId());
          basePlateInDetailService.save(detailInfo);
          orderDetailList.add(detailInfo);
        }

        // 主表求和字段计算
        var totalReturnQty = orderDetailList.stream().map(BasePlateInDetail::getReturnQty).reduce(BigDecimal.ZERO, BigDecimal::add);
        orderInfo.setTotalReturnQty(totalReturnQty);
        var totalWeight = orderDetailList.stream().map(BasePlateInDetail::getRowWeight).reduce(BigDecimal.ZERO, BigDecimal::add);
        orderInfo.setTotalWeight(totalWeight);
        var totalCube = orderDetailList.stream().map(BasePlateInDetail::getRowCube).reduce(BigDecimal.ZERO, BigDecimal::add);
        orderInfo.setTotalCube(totalCube);

        // 更新主表字段
        this.getBaseMapper().updateById(orderInfo);
        successCount++;
      }

      var endDate = DateUtil.date();
      var totalSeconds = DateUtil.between(startDate, endDate, DateUnit.SECOND);

      // 更新日志
      String remark = StringUtils.format("导入完成，新增{}条，导入成功,共耗时{}秒", successCount, totalSeconds);
      sysOperLogService.updateRemark(key, remark);
      sysImportService.writeMsgBlue("导入完成，新增{}条", successCount);
      sysImportService.writeMsg("导入成功,共耗时{}秒", totalSeconds);
      transactionManager.commit(transaction); // 手动提交事务
    } catch (Exception exception) {
      sysImportService.writeMsgRed("导入错误：{}", exception);
      transactionManager.rollback(transaction); // 手动回滚事务
    }
    sysImportService.writeEnd(); // 标记结算
  }
  //#endregion

  //#region 删除数据
  @Override
  public R<Void> deleteData(Map<String, Object> map) {
    Long inId = Convert.toLong(map.get("inId"));

    Assert.isFalse(ObjectUtil.isNull(inId), "至少选择一条数据");
    IBasePlateInCostService basePlateInCostService = SpringUtils.getBean(IBasePlateInCostService.class); // 需要动态获取bean，否则循环冲突

    LambdaQueryWrapper<BasePlateInCost> plateInLambdaQueryWrapper = new LambdaQueryWrapper<>();
    plateInLambdaQueryWrapper.eq(BasePlateInCost::getInId, inId);
    var mainInfo = basePlateInCostService.list(plateInLambdaQueryWrapper);

    if (!mainInfo.isEmpty()) {
      for (var item : mainInfo) {
        //删除对应的 费用明细
        basePlateInCostService.deleteById(item.getCostId());
      }

    }
    return R.ok();
  }
  //#endregion

  /**
   * 已核对
   *
   * @param map 审核参数
   * @return 返回查询列表数据
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> isCheck(Map<String, Object> map) {
    String[] ids = StringUtils.split(map.get("ids").toString(), ",");
    Long[] _ids = Convert.toLongArray(ids);
    IBasePlateInService sortingBean = SpringUtils.getBean(IBasePlateInService.class); // 需要动态获取bean，否则循环冲突

    for (long inId : _ids) {

      LambdaQueryWrapper<BasePlateIn> plateInLambdaQueryWrapper = new LambdaQueryWrapper<>();
      plateInLambdaQueryWrapper.eq(BasePlateIn::getInId, inId);
      var mainInfo = sortingBean.getOne(plateInLambdaQueryWrapper);

      if (ObjectUtil.isNull(mainInfo)) {
        throw new ServiceException("单据为空");
      }

      // 修改数据
      LambdaUpdateWrapper<BasePlateIn> lambda = new UpdateWrapper<BasePlateIn>().lambda();
      lambda.set(BasePlateIn::getStatusText, PlateStatusEnum.ISCHECK.getName()) // 已核对
        .eq(BasePlateIn::getInId, inId);
      this.update(lambda);//提交
    }

    return R.ok("核对成功");
  }
  //#endregion

  /**
   * 垫板入库批量审核
   *
   * @param map 审核参数
   * @return 返回查询列表数据
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> backPlateAudit(Map<String, Object> map) {
    Date returnDate = Convert.toDate(map.get("returnDate"));

    String[] ids = StringUtils.split(map.get("ids").toString(), ",");
    Long[] _ids = Convert.toLongArray(ids);
    IBasePlateInService sortingBean = SpringUtils.getBean(IBasePlateInService.class); // 需要动态获取bean，否则循环冲突

    for (long inId : _ids) {

      LambdaQueryWrapper<BasePlateIn> plateInLambdaQueryWrapper = new LambdaQueryWrapper<>();
      plateInLambdaQueryWrapper.eq(BasePlateIn::getInId, inId);
      var mainInfo = sortingBean.getOne(plateInLambdaQueryWrapper);

      if (ObjectUtil.isNull(mainInfo)) {
        throw new ServiceException("单据为空");
      }

      // 归还单明细
      LambdaQueryWrapper<BasePlateInDetail> queryWrapper = new LambdaQueryWrapper<>();
      queryWrapper.eq(BasePlateInDetail::getInId, mainInfo.getInId());
      var detailList = basePlateInDetailService.selectList(queryWrapper);
      if (ObjectUtil.isNull(detailList)) {
        throw new ServiceException("单据明细为空，请检查");
      }
      for (var item : detailList) {

        BaseStoragePlate dateInfo = new BaseStoragePlate();
        BeanUtil.copyProperties(mainInfo, dateInfo);
        dateInfo.setBillId(mainInfo.getInId());
        dateInfo.setBillCode(mainInfo.getInCode());

        dateInfo.setSourceType(PlateSourceTypeEnum.BACKPLATE.getName());
        dateInfo.setPlateSpec(item.getPlateSpec());
        dateInfo.setPlateType(item.getPlateType());
        dateInfo.setPlateName(item.getPlateName());
        dateInfo.setPlateAttribute(item.getPlateAttribute());
        dateInfo.setEnterQuantity(item.getReturnQty()); // 入库数量
        dateInfo.setUnReturnFactoryQty(item.getReturnQty()); // 未返厂数量
        dateInfo.setReturnFactoryQty(BigDecimal.ZERO); // 已返厂数量
        baseStoragePlateService.save(dateInfo);


        LambdaQueryWrapper<BasePlateOut> outqwp = new LambdaQueryWrapper<>();
        outqwp.eq(BasePlateOut::getCustomerOrderCode, mainInfo.getSourceOutCode())
          .eq(BasePlateOut::getOutId, mainInfo.getOutId());
        var outqwpInfo = basePlateOutService.getOne(outqwp);
        if (ObjectUtil.isNotNull(outqwpInfo)) {
          // 修改明细数据
          LambdaUpdateWrapper<BasePlateOutDetail> plateOutDetailLambda = new UpdateWrapper<BasePlateOutDetail>().lambda();
          plateOutDetailLambda.set(BasePlateOutDetail::getReturnedQty, item.getReturnQty()) // 已归还数量
            .set(BasePlateOutDetail::getUnreturnedQty, B.sub(item.getReturnQty(), item.getReturnQty())) // 未归还数量=借出数量-归还数量
            .eq(BasePlateOutDetail::getPlateType, item.getPlateType())
            .eq(BasePlateOutDetail::getPlateSpec, item.getPlateSpec())
            .eq(BasePlateOutDetail::getOutDetailId, item.getSourceDetailId()) // 明细id
            .eq(BasePlateOutDetail::getOutId, outqwpInfo.getOutId());
          basePlateOutDetailService.update(plateOutDetailLambda);//提交
        }
      }

      // 更新明细数据
      LambdaUpdateWrapper<BasePlateIn> lambda = new UpdateWrapper<BasePlateIn>().lambda();
      lambda.set(BasePlateIn::getStatusText, AuditEnum.AUDITED_SUCCESS.getName()) // 状态审核成功
        .set(BasePlateIn::getAuditing, AuditEnum.AUDITED_SUCCESS.getId()) // 审核状态审核成功
        .set(BasePlateIn::getAuditDate, new Date()) // 审核时间
        .set(BasePlateIn::getAuditor, LoginHelper.getNickname()) // 审核人
        .set(BasePlateIn::getReturnDate, returnDate) // 归还日期
        .eq(BasePlateIn::getInId, inId);
      this.update(lambda);//提交

    }

    return R.ok("审核成功");
  }
  //#endregion
}

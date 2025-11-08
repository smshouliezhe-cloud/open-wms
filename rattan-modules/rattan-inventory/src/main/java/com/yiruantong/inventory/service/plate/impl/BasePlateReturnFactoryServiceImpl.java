package com.yiruantong.inventory.service.plate.impl;

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
import com.yiruantong.basic.domain.storage.BasePlateProduct;
import com.yiruantong.basic.domain.storage.BaseStorage;
import com.yiruantong.basic.service.client.IBaseClientService;
import com.yiruantong.basic.service.storage.IBasePlateProductService;
import com.yiruantong.basic.service.storage.IBaseStorageService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.base.AuditEnum;
import com.yiruantong.common.core.enums.base.MenuEnum;
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
import com.yiruantong.inventory.domain.plate.BasePlateReturnFactory;
import com.yiruantong.inventory.domain.plate.BasePlateReturnFactoryDetail;
import com.yiruantong.inventory.domain.plate.BasePlateReturnOut;
import com.yiruantong.inventory.domain.plate.BaseStoragePlate;
import com.yiruantong.inventory.domain.plate.bo.BasePlateReturnFactoryBo;
import com.yiruantong.inventory.domain.plate.vo.BasePlateReturnFactoryVo;
import com.yiruantong.inventory.mapper.plate.BasePlateReturnFactoryMapper;
import com.yiruantong.inventory.service.plate.IBasePlateReturnFactoryDetailService;
import com.yiruantong.inventory.service.plate.IBasePlateReturnFactoryService;
import com.yiruantong.inventory.service.plate.IBasePlateReturnOutService;
import com.yiruantong.inventory.service.plate.IBaseStoragePlateService;
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
import java.util.*;

/**
 * 容器返厂单Service业务层处理
 *
 * @author YRT
 * @date 2024-03-05
 */
@RequiredArgsConstructor
@Service
public class BasePlateReturnFactoryServiceImpl extends ServiceImplPlus<BasePlateReturnFactoryMapper, BasePlateReturnFactory, BasePlateReturnFactoryVo, BasePlateReturnFactoryBo> implements IBasePlateReturnFactoryService {
  private final IBasePlateReturnFactoryDetailService returnFactoryDetailService;
  private final IBaseStoragePlateService baseStoragePlateService;
  private final IBasePlateReturnOutService basePlateReturnOutService;
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
    IBasePlateReturnFactoryService sortingBean = SpringUtils.getBean(IBasePlateReturnFactoryService.class); // 需要动态获取bean，否则循环冲突

    for (long returnFactoryId : ids) {

      LambdaQueryWrapper<BasePlateReturnFactory> returnLambdaQueryWrapper = new LambdaQueryWrapper<>();
      returnLambdaQueryWrapper.eq(BasePlateReturnFactory::getReturnFactoryId, returnFactoryId);
      var returnInfo = sortingBean.getOne(returnLambdaQueryWrapper);

      if (ObjectUtil.isNull(returnInfo)) {
        throw new ServiceException("单据为空");
      }

      // 归还单明细
      LambdaQueryWrapper<BasePlateReturnFactoryDetail> queryWrapper = new LambdaQueryWrapper<>();
      queryWrapper.eq(BasePlateReturnFactoryDetail::getReturnFactoryId, returnInfo.getReturnFactoryId());
      var returnDetails = returnFactoryDetailService.selectList(queryWrapper);
      if (ObjectUtil.isNull(returnDetails)) {
        throw new ServiceException("单据明细为空，请检查");
      }
      // 循环明细
      for (var item : returnDetails) {

        // 按仓库 + 容器类别 + 容器规格 查询仓库容器
        LambdaQueryWrapper<BaseStoragePlate> storagePlateQueryWrapper = new LambdaQueryWrapper<>();
        storagePlateQueryWrapper.eq(BaseStoragePlate::getStorageId, returnInfo.getStorageId())
          .eq(BaseStoragePlate::getPlateType, item.getPlateType())
          .eq(BaseStoragePlate::getPlateSpec, item.getPlateSpec())
          .gt(BaseStoragePlate::getUnReturnFactoryQty, BigDecimal.ZERO); // 未返厂数量大于0
        var storagePlateList = baseStoragePlateService.list(storagePlateQueryWrapper);
        // 排序顺序：生产日期
//        storagePlateList = storagePlateList.stream().sorted(Comparator.comparing(BaseStoragePlate::getCreateTime, Comparator.nullsFirst(Date::compareTo))).toList();
        storagePlateList = storagePlateList.stream().sorted(Comparator.comparing(BaseStoragePlate::getCreateTime)).toList();

        BigDecimal totalUnReturnFactoryQty;
        totalUnReturnFactoryQty = storagePlateList.stream().map(BaseStoragePlate::getUnReturnFactoryQty).reduce(BigDecimal.ZERO, BigDecimal::add); // 未返厂数量合计

        if (ObjectUtil.isNotNull(storagePlateList)) {
          if (B.isLess(totalUnReturnFactoryQty, item.getReturnFactoryQty())) {
            throw new ServiceException("审核失败！返厂数量大于剩余未返厂数量");
          }
        }

        BigDecimal lackStorage = item.getReturnFactoryQty();
        BigDecimal validStorage;

        for (var items : storagePlateList) {
          if (B.isLessOrEqual(lackStorage)) {
            break;
          }
          validStorage = items.getUnReturnFactoryQty();
          BigDecimal holderQty;

          if (B.isGreater(validStorage, lackStorage)) { // 当前库存大于需求数量
            holderQty = lackStorage;
            lackStorage = BigDecimal.ZERO;
          } else {
            holderQty = validStorage;
            lackStorage = B.sub(lackStorage, validStorage);
          }

          // 更新缓存中的有效库存
          BigDecimal unReturnFactoryQty = B.sub(Convert.toBigDecimal(items.getUnReturnFactoryQty()), holderQty); // 消减未返厂数量，未返厂数量减当前明细数量
          items.setUnReturnFactoryQty(unReturnFactoryQty);  // 未返厂数量

          BigDecimal returnFactoryQty = B.add(Convert.toBigDecimal(items.getReturnFactoryQty()), holderQty); // 增加返厂数量，返厂数量加当前明细数量
          items.setReturnFactoryQty(returnFactoryQty); // 返厂数量

          // 更新仓库容器数据
          LambdaUpdateWrapper<BaseStoragePlate> lambda2 = new UpdateWrapper<BaseStoragePlate>().lambda();
          lambda2.set(BaseStoragePlate::getUnReturnFactoryQty, unReturnFactoryQty) //
            .set(BaseStoragePlate::getReturnFactoryQty, returnFactoryQty) // 审核状态审核成功
            .eq(BaseStoragePlate::getBillId, items.getBillId())
            .eq(BaseStoragePlate::getStorageId, returnInfo.getStorageId())
            .eq(BaseStoragePlate::getPlateType, item.getPlateType())
            .eq(BaseStoragePlate::getPlateSpec, item.getPlateSpec());
          baseStoragePlateService.update(lambda2);//提交


          // 生成容器返厂出库记录明细
          BasePlateReturnOut returnOut = new BasePlateReturnOut();
          BeanUtil.copyProperties(items, returnOut);
          returnOut.setReturnFactoryId(returnInfo.getReturnFactoryId());
          returnOut.setReturnFactoryQty(Convert.toLong(holderQty)); // 当前消减库存数量
          returnOut.setClientShortName(items.getClientShortName());
          returnOut.setConsignorNameSale(items.getConsignorNameSale());
          basePlateReturnOutService.save(returnOut);
        }

      }

      // 修改返厂单数据
      LambdaUpdateWrapper<BasePlateReturnFactory> lambda = new UpdateWrapper<BasePlateReturnFactory>().lambda();
      lambda.set(BasePlateReturnFactory::getStatusText, AuditEnum.AUDITED_SUCCESS.getName()) // 状态审核成功
        .set(BasePlateReturnFactory::getAuditing, AuditEnum.AUDITED_SUCCESS.getId()) // 审核状态审核成功
        .set(BasePlateReturnFactory::getAuditDate, new Date()) // 审核时间
        .set(BasePlateReturnFactory::getAuditor, LoginHelper.getNickname()) // 审核人
        .eq(BasePlateReturnFactory::getReturnFactoryId, returnFactoryId);
      this.update(lambda);//提交

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
        final String storageName = Convert.toStr(item.get("storageName")); // 仓库

        LambdaQueryWrapper<BaseStorage> storageLambdaQueryWrapper = new LambdaQueryWrapper<>();
        storageLambdaQueryWrapper.eq(BaseStorage::getStorageName, storageName);
        var storageInfo = baseStorageService.getOne(storageLambdaQueryWrapper);

        // 获得明细数据
        var detailList = sysImportService.getGroupDetails(dataList, item, importId);

        BasePlateReturnFactory orderInfo = new BasePlateReturnFactory();
        BeanUtil.copyProperties(item, orderInfo);
        String orderCode = DBUtils.getCodeRegular(MenuEnum.MENU_1812, loginUser.getTenantId());
        orderInfo.setReturnFactoryCode(orderCode);
        if (ObjectUtil.isNotNull(storageInfo)) {
          orderInfo.setPlaceOrigin(storageInfo.getSiteName());
          orderInfo.setPlaceOriginId(storageInfo.getSiteId());
        }

        orderInfo.setCreateBy(loginUser.getUserId());
        orderInfo.setCreateByName(loginUser.getNickname());

        // 保存主表
        this.getBaseMapper().insert(orderInfo);

        /*——————————————————————————————————————————————————
         * 处理明细，将明细挂载到主表下
         *——————————————————————————————————————————————————*/
        List<BasePlateReturnFactoryDetail> orderDetailList = new ArrayList<>();
        for (var detail : detailList) {
          var detailInfo = new BasePlateReturnFactoryDetail();

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
            detailInfo.setReturnFactoryQty(Convert.toBigDecimal(detail.get("returnQty")));
            detailInfo.setRowWeight(B.mul(productInfo.getWeight(), detailInfo.getReturnFactoryQty())); // 小计重量
            detailInfo.setRowCube(B.mul(productInfo.getUnitCube(), detailInfo.getReturnFactoryQty())); // 小计体积
          }

          //建立关系，设置主表ID
          detailInfo.setReturnFactoryId(orderInfo.getReturnFactoryId());
          returnFactoryDetailService.save(detailInfo);
          orderDetailList.add(detailInfo);
        }

        // 主表求和字段计算
        var totalReturnFactoryQty = orderDetailList.stream().map(BasePlateReturnFactoryDetail::getReturnFactoryQty).reduce(BigDecimal.ZERO, BigDecimal::add);
        orderInfo.setTotalReturnFactoryQty(totalReturnFactoryQty);
        var totalWeight = orderDetailList.stream().map(BasePlateReturnFactoryDetail::getRowWeight).reduce(BigDecimal.ZERO, BigDecimal::add);
        orderInfo.setTotalWeight(totalWeight);
        var totalCube = orderDetailList.stream().map(BasePlateReturnFactoryDetail::getRowCube).reduce(BigDecimal.ZERO, BigDecimal::add);
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
}

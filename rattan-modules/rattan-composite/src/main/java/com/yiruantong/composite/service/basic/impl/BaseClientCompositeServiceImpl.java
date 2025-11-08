package com.yiruantong.composite.service.basic.impl;


import lombok.RequiredArgsConstructor;
import com.yiruantong.composite.service.basic.IBaseClientCompositeService;
import org.springframework.stereotype.Service;

/**
 * 客户信息操作
 */
@RequiredArgsConstructor
@Service
public class BaseClientCompositeServiceImpl implements IBaseClientCompositeService {
//  private final DataSourceTransactionManager transactionManager;
//  private final ISysImportService sysImportService;
//  private final IBaseClientService baseClientService;
//
//
//  //#region 客户信息批量导入
//  @Async
//  @Override
//  public void importData(InputStream inputStream, Long importId, HttpServletRequest request, LoginUser loginUser) {
//    // 手动开启事务  start
//    DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
//    definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//    TransactionStatus transaction = transactionManager.getTransaction(definition);
//
//    TenantHelper.setDynamic(loginUser.getTenantId()); // 在异步线程里面需要手动设置当前租户ID，缓存用到
//    String key = request.getParameter("key");
//    sysImportService.setKey(key);
//    DateTime startDate = DateUtil.date(); // 导入开始时间
//
//    try {
//      Long storageId = Convert.toLong(request.getParameter("storageId"));
//      Long consignorId = Convert.toLong(request.getParameter("consignorId"));
//      // 是否开启唯一码
//      sysImportService.isAssert(StringUtils.isEmpty(key), "上传key不存在");
//      sysImportService.isAssert(ObjectUtil.isEmpty(importId), "未关联模板，不可导入");
//
//      // 处理解析结果
//      ExcelResult<Map<String, Object>> excelResult = null;
//      excelResult = ExcelUtil.importExcel(inputStream, new ImportCommonListener());
//      var dataList = excelResult.getList();
//
//      // 通用验证
//      sysImportService.setError(false);
//      R<Void> result = sysImportService.commonCheck(dataList, importId, request, loginUser);
//      sysImportService.isAssert(!result.isResult(), "导入数据有错误，请处理好重新导入");
//
//      sysImportService.writeMsg("开始导入...");
//
//      sysImportService.isAssert(sysImportService.isError(), "导入数据有错误，请处理好重新导入");
//
//      // 循环处理分组数据，groupList对应的是主表数据
//      int i = 1;
//      int successCount = 0;
//      int updateCount = 0;
//      for (var item : dataList) {
//        i++;
//
////        final String lineName = Convert.toStr(item.get("lineName")); // 配送路线
////
////        LambdaQueryWrapper<TmsLine> tmsLineLambdaQueryWrapper = new LambdaQueryWrapper<>();
////        tmsLineLambdaQueryWrapper.eq(TmsLine::getLineName, lineName); // 客户编号
////        var lineInfo = tmsLineService.selectOne(tmsLineLambdaQueryWrapper);
////        if(ObjectUtil.isNull(lineInfo)){
////          sysImportService.isAssert(StringUtils.isEmpty(lineInfo.getLineName), "模板线路不存在，请检查导入模板后重新导入");
////        }
//
//        final String clientCode = Convert.toStr(item.get("clientCode"));
//
//        LambdaQueryWrapper<BaseClient> clientLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        clientLambdaQueryWrapper.eq(BaseClient::getClientCode, clientCode); // 客户编号
//        var dataInfo = baseClientService.getBaseMapper().selectOne(clientLambdaQueryWrapper);
//
//        if (ObjectUtil.isNotEmpty(dataInfo)) {
//          BeanUtil.copyProperties(item, dataInfo);
//          sysImportService.writeMsgBlue("第{}行、{}正在导入更新...", i, dataInfo.getClientCode());
//          updateCount++;
//        } else {
//          dataInfo = new BaseClient(); // 新建数据
//          // 数据拷贝
//          BeanUtil.copyProperties(item, dataInfo);
//          if(ObjectUtil.isNotEmpty(clientCode)){
//            dataInfo.setClientCode(clientCode);
//          }else {
//            dataInfo.setClientCode(DBUtils.getCodeRegular(MenuEnum.MENU_2063.getId(), loginUser.getTenantId()));
//          }
//          dataInfo.setCreateBy(loginUser.getUserId());
//          dataInfo.setCreateByName(loginUser.getNickname());
//          dataInfo.setUserId(loginUser.getUserId());
//          dataInfo.setNickName(loginUser.getNickname());
//          sysImportService.writeMsgBlue("第{}行、{}正在导入新增...", i, dataInfo.getClientCode());
//          successCount++;
//        }
//        baseClientService.saveOrUpdate(dataInfo);
//      }
//
//      var endDate = DateUtil.date();
//      var totalSeconds = DateUtil.between(startDate, endDate, DateUnit.SECOND);
//      sysImportService.writeMsgBlue("导入完成，新增{}条", successCount);
//      sysImportService.writeMsgBlue("导入完成，更新{}条", updateCount);
//      sysImportService.writeMsg("导入成功,共耗时{}秒", totalSeconds);
//      transactionManager.commit(transaction); // 手动提交事务
//    } catch (Exception exception) {
//      sysImportService.writeMsgRed("导入错误：{}", exception);
//      transactionManager.rollback(transaction); // 手动回滚事务
//    }
//    sysImportService.writeEnd(); // 标记结算
//  }
//  //#endregion

}

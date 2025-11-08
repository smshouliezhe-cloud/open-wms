package com.yiruantong.basic.service.tms.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yiruantong.basic.domain.base.bo.GetListBo;
import com.yiruantong.basic.domain.tms.TmsLine;
import com.yiruantong.basic.domain.tms.TmsSite;
import com.yiruantong.basic.domain.tms.bo.TmsLineBo;
import com.yiruantong.basic.domain.tms.vo.TmsLineVo;
import com.yiruantong.basic.mapper.tms.TmsLineMapper;
import com.yiruantong.basic.service.tms.ITmsLineService;
import com.yiruantong.basic.service.tms.ITmsSiteService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.base.AuditEnum;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.in.InOrderStatusEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.DateUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.excel.core.ExcelResult;
import com.yiruantong.common.excel.listener.ImportCommonListener;
import com.yiruantong.common.excel.utils.ExcelUtil;
import com.yiruantong.common.mybatis.core.domain.bo.SaveEditorBo;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.tenant.helper.TenantHelper;
import com.yiruantong.system.service.dataHandler.ISysImportService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 线路管理Service业务层处理
 *
 * @author YRT
 * @date 2023-12-28
 */
@RequiredArgsConstructor
@Service
public class TmsLineServiceImpl extends ServiceImplPlus<TmsLineMapper, TmsLine, TmsLineVo, TmsLineBo> implements ITmsLineService {
  private final DataSourceTransactionManager transactionManager;
  private final ISysImportService sysImportService;
  private final ITmsSiteService tmsSiteService;


  //#region 保存前事件

  /**
   * 保存前事件
   *
   * @param saveEditorBo 保存提交bo数据
   */
  @Override
  public void beforeSaveEditor(SaveEditorBo<TmsLineBo> saveEditorBo) {

    LambdaQueryWrapper<TmsLine> lineLambdaQueryWrapper = new LambdaQueryWrapper<>();
    lineLambdaQueryWrapper.eq(TmsLine::getLineName, saveEditorBo.getData().getMaster().getLineName());
    // 如果线路Id不为空
    if (ObjectUtil.isNotEmpty(saveEditorBo.getData().getMaster().getLineId())) {
      lineLambdaQueryWrapper.eq(TmsLine::getLineName, saveEditorBo.getData().getMaster().getLineName())
        .ne(TmsLine::getLineId, saveEditorBo.getData().getMaster().getLineId());
    }

    long count = this.count(lineLambdaQueryWrapper);
    if (count > 0) {
      throw new ServiceException("线路名称【" + saveEditorBo.getData().getMaster().getLineName() + "】已存在不允许重复添加，无法添加！");
    }
    super.beforeSaveEditor(saveEditorBo);
  }
  //#endregion

  public R<Void> multiAuditing(List<Long> ids) {
    for (long lineId : ids) {
      LambdaQueryWrapper<TmsLine> lineLambdaQueryWrapper = new LambdaQueryWrapper<>();
      lineLambdaQueryWrapper.eq(TmsLine::getLineId, lineId);
      TmsLine tmsLine = this.getOne(lineLambdaQueryWrapper);

      if (ObjectUtil.isEmpty(tmsLine)) {
        throw new ServiceException("未获取到线路");
      }
      Byte auditing = tmsLine.getAuditing();
      if (ObjectUtil.isNotNull(auditing) && !auditing.equals(AuditEnum.AUDIT.getId())) {
        throw new ServiceException(tmsLine.getLineCode() + "只有新建的单子才允许审核");
      }
      //修改数据
      LambdaUpdateWrapper<TmsLine> lambda = new UpdateWrapper<TmsLine>().lambda();
      lambda.set(TmsLine::getAuditDate, DateUtils.getNowDate())
        .set(TmsLine::getAuditor, LoginHelper.getNickname())
        .set(TmsLine::getAuditing, AuditEnum.AUDITED_SUCCESS.getId())
        .set(TmsLine::getStatusText, InOrderStatusEnum.SUCCESS.getName())
        .eq(TmsLine::getLineId, lineId);
      this.update(lambda);//提交
    }
    return R.ok("审核成功");
  }

  @Override
  public TmsLine getByName(String name) {
    LambdaQueryWrapper<TmsLine> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(TmsLine::getLineName, name);
    return this.getOnly(lambdaQueryWrapper);
  }

  //#region getList 下拉框通用查询

  /**
   * getList 下拉框通用查询
   *
   * @param getListBo@return 返回查询结果
   */
  @Override
  public List<Map<String, Object>> getList(GetListBo getListBo) {
    Integer take = Optional.ofNullable(getListBo).map(m -> Convert.toInt(m.getTake())).orElse(500); // 查询top N，如果为空，默认50
    boolean isDesc = Optional.ofNullable(getListBo).map(GetListBo::isDesc).orElse(false); // 倒序方式
    String name = getListBo.getName();
    // amis下拉框搜索
    if (StringUtils.isEmpty(name)) {
      name = Convert.toStr(getListBo.getTerm());
    }

    String searchFields = Convert.toStr(getListBo.getSearchFields());
    LoginUser loginUser = LoginHelper.getLoginUser();
    if (ObjectUtil.isNull(loginUser)) return null;

    QueryWrapper<TmsLine> queryWrapper = new QueryWrapper<>();
    String finalName = name;
    queryWrapper.lambda()
//      .eq(StringUtils.isNotEmpty(getListBo.getType()), TmsLine::getLineType, getListBo.getType())
      .isNotNull(StringUtils.isEmpty(getListBo.getType()), TmsLine::getLineCode)
      .and(StringUtils.isNotEmpty(finalName), a -> a
        .like(TmsLine::getLineName, finalName)
        .or()
        .like(TmsLine::getLineCode, finalName)
      )
    ; // 关键词对编号和名称模糊查询

    try {
      List<String> fields = CollUtil.newArrayList("lineId", "lineCode", "lineName"); // 查询默认字段
      // 自定义查询字段
      if (StringUtils.isNotEmpty(searchFields)) {
        fields = CollUtil.newArrayList(StringUtils.split(searchFields, ",")); // 查询指定字段
      }
      if ("*".equals(searchFields)) {
        fields.clear(); // 清空，查询所有字段
      }

      // 自定义查询字段
      if (!fields.isEmpty()) {
        List<String> finalFields = fields;
        queryWrapper.select(TmsLine.class, s -> finalFields.contains(s.getProperty()));
      }

      queryWrapper.lambda().orderByDesc(isDesc, TmsLine::getLineId).orderByAsc(!isDesc, TmsLine::getLineId); // 排序
      queryWrapper.last("limit " + take); // top N

      var mapList = this.baseMapper.selectMaps(queryWrapper);
      for (var map : mapList) {
        String expandFields = Convert.toStr(map.get("expandFields"));
        if (StringUtils.isNotEmpty(expandFields)) {
          map.put("expandFields", JSONUtil.parseObj(expandFields));
        }
      }
      return mapList;
    } catch (Exception error) {
      var msg = "异常错误信息：" + error.getCause();
      throw new ServiceException(msg);
    }
  }

  //#endregion

  //#region 线路管理导入数据
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
        final String lineName = Convert.toStr(row.get("lineName"));
        final String distributionSite = Convert.toStr(row.get("distributionSite"));
        final String unloadSite = Convert.toStr(row.get("unloadSite"));

        TmsLine dataInfo;
        LambdaQueryWrapper<TmsLine> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(TmsLine::getLineName, lineName)
          .eq(TmsLine::getDistributionSite, distributionSite)
          .eq(TmsLine::getUnloadSite, unloadSite);
        dataInfo = this.getOne(lambdaQueryWrapper);

        // 如果不存在，增长线路
        if (ObjectUtil.isNull(dataInfo)) {
          successCount++;
          dataInfo = new TmsLine(); // 新建数据

          // 数据拷贝
          BeanUtil.copyProperties(row, dataInfo);
          dataInfo.setLineCode(DBUtils.getCodeRegular(MenuEnum.MENU_1844));
          TmsSite tmsSiteA = tmsSiteService.getByName(distributionSite);
          TmsSite tmsSiteB = tmsSiteService.getByName(unloadSite);
          dataInfo.setDistributionSiteId(tmsSiteA.getSiteId());
          dataInfo.setUnloadSiteId(tmsSiteB.getSiteId());
          dataInfo.setCreateBy(loginUser.getUserId());
          dataInfo.setCreateByName(loginUser.getNickname());
          sysImportService.writeMsgBlue("第{}行、{}正在导入新增...", i, dataInfo.getLineName());
        } else {
          sysImportService.writeMsgBlue("第{}行、{}正在导入更新...", i, dataInfo.getLineName());
          updateCount++;
        }

        BeanUtil.copyProperties(row, dataInfo);
        // 扩展字段处理
        dataInfo.setExpandFields(sysImportService.getExpandFields(row));
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

package com.yiruantong.basic.service.product.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import com.yiruantong.common.mybatis.core.dto.QueryBo;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.enums.DataTypeEnum;
import com.yiruantong.common.mybatis.enums.QueryTypeEnum;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.product.BaseProductType;
import com.yiruantong.basic.domain.product.bo.BaseProductTypeBo;
import com.yiruantong.basic.domain.product.vo.BaseProductTypeVo;
import com.yiruantong.basic.mapper.product.BaseProductTypeMapper;
import com.yiruantong.basic.service.product.IBaseProductTypeService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.utils.DateUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.excel.core.ExcelResult;
import com.yiruantong.common.excel.listener.ImportCommonListener;
import com.yiruantong.common.excel.utils.ExcelUtil;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.tenant.helper.TenantHelper;
import com.yiruantong.system.service.dataHandler.ISysImportService;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.InputStream;
import java.util.*;

/**
 * 商品类目管理Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-15
 */
@RequiredArgsConstructor
@Service
public class BaseProductTypeServiceImpl extends ServiceImplPlus<BaseProductTypeMapper, BaseProductType, BaseProductTypeVo, BaseProductTypeBo> implements IBaseProductTypeService {
  private final DataSourceTransactionManager transactionManager;
  private final ISysImportService sysImportService;

  //#region 根据名称获取商品类目
  @Override
  public BaseProductType getByName(String name) {
    LambdaQueryWrapper<BaseProductType> typeLambdaQueryWrapper = new LambdaQueryWrapper<>();
    typeLambdaQueryWrapper.eq(BaseProductType::getTypeName, name);

    return this.getOnly(typeLambdaQueryWrapper);
  }
  //#endregion

  //#region 新增数据
  /**
   * 新增数据
   */
  @Override
  public R<Map<String, Object>> add(BaseProductTypeBo bo) {


    if (ObjectUtil.isNull(bo.getParentId())) {
      bo.setParentId(0L);
    }
    bo.setEnable(Convert.toByte(1));

    LambdaQueryWrapper<BaseProductType> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(BaseProductType::getTypeName, bo.getTypeName())
      .eq(BaseProductType::getParentId, bo.getParentId());
    BaseProductType baseProductType = this.getOnly(lambdaQueryWrapper);

    if (ObjectUtil.isNotNull(baseProductType)) {
      BeanUtil.copyProperties(bo, baseProductType, new CopyOptions().setIgnoreProperties("typeId"));
      this.saveOrUpdate(baseProductType);

      Map<String, Object> result = new HashMap<>();
      result.put("typeId", baseProductType.getTypeId());
      result.put("typeName", baseProductType.getTypeName());

      return R.ok("类别更新成功", result);
    }

    baseProductType = new BaseProductType();
    BeanUtil.copyProperties(bo, baseProductType, new CopyOptions().setIgnoreProperties("typeId"));
    this.save(baseProductType);

    Map<String, Object> result = new HashMap<>();
    result.put("typeId", baseProductType.getTypeId());
    result.put("typeName", baseProductType.getTypeName());
    return R.ok("类别保存成功", result);

  }
  //#endregion

  //#region 根据ID查询都有哪些下级ID
  /**
   * 根据 当前ID 查询都有哪些下级iD
   *
   * @param ids
   * @return
   */
  @Override
  public List<Long> findLeafNodesByParentIds(List<Long> ids) {

    String sql = StringUtils.format("""
       WITH RECURSIVE recursiveInfo AS (
          SELECT type_id, type_name, parent_id,tenant_id
          FROM base_product_type
          WHERE parent_id in ({})
          UNION ALL
          SELECT c.type_id, c.type_name, c.parent_id,c.tenant_id
          FROM base_product_type c
          INNER JOIN recursiveInfo ON c.parent_id = recursiveInfo.type_id
      )
      SELECT type_id FROM recursiveInfo
      """, CollUtil.join(ids, ","));

    List<Map<String, Object>> dataInfo = SqlRunner.db().selectList(sql);
    return dataInfo.stream().map(item -> Convert.toLong(item.get("typeId"))).toList();
  }
  //#endregion

  //#region 根据ID查询都有哪些下级ID
  /**
   * 根据 当前ID 查询都有哪些下级iD
   *
   * @param ids 需要查询的 类别ID
   * @return
   */
  @Override
  public Map<String, Object> findAncestorsToRoot(List<Long> ids) {
    String sql = StringUtils.format("""
       WITH RECURSIVE recursiveInfo AS (
          SELECT type_id, parent_id,type_name
          FROM base_product_type
          WHERE type_id in({})  -- 当前节点的 ID

          UNION ALL

          SELECT t.type_id, t.parent_id,t.type_name
          FROM base_product_type t
          INNER JOIN recursiveInfo pt ON t.type_id = pt.parent_id
      )
      SELECT * FROM recursiveInfo;
       """, CollUtil.join(ids, ","));
    List<Map<String, Object>> dataInfo = SqlRunner.db().selectList(sql);
    //数据翻转
    Collections.reverse(dataInfo);

    Map<String, Object> maps = new HashMap<>();

    maps.put("typeId", CollUtil.join(dataInfo.stream().map(item -> Convert.toStr(item.get("typeId"))).toList(), "/"));
    maps.put("typeName", CollUtil.join(dataInfo.stream().map(item -> Convert.toStr(item.get("typeName"))).toList(), "/"));
    maps.put("parentId", CollUtil.join(dataInfo.stream().map(item -> Convert.toStr(item.get("parentId"))).toList(), "/"));


    return maps;
  }
  //#endregion

  //#region 根据编号获取商品类目
  //#region getByCode
  @Override
  public BaseProductType getByCode(String code) {
    LambdaQueryWrapper<BaseProductType> typeLambdaQueryWrapper = new LambdaQueryWrapper<>();
    typeLambdaQueryWrapper.eq(BaseProductType::getTypeCode, code);

    return this.getOnly(typeLambdaQueryWrapper);
  }
  //#endregion

  //#region 导入数据
  //#region importData
  @Override
  public void importData(InputStream inputStream, Long importId, HttpServletRequest request, LoginUser loginUser) {  // 手动开启事务  start
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
        final String typeCode = Convert.toStr(row.get("typeCode"));
        final String typeName = Convert.toStr(row.get("typeName"));

        BaseProductType dataInfo;
        LambdaQueryWrapper<BaseProductType> providerLambdaQueryWrapper = new LambdaQueryWrapper<>();
        providerLambdaQueryWrapper.eq(BaseProductType::getTypeCode, typeCode)
          .eq(BaseProductType::getTypeName, typeName);
        dataInfo = this.getOne(providerLambdaQueryWrapper);

        // 如果不存在，增长品牌
        if (ObjectUtil.isNull(dataInfo)) {
          successCount++;
          dataInfo = new BaseProductType(); // 新建数据

          // 数据拷贝
          BeanUtil.copyProperties(row, dataInfo);
          sysImportService.writeMsgBlue("第{}行、{}正在导入新增...", i, dataInfo.getTypeName());
        } else {
          BeanUtil.copyProperties(row, dataInfo);
          sysImportService.writeMsgBlue("第{}行、{}正在导入更新...", i, dataInfo.getTypeName());
          updateCount++;
        }
        dataInfo.setCreateBy(loginUser.getUserId());
        dataInfo.setCreateByName(loginUser.getNickname());
        dataInfo.setCreateTime(DateUtils.getNowDate());
        dataInfo.setEnable(EnableEnum.ENABLE.getId());
        LambdaQueryWrapper<BaseProductType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BaseProductType::getTypeCode, Convert.toStr(row.get("fullTypeId")));
        BaseProductType fullTypeInfo = this.getOne(queryWrapper);
        if (ObjectUtil.isNotNull(fullTypeInfo)) {
          dataInfo.setParentId(fullTypeInfo.getTypeId());

          List<Long> ids = new ArrayList<>();
          ids.add(dataInfo.getParentId());
          var findAncestorsToRoot = this.findAncestorsToRoot(ids);
          dataInfo.setFullTypeId("0/" + Convert.toStr(findAncestorsToRoot.get("typeId")));
          dataInfo.setFullTypeName("根/" + Convert.toStr(findAncestorsToRoot.get("typeName")));
        } else {
          dataInfo.setParentId(0L);
          dataInfo.setFullTypeId("0");
          dataInfo.setFullTypeName("根");
        }

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

  //#region 树形结构
  @Override
  public void beforePageQuery(PageQuery pageQuery) {
    Optional.ofNullable(pageQuery).map(PageQuery::getOtherParams).map(m -> m.get("currentTypeId")).ifPresent(typeId -> {
      Long currentTypeId = Convert.toLong(typeId);
      // 根据当前部门ID获取所有子孙ID，包含自己
      List<Long> childrenId = this.baseMapper.getChildrenId(currentTypeId);
      // 加载部门条件
      var queryBo = new QueryBo();
      queryBo.setQueryType(QueryTypeEnum.IN);
      queryBo.setColumn("typeId");
      queryBo.setValues(StringUtils.join(childrenId, ","));
      queryBo.setDataType(DataTypeEnum.LONG);
      pageQuery.addQueryBo(queryBo);
    });
  }
  //#endregion
}

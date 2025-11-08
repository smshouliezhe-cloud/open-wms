package com.yiruantong.inventory.service.rule.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.domain.product.BaseProvider;
import com.yiruantong.basic.domain.storage.BasePosition;
import com.yiruantong.basic.domain.storage.BaseShelveRegular;
import com.yiruantong.basic.domain.storage.BaseStorage;
import com.yiruantong.basic.service.product.IBaseProductService;
import com.yiruantong.basic.service.product.IBaseProviderService;
import com.yiruantong.basic.service.storage.IBasePositionService;
import com.yiruantong.basic.service.storage.IBaseShelveRegularService;
import com.yiruantong.basic.service.storage.IBaseStorageService;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.enums.base.PositionTypeEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.json.utils.JsonUtils;
import com.yiruantong.common.mybatis.core.page.BuildWrapperHelper;
import com.yiruantong.inventory.domain.core.CoreInventory;
import com.yiruantong.inventory.domain.helper.*;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import com.yiruantong.inventory.service.rule.IRecommendPositionService;
import com.yiruantong.system.service.core.ICommonService;
import com.yiruantong.system.service.core.ISysConfigService;
import com.yiruantong.system.service.dataHandler.ISysParamValueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RecommendPositionServiceImpl implements IRecommendPositionService {
  private final IBaseStorageService baseStorageService;
  private final IBaseProductService baseProductService;
  private final ISysParamValueService sysParamValueService;
  private final IBaseShelveRegularService baseShelveRegularService;
  private final ICoreInventoryService coreInventoryService;
  private final IBasePositionService basePositionService;
  private final IBaseProviderService baseProviderService;
  private final ISysConfigService sysConfigService;
  private final ICommonService commonService;

  //#region  推荐货位
  @Override
  public String getRecommendPosition(RecommendPositionDto recommendPositionDto) {
    BaseProduct productInfo = baseProductService.getBaseMapper().selectById(recommendPositionDto.getProductId());
    Assert.isTrue(ObjectUtil.isNotEmpty(productInfo), "未找到对应商品信息");

    BaseStorage storageInfo = baseStorageService.getBaseMapper().selectById(recommendPositionDto.getStorageId());
    Assert.isTrue(ObjectUtil.isNotEmpty(storageInfo), "未找到对应仓库信息");

    BaseProvider baseProvider = baseProviderService.getById(recommendPositionDto.getProviderId());

    //按拍上架推荐货位根据拍数推荐
    boolean in_shelvePaiCount = sysConfigService.getConfigBool("in_shelvePaiCount");

    // ********************************************
    // 对明细生成推荐货位
    // ********************************************
    LambdaQueryWrapper<BaseShelveRegular> shelveRegularLqw = new LambdaQueryWrapper<>();
    shelveRegularLqw.eq(BaseShelveRegular::getStorageId, recommendPositionDto.getStorageId())
      .eq(BaseShelveRegular::getEnable, EnableEnum.ENABLE.getId())
      .orderByDesc(BaseShelveRegular::getOrderNum);
    List<BaseShelveRegular> list = baseShelveRegularService.list(shelveRegularLqw);

    List<RecommendRegularDto> tempDetailShelveRegular = new ArrayList<>();
    list.forEach(item -> {
        RecommendRegularDto recommendRegularDto = JsonUtils.parseObject(item.getJsonData(), RecommendRegularDto.class);
        Assert.isFalse(ObjectUtil.isNull(recommendRegularDto), "推荐规则不正确！");
        recommendRegularDto.setName(item.getShelveRegularName());
        tempDetailShelveRegular.add(recommendRegularDto);
      }
    );
    //是否满足条件
    boolean isMeetConditions = false;
    String shelvePositionName = "";

    for (var regularInfo : tempDetailShelveRegular) {
      Condition condition = regularInfo.getCondition();

      //#region 条件匹配
      //货主条件
      if (condition.getConsignorInfo().getIsSelect()) {
        int length = condition.getConsignorInfo().getConsignorList().stream().filter(i -> NumberUtil.equals(i, recommendPositionDto.getConsignorId())).toList().size();
        if (B.isEqual(length)) {
          continue;
        }
        isMeetConditions = true;
      }

      //商品条件
      if (condition.getProductInfo().getIsSelect()) {
        int length = condition.getProductInfo().getProducts().stream().filter(i -> NumberUtil.equals(i.getProductId(), recommendPositionDto.getProductId())).toList().size();
        if (B.isEqual(length)) {
          continue;
        }
        isMeetConditions = true;
      }

      //商品类别条件
      if (condition.getTypeInfo().getIsSelect()) {
        int length = condition.getTypeInfo().getTypes().stream().filter(i -> NumberUtil.equals(i.getTypeId(), productInfo.getTypeId())).toList().size();
        if (B.isEqual(length)) {
          continue;
        }
        isMeetConditions = true;
      }

      //商品品牌条件
      if (condition.getBrandInfo().getIsSelect()) {
        int length = condition.getBrandInfo().getBrandList().stream().filter(i -> NumberUtil.equals(i, productInfo.getBrandId())).toList().size();
        if (B.isEqual(length)) {
          continue;
        }
        isMeetConditions = true;
      }


      //按到货单商品数量，在数量范围内的推进货位
      if (condition.getQuantityInfo().getIsSelect()) {
        if (!(B.isGreater(recommendPositionDto.getInQty(), Convert.toBigDecimal(condition.getQuantityInfo().getQuantityMin())) && B.isGreater(Convert.toBigDecimal(condition.getQuantityInfo().getQuantityMax()), recommendPositionDto.getInQty()))) {
          continue;
        }
        isMeetConditions = true;
      }
      //按供应商
      if (condition.getProviderInfo().getIsSelect()) {
        if (ObjectUtil.isEmpty(baseProvider) || !StrUtil.equals(baseProvider.getProviderShortName(), condition.getProviderInfo().getProviderShortName())) {
          continue;
        }
        isMeetConditions = true;
      }

      //按重量
      if (condition.getWeightInfo().getIsSelect()) {
        if (!(B.isGreater(recommendPositionDto.getWeight(), Convert.toBigDecimal(condition.getWeightInfo().getWeightMin())) && B.isGreater(Convert.toBigDecimal(condition.getWeightInfo().getWeightMax()), recommendPositionDto.getWeight()))) {
          continue;
        }
        isMeetConditions = true;
      }

      //按体积
      if (condition.getVolumeInfo().getIsSelect()) {
        if (!(B.isGreater(recommendPositionDto.getUnitCube(), Convert.toBigDecimal(condition.getVolumeInfo().getVolumeMin())) && B.isGreater(Convert.toBigDecimal(condition.getVolumeInfo().getVolumeMax()), recommendPositionDto.getUnitCube()))) {
          continue;
        }
        isMeetConditions = true;
      }

      //无条件
      if (condition.getNoCondition().getIsSelect()) {
        isMeetConditions = true;
      }

      //没有任何一个条件满足
      if (!isMeetConditions) {
        continue;
      }
      //#endregion

      //#region 设定动作
      Target target = regularInfo.getTarget();
      String positionNames = "";
      boolean isExistPosition = false;
      boolean isExistTarget = false;

      // 获得货位
      if (target.getPositionInfo().getIsSelect()) {
        positionNames = target.getPositionInfo().getPositionNames();
        isExistPosition = true;
        isExistTarget = true;
      }

      //获得库区
      List<String> areas;
      boolean isAreas = false;
      if (target.getStorageAreaInfo().getIsSelect()) {
        areas = target.getStorageAreaInfo().getAreas();
        isAreas = true;
        isExistTarget = true;

      } else {
        areas = new ArrayList<>();
      }

      //获得通道
      List<String> channel;
      boolean isChannel = false;
      if (target.getChannelInfo().getIsSelect()) {
        channel = target.getChannelInfo().getChannelCodes();
        isChannel = true;
        isExistTarget = true;
      } else {
        channel = new ArrayList<>();
      }

      //获得货架
      List<String> shelve;
      boolean isShelve = false;
      if (target.getShelveInfo().getIsSelect()) {
        shelve = target.getShelveInfo().getShelveCodes();
        isShelve = true;
        isExistTarget = true;
      } else {
        shelve = new ArrayList<>();
      }

      //无条件
      if (target.getNoCondition().getIsSelect()) {
        isExistTarget = true;
      }


      //获得货位类型优先级
      List<Long> positionTypeOrderBy;
      if (target.getPositionTypeInfo().getIsSelect()) {
        positionTypeOrderBy = target.getPositionTypeInfo().getPositionTypes();
        isExistTarget = true;
      } else {
        positionTypeOrderBy = new ArrayList<>();
      }

      //获得温层条件
      List<String> thermohaline = new ArrayList<>();
      boolean isThermohaline = false;
      if (target.getThermoclineInfo().getIsSelect()) {
        thermohaline = target.getThermoclineInfo().getThermocline();
        isThermohaline = true;
        isExistTarget = true;
      }

      if (!isExistTarget) {
        continue;
      }
      //#endregion

      //#region 获得货位种类优先级 并获取货位
      for (String positionOrderBy : regularInfo.getPositionOrderBy()) {
        List<PositionList> positionlist = new ArrayList<>();
        List<PositionList> tempPositionlist;
        String existPosition = "";

        //#region 获得货位种类优先级
        if (in_shelvePaiCount && StrUtil.isNotEmpty(recommendPositionDto.getPlateCode())) {
          //#region 按拍上架推荐货位根据拍数推荐
//          MPJLambdaWrapper<CoreInventory> wrapper = new MPJLambdaWrapper<>();
//          if (ObjectUtil.equal(positionOrderBy, "已有库存货位")) {
//            wrapper
//              .eq(CoreInventory::getStorageId, recommendPositionDto.getStorageId())
//              .eq(CoreInventory::getProductId, recommendPositionDto.getProductId())
//              .eq(BasePosition::getMaxPaiQty, recommendPositionDto.getProductId())
//              .in(CoreInventory::getPositionName, List.of(PositionTypeEnum.NORMAL.getId(), PositionTypeEnum.ELEVATED.getId(), PositionTypeEnum.STORAGE.getId()))
//              // 货位还有可用空间
//              .exists("IFNULL(t2.max_pai_qty, 99999) > 1 + IFNULL((SELECT COUNT(DISTINCT po.plate_code) FROM core_inventory po WHERE po.position_name=t.position_name AND po.storage_id=t.storage_id), 0)")
//              .orderByAsc(CoreInventory::getPositionName)
//              .orderByAsc(BasePosition::getAreaCode);
//          } else if (ObjectUtil.equal(positionOrderBy, "默认货位")){
//            wrapper
//              .eq(CoreInventory::getStorageId, recommendPositionDto.getStorageId())
//              .eq(CoreInventory::getProductId, recommendPositionDto.getProductId())
//              .eq(BasePosition::getMaxPaiQty, recommendPositionDto.getProductId())
//              .in(CoreInventory::getPositionName, List.of(PositionTypeEnum.NORMAL.getId(), PositionTypeEnum.ELEVATED.getId(), PositionTypeEnum.STORAGE.getId()))
//              // 货位还有可用空间
//              .exists("IFNULL(t2.max_pai_qty, 99999) > 1 + IFNULL((SELECT COUNT(DISTINCT po.plate_code) FROM core_inventory po WHERE po.position_name=t.position_name AND po.storage_id=t.storage_id), 0)")
//              .orderByAsc(CoreInventory::getPositionName)
//              .orderByAsc(BasePosition::getAreaCode);
//          } else  if (ObjectUtil.equal(positionOrderBy, "未使用的货位")){
//            wrapper
//              .eq(CoreInventory::getStorageId, recommendPositionDto.getStorageId())
//              .eq(CoreInventory::getProductId, recommendPositionDto.getProductId())
//              .eq(BasePosition::getMaxPaiQty, recommendPositionDto.getProductId())
//              .in(CoreInventory::getPositionName, List.of(PositionTypeEnum.NORMAL.getId(), PositionTypeEnum.ELEVATED.getId(), PositionTypeEnum.STORAGE.getId()))
//              // 货位还有可用空间
//              .exists("IFNULL(t2.max_pai_qty, 99999) > 1 + IFNULL((SELECT COUNT(DISTINCT po.plate_code) FROM core_inventory po WHERE po.position_name=t.position_name AND po.storage_id=t.storage_id), 0)")
//              .orderByAsc(CoreInventory::getPositionName)
//              .orderByAsc(BasePosition::getAreaCode);
//          }
//            List<CoreInventoryComposeVo> inventoryComposeVoList = coreInventoryService.selectValidInventoryList(wrapper);
//          END
//          ELSE IF @positionOrderBy='默认货位'
//          BEGIN
//          INSERT INTO #Positionlist(PositionName, areaCode, storage_Id, storageName)
//          SELECT P.PositionName, NULL AS areaCode, @storage_Id, @storageName
//          FROM dbo.vBase_ProductInfo_SKU AS P
//          WHERE  P.Product_Id=@Product_Id   AND ISNULL(P.PositionName,'')<>''
//          AND EXISTS(SELECT * FROM dbo.Base_Position WHERE PositionName=P.PositionName AND PositionType=1 AND Storage_Id=@Storage_Id AND Enable=1);
//          END
//          ELSE IF @positionOrderBy='未使用的货位'
//          BEGIN
//            -- 推荐当前商品已经在库存内靠近的货位
//          DECLARE @ExistPosition NVARCHAR(50); -- 已经存在的库存货位
//
//          IF @isExistPosition=1 -- 存在货位条件
//            BEGIN
//          SELECT @ExistPosition = PositionName FROM dbo.vBase_ProductPosition pp WHERE pp.Storage_Id=@Storage_Id
//            AND pp.Product_Id=@Product_Id
//            AND pp.ProductStorage>0
//          AND pp.PositionType IN(1, 12, 13)
//          AND EXISTS(SELECT 1 FROM STRING_SPLIT(@positionNames, ',') p WHERE p.value=pp.PositionName) -- 货位条件
//          END
//            ELSE
//          BEGIN
//          SELECT @ExistPosition = PositionName FROM dbo.vBase_ProductPosition pp WHERE pp.Storage_Id=@Storage_Id
//            AND pp.Product_Id=@Product_Id
//            AND pp.ProductStorage>0
//          AND pp.PositionType IN(1, 12, 13)
//          END
//
//          SELECT '3333333333333', @ExistPosition AS ExistPosition, @positionOrderBy AS ShelveRegularName
//          IF @ExistPosition IS NOT NULL
//          BEGIN
//          DECLARE @ExistRowId INT;
//          SELECT @ExistRowId = rowId FROM (
//            SELECT ROW_NUMBER() OVER(ORDER BY PositionName) rowId, PositionName
//          FROM dbo.Base_Position p WHERE Storage_Id=@Storage_Id
//            AND Enable=1
//          AND NOT EXISTS(
//            SELECT 1 FROM dbo.Base_ProductPosition pp
//            WHERE p.Storage_Id=pp.Storage_Id AND p.PositionName=pp.PositionName
//            HAVING  ISNULL(JSON_VALUE(p.ExpandFields, '$.maxPaiQty'), 99999) > COUNT(DISTINCT pp.PlateCode) + 1
//          )
//						) tt WHERE tt.PositionName=@ExistPosition;
//
//          -- 查询没有库存的空货位
//          TRUNCATE TABLE #Positionlist;
//          INSERT INTO #Positionlist(PositionName, areaCode, storage_Id, storageName)
//          SELECT t2.PositionName, t2.AreaCode, storage_Id, storageName  FROM(
//            SELECT t1.rowId, t1.PositionName, ABS(t1.rowId - ISNULL(@ExistRowId, 0)) AS orderNo, t1.AreaCode, storage_Id, storageName FROM(
//            SELECT ROW_NUMBER() OVER(ORDER BY PositionName) rowId, PositionName, p.AreaCode, storage_Id, storageName
//            FROM dbo.Base_Position p
//            WHERE Storage_Id=@Storage_Id
//              AND Enable=1
//            AND NOT EXISTS(
//              SELECT 1 FROM dbo.Base_ProductPosition pp
//              WHERE p.Storage_Id=pp.Storage_Id AND p.PositionName=pp.PositionName
//              HAVING ISNULL(JSON_VALUE(p.ExpandFields, '$.maxPaiQty'), 99999) <= COUNT(DISTINCT pp.PlateCode) + 1
//            )
//            AND (p.IsMixProduct=1 OR (p.IsMixProduct=0 AND NOT EXISTS(SELECT 1 FROM dbo.Base_ProductPosition pp WHERE pp.ProductStorage>0 AND pp.PositionName=p.PositionName))) -- 排除非混物料的货位
//          ) t1
//						) t2
//          ORDER BY orderNo
//            --SELECT @ShelvePositionName AS ShelvePositionName, @Storage_Id AS Storage_Id, @Product_Id AS Product_Id
//            END
//          ELSE
//            BEGIN
//          -- 商品头一次入库推荐空货位，或者未满库位
//          INSERT INTO #Positionlist(PositionName, areaCode, storage_Id, storageName)
//          SELECT PositionName, p.AreaCode, storage_Id, storageName
//          FROM dbo.Base_Position p
//          WHERE PositionType=1
//          AND NOT EXISTS(
//            SELECT PositionName FROM dbo.Base_ProductPosition pp
//            WHERE pp.PositionName=p.PositionName AND  Storage_Id=@Storage_Id
//              GROUP BY pp.PositionName, pp.Product_Id
//            HAVING  ISNULL(JSON_VALUE(p.ExpandFields, '$.maxPaiQty'), 99999) <= COUNT(DISTINCT pp.PlateCode) + 1
//          )
//          AND NOT EXISTS(SELECT Position_Id FROM Base_Position t WHERE t.ParentId=p.Position_Id AND  Storage_Id=@Storage_Id) -- 父级货位，分组名称
//          AND  p.Storage_Id=@Storage_Id AND p.Enable=1
//          AND (
//            p.IsMixProduct=1 OR (p.IsMixProduct=0 AND NOT EXISTS(SELECT 1 FROM dbo.Base_ProductPosition pp WHERE pp.ProductStorage>0 AND pp.PositionName=p.PositionName))
//          ) -- 排除非混物料的货位
//          ORDER BY p.PositionName;
//          --SELECT '4444444', @ShelvePositionName AS ShelvePositionName, @Storage_Id AS Storage_Id, @Product_Id AS Product_Id
//            END
//          END
//          ELSE IF @positionOrderBy='同供应商货位'
//          BEGIN
//          INSERT INTO #Positionlist(PositionName, areaCode, storage_Id, storageName)
//          SELECT pp.PositionName, pp.AreaCode, storage_Id, storageName
//          FROM dbo.Base_ProductPosition AS pp
//          WHERE pp.Product_Id=@Product_Id AND pp.Storage_Id=@Storage_Id
//            AND EXISTS(SELECT * FROM dbo.Base_Position WHERE PositionName=pp.PositionName AND PositionType=1 AND Storage_Id=@Storage_Id  AND Enable=1);
//          END
//          ELSE IF @positionOrderBy='同品牌货位'
//          BEGIN
//          INSERT INTO #Positionlist(PositionName, areaCode, storage_Id, storageName)
//          SELECT PositionName, AreaCode, storage_Id, storageName
//          FROM dbo.Base_ProductPosition
//          WHERE Product_Id IN(
//            SELECT Product_Id FROM dbo.Base_ProductInfo
//            WHERE Brand_Id=(SELECT Brand_Id FROM dbo.Base_ProductInfo p WHERE p.Product_Id=@Product_Id )
//          AND ISNULL(BrandName,'')<>''
//					) AND Storage_Id=@Storage_Id
//            GROUP BY PositionName, AreaCode, storage_Id, storageName;
//          END
//          ELSE IF @positionOrderBy='同批次货位'
//          BEGIN
//          INSERT INTO #Positionlist(PositionName, areaCode, storage_Id, storageName)
//          SELECT pp.PositionName, pp.AreaCode, storage_Id, storageName
//          FROM dbo.Base_ProductPosition AS pp
//          WHERE pp.Product_Id=@Product_Id AND pp.Storage_Id=@Storage_Id
//            AND EXISTS(SELECT * FROM dbo.Base_Position WHERE PositionName=pp.PositionName AND PositionType=1 AND Storage_Id=@Storage_Id  AND Enable=1);
//          END
          //#endregion
        } else {
          // 已有库存货位
          if (StrUtil.equals(positionOrderBy, "已有库存货位")) {
            LambdaQueryWrapper<CoreInventory> coreInventoryLqw = new LambdaQueryWrapper<>();
            coreInventoryLqw.eq(CoreInventory::getProductId, recommendPositionDto.getProductId())
              .eq(CoreInventory::getStorageId, recommendPositionDto.getStorageId())
              .exists("SELECT position_name FROM base_position as p" +
                " WHERE p.position_name=core_inventory.position_name" +
                " AND p.position_type IN(1, 12, 13)" +
                " AND p.storage_id=" + recommendPositionDto.getStorageId() + "  AND p.enable=1" +
                " AND IFNULL(p.max_capacity, 99999) >1 + IFNULL((SELECT SUM(po.product_storage) FROM core_inventory po WHERE po.position_name=p.position_name AND po.storage_id=" + recommendPositionDto.getStorageId() + "), 0)")
              .orderByAsc(CoreInventory::getPositionName);
            List<CoreInventory> coreInventories = coreInventoryService.list(coreInventoryLqw);
            positionlist = BeanUtil.copyToList(coreInventories, PositionList.class);// 复制数据

          } else if (StrUtil.equals(positionOrderBy, "默认货位")) { //默认货位
            LambdaQueryWrapper<BaseProduct> productLqw = new LambdaQueryWrapper<>();
            productLqw.eq(BaseProduct::getProductId, recommendPositionDto.getProductId())
              .isNotNull(BaseProduct::getPositionName)
              .exists("SELECT position_id FROM base_position WHERE position_name=base_product.position_name AND position_type=1 AND storage_id=" + recommendPositionDto.getStorageId() + " AND enable=1")
              .last("limit 1");
            BaseProduct product = baseProductService.getOne(productLqw);
            if (ObjectUtil.isNotNull(product)) {
              shelvePositionName = product.getProductName();
            }
          } else if (StrUtil.equals(positionOrderBy, "未使用的货位")) { //未使用的货位
            //推荐当前商品已经在库存内靠近的货位
            MPJLambdaWrapper<CoreInventory> coreInventoryLqw = new MPJLambdaWrapper<>();
            coreInventoryLqw
              .selectAll(CoreInventory.class)
              .eq(CoreInventory::getProductId, recommendPositionDto.getProductId())
              .eq(CoreInventory::getStorageId, recommendPositionDto.getStorageId())
              .gt(CoreInventory::getProductStorage, 0L)
              .innerJoin(BasePosition.class, on -> on.eq(BasePosition::getStorageId, CoreInventory::getStorageId).eq(BasePosition::getPositionName, CoreInventory::getPositionName))
              .in(BasePosition::getPositionType, List.of(PositionTypeEnum.NORMAL.getId(), PositionTypeEnum.ELEVATED.getId(), PositionTypeEnum.ELEVATED.getId()))
              .last("limit 1");
            CoreInventory one = coreInventoryService.getOne(coreInventoryLqw);
            if (ObjectUtil.isNotNull(one)) {
              existPosition = one.getPositionName();
            }
            if (StrUtil.isNotEmpty(existPosition)) {
              String sql = """
                  SELECT row_id FROM (
                  	SELECT ROW_NUMBER() OVER(ORDER BY position_name) row_id, position_name
                  	FROM base_position p WHERE storage_id={0}
                  		AND enable=1
                  		AND NOT EXISTS(
                  			SELECT 1 FROM core_inventory pp WHERE p.storage_id=pp.storage_id AND p.Position_name=pp.position_name AND p.Position_name<>{1}
                  			HAVING p.max_capacity > SUM(pp.product_storage) + {2}
                  		)
                  ) tt WHERE tt.position_name={1}
                """;
              Map<String, Object> selectOneRowId = SqlRunner.db().selectOne(sql, recommendPositionDto.getStorageId(), existPosition, recommendPositionDto.getInQty());
              int rowId = Convert.toInt(selectOneRowId.get("rowId"));

              sql = """
                SELECT t2.position_name, t2.area_code, storage_id, storage_name, channel_code, shelve_code, order_no FROM(
                	SELECT t1.row_id, t1.position_name, ABS(CAST(t1.row_id AS SIGNED) - {1}) AS order_no, t1.area_code, storage_id, storage_name, channel_code, shelve_code FROM(
                		SELECT ROW_NUMBER() OVER(ORDER BY position_name) row_id, position_name, p.area_code, storage_id, storage_name, p.channel_code, p.shelve_code
                		FROM base_position p WHERE storage_id={0}
                			AND enable=1
                			AND NOT EXISTS(
                				SELECT 1 FROM core_inventory pp
                				WHERE p.storage_id=pp.storage_id AND p.position_name=pp.position_name
                				AND pp.product_storage>0
                			)
                	) t1
                ) t2
                ORDER BY order_no
                LIMIT 1
                """;
//              sql = MessageFormat.format(sql, recommendPositionDto.getStorageId(), rowId);
              Map<String, Object> selectOnePositionName = SqlRunner.db().selectOne(sql, recommendPositionDto.getStorageId(), rowId);
              shelvePositionName = Convert.toStr(selectOnePositionName.get("positionName"));
            } else {
              LambdaQueryWrapper<BasePosition> positionLqw = new LambdaQueryWrapper<>();
              positionLqw.in(BasePosition::getPositionType, List.of(PositionTypeEnum.NORMAL.getId(), PositionTypeEnum.ELEVATED.getId(), PositionTypeEnum.ELEVATED.getId()))
                .eq(BasePosition::getStorageId, recommendPositionDto.getStorageId())
                .eq(BasePosition::getEnable, EnableEnum.ENABLE.getId())
                .orderByAsc(BasePosition::getPositionName)
                .notExists("""
                  SELECT product_id  FROM core_inventory as  pp WHERE base_position.storage_id=pp.storage_id
                  AND base_position.position_name=pp.position_name
                  AND pp.product_storage > 0
                  """)
                .notExists("""
                  SELECT position_id FROM base_position as  t WHERE t.parent_id=base_position.position_id AND  t.storage_id=
                  """ + recommendPositionDto.getStorageId());
              List<BasePosition> basePositions = basePositionService.list(positionLqw);

              positionlist.addAll(BeanUtil.copyToList(basePositions, PositionList.class));// 复制数据
            }
          } else if (StrUtil.equals(positionOrderBy, "同供应商货位")) { //同供应商货位

            LambdaQueryWrapper<CoreInventory> coreInventoryLqw = new LambdaQueryWrapper<>();
            coreInventoryLqw.eq(CoreInventory::getProviderId, recommendPositionDto.getProviderId())
              .eq(CoreInventory::getStorageId, recommendPositionDto.getStorageId())
              .exists("SELECT * FROM base_position WHERE position_name=core_inventory.position_name AND position_type=1 AND storage_Id=" + recommendPositionDto.getStorageId() + " AND enable=1");
            List<CoreInventory> coreInventories = coreInventoryService.list(coreInventoryLqw);
            positionlist.addAll(BeanUtil.copyToList(coreInventories, PositionList.class));// 复制数据

          } else if (StrUtil.equals(positionOrderBy, "同品牌货位")) { //同品牌货位
            //如果品牌不为空
            if (StrUtil.isNotEmpty(productInfo.getBrandName())) {
              // 分组字段
              String groupFields = "PositionName, AreaCode, storage_Id, storageName, channelCode, shelveCode";
              MPJLambdaWrapper<CoreInventory> wrapper = new MPJLambdaWrapper<>();
              wrapper.eq(CoreInventory::getProductId, recommendPositionDto.getProductId())
                .eq(CoreInventory::getStorageId, recommendPositionDto.getStorageId());
              // 构建分组查询
              BuildWrapperHelper.mpjWrapperGroup("", "", groupFields, wrapper, CoreInventory.class);
              List<CoreInventory> inventoryList = coreInventoryService.selectJoinList(CoreInventory.class, wrapper);

              positionlist.addAll(BeanUtil.copyToList(inventoryList, PositionList.class));// 复制数据
            }
          } else if (StrUtil.equals(positionOrderBy, "同批次货位")) { //同批次货位
            LambdaQueryWrapper<CoreInventory> coreInventoryLqw = new LambdaQueryWrapper<>();
            coreInventoryLqw.eq(CoreInventory::getBatchNumber, recommendPositionDto.getBatchNumber())
              .eq(CoreInventory::getStorageId, recommendPositionDto.getStorageId())
              .eq(CoreInventory::getProductId, recommendPositionDto.getProductId())
              .exists("SELECT * FROM base_position WHERE position_name=core_inventory.position_name AND position_type=1 AND storage_Id=" + recommendPositionDto.getStorageId() + " AND enable=1");
            List<CoreInventory> inventoryList = coreInventoryService.list(coreInventoryLqw);
            positionlist.addAll(BeanUtil.copyToList(inventoryList, PositionList.class));// 复制数据
          }
        }
        //#endregion

        //#region  匹配货位
        //获取指定货位
        if (isExistPosition) {
          List<String> split = Arrays.stream(StringUtils.split(positionNames, ",")).toList();
          positionlist = positionlist.stream().filter(i -> B.isGreater(split.stream().filter(k -> StrUtil.equals(k, i.getPositionName())).toList().size())).toList();
        }

        //获取可用库区
        if (isAreas) {
          positionlist = positionlist.stream().filter(i -> B.isGreater(areas.stream().filter(k -> StrUtil.equals(k, i.getAreaCode())).toList().size())).toList();
        }

        //获取可用通道
        if (isChannel) {
          positionlist = positionlist.stream().filter(i -> B.isGreater(channel.stream().filter(k -> StrUtil.equals(k, i.getChannelCode())).toList().size())).toList();
        }

        //获取可用货架
        if (isShelve) {
          positionlist = positionlist.stream().filter(i -> B.isGreater(shelve.stream().filter(k -> StrUtil.equals(k, i.getShelveCode())).toList().size())).toList();
        }

        //根据货位类型进行排序和过滤
        if (B.isGreater(positionTypeOrderBy.size())) {
          List<String> names = positionlist.stream().map(PositionList::getPositionName).filter(StrUtil::isNotEmpty).toList();
          //去除重复性
          names = names.stream().distinct().collect(Collectors.toList());
          LambdaQueryWrapper<BasePosition> basePositionLqw = new LambdaQueryWrapper<>();
          basePositionLqw
            .select(BasePosition::getPositionName)
            .in(BasePosition::getPositionName, names)
            .in(BasePosition::getPositionType, positionTypeOrderBy)
            .eq(BasePosition::getEnable, EnableEnum.ENABLE.getId());
          List<BasePosition> storages = basePositionService.list(basePositionLqw);
          names = storages.stream().map(BasePosition::getPositionName).filter(StrUtil::isNotEmpty).toList();

          List<String> finalNames = names;
          positionlist = positionlist.stream().filter(i -> B.isGreater(finalNames.stream().filter(k -> StrUtil.equals(k, i.getPositionName())).toList().size())).toList();
        }

        //获取温层仓库货位
        if (isThermohaline) {
          tempPositionlist = new ArrayList<>();
          for (PositionList info : positionlist) {
            BaseStorage storage = baseStorageService.getById(info.getStorageId());
            if (B.isGreater(thermohaline.stream().filter(i -> i.equals(storage.getThermocLine())).toList().size())) {
              tempPositionlist.add(info);
            }
          }
          positionlist = tempPositionlist;
        }
        if (B.isGreater(positionlist.size())) {
          shelvePositionName = positionlist.get(0).getPositionName();
          break;
        }
        //#endregion

      }
      //#endregion
      //结束循环
      if (StrUtil.isNotEmpty(shelvePositionName)) {
        break;
      }
    }
    return shelvePositionName;
  }
  //#endregion
}

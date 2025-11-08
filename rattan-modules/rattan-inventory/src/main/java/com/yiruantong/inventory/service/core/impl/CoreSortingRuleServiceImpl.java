package com.yiruantong.inventory.service.core.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.inventory.SortingRuleTypeEnum;
import com.yiruantong.common.core.enums.system.RabbitmqTypeEnum;
import com.yiruantong.common.mybatis.core.domain.bo.SaveEditorBo;
import com.yiruantong.common.mybatis.core.domain.vo.EditorVo;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.system.service.task.ITaskQueueService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.enums.inventory.SortingRuleBillTypeEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.inventory.domain.core.CoreSortingRule;
import com.yiruantong.inventory.domain.core.bo.CoreSortingRuleBo;
import com.yiruantong.inventory.domain.core.dto.*;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryComposeVo;
import com.yiruantong.inventory.domain.core.vo.CoreSortingRuleVo;
import com.yiruantong.inventory.mapper.core.CoreSortingRuleMapper;
import com.yiruantong.inventory.service.core.ICoreSortingRuleService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 分拣规则Service业务层处理
 *
 * @author YRT
 * @date 2025-02-27
 */
@RequiredArgsConstructor
@Service
public class CoreSortingRuleServiceImpl extends ServiceImplPlus<CoreSortingRuleMapper, CoreSortingRule, CoreSortingRuleVo, CoreSortingRuleBo> implements ICoreSortingRuleService {
  private final ITaskQueueService taskQueueService;


  @Override
  public CoreSortingRule getSortingRuleInfo(SortingRuleBillTypeEnum billType, Long billId, Long billDetailId) {
    LambdaQueryWrapper<CoreSortingRule> ruleLambdaQueryWrapper = new LambdaQueryWrapper<>();
    ruleLambdaQueryWrapper.eq(CoreSortingRule::getBillType, billType.getName())
      .eq(CoreSortingRule::getBillId, billId)
      .eq(CoreSortingRule::getBillDetailId, billDetailId);
    return this.getOnly(ruleLambdaQueryWrapper);
  }

  @Override
  public List<CoreInventoryComposeVo> filter(List<CoreInventoryComposeVo> inventoryList, SortingRuleBillTypeEnum billType, Long billId, Long billDetailId) {
    CoreSortingRule sortingRuleInfo = this.getSortingRuleInfo(billType, billId, billDetailId);
    if (ObjectUtil.isNull(sortingRuleInfo)) {
      return inventoryList;
    }

    SortingRuleDto sortingRuleDto = BeanUtil.toBean(sortingRuleInfo.getSortingRule(), SortingRuleDto.class);
    for (SortingRuleItemDto item : sortingRuleDto.getChildren()) {
      if (ObjectUtil.isNull(item.getRight())) {
        continue;
      }
      SortingRuleTypeEnum enumByCode = SortingRuleTypeEnum.getEnumByCode(item.getLeft().getField());
      switch (enumByCode) {
        case POSITIONNAME -> inventoryList = this.positionNameFilter(inventoryList, item.getRight());
        case POSITIONNAMEQTY -> inventoryList = this.positionNameQtyFilter(inventoryList, item.getRight());
        case BATCHNUMBERQTY -> inventoryList = this.batchNumberQtyFilter(inventoryList, item.getRight());
        case PRODUCEDATE -> inventoryList = this.produceDateFilter(inventoryList, item.getRight());
        case PRODUCEDATERANGE -> inventoryList = this.produceDateRangeFilter(inventoryList, item.getRight());
        case BATCHNUMBER -> inventoryList = this.batchNumberFilter(inventoryList, item.getRight());
      }
    }

    return inventoryList;
  }


  /**
   * 货位分拣规则过滤器
   *
   * @param inventoryList 待过滤库存集合
   * @param rule          过滤规则
   * @return 返回过滤后的库存集合
   */
  private List<CoreInventoryComposeVo> positionNameFilter(List<CoreInventoryComposeVo> inventoryList, Object rule) {
    List<String> ruleList = StringUtils.splitList(Convert.toStr(rule));
    inventoryList = inventoryList.stream().filter(x -> ruleList.contains(x.getPositionName())).toList();

    return inventoryList;
  }


  /**
   * 批次号规则过滤器
   *
   * @param inventoryList 待过滤库存集合
   * @param rule          过滤规则
   * @return 返回过滤后的库存集合
   */
  private List<CoreInventoryComposeVo> batchNumberFilter(List<CoreInventoryComposeVo> inventoryList, Object rule) {
    List<String> ruleList = StringUtils.splitList(Convert.toStr(rule));
    inventoryList = inventoryList.stream().filter(x -> ruleList.contains(x.getBatchNumber())).toList();

    return inventoryList;
  }

  /**
   * 货位数量分拣规则过滤器
   *
   * @param inventoryList 待过滤库存集合
   * @param rule          过滤规则
   * @return 返回过滤后的库存集合
   */
  private List<CoreInventoryComposeVo> positionNameQtyFilter(List<CoreInventoryComposeVo> inventoryList, Object rule) {
    List<CoreInventoryComposeVo> newInventoryList = new ArrayList<>(); // 筛选出来可用库存
    List<PositionNameQtyDto> positionNameQtyDtoList = Convert.toList(PositionNameQtyDto.class, rule);
    for (PositionNameQtyDto qtyDto : positionNameQtyDtoList) {
      var list = inventoryList.stream().filter(x -> StringUtils.equals(qtyDto.getPositionName(), x.getPositionName())).toList();

      BigDecimal qty = qtyDto.getQuantity();
      for (var inventory : list) {
        // 如果当前行有效库存小于等于当前规则里面的数量跳过
        if (B.isGreaterOrEqual(qty, inventory.getValidStorage())) {
          qty = B.sub(qty, inventory.getValidStorage());
          newInventoryList.add(BeanUtil.copyProperties(inventory, CoreInventoryComposeVo.class));
          inventory.setValidStorage(BigDecimal.ZERO); // 库存清零
          continue;
        }

        if (B.isGreater(qty)) {
          // 可参与分拣的实际数量
          inventory.setValidStorage(qty);
          // 剩余数量清零
          qty = BigDecimal.ZERO;
          newInventoryList.add(BeanUtil.copyProperties(inventory, CoreInventoryComposeVo.class));
        }
      }
    }

    return newInventoryList;
  }

  /**
   * 生产日期规则过滤器
   *
   * @param inventoryList 待过滤库存集合
   * @param rule          过滤规则
   * @return 返回过滤后的库存集合
   */
  private List<CoreInventoryComposeVo> produceDateFilter(List<CoreInventoryComposeVo> inventoryList, Object rule) {
    List<Date> produceDateList = Convert.toList(Date.class, rule);
    inventoryList = inventoryList.stream().filter(x -> produceDateList.contains(x.getProduceDate())).toList();

    return inventoryList;
  }

  /**
   * 生产日期规则过滤器
   *
   * @param inventoryList 待过滤库存集合
   * @param rule          过滤规则
   * @return 返回过滤后的库存集合
   */
  private List<CoreInventoryComposeVo> produceDateRangeFilter(List<CoreInventoryComposeVo> inventoryList, Object rule) {
    ProduceDateRangeDto produceDateRangeDto = Convert.convert(ProduceDateRangeDto.class, rule);
    inventoryList = inventoryList.stream().filter(x -> DateUtil.isIn(x.getProduceDate(), produceDateRangeDto.getProduceDateStart(), produceDateRangeDto.getProduceDateEnd())).toList();

    return inventoryList;
  }

  @Override
  public CoreSortingRule getByCode(String orderCode) {
    LambdaQueryWrapper<CoreSortingRule> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(CoreSortingRule::getBillCode, orderCode);
    return this.getOnly(lambdaQueryWrapper);
  }

  /**
   * 货位数量分拣规则过滤器
   *
   * @param inventoryList 待过滤库存集合
   * @param rule          过滤规则
   * @return 返回过滤后的库存集合
   */
  private List<CoreInventoryComposeVo> batchNumberQtyFilter(List<CoreInventoryComposeVo> inventoryList, Object rule) {
    List<CoreInventoryComposeVo> newInventoryList = new ArrayList<>(); // 筛选出来可用库存
    List<BatchNumberQtyDto> positionNameQtyDtoList = Convert.toList(BatchNumberQtyDto.class, rule);
    for (BatchNumberQtyDto qtyDto : positionNameQtyDtoList) {
      var list = inventoryList.stream().filter(x -> StringUtils.equals(qtyDto.getBatchNumber(), x.getBatchNumber()) && B.isEqual(qtyDto.getPositionName(),x.getPositionName())).toList();

      BigDecimal qty = qtyDto.getQuantity();
      for (var inventory : list) {
        // 如果当前行有效库存小于等于当前规则里面的数量跳过
        if (B.isGreaterOrEqual(qty, inventory.getValidStorage())) {
          qty = B.sub(qty, inventory.getValidStorage());
          newInventoryList.add(BeanUtil.copyProperties(inventory, CoreInventoryComposeVo.class));
          inventory.setValidStorage(BigDecimal.ZERO); // 库存清零
          continue;
        }

        if (B.isGreater(qty)) {
          // 可参与分拣的实际数量
          inventory.setValidStorage(qty);
          // 剩余数量清零
          qty = BigDecimal.ZERO;
          newInventoryList.add(BeanUtil.copyProperties(inventory, CoreInventoryComposeVo.class));
        }
      }
    }

    return newInventoryList;
  }

  /*
  保存后事件
   */
  @Override
  public void afterSaveEditor(SaveEditorBo<CoreSortingRuleBo> saveEditorBo, EditorVo<CoreSortingRuleVo> editor) {
    Map<String, Object> expandFields = saveEditorBo.getData().getMaster().getExpandFields();
    if (ObjectUtil.isNull(expandFields)) {
      super.afterSaveEditor(saveEditorBo, editor);
      return;
    }
    if (ObjectUtil.isNotNull(expandFields.get("release")) && Convert.toBool(expandFields.get("release"))) {
      LoginUser loginUser = LoginHelper.getLoginUser();
      //调用RabbitMQ
      RabbitReceiverDto receiverDto = new RabbitReceiverDto();
      receiverDto.setRabbitmqType(RabbitmqTypeEnum.SORTING_SAVE_ORDER_SET_RELEASE); // 审核完成后，自动分拣、自动生成波次
      receiverDto.setBillId(saveEditorBo.getData().getMaster().getBillId());
      receiverDto.setBillCode(saveEditorBo.getData().getMaster().getBillCode());
      receiverDto.setLoginUser(loginUser);
      taskQueueService.createTask(receiverDto);
      //#endregion
    }
    //湘钢特殊处理，如果点击的是分拣按钮，则需要保存后分拣库存 湘钢
    //正常保存则不分配库存
    if (ObjectUtil.isNotNull(expandFields.get("isSorting")) && Convert.toBool(expandFields.get("isSorting"))) {
      LoginUser loginUser = LoginHelper.getLoginUser();
      //调用RabbitMQ
      RabbitReceiverDto receiverDto = new RabbitReceiverDto();
      receiverDto.setRabbitmqType(RabbitmqTypeEnum.ORDER_SORTING_TO_TMS); // 审核完成后，自动分拣、自动生成波次
      receiverDto.setBillId(saveEditorBo.getData().getMaster().getBillId());
      receiverDto.setBillCode(saveEditorBo.getData().getMaster().getBillCode());
      receiverDto.setLoginUser(loginUser);
      taskQueueService.createTask(receiverDto);
      //#endregion
    }


  }
}

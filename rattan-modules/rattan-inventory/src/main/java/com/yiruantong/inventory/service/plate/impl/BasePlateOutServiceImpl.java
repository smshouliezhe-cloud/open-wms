package com.yiruantong.inventory.service.plate.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.storage.BaseStorage;
import com.yiruantong.basic.service.storage.IBaseStorageService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.inventory.domain.plate.BasePlateOut;
import com.yiruantong.inventory.domain.plate.BasePlateOutDetail;
import com.yiruantong.inventory.domain.plate.bo.BasePlateOutBo;
import com.yiruantong.inventory.domain.plate.vo.BasePlateOutDetailMergeVo;
import com.yiruantong.inventory.domain.plate.vo.BasePlateOutVo;
import com.yiruantong.inventory.mapper.plate.BasePlateOutMapper;
import com.yiruantong.inventory.service.plate.IBasePlateOutDetailService;
import com.yiruantong.inventory.service.plate.IBasePlateOutService;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 容器借出主Service业务层处理
 *
 * @author YRT
 * @date 2023-12-21
 */
@RequiredArgsConstructor
@Service
public class BasePlateOutServiceImpl extends ServiceImplPlus<BasePlateOutMapper, BasePlateOut, BasePlateOutVo, BasePlateOutBo> implements IBasePlateOutService {
  private  final IBasePlateOutDetailService basePlateOutDetailService;
  private  final IBaseStorageService storageService;

  /**
   * 容器归还根据客户名称获取源借出单号
   *
   * @param map
   * @return
   */
  @Override
  public List<Map<String, Object>> getSourceInfo(Map<String, Object> map) {
    String clientShortName = Convert.toStr(map.get("clientShortName"));

    LambdaQueryWrapper<BasePlateOut> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper
      .eq(BasePlateOut::getClientShortName, clientShortName) // 客户名称
      .isNotNull(BasePlateOut::getCustomerOrderCode) // 客户订单号不为空
      .gt(BasePlateOut::getTotalUnreturnedQty, 0); // 未归还数量不为0


    try {
      List<String> fields = Arrays.asList("outId", "outCode","customerOrderCode","sourceCode"); // 查询默认字段
      // 自定义查询字段
      if (fields.size() > 0) {
        List<String> finalFields = fields;
        queryWrapper.select(BasePlateOut.class, s -> finalFields.contains(s.getProperty()));
      }
      var dataList = this.baseMapper.selectMaps(queryWrapper);
      return dataList;
    } catch (Exception error) {
      var msg = "异常错误信息：" + error.getCause();
      throw new ServiceException(msg);
    }
  }

  /**
   * 容器归还根据源借出单号查询对应的借出单信息
   *
   * @param map
   * @return
   */
  @Override
  public R <BasePlateOutDetailMergeVo> getOrderInfo(Map<String, Object> map) {
    try {
      LambdaQueryWrapper<BasePlateOut> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
      orderLambdaQueryWrapper
        .eq(BasePlateOut::getOutId, map.get("outId"));

      var plateOutInfo = this.getOne(orderLambdaQueryWrapper);
      if (ObjectUtil.isEmpty(plateOutInfo)) {
        throw new ServiceException("获取失败,未找到相应的单据信息。");
      }
      LambdaQueryWrapper<BasePlateOutDetail> orderDetailLambdaQueryWrapper = new LambdaQueryWrapper<>();
      orderDetailLambdaQueryWrapper.eq(BasePlateOutDetail::getOutId, plateOutInfo.getOutId())
        .gt(BasePlateOutDetail::getUnreturnedQty, 0);

      var plateOutDetails = basePlateOutDetailService.list(orderDetailLambdaQueryWrapper);

      LambdaQueryWrapper<BaseStorage> storageLambdaQueryWrapper = new LambdaQueryWrapper<>();
      storageLambdaQueryWrapper
        .eq(BaseStorage::getStorageName, plateOutInfo.getStorageName());
      var storageInfo = storageService.getOne(storageLambdaQueryWrapper);
      BasePlateOutDetailMergeVo orderAndDetailVO = new BasePlateOutDetailMergeVo(); //重新构建Vo，传到前端
      orderAndDetailVO.setDetails(plateOutDetails);
      orderAndDetailVO.setOrder(plateOutInfo);
      orderAndDetailVO.setSiteId(storageInfo.getSiteId());
      orderAndDetailVO.setSiteName(storageInfo.getSiteName());
      return R.ok(orderAndDetailVO);
    } catch (NoSuchMessageException e) {
      throw new ServiceException("错误" + e.getMessage());
    }
  }
}

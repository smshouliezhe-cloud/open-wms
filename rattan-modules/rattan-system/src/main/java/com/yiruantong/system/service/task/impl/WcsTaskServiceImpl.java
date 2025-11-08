package com.yiruantong.system.service.task.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yiruantong.system.domain.task.WcsTask;
import com.yiruantong.system.domain.task.bo.WcsTaskBo;
import com.yiruantong.system.domain.task.vo.WcsTaskVo;
import com.yiruantong.system.mapper.task.WcsTaskMapper;
import com.yiruantong.system.service.core.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.system.WcsTaskTypeEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.json.utils.JsonUtils;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.system.service.task.IWcsTaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * WCS接口Service业务层处理
 *
 * @author YRT
 * @date 2024-10-04
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class WcsTaskServiceImpl extends ServiceImplPlus<WcsTaskMapper, WcsTask, WcsTaskVo, WcsTaskBo> implements IWcsTaskService {
  private final ISysConfigService sysConfigService;
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Map<String, Object>> push(List<Long> ids) {
    String url = sysConfigService.selectConfigByKey("wcsUrl");
    if(StrUtil.isEmpty(url) || B.isEqual(url,"")){
      throw new ServiceException("请配置WCS接口地址");
    }
//    var url = "http://192.168.202.220:8072/wcs-service/service/taskInfo/addTask111"; // 修改为WCS系统的接口地址
//    var url = "http://192.168.202.220:8072/task/ceshi"; // WCS 测试地址
    List<WcsTask> taskList = this.listByIds(ids);
    for (var task : taskList) {
      WcsTaskTypeEnum taskType = WcsTaskTypeEnum.matchingEnumByName(task.getWcsTaskType());
      if (taskType == null) {
        task.setPushStatus("推送失败");
        task.setPushMsg("任务类型不正确");
        this.updateById(task);
        continue;
      }

      HashMap<String, Object> paramMap = new HashMap<>();
      paramMap.put("wmsId", task.getBillCode());
      paramMap.put("taskType", taskType.getCode());
      paramMap.put("palletId", task.getPlateCode());
      paramMap.put("warehouseId", task.getStorageCode());
      paramMap.put("skuCode", task.getProductCode());

      if(StrUtil.isEmpty(task.getItemGroup())){
        paramMap.put("itemGroup", "国产");
      }else{
        paramMap.put("itemGroup", task.getItemGroup());
      }
      if(StrUtil.isEmpty(task.getSingleSignCode())){
        paramMap.put("sn", task.getPlateCode());
      }else{
        paramMap.put("sn", task.getSingleSignCode());
      }

      paramMap.put("productDate", task.getProduceDate());
      paramMap.put("orderId", task.getBillCode());

      log.error("推送订单:"+task.getBillCode()+";接口地址："+url+"推送内容："+JsonUtils.toJsonString(paramMap));

      HttpResponse response = HttpRequest.post(url)
        .body(JSONUtil.toJsonStr(paramMap)) // 设置请求体
        .header("Content-Type", "application/json") // 设置请求头
        .execute();
      String result = response.body();
      log.error("推送订单:"+task.getBillCode()+";接口地址："+url+"返回内容："+result);

      JSONObject entries = JSONUtil.parseObj(result);

      int code = entries.getInt("code");
      task.setRemark(result);
      task.setPushCount(Convert.toLong(B.add(task.getPushCount(), 1L)));
      task.setPushDate(DateUtil.date());
      if (code == 200) {
        // 推送成功
        task.setPushStatus("推送成功");
        task.setPushMsg(entries.getStr("msg"));
        this.updateById(task);
      } else {
        task.setPushStatus("推送失败");
        task.setPushMsg(entries.getStr("msg"));
        this.updateById(task);
      }
    }

    return R.ok("推送成功");
  }
}

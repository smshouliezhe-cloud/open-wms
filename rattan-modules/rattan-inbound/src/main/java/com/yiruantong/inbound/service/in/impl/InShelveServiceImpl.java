package com.yiruantong.inbound.service.in.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.AuditEnum;
import com.yiruantong.common.core.enums.in.InShelveStatusEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.mybatis.enums.QueryTypeEnum;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.inbound.domain.in.InShelve;
import com.yiruantong.inbound.domain.in.bo.InShelveBo;
import com.yiruantong.inbound.domain.in.vo.InShelveVo;
import com.yiruantong.inbound.mapper.in.InShelveMapper;
import com.yiruantong.inbound.service.in.IInShelveService;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 商品上架Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-18
 */
@RequiredArgsConstructor
@Service
public class InShelveServiceImpl extends ServiceImplPlus<InShelveMapper, InShelve, InShelveVo, InShelveBo> implements IInShelveService {

  //#region 列表加载前事件
  @Override
  public void beforePageQuery(PageQuery pageQuery) {
    pageQuery.getQueryBoList().stream().filter(f -> f.getQueryType() == QueryTypeEnum.CUSTOM).forEach(f -> {
      if (f.getColumn().equals("productCode")) {
        f.setQueryType(QueryTypeEnum.EXISTS);
        f.setValues(StringUtils.format("SELECT shelve_id FROM in_shelve_detail d WHERE d.shelve_id=in_shelve.shelve_id AND d.product_code='{}'", f.getValues()));
      }
      //如果还有其他查询条件 则继续加else if

    });
    super.beforePageQuery(pageQuery);
  }
  //#endregion

  /**
   * 上架单编号
   *
   * @param shelveCode
   * @return 返回上架单信息
   */
  @Override
  public InShelve getByCode(String shelveCode) {
    LambdaQueryWrapper<InShelve> shelveLambdaQueryWrapper = new LambdaQueryWrapper<>();
    shelveLambdaQueryWrapper.eq(InShelve::getShelveCode, shelveCode);

    return this.getOne(shelveLambdaQueryWrapper);
  }

  /**
   * 根据入库单ID获取上架单信息
   *
   * @param enterId
   * @return 返回上架单信息
   */
  @Override
  public InShelve getByEnterId(Long enterId) {
    LambdaQueryWrapper<InShelve> shelveLambdaQueryWrapper = new LambdaQueryWrapper<>();
    shelveLambdaQueryWrapper.eq(InShelve::getEnterId, enterId).last("limit 1");

    return this.getOne(shelveLambdaQueryWrapper);
  }

  /**
   * 更新上架单状态
   *
   * @param shelveId
   * @return 返回更新完成
   */
  @Override
  public boolean updateShelveStatus(Long shelveId, InShelveStatusEnum status) {
    LambdaUpdateWrapper<InShelve> lambda = new UpdateWrapper<InShelve>().lambda();
    lambda.set(InShelve::getShelveStatus, status.getName())
      .set(InShelve::getAuditing, AuditEnum.AUDITED_SUCCESS.getId())
      .set(InShelve::getAuditor, LoginHelper.getNickname())
      .set(InShelve::getAuditDate, DateUtil.date())
      .eq(InShelve::getShelveId, shelveId);

    return this.update(lambda);//更新状态
  }

  //#region 强制上架
  @Override
  public R<Void> forceShelve(Map<String, Object> map) {
    try {
      String[] ids = StringUtils.split(map.get("ids").toString(), ",");
      Long[] _ids = Convert.toLongArray(ids);

      //修改状态为 上架完成
      LambdaUpdateWrapper<InShelve> lwq = new UpdateWrapper<InShelve>().lambda();
      lwq.set(InShelve::getShelveStatus, InShelveStatusEnum.COMPLETION.getName())
        .in(InShelve::getShelveId, _ids);
      this.update(lwq);//提交
      return R.ok("强制上架完成！");
    } catch (NoSuchMessageException e) {
      throw new ServiceException("错误" + e.getMessage());
    }
  }
//#endregion

  @Override
  public R<Void> cancelNickName(Map<String, Object> map) {
    try {
      String[] ids = StringUtils.split(map.get("ids").toString(), ",");
      Long[] _ids = Convert.toLongArray(ids);

      for (Long shelveId : _ids) {
        InShelveVo inShelveVo = this.baseMapper.selectVoById(shelveId);
        if (ObjectUtil.isEmpty(inShelveVo)) {
          throw new ServiceException("未获取到预到货单");
        }
        if (InShelveStatusEnum.COMPLETION.getName().equals(inShelveVo.getShelveStatus())) {
          throw new ServiceException(inShelveVo.getShelveCode() + "只有是在上架中、部分上架才可以解绑！");
        }

        //修改状态为
        LambdaUpdateWrapper<InShelve> lwq = new UpdateWrapper<InShelve>().lambda();
        lwq.set(InShelve::getUserId, null)
          .set(InShelve::getNickName, null)
          .set(InShelve::getReceiveTaskPeople, null)
          .eq(InShelve::getShelveId, shelveId);
        this.update(lwq);//提交

      }
      return R.ok("上架人信息已解绑！");
    } catch (NoSuchMessageException e) {
      throw new ServiceException("错误" + e.getMessage());
    }
  }

  @Override
  public R<Void> receiveTask(Map<String, Object> map) {
    String shelveCode = Convert.toStr(map.get("shelveCode"));
    InShelve shelveInfo = this.getByCode(shelveCode);
    if (B.isEqual(shelveInfo.getUserId()) && !B.isEqual(shelveInfo.getUserId(), LoginHelper.getUserId())) {
      return R.fail("当前上架任务已经被抢走，请选择其他上架任务！");
    }
    LambdaUpdateWrapper<InShelve> inShelveLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    inShelveLambdaUpdateWrapper.set(InShelve::getUserId, LoginHelper.getUserId())
      .set(InShelve::getNickName, LoginHelper.getNickname())
      .eq(InShelve::getShelveId, shelveInfo.getShelveId());
    this.update(inShelveLambdaUpdateWrapper);
    return R.ok("领取任务成功！");
  }
}

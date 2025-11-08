package com.yiruantong.inbound.controller.in;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inbound.domain.in.InEnter;
import com.yiruantong.inbound.domain.in.bo.InEnterBo;
import com.yiruantong.inbound.domain.in.vo.InCreateShelveVo;
import com.yiruantong.inbound.domain.in.vo.InEnterVo;
import com.yiruantong.inbound.mapper.in.InEnterMapper;
import com.yiruantong.inbound.service.in.IInEnterService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 入库管理
 *
 * @author YiRuanTong
 * @date 2023-10-17
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inbound/in/enter")
public class InEnterController extends AbstractController<InEnterMapper, InEnter, InEnterVo, InEnterBo> {
  private final IInEnterService inEnterService;

  /**
   * 撤销入库前校验
   *
   * @param map 前端传递参数
   * @return
   */
  @PostMapping("/quickOut")
  public R<Void> quickOut(@RequestBody Map<String, Object> map) {
    return inEnterService.quickOut(map);
  }

  /**
   * 撤销入库
   *
   * @param map 前端传递参数
   * @return
   */
  @PostMapping("/cancelEnter")
  public R<Void> cancelEnter(@RequestBody Map<String, Object> map) {
    return inEnterService.cancelEnter(map);
  }

  /**
   * 生成上架单
   *
   * @param map 前端传递参数
   * @return
   */
  @PostMapping("/createShelve")
  public R<Void> createShelve(@RequestBody Map<String, Object> map) {
    return inEnterService.createShelve(map);
  }

  /**
   * 查询生成上架单的数据信息
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/selectCreateShelveList")
  public TableDataInfo<InCreateShelveVo> selectCreateShelveList(@RequestBody PageQuery pageQuery) {
    return inEnterService.selectCreateShelveList(pageQuery);
  }

  /**
   * 重复分拣
   *
   * @param map 前端传递参数
   * @return
   */
  @PostMapping("/repeatingSorting")
  public R<Void> repeatingSorting(@RequestBody Map<String, Object> map) {
    return inEnterService.repeatingSorting(map);
  }

  /**
   * 生成一次性费用
   *
   * @param map 前端传递参数
   * @return
   */
  @PostMapping("/createBill")
  public R<Void> createBill(@RequestBody Map<String, Object> map) {
    return inEnterService.createBill(map);
  }
}

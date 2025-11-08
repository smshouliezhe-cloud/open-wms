package com.yiruantong.inbound.controller.in;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inbound.domain.in.InShelve;
import com.yiruantong.inbound.domain.in.vo.InCreateShelveVo;
import com.yiruantong.inbound.domain.in.vo.InShelveVo;
import com.yiruantong.inbound.domain.in.bo.InShelveBo;
import com.yiruantong.inbound.mapper.in.InShelveMapper;
import com.yiruantong.inbound.service.in.IInEnterService;
import com.yiruantong.inbound.service.in.IInOrderService;
import com.yiruantong.inbound.service.in.IInShelveCancelService;
import com.yiruantong.inbound.service.in.IInShelveService;
import com.yiruantong.inventory.domain.core.vo.CoreSnComposeVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 商品上架
 *
 * @author YiRuanTong
 * @date 2023-10-18
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inbound/in/shelve")
public class InShelveController extends AbstractController<InShelveMapper, InShelve, InShelveVo, InShelveBo> {

  private final IInShelveService inShelveService;

  private final IInShelveCancelService inSchelveCancelService;


  /**
   * 强制上架完成
   *
   * @param map 审核参数
   */
  @PostMapping("/forceShelve")
  public R<Void> forceShelve(@RequestBody Map<String, Object> map) {
    return inShelveService.forceShelve(map);
  }

  /**
   * 取消上架
   *
   * @param map 审核参数
   */
  @PostMapping("/cancelShelve")
  public R<Void> cancelShelve(@RequestBody Map<String, Object> map) {
    //原先调用存储过程把存储过程提取到Service中
    return inSchelveCancelService.cancelShelve(map);
  }

  /**
   * 取消上架记录查询的上架人
   *
   * @param map 审核参数
   */
  @PostMapping("/cancelNickName")
  public R<Void> cancelNickName(@RequestBody Map<String, Object> map) {
    return inShelveService.cancelNickName(map);
  }


  /**
   * PDA 领取任务
   *
   * @param map 参数
   */
  @PostMapping("/receiveTask")
  public R<Void> receiveTask(@RequestBody Map<String, Object> map) {
    return inShelveService.receiveTask(map);
  }
}

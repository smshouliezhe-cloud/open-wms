package com.yiruantong.system.controller.core;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yiruantong.system.domain.core.SysNotice;
import com.yiruantong.system.domain.core.bo.SysNoticeBo;
import com.yiruantong.system.domain.core.vo.SysNoticeVo;
import com.yiruantong.system.mapper.core.SysNoticeMapper;
import com.yiruantong.system.service.core.ISysNoticeService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 公告 信息操作处理
 *
 * @author YiRuanTong
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/core/notice")
public class SysNoticeController extends AbstractController<SysNoticeMapper, SysNotice, SysNoticeVo, SysNoticeBo> {

  private final ISysNoticeService noticeService;

  /**
   * 获取通知公告列表
   */
  @SaCheckPermission("system:notice:list")
  @GetMapping("/list")
  public TableDataInfo<SysNoticeVo> list(SysNoticeBo notice, PageQuery pageQuery) {
    return noticeService.selectPageNoticeList(notice, pageQuery);
  }

  /**
   * 根据通知公告编号获取详细信息
   *
   * @param noticeId 公告ID
   */
  @SaCheckPermission("system:notice:query")
  @GetMapping(value = "/{noticeId}")
  public R<SysNoticeVo> getInfo(@PathVariable Long noticeId) {
    return R.ok(noticeService.selectNoticeById(noticeId));
  }

  /**
   * 新增通知公告
   */
  @SaCheckPermission("system:notice:add")
  @Log(title = "通知公告", businessType = BusinessType.INSERT)
  @PostMapping
  public R<Void> add(@Validated @RequestBody SysNoticeBo notice) {
    return toAjax(noticeService.insertNotice(notice));
  }

  /**
   * 修改通知公告
   */
  @SaCheckPermission("system:notice:edit")
  @Log(title = "通知公告", businessType = BusinessType.UPDATE)
  @PutMapping
  public R<Void> editData(@Validated @RequestBody SysNoticeBo notice) {
    return toAjax(noticeService.updateNotice(notice));
  }

  /**
   * 删除通知公告
   *
   * @param noticeIds 公告ID串
   */
  @SaCheckPermission("system:notice:remove")
  @Log(title = "通知公告", businessType = BusinessType.DELETE)
  @DeleteMapping("/{noticeIds}")
  public R<Void> remove(@PathVariable Long[] noticeIds) {
    return toAjax(noticeService.deleteNoticeByIds(noticeIds));
  }
}

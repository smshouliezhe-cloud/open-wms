package com.yiruantong.system.service.core;

import com.yiruantong.system.domain.core.SysNotice;
import com.yiruantong.system.domain.core.bo.SysNoticeBo;
import com.yiruantong.system.domain.core.vo.SysNoticeVo;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.List;

/**
 * 公告 服务层
 *
 * @author YiRuanTong
 */
public interface ISysNoticeService extends IServicePlus<SysNotice, SysNoticeVo, SysNoticeBo> {


  TableDataInfo<SysNoticeVo> selectPageNoticeList(SysNoticeBo notice, PageQuery pageQuery);

  /**
   * 查询公告信息
   *
   * @param noticeId 公告ID
   * @return 公告信息
   */
  SysNoticeVo selectNoticeById(Long noticeId);

  /**
   * 查询公告列表
   *
   * @param notice 公告信息
   * @return 公告集合
   */
  List<SysNoticeVo> selectNoticeList(SysNoticeBo notice);

  /**
   * 新增公告
   *
   * @param bo 公告信息
   * @return 结果
   */
  int insertNotice(SysNoticeBo bo);

  /**
   * 修改公告
   *
   * @param bo 公告信息
   * @return 结果
   */
  int updateNotice(SysNoticeBo bo);

  /**
   * 删除公告信息
   *
   * @param noticeId 公告ID
   * @return 结果
   */
  int deleteNoticeById(Long noticeId);

  /**
   * 批量删除公告信息
   *
   * @param noticeIds 需要删除的公告ID
   * @return 结果
   */
  int deleteNoticeByIds(Long[] noticeIds);
}

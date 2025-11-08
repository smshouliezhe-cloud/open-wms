package com.yiruantong.common.mybatis.core.domain.bo;

import lombok.Data;

import java.util.List;

@Data
public class SaveDataBo<B> {
  /** 主表数 */
  private B master;

  /** 明细表名 */
  private List<SaveDataDetailBo> detailList;
}

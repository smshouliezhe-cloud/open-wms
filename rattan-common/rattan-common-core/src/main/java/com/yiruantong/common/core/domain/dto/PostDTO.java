package com.yiruantong.common.core.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 岗位
 *
 * @author xietb
 */

@Data
@NoArgsConstructor
public class PostDTO implements Serializable {

  /**
   * 岗位ID
   */
  private Long postId;

  /**
   * 名称
   */
  private String postName;
}

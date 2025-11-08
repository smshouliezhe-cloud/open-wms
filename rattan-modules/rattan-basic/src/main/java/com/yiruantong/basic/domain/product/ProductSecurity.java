package com.yiruantong.basic.domain.product;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.common.mybatis.core.domain.TenantEntity;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 获取防伪码信息
 *
 * @author YRT
 * @date 2025-02-25
 */
@Data
public class ProductSecurity  {


  /**
   * 产品ID
   */
  private Long productId;
  /**
   * 批次号
   */
  private String batchNumber;
  /**
   * 数量
   */
  private BigDecimal quantity;
  /**
   * 防伪码
   */
  private String securityCode;


}

package com.yiruantong.inventory.domain.core.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.yiruantong.inventory.domain.core.CoreInventoryHolder;

import java.io.Serializable;


/**
 * 库存占位查询(异常)视图对象 core_inventory_holder
 *
 * @author YiRuanTong
 * @date 2023-12-09
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CoreInventoryHolderComposeVo extends CoreInventoryHolder implements Serializable {
}

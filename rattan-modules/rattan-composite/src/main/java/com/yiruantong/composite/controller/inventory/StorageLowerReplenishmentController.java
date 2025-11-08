package com.yiruantong.composite.controller.inventory;

import cn.hutool.core.bean.BeanUtil;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.composite.domain.inventory.Bo.StorageLowerReplenishmentBo;
import com.yiruantong.composite.service.inventory.IStorageLowerReplenishmentService;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryReplenishmentVo;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 库存下限转补货单
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/composite/StorageLowerReplenishment")
public class StorageLowerReplenishmentController {
  private final IStorageLowerReplenishmentService storageLowerReplenishmentService;
  final private ICoreInventoryService coreInventoryService;


  /**
   * 生成补货单
   */
  @PostMapping("/toReplenishment")
  public R<Void> toReplenishment(@Validated @RequestBody List<StorageLowerReplenishmentBo> storageReplenishmentBoList) {
    storageLowerReplenishmentService.toReplenishment(LoginHelper.getLoginUser(), storageReplenishmentBoList);

    return R.ok("提交成功，正在异步执行");
  }

  /**
   * 自动生成补货单
   */
  @PostMapping(value = "/autoToReplenishment")
  public R<Void> autoToReplenishment() {
    LoginUser loginUser = LoginHelper.getLoginUser();

    try {
      // 可以生成的补货单候选数据
      List<StorageLowerReplenishmentBo> storageReplenishmentBoList;
      PageQuery pageQuery = new PageQuery();
      pageQuery.setMenuId(MenuEnum.MENU_2235.getId());
      pageQuery.setPageIndex(1);
      pageQuery.setPageSize(5000);
      pageQuery.setTableName("core_inventory");
      pageQuery.setIsAsc("desc");
      pageQuery.setListMethod("selectInventoryReplenishmentList");
      pageQuery.setOrderByColumn("inventoryId");
      pageQuery.setSumColumnNames(List.of());

      final TableDataInfo<CoreInventoryReplenishmentVo> replenishmentVoTableDataInfo = coreInventoryService.selectInventoryReplenishmentList(pageQuery);
      // 过滤掉以生成的库存
      final List<CoreInventoryReplenishmentVo> replenishmentVoList = replenishmentVoTableDataInfo.getRows().stream().filter(x -> B.isEqual(x.getIsCreateReplenishment(), 0)).toList();
      storageReplenishmentBoList = BeanUtil.copyToList(replenishmentVoList, StorageLowerReplenishmentBo.class);

      storageLowerReplenishmentService.toReplenishment(loginUser, storageReplenishmentBoList);
    } catch (Exception e) {
      return R.fail("执行错误：" + e.getMessage());
    }
    return R.ok("开始执行...");
  }
}

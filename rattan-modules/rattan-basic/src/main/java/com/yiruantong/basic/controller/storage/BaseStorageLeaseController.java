package com.yiruantong.basic.controller.storage;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.basic.domain.storage.BaseStorageLease;
import com.yiruantong.basic.domain.storage.vo.BaseStorageLeaseVo;
import com.yiruantong.basic.domain.storage.bo.BaseStorageLeaseBo;
import com.yiruantong.basic.mapper.storage.BaseStorageLeaseMapper;
import com.yiruantong.basic.service.storage.IBaseStorageLeaseService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 租赁管理
 *
 * @author YRT
 * @date 2024-03-09
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/storage/storageLease")
public class BaseStorageLeaseController extends AbstractController<BaseStorageLeaseMapper, BaseStorageLease, BaseStorageLeaseVo, BaseStorageLeaseBo> {
}

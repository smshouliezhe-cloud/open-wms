package com.yiruantong.basic.controller.storage;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.basic.domain.storage.BaseStorageShelve;
import com.yiruantong.basic.domain.storage.vo.BaseStorageShelveVo;
import com.yiruantong.basic.domain.storage.bo.BaseStorageShelveBo;
import com.yiruantong.basic.mapper.storage.BaseStorageShelveMapper;
import com.yiruantong.basic.service.storage.IBaseStorageShelveService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 仓库货架
 *
 * @author YRT
 * @date 2024-02-22
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/storage/storageShelve")
public class BaseStorageShelveController extends AbstractController<BaseStorageShelveMapper, BaseStorageShelve, BaseStorageShelveVo, BaseStorageShelveBo> {
}

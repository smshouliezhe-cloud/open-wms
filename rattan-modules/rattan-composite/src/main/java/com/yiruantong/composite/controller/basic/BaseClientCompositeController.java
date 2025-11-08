package com.yiruantong.composite.controller.basic;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.BaseController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户信息操作
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/composite/basic/baseClientComposite")
class BaseClientCompositeController extends BaseController {

//  private final IBaseClientCompositeService baseClientCompositeService;
//
//  @Log(title = "导入数据", businessType = BusinessType.IMPORT)
//  @PostMapping(value = "/importData", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//  public R<Void> importData(@RequestPart("file") MultipartFile file, Long importId, HttpServletRequest request) {
//    String key = request.getParameter("key");
//    try {
//      baseClientCompositeService.importData(file.getInputStream(), importId, request, LoginHelper.getLoginUser());
//    } catch (Exception e) {
//      QueueUtils.addQueueObject(key, "错误：" + e);
//      QueueUtils.getClient().getQueue(key).expire(Instant.now().plus(10, ChronoUnit.MINUTES)); // 设置过期时间
//      QueueUtils.addQueueObject(key, "-1");
//    }
//    return R.ok("开始执行...");
//  }

}

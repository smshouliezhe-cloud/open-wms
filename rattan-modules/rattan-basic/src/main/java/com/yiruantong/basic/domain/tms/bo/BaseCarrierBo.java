package com.yiruantong.basic.domain.tms.bo;

import com.yiruantong.basic.domain.tms.BaseCarrier;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Map;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 承运商管理业务对象 base_carrier
 *
 * @author YRT
 * @date 2025-06-16
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BaseCarrier.class, reverseConvertGenerate = false)
public class BaseCarrierBo extends BaseEntity {

      /**
       * 承运商ID
       */
    private Long carrierId;

      /**
       * 承运商编号
       */
        @NotBlank(message = "承运商编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String carrierCode;

      /**
       * 承运方名称
       */
        @NotBlank(message = "承运方名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String carrierName;

      /**
       * 公司性质
       */
    private String companyType;

      /**
       * 承运方类型
       */
    private String carrierType;

      /**
       * 纳税人识别号
       */
    private String taxpayerNumber;

      /**
       * 营业执照
       */
    private String businessLicense;

      /**
       * 英文名称
       */
    private String englishName;

      /**
       * 开户行
       */
    private String bank;

      /**
       * 助记码
       */
    private String helpCode;

      /**
       * 银行账号
       */
    private String bankAccount;

      /**
       * 所属地
       */
    private String homeIand;

      /**
       * 公司地址
       */
    private String companyAddress;

      /**
       * 所属商务
       */
    private String business;

      /**
       * 结算方式
       */
    private String settlementMode;

      /**
       * 官方网址
       */
    private String officialWebsite;

      /**
       * 联系人
       */
    private String contactName;

      /**
       * 联系电话
       */
    private String contactNumber;

      /**
       * 扩展字段
       */
    private Map<String, Object> expandFields;

      /**
       * 备注
       */
    private String remark;

      /**
       * 删除时间
       */
    private Date deleteTime;

      /**
       * 删除人id
       */
    private Long deleteBy;

      /**
       * 删除人
       */
    private String deleteByName;

      /**
       * 排序号
       */
    private Long orderNum;

      /**
       * 是否可用
       */
    private Byte enable;

  
}

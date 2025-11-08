package com.yiruantong.basic.domain.tms.vo;

  import java.util.Date;
  import com.fasterxml.jackson.annotation.JsonFormat;
import com.yiruantong.basic.domain.tms.BaseCarrier;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yiruantong.common.excel.annotation.ExcelDictFormat;
import com.yiruantong.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;
import java.util.Map;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * 承运商管理视图对象 base_carrier
 *
 * @author YRT
 * @date 2025-06-16
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BaseCarrier.class)
public class BaseCarrierVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

      /**
       * 承运商ID
       */
      @ExcelProperty(value = "承运商ID")
    private Long carrierId;

      /**
       * 承运商编号
       */
      @ExcelProperty(value = "承运商编号")
    private String carrierCode;

      /**
       * 承运方名称
       */
      @ExcelProperty(value = "承运方名称")
    private String carrierName;

      /**
       * 公司性质
       */
      @ExcelProperty(value = "公司性质")
    private String companyType;

      /**
       * 承运方类型
       */
      @ExcelProperty(value = "承运方类型")
    private String carrierType;

      /**
       * 纳税人识别号
       */
      @ExcelProperty(value = "纳税人识别号")
    private String taxpayerNumber;

      /**
       * 营业执照
       */
      @ExcelProperty(value = "营业执照")
    private String businessLicense;

      /**
       * 英文名称
       */
      @ExcelProperty(value = "英文名称")
    private String englishName;

      /**
       * 开户行
       */
      @ExcelProperty(value = "开户行")
    private String bank;

      /**
       * 助记码
       */
      @ExcelProperty(value = "助记码")
    private String helpCode;

      /**
       * 银行账号
       */
      @ExcelProperty(value = "银行账号")
    private String bankAccount;

      /**
       * 所属地
       */
      @ExcelProperty(value = "所属地")
    private String homeIand;

      /**
       * 公司地址
       */
      @ExcelProperty(value = "公司地址")
    private String companyAddress;

      /**
       * 所属商务
       */
      @ExcelProperty(value = "所属商务")
    private String business;

      /**
       * 结算方式
       */
      @ExcelProperty(value = "结算方式")
    private String settlementMode;

      /**
       * 官方网址
       */
      @ExcelProperty(value = "官方网址")
    private String officialWebsite;

      /**
       * 联系人
       */
      @ExcelProperty(value = "联系人")
    private String contactName;

      /**
       * 联系电话
       */
      @ExcelProperty(value = "联系电话")
    private String contactNumber;

      /**
       * 扩展字段
       */
      @ExcelProperty(value = "扩展字段")
    private Map<String, Object> expandFields;

      /**
       * 备注
       */
      @ExcelProperty(value = "备注")
    private String remark;

      /**
       * 创建人
       */
      @ExcelProperty(value = "创建人")
    private String createByName;

      /**
       * 创建时间
       */
      @ExcelProperty(value = "创建时间")
    private Date createTime;

      /**
       * 修改人
       */
      @ExcelProperty(value = "修改人")
    private String updateByName;

      /**
       * 修改时间
       */
      @ExcelProperty(value = "修改时间")
    private Date updateTime;

      /**
       * 删除时间
       */
      @ExcelProperty(value = "删除时间")
    private Date deleteTime;

      /**
       * 删除人id
       */
      @ExcelProperty(value = "删除人id")
    private Long deleteBy;

      /**
       * 删除人
       */
      @ExcelProperty(value = "删除人")
    private String deleteByName;

      /**
       * 排序号
       */
      @ExcelProperty(value = "排序号")
    private Long orderNum;

      /**
       * 是否可用
       */
      @ExcelProperty(value = "是否可用")
    private Byte enable;

  
}

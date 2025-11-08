package com.yiruantong.basic.domain.tms.vo;

  import java.util.Date;
  import com.fasterxml.jackson.annotation.JsonFormat;
import com.yiruantong.basic.domain.tms.TmsSite;
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
 * 网点管理视图对象 tms_site
 *
 * @author YRT
 * @date 2025-02-20
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TmsSite.class)
public class TmsSiteVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

      /**
       * 网点Id
       */
      @ExcelProperty(value = "网点Id")
    private Long siteId;

      /**
       * 网点编号
       */
      @ExcelProperty(value = "网点编号")
    private String siteCode;

      /**
       * 网点类型
       */
      @ExcelProperty(value = "网点类型")
    private String siteType;

      /**
       * 网点名称
       */
      @ExcelProperty(value = "网点名称")
    private String siteName;

      /**
       * 详细地址
       */
      @ExcelProperty(value = "详细地址")
    private String siteAddress;

      /**
       * 所属区域
       */
      @ExcelProperty(value = "所属区域")
    private String region;

      /**
       * 网店负责人
       */
      @ExcelProperty(value = "网店负责人")
    private String sitePerson;

      /**
       * 负责人电话
       */
      @ExcelProperty(value = "负责人电话")
    private String personPhone;

      /**
       * 仓库编号
       */
      @ExcelProperty(value = "仓库编号")
    private String storageCode;

      /**
       * 仓库编号
       */
      @ExcelProperty(value = "仓库编号")
    private String storageName;

      /**
       * 仓库Id
       */
      @ExcelProperty(value = "仓库Id")
    private Long storageId;

      /**
       * 排序号
       */
      @ExcelProperty(value = "排序号")
    private Long orderNum;

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
       * 省id
       */
      @ExcelProperty(value = "省id")
    private Long provinceId;

      /**
       * 省
       */
      @ExcelProperty(value = "省")
    private String provinceName;

      /**
       * 市id
       */
      @ExcelProperty(value = "市id")
    private Long cityId;

      /**
       * 市
       */
      @ExcelProperty(value = "市")
    private String cityName;

      /**
       * 区id
       */
      @ExcelProperty(value = "区id")
    private Long regionId;

      /**
       * 区
       */
      @ExcelProperty(value = "区")
    private String regionName;

      /**
       * 街道
       */
      @ExcelProperty(value = "街道")
    private String street;

      /**
       * 国家
       */
      @ExcelProperty(value = "国家")
    private String countryNameCn;

      /**
       * 洲
       */
      @ExcelProperty(value = "洲")
    private String continent;


}

package com.yiruantong.basic.domain.tms.bo;

import com.yiruantong.basic.domain.tms.TmsSite;
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
 * 网点管理业务对象 tms_site
 *
 * @author YRT
 * @date 2025-02-20
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TmsSite.class, reverseConvertGenerate = false)
public class TmsSiteBo extends BaseEntity {

      /**
       * 网点Id
       */
        @NotNull(message = "网点Id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long siteId;

      /**
       * 网点编号
       */
        @NotBlank(message = "网点编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String siteCode;

      /**
       * 网点类型
       */
        @NotBlank(message = "网点类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String siteType;

      /**
       * 网点名称
       */
        @NotBlank(message = "网点名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String siteName;

      /**
       * 详细地址
       */
        @NotBlank(message = "详细地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private String siteAddress;

      /**
       * 所属区域
       */
        @NotBlank(message = "所属区域不能为空", groups = { AddGroup.class, EditGroup.class })
    private String region;

      /**
       * 网店负责人
       */
        @NotBlank(message = "网店负责人不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sitePerson;

      /**
       * 负责人电话
       */
        @NotBlank(message = "负责人电话不能为空", groups = { AddGroup.class, EditGroup.class })
    private String personPhone;

      /**
       * 仓库编号
       */
        @NotBlank(message = "仓库编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String storageCode;

      /**
       * 仓库编号
       */
        @NotBlank(message = "仓库编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String storageName;

      /**
       * 仓库Id
       */
        @NotNull(message = "仓库Id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long storageId;

      /**
       * 排序号
       */
        @NotNull(message = "排序号不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long orderNum;

      /**
       * 扩展字段
       */
        @NotNull(message = "扩展字段不能为空", groups = { AddGroup.class, EditGroup.class })
    private Map<String, Object> expandFields;

      /**
       * 备注
       */
        @NotBlank(message = "备注不能为空", groups = { AddGroup.class, EditGroup.class })
    private String remark;

      /**
       * 删除时间
       */
        @NotNull(message = "删除时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date deleteTime;

      /**
       * 删除人id
       */
        @NotNull(message = "删除人id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long deleteBy;

      /**
       * 删除人
       */
        @NotBlank(message = "删除人不能为空", groups = { AddGroup.class, EditGroup.class })
    private String deleteByName;

      /**
       * 省id
       */
        @NotNull(message = "省id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long provinceId;

      /**
       * 省
       */
        @NotBlank(message = "省不能为空", groups = { AddGroup.class, EditGroup.class })
    private String provinceName;

      /**
       * 市id
       */
        @NotNull(message = "市id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long cityId;

      /**
       * 市
       */
        @NotBlank(message = "市不能为空", groups = { AddGroup.class, EditGroup.class })
    private String cityName;

      /**
       * 区id
       */
        @NotNull(message = "区id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long regionId;

      /**
       * 区
       */
        @NotBlank(message = "区不能为空", groups = { AddGroup.class, EditGroup.class })
    private String regionName;

      /**
       * 街道
       */
        @NotBlank(message = "街道不能为空", groups = { AddGroup.class, EditGroup.class })
    private String street;

      /**
       * 国家
       */
        @NotBlank(message = "国家不能为空", groups = { AddGroup.class, EditGroup.class })
    private String countryNameCn;

      /**
       * 洲
       */
        @NotBlank(message = "洲不能为空", groups = { AddGroup.class, EditGroup.class })
    private String continent;


}

package com.yiruantong.generator.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.generator.domain.vo.GenTableTenantVo;
import com.yiruantong.generator.mapper.GenTableTenantMapper;
import com.yiruantong.generator.service.IGenTableService;
import com.yiruantong.generator.service.IGenTableTenantService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.jdbc.SQL;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.MapstructUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.generator.domain.GenTable;
import com.yiruantong.generator.domain.GenTableTenant;
import com.yiruantong.generator.domain.bo.GenTableTenantBo;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生成表租户数据Service业务层处理
 *
 * @author 谢天保
 * @date 2023-06-19
 */
@RequiredArgsConstructor
@Service
public class GenTableTenantServiceImpl
  extends ServiceImplPlus<
  GenTableTenantMapper, GenTableTenant, GenTableTenantVo, GenTableTenantBo>
  implements IGenTableTenantService {

  private final GenTableTenantMapper baseMapper;
  private final IGenTableService genTableService;

  /**
   * 查询生成表租户数据
   */
  @Override
  public GenTableTenantVo queryById(Long tableTenantId) {
    return baseMapper.selectVoById(tableTenantId);
  }

  /**
   * 查询生成表租户数据列表
   */
  @Override
  public TableDataInfo<GenTableTenantVo> queryPageList(GenTableTenantBo bo, PageQuery pageQuery) {
    LambdaQueryWrapper<GenTableTenant> lqw = buildQueryWrapper(bo);
    Page<GenTableTenantVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
    return TableDataInfo.build(result);
  }

  /**
   * 查询生成表租户数据列表
   */
  @Override
  public List<GenTableTenantVo> queryList(GenTableTenantBo bo) {
    LambdaQueryWrapper<GenTableTenant> lqw = buildQueryWrapper(bo);
    return baseMapper.selectVoList(lqw);
  }

  private LambdaQueryWrapper<GenTableTenant> buildQueryWrapper(GenTableTenantBo bo) {
    Map<String, Object> params = bo.getParams();
    LambdaQueryWrapper<GenTableTenant> lqw = Wrappers.lambdaQuery();
    lqw.eq(ObjectUtil.isNotEmpty(bo.getTableId()), GenTableTenant::getTableId, bo.getTableId());
    lqw.like(
      StringUtils.isNotBlank(bo.getTableTenantName()),
      GenTableTenant::getTableTenantName,
      bo.getTableTenantName());
    lqw.eq(StringUtils.isNotBlank(bo.getJsonData()), GenTableTenant::getJsonData, bo.getJsonData());
    lqw.eq(
      ObjectUtil.isNotEmpty(bo.getFromTableTenantId()),
      GenTableTenant::getFromTableTenantId,
      bo.getFromTableTenantId());
    // lqw.eq(
    //   StringUtils.isNotBlank(bo.getExpandFields()),
    //   GenTableTenant::getExpandFields,
    //   bo.getExpandFields());
    return lqw;
  }

  /**
   * 新增生成表租户数据
   */
  @Override
  public Boolean insertByBo(GenTableTenantBo bo) {
    GenTableTenant add = MapstructUtils.convert(bo, GenTableTenant.class);
    validEntityBeforeSave(add);
    boolean flag = baseMapper.insert(add) > 0;
    if (flag) {
      bo.setTableTenantId(add.getTableTenantId());
    }
    return flag;
  }

  /**
   * 修改生成表租户数据
   */
  @Override
  public Boolean updateByBo(GenTableTenantBo bo) {
    GenTableTenant update = MapstructUtils.convert(bo, GenTableTenant.class);
    validEntityBeforeSave(update);
    return baseMapper.updateById(update) > 0;
  }

  /**
   * 保存前的数据校验
   */
  @Override
  public boolean validEntityBeforeSave(GenTableTenant entity) {
    // TODO 做一些数据校验,如唯一约束
    return true;
  }

  /**
   * 批量删除生成表租户数据
   */
  @Override
  public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
    if (isValid) {
      // TODO 做一些业务上的校验,判断是否需要校验
    }
    return baseMapper.deleteBatchIds(ids) > 0;
  }

  /**
   * 保存UI设计
   */
  @Override
  public R<Map<String, Object>> saveui(GenTableTenantBo genTableTenantBo) {
    Long tableId = genTableTenantBo.getTableId();
    Long tableTenantId = genTableTenantBo.getTableTenantId();
    Long fromTableTenantId = genTableTenantBo.getFromTableTenantId();
    String designMode = genTableTenantBo.getDesignMode(); // 设计模式：用户自定义、系统
    R<Map<String, Object>> r = R.ok();
    r.setResult(true);

    try {
      if (designMode.equals("system")) {
        //#region 系统设计
        if (tableTenantId == 0) {
          // 主数据
          GenTable genTableInfo = genTableService.selectGenTableById(genTableTenantBo.getTableId());
          if (ObjectUtil.isNotEmpty(genTableInfo)) {
            final UpdateWrapper<GenTable> genTableUpdateWrapper = new UpdateWrapper<>();
            genTableUpdateWrapper
              .lambda()
              .set(GenTable::getJsonData, genTableTenantBo.getJsonData())
              .eq(GenTable::getTableId, genTableTenantBo.getTableId());
            genTableService.update(null, genTableUpdateWrapper);
          }
          Map<String, Object> map = new HashMap<>();
          map.put("tableTenantId", tableTenantId);

          r.setMsg("保存成功");
          r.setData(map);
        } else {
          // 复制模块
          if (tableTenantId > 0) {
            GenTableTenant tempInfo = this.getById(tableTenantId);
            if (tempInfo != null) {
              tempInfo.setJsonData(genTableTenantBo.getJsonData());
              tempInfo.setTableTenantName(genTableTenantBo.getTableTenantName());
              tempInfo.setTableId(genTableTenantBo.getTableId());
              tempInfo.setMenuId(genTableTenantBo.getMenuId());
              String vueType = genTableTenantBo.getVueType();
              if (ObjectUtil.isEmpty(vueType)) {
                vueType = "pcUI";
              }
              tempInfo.setVueType(genTableTenantBo.getVueType());
              this.updateById(tempInfo);

              Map<String, Object> map = new HashMap<>();
              map.put("tableTenantId", tempInfo.getTableTenantId());
              r.setMsg("保存成功");
              r.setData(map);
            }
          } else {
            GenTableTenant vueInfo = new GenTableTenant();
            vueInfo.setJsonData(genTableTenantBo.getJsonData());
            vueInfo.setTableTenantName(genTableTenantBo.getTableTenantName());
            vueInfo.setTableId(genTableTenantBo.getTableId());
            vueInfo.setMenuId(genTableTenantBo.getMenuId());
            vueInfo.setFromTableTenantId(genTableTenantBo.getFromTableTenantId());
            String vueType = genTableTenantBo.getVueType();
            if (ObjectUtil.isEmpty(vueType)) {
              vueType = "pcUI";
            }
            vueInfo.setVueType(genTableTenantBo.getVueType());
            this.save(vueInfo);

            Map<String, Object> map = new HashMap<>();
            map.put("tableTenantId", vueInfo.getTableTenantId());
            r.setMsg("保存成功");
            r.setData(map);
          }
        }

        if (ObjectUtil.isNotEmpty(genTableTenantBo.getWebRouter())
          && genTableTenantBo.getType().equals("json")
          && !genTableTenantBo.isPda()) {
          Resource resource = new ClassPathResource("");
          String rootPath = resource.getFile().getAbsolutePath();
          String appPath = rootPath + File.separator + ".." + File.separator
            + ".." + File.separator + ".." + File.separator + ".." + File.separator
            + (StringUtils.contains(rootPath, "rattan-admin" + File.separator + "target") ? "" : ".." + File.separator)
            + "open-wms-ui" + File.separator;
          // bbc后台
          if (ObjectUtil.isNotEmpty(genTableTenantBo.getVueType())
            && genTableTenantBo.getVueType().equals("bbc后台")) {
            appPath = rootPath + File.separator + "wms-site-bbc" + File.separator;
          } else if (ObjectUtil.isNotEmpty(genTableTenantBo.getVueType())
            && genTableTenantBo.getVueType().equals("bbc门店")) {
            appPath = rootPath + File.separator + "wms-site-vender" + File.separator;
          }
          String baseVuePath =
            appPath
              + "public"
              + File.separator
              + "static"
              + genTableTenantBo.getWebRouter().replace("/", File.separator)
              + ".json";
          String pathToCreate = baseVuePath.substring(0, baseVuePath.lastIndexOf(File.separator));
          File file = new File(pathToCreate); // 以某路径实例化一个File对象
          if (!file.exists()) { // 如果不存在
            file.mkdirs(); // 创建目录
          }

          PrintStream stream = new PrintStream(baseVuePath); // 写入的文件path
          stream.print(genTableTenantBo.getJsonData()); // 写入的字符串
          stream.close();

          r.setMsg("数据和JSON文件保存成功");

          String mainPath = appPath + "src" + File.separator + "views" + File.separator
            + genTableTenantBo.getWebRouter().replace("/", File.separator) + ".vue";
          pathToCreate = mainPath.substring(0, mainPath.lastIndexOf(File.separator));
          file = new File(pathToCreate); // 以某路径实例化一个File对象
          if (!file.exists()) { // 如果不存在
            file.mkdirs(); // 创建目录
          }

          // 生成biz文件，选择器不生成文件
          if (!Files.exists(Paths.get(mainPath)) && genTableTenantBo.getWebRouter().indexOf("/selector") != 0) {
            String jsonData = genTableTenantBo.getJsonData();
            Number menuId = (Number) JSONUtil.parse(jsonData).getByPath("dataOptions.menuId");
            SQL sql = new SQL().SELECT("component_name").FROM("sys_menu").WHERE("menu_Id=" + menuId);
            Map<String, Object> objectMap = SqlRunner.db().selectOne(sql.toString());
            if (ObjectUtil.isEmpty(objectMap)) {
              throw new ServiceException("未设置模块ID");
            }
            String componentName = Convert.toStr(objectMap.get("componentName"));
            if (StringUtils.isEmpty(componentName)) {
              throw new ServiceException("菜单设置中，组件名称不能为空！");
            }
            var mainCode = genTableTenantBo.getMainCode().replace("{name}", componentName);

            stream = new PrintStream(mainPath); // 写入的文件path
            stream.print(mainCode); // 写入的字符串
            stream.close();
            r.setMsg("文件创建成功");
          }
        }
        //#endregion
      } else {
        //#region 用户设计
        // 判断是否存在用户自定义UI
        GenTableTenant tableTenantInfo = null;
        if(B.isGreater(fromTableTenantId, 0)) {
          // fromTableTenantId>0,表示已经存在用户自定义UI了
          tableTenantInfo = this.getById(tableTenantId);
        }

        if (ObjectUtil.isEmpty(tableTenantInfo)) {
          tableTenantInfo = new GenTableTenant();
          tableTenantInfo.setFromTableTenantId(genTableTenantBo.getTableTenantId()); // 新建时确认来源ID
        }
        tableTenantInfo.setTableTenantName(genTableTenantBo.getTableTenantName());
        tableTenantInfo.setJsonData(genTableTenantBo.getJsonData());
        tableTenantInfo.setTableId(genTableTenantBo.getTableId());
        tableTenantInfo.setMenuId(genTableTenantBo.getMenuId());
        String vueType = genTableTenantBo.getVueType();
        if (ObjectUtil.isEmpty(vueType)) {
          vueType = "pcUI";
        }
        tableTenantInfo.setVueType(vueType);
        this.saveOrUpdate(tableTenantInfo);
        tableTenantId = tableTenantInfo.getTableTenantId();
        Map<String, Object> map = new HashMap<>();
        map.put("tableTenantId", tableTenantId);
        map.put("fromTableTenantId", tableTenantInfo.getFromTableTenantId());

        r.setMsg("保存成功");
        r.setData(map);
        //#endregion
      }
    } catch (Exception error) {
      r = R.fail();
      r.setMsg(error.getMessage());
    }

    return r;
  }

  /**
   * 根据fromTableTenantId获取用户自定义UI
   * @param fromTableTenantId
   * @return
   */
  private GenTableTenant getByFromTableTenantId(Long tableId, Long fromTableTenantId) {
    LambdaQueryWrapper<GenTableTenant> tableTenantLambdaQueryWrapper = new LambdaQueryWrapper<>();
    tableTenantLambdaQueryWrapper.eq(GenTableTenant::getFromTableTenantId, fromTableTenantId)
      .eq(GenTableTenant::getTableId, tableId);
    return  this.getOnly(tableTenantLambdaQueryWrapper);
  }
}

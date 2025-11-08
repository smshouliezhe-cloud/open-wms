<div style="height: 10px; clear: both;"></div>

<div align="center">

![易软通logo](logo.png)

[![码云Gitee](https://gitee.com/yiruantong/open-wms/badge/star.svg?theme=blue)](https://gitee.com/yiruantong/open-wms)
[![GitHub](https://img.shields.io/github/stars/yiruantong/open-wms.svg?style=social&label=Stars)](https://gitee.com/yiruantong/open-wms)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](https://gitee.com/yiruantong/open-wms/blob/master/LICENSE)
[![使用IntelliJ IDEA开发维护](https://img.shields.io/badge/IntelliJ%20IDEA-提供支持-blue.svg)](https://www.jetbrains.com/?from=RuoYi-Vue-Plus)
<br>
[![RuoYi-Vue-Plus](https://img.shields.io/badge/RuoYi_Vue_Plus-5.1.0-success.svg)](https://gitee.com/yiruantong/open-wms)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0-blue.svg)]()
[![JDK-17](https://img.shields.io/badge/JDK-17-green.svg)]()
[![JDK-19](https://img.shields.io/badge/JDK-19-green.svg)]()

[官网](https://www.wms.kim/) |
[在线体验](http://open.wms.kim) |
[帮助文档](https://www.yuque.com/xietianbao/xc91gm) |
[技术社区](https://gitee.com/yiruantong/open-wms/issues)

[comment]: <> ([宽屏预览]&#40;https://gitee.com/yiruantong/open-wms/blob/master/README.md&#41;)
</div>

<div align="center" >
<a href="https://gitee.com/yiruantong/open-wms/blob/master/README.md">宽屏预览</a>
</div>

- - -

# 易软通开源免费openWMS系统简介

> 易软通openWMS是采用RuoYi-Vue-Plus作为后端Java框架，已做调整不兼容原框架；前端采用Vue3 + VueX + Vue-Router + Element Plus + Pinia + TypeScript + Axios + Vite为前端框架。

> 项目代码、文档均开源免费可商用 遵循开源协议在项目中保留开源协议文件即可

> 易软通WMS系统，名称中“易”代表便捷、“软”代表软件、“通”代表畅通无阻，整体寓意系统操作简便、信息流畅、功能全面。在WMS（Warehouse Management System，仓库管理系统）行业中，易软通商标名体现了我们致力于为企业提供高效、智能、易用的仓储管理解决方案。
>
> 易软通WMS系统，不仅是一套管理工具，更是企业物流与仓储管理的智能伙伴。它像其名称所寓意的那样，帮助企业打通仓储管理各个环节，实现信息无缝对接与流程高效协同。系统实时监控库存状态、货物动向及作业进度，为企业提供精准的数据分析和可靠的决策支持。
>
> 易软通WMS系统的强大功能，助力企业轻松应对多样化的物流挑战。无论是日常订单的高效处理，还是突发状况的快速响应，系统都能灵活适配、稳定运行，保障企业仓储物流始终顺畅高效。
>
> 易软通WMS系统，凭借其出色的易用性、灵活的适应性和稳定的性能，已成为WMS领域中广受信赖的选择。它始终关注行业发展趋势，持续推动企业仓储管理数字化、智能化转型。选择易软通WMS系统，就是选择一位高效、可靠、智能的合作伙伴，助您在日益激烈的市场竞争中稳步前行。

## PC端演示地址
> WMS系统: http://open.wms.kim <br/>
> 账号/密码：请点击star后，获取作者账号密码 <br/>

## 帮助文档
> 易软通openWMS帮助文档: https://www.yuque.com/xietianbao/xc91gm <br/>

# 系统采用的技术栈

| 功能          | 本框架                                                                                                               |
|-------------|-------------------------------------------------------------------------------------------------------------------|
| 前端项目        | 前端在vue-next-admin框架基础上进行二开，采用 Vue3 + TS + ElementPlus+Pinia开发            |
| 后端项目结构      | 采用插件化 + 扩展包形式 结构解耦 易于扩展                                                                                           |
| 后端代码风格      | 严格遵守Alibaba规范与项目统一配置的代码格式化                                                                                        |
| Web容器       | 采用 Undertow 基于 XNIO 的高性能容器                                                                                        |
| 权限认证        | 采用 Sa-Token、Jwt 静态使用功能齐全 低耦合 高扩展                                                                                  |
| 权限注解        | 采用 Sa-Token 支持注解 登录校验、角色校验、权限校验、二级认证校验、HttpBasic校验、忽略校验<br/>角色与权限校验支持多种条件 如 `AND` `OR` 或 `权限 OR 角色` 等复杂表达式        |
| 三方鉴权        | 采用 JustAuth 第三方登录组件 支持微信、钉钉等数十种三方认证                                                                               |
| 关系数据库支持     | 原生支持 MySQL、Oracle、PostgreSQL、SQLServer<br/>可同时使用异构切换                                                              |
| 缓存数据库       | 支持 Redis 5-7 支持大部分新功能特性 如 分布式限流、分布式队列                                                                             |
| Redis客户端    | 采用 Redisson Redis官方推荐 基于Netty的客户端工具<br/>支持Redis 90%以上的命令 底层优化规避很多不正确的用法 例如: keys被转换为scan<br/>支持单机、哨兵、单主集群、多主集群等模式 |
| 缓存注解        | 采用 Spring-Cache 注解 对其扩展了实现支持了更多功能<br/>例如 过期时间 最大空闲时间 组最大长度等 只需一个注解即可完成数据自动缓存                                      |
| ORM框架       | 采用 Mybatis-Plus 基于对象几乎不用写SQL全java操作 功能强大插件众多<br/>例如多租户插件 分页插件 乐观锁插件等等                                             |
| SQL监控       | 采用 p6spy 可输出完整SQL与执行时间监控                                                                                          |
| 数据分页        | 采用 Mybatis-Plus 分页插件<br/>框架对其进行了扩展 对象化分页对象 支持多种方式传参 支持前端多排序 复杂排序                                                  | 采用 PageHelper 仅支持单查询分页 参数只能从param传 只能单排序 功能扩展性差 体验不好                               |
| 数据权限        | 采用 Mybatis-Plus 插件 自行分析拼接SQL 无感式过滤<br/>只需为Mapper设置好注解条件 支持多种自定义 不限于部门角色                                           | 采用 注解+aop 实现 基于部门角色 生成的sql兼容性差 不支持其他业务扩展<br/>生成sql后需手动拼接到具体业务sql上 对于多个Mapper查询不起作用 |
| 数据脱敏        | 采用 注解 + jackson 序列化期间脱敏 支持不同模块不同的脱敏条件<br/>支持多种策略 如身份证、手机号、地址、邮箱、银行卡等 可自行扩展                                        |
| 数据加解密       | 采用 注解 + mybatis 拦截器 对存取数据期间自动加解密<br/>支持多种策略 如BASE64、AES、RSA、SM2、SM4等                                              |
| 接口传输加密      | 采用 动态 AES + RSA 加密请求 body 每一次请求秘钥都不同大幅度降低可破解性                                                                     |
| 数据翻译        | 采用 注解 + jackson 序列化期间动态修改数据 数据进行翻译<br/>支持多种模式: `映射翻译` `直接翻译` `其他扩展条件翻译` 接口化两步即可完成自定义扩展 内置多种翻译实现                   |
| 多数据源框架      | 采用 dynamic-datasource 支持世面大部分数据库<br/>通过yml配置即可动态管理异构不同种类的数据库 也可通过前端页面添加数据源<br/>支持spel表达式从请求头参数等条件切换数据源            | 基于 druid 手动编写代码配置数据源 配置繁琐 支持性差                                                     |
| 多数据源事务      | 采用 dynamic-datasource 支持多数据源不同种类的数据库事务回滚                                                                          | 不支持                                                                                |
| 数据库连接池      | 采用 HikariCP Spring官方内置连接池 配置简单 以性能与稳定性闻名天下                                                                        | 采用 druid bug众多 社区维护差 活跃度低 配置众多繁琐性能一般                                               |
| 数据库主键       | 采用 雪花ID 基于时间戳的 有序增长 唯一ID 再也不用为分库分表 数据合并主键冲突重复而发愁                                                                  | 采用 数据库自增ID 支持数据量有限 不支持多数据源主键唯一                                                     |
| WebSocket协议 | 基于 Spring 封装的 WebSocket 协议 扩展了Token鉴权与分布式会话同步 不再只是基于单机的废物                                                         |
| 序列化         | 采用 Jackson Spring官方内置序列化 靠谱!!!                                                                                    | 采用 fastjson bugjson 远近闻名                                                           | 
| 分布式幂等       | 参考美团GTIS防重系统简化实现(细节可看文档)                                                                                          | 手动编写注解基于aop实现                                                                      |
| 分布式锁        | 采用 Lock4j 底层基于 Redisson                                                                                           |
| 分布式任务调度     | 采用 SnailJob 天生支持分布式 统一的管理中心                                                                                       | 采用 Quartz 基于数据库锁性能差 集群需要做很多配置与改造                                                   | 
| 文件存储        | 采用 Minio 分布式文件存储 天生支持多机、多硬盘、多分片、多副本存储<br/>支持权限管理 安全可靠 文件可加密存储                                                     | 采用 本机文件存储 文件裸漏 易丢失泄漏 不支持集群有单点效应                                                    |
| 云存储         | 采用 AWS S3 协议客户端 支持 七牛、阿里、腾讯 等一切支持S3协议的厂家                                                                          | 不支持                                                                                |
| 短信          | 采用 sms4j 短信融合包 支持数十种短信厂家 只需在yml配置好厂家密钥即可使用 可多厂家共用                                                                 | 不支持                                                                                |
| 邮件          | 采用 mail-api 通用协议支持大部分邮件厂商                                                                                         | 不支持                                                                                |
| 接口文档        | 采用 SpringDoc、javadoc 无注解零入侵基于java注释<br/>只需把注释写好 无需再写一大堆的文档注解了                                                     | 采用 Springfox 已停止维护 需要编写大量的注解来支持文档生成                                                | 
| 校验框架        | 采用 Validation 支持注解与工具类校验 注解支持国际化                                                                                  | 仅支持注解 且注解不支持国际化                                                                    |
| Excel框架     | 采用 Alibaba EasyExcel 基于插件化<br/>框架对其增加了很多功能 例如 自动合并相同内容 自动排列布局 字典翻译等                                               | 基于 POI 手写实现 功能有限 复杂 扩展性差                                                           |
| 工具类框架       | 采用 Hutool、Lombok 上百种工具覆盖90%的使用需求 基于注解自动生成 get set 等简化框架大量代码                                                       | 手写工具稳定性差易出问题 工具数量有限 代码臃肿需自己手写 get set 等                                            | 
| 监控框架        | 采用 SpringBoot-Admin 基于SpringBoot官方 actuator 探针机制<br/>实时监控服务状态 框架还为其扩展了在线日志查看监控                                    | 
| 链路追踪        | 采用 Apache SkyWalking 还在为请求不知道去哪了 到哪出了问题而烦恼吗<br/>用了它即可实时查看请求经过的每一处每一个节点                                            |
| 代码生成器       | 只需设计好表结构 一键生成所有crud代码与页面<br/>降低80%的开发量 把精力都投入到业务设计上<br/>框架为其适配MP、SpringDoc规范化代码 同时支持动态多数据源代码生成                    | 代码生成原生结构 只支持单数据源生成                                                                 |
| 部署方式        | 支持 Docker 编排 一键搭建所有环境 让开发人员从此不再为搭建环境而烦恼                                                                           | 原生jar部署 其他环境需手动下载安装 自行搭建                                                           | 
| 项目路径修改      | 提供详细的修改方案文档 并为其做了一些改动 非常简单即可修改成自己想要的                                                                              | 需要做很多改造 文档说明有限                                                                     |
| 国际化         | 基于请求头动态返回不同语种的文本内容 开发难度低 有对应的工具类 支持大部分注解内容国际化                                                                     | 只提供基础功能 其他需自行编写扩展                                                                  |
| 代码单例测试      | 提供单例测试 使用方式编写方法与maven多环境单测插件                                                                                      | 只提供基础功能 其他需自行编写扩展                                                                  |
| Mongoplus   | 使用MyBatisPlus的方式，优雅的操作MongoDB                                                                                       |


## 软件架构图

![易软通openWMS部署架构图](images/wms/wms02.png "易软通openWMS部署架构图.png")

## 软件功能框架

![输入图片说明](images/9618c0552bb769e8e327806d764150c.png)

## 软件功能亮点

![输入图片说明](images/b135a4287175bd731129f91bc7bd62d.png)

## 易软通openWMS系统功能脑图
<img src="images/wms/openWMS入库模块.jpg" width="750px" />
<br/>
<img src="images/wms/openWMS出库模块.jpg" width="750px" />
<br/>
<img src="images/wms/openWMS仓储模块.png" width="750px" />
<br/>

<img src="images/wms/openWMS系统管理模块.jpg"  />
<br/>


## 易软通openWMS系统功能列表
<table>
	<tr>
		<th>功能</th>
		<th>描述</th>
	</tr>
<tr>
    <td>入库大屏</td>    
    <td>入库数据图形化显示</td>    
</tr>
<tr>    
    <td>收货计划单</td>    
    <td>向供应商下预订购商品，供应商确认后可转为预到货单。</td>    
</tr>  
<tr>    
    <td>残品入库单</td>    
    <td>把收货时检验出的不合格品入库</td>    
</tr>  
<tr>    
    <td>预到货单</td>    
    <td>将要到货入库的订单</td>    
</tr>  
<tr>    
    <td>缺货订单转预到货</td>    
    <td>缺货的出库单可转为预到货单进行采购入库。</td>    
</tr>  
<tr>    
    <td>建议采购转预到货</td>    
    <td>根据近30天出库量计算建议采购数量</td>    
</tr>  
<tr>    
    <td>质检管理</td>    
    <td>对需要质检的入库单据进行质检管理</td>    
</tr>  
<tr>    
    <td>按单扫描入库</td>    
    <td>把已到货的商品按预到货单进行收货入库。</td>    
        
        
</tr>  
<tr>    
    <td>装箱扫描入库</td>    
    <td>把已到货的商品按装箱进行收货入库。</td>    
</tr>  
<tr>    
    <td>按拍扫描入库</td>    
    <td>按拍到货的订单可按拍入库（需提前将到货拍号维护到预到货单中）</td>    
</tr>
<tr>  
    <td>按单码盘扫描</td>  
    <td>码盘就是将需要入库的商品，码到托盘或其他专门的收货箱或盘中，然后可按托盘上架。</td>  
</tr>  
<tr>  
    <td>收货区码盘</td>  
    <td>将已经收货到收货区的商品，进行码盘操作（绑定托盘号）</td>  
</tr>  
<tr>  
    <td>LPN号扫描入库</td>  
    <td>把已到货的商品按LPN号进行收货入库（专门用于收货的收货周转容器）。</td>  
</tr>  
<tr>  
    <td>无单扫描入库</td>  
    <td>对没有预到货单的需入库商品，可直接扫描商品入库。</td>  
</tr>  
<tr>  
    <td>入库记录查询</td>  
    <td>可查询已收货入库的订单</td>  
</tr>  
<tr>  
    <td>生成上架单</td>  
    <td>把收到收货位的商品生成上架单，供手持PDA上架操作。（系统已默认设置为在收货入库时自动生成上架单，所以不用再领生成上架单）</td>  
</tr>  
<tr>  
    <td>待上架单扫描</td>  
    <td>将已收货到收货区还未上架的商品，上架单上架到库内常规货位。</td>  
</tr>  
<tr>  
    <td>按拍扫描上架</td>  
    <td>将已码盘的商品按拍号进行上架操作。</td>  
</tr>  
<tr>  
    <td>LPN号扫描上架</td>  
    <td>按LPN号（收货周转箱）进行上架操作</td>  
</tr>  
<tr>  
    <td>无单扫描上架</td>  
    <td>将已收货入库的商品进行不按订单直接扫商品上架。</td>  
</tr>  
<tr>  
    <td>上架记录查询</td>  
    <td>可查询已上架操作的订单</td>  
</tr>
<tr>  
    <td>到货退货单</td>  
    <td>对已入库需要进行退货的商品或预到货单进行退货操作。</td>  
</tr>  
<tr>  
    <td>出库大屏</td>  
    <td>出库数据图形化显示</td>  
</tr>  
<tr>  
    <td>出库计划单</td>  
    <td>出库计划单（审核通过后可转为出库订单）</td>  
</tr>  
<tr>  
    <td>出库订单</td>  
    <td>需出库的订单</td>  
</tr>  
<tr>  
    <td>生成波次</td>  
    <td>将已分配的订单按订单、收货人、快递公司等不同的方式生成为一个波茨。</td>  
</tr>  
<tr>  
    <td>波次查询</td>  
    <td>查询已生成的波次单</td>  
</tr>  
<tr>  
    <td>出库拣货下架</td>  
    <td>按波次将需出库商品拣货下架到理货区</td>  
</tr>  
<tr>  
    <td>灯光效验出库</td>  
    <td>根据灯光指示精准的出库方式</td>  
</tr>  
<tr>  
    <td>出库按拍下架</td>  
    <td>扫描托盘号进行拣货下架</td>  
</tr>  
<tr>  
    <td>出库下架回拣</td>  
    <td>将已下架到理货区但不需要出库的商品上架回货位</td>  
</tr>  
<tr>  
    <td>出库单常规配货</td>  
    <td>将已拣货下架至理货区的商品按订单进行分拣配货。</td>  
</tr>  
<tr>  
    <td>出库单批量配货</td>  
    <td>将已拣货下架至理货区的商品按订单进行快速批量配货，配货成功后直接消减库存出库完成。</td>  
</tr>  
<tr>  
    <td>出库灯光配货</td>  
    <td>根据灯光指示配货</td>  
</tr>
<tr>    
    <td>出库单打包校验</td>    
    <td>将已配货完成的订单按订单进行打包。</td>    
</tr>  
<tr>    
    <td>波次打包校验</td>    
    <td>按波次进行打包</td>    
</tr>  
<tr>    
    <td>无单打包校验</td>    
    <td>无需出库单直接扫描商品进行打包出库</td>    
</tr>  
<tr>    
    <td>打包校验查询</td>    
    <td>可查询已打包的商品或订单</td>    
</tr>  
<tr>    
    <td>单据补打</td>    
    <td>单据丢失补打功能</td>    
</tr>  
<tr>    
    <td>包材使用统计</td>    
    <td>使用统计功能</td>    
</tr>  
<tr>    
    <td>发货校验</td>    
    <td>将已打包出库的订单发货交接时的校验</td>    
</tr>  
<tr>    
    <td>闪电发货校验</td>    
    <td>不需拣货、配货、打包等环节，用波次号发货的简易出库流程</td>    
        
        
</tr>  
<tr>    
    <td>发货历史记录</td>    
    <td>已发货的出库订单，送货单、交货清单、商品明细单</td>    
        
        
</tr>  
<tr>    
    <td>已发货运单号导入</td>    
    <td>将已发货的快递单号批量导入</td>    
        
        
</tr>  
<tr>    
    <td>发货重量导入</td>    
    <td>发货时称重的记录</td>    
</tr>  
<tr>    
    <td>发货信息导入</td>    
    <td>查询发货时信息的记录</td>    
</tr>  
<tr>    
    <td>出库退货单</td>    
    <td>对已销售出库需退货的商品或订单进行退货操作</td>    
</tr>
<tr>    
    <td>库存大屏</td>    
    <td>库存数据图形化显示</td>    
</tr>  
<tr>    
    <td>库存明细查询</td>    
    <td>商品入库、上架、货位转移等操作生成的库存明细数据</td>    
</tr>  
<tr>    
    <td>库存SN查询</td>    
    <td>按SN号查询库</td>    
</tr>  
<tr>    
    <td>商品库存查询</td>    
    <td>商品库存信息查询</td>    
</tr>  
<tr>    
    <td>库存监测记录</td>    
    <td>库存入库、出库、转移货位等所有库存变化轨迹的记录</td>    
</tr>  
<tr>    
    <td>库存占位查询</td>    
    <td>出库订单已分配但还没拣货下架的商品为占位商品，已占位的商品其它单据不能使用</td>    
</tr>  
<tr>    
    <td>库存可视化查询</td>    
    <td>可分别平面化、直观的查询各库区货位的库存</td>    
</tr>  
<tr>    
    <td>库存实时使用查询</td>    
    <td>查询库存实时使用情况</td>    
</tr>  
<tr>    
    <td>货位转移扫描</td>    
    <td>同一仓库的库存商品的货位转移操作</td>    
</tr>  
<tr>    
    <td>货位转移查询</td>    
    <td>查询货位转移记录</td>    
</tr>
<tr>    
    <td>生成盘点单</td>    
    <td>选择需盘点的商品，按相应的盘点类型生成盘点单。</td>    
</tr>  
<tr>    
    <td>库存盘点查询</td>    
    <td>查询已生成的盘点单，可打印或导出盘点单进行盘点操作，盘点完成后与账面库存有差异的需生成盘点盈亏单。</td>    
</tr>  
<tr>    
    <td>盘点盈亏单</td>    
    <td>可查询已生成的盘点盈亏单，对已经复盘完成的盈亏单可调整库存。</td>    
</tr>  
<tr>    
    <td>其它入库单</td>    
    <td>可对一些特殊情况直接入库</td>    
</tr>  
<tr>    
    <td>其它出库单</td>    
    <td>可对一些特殊情况直接出库</td>    
</tr>  
<tr>    
    <td>库存成本调价单</td>    
    <td>对库存的商品进行成本价调整</td>    
</tr>  
<tr>    
    <td>商品拆装单</td>    
    <td>将库存商品组成套装或将套装拆分</td>    
</tr>  
<tr>    
    <td>库存调整</td>    
    <td>对商品库存进行快速、简易的调整</td>    
</tr>  
<tr>    
    <td>货主过户</td>    
    <td>货主过户信息</td>    
</tr>  
<tr>    
    <td>效期信息调整</td>    
    <td>效期信息调整管理</td>    
</tr>
<tr>    
    <td>调拨申请单</td>    
    <td>跨库调拨，可新建/编辑调拨申请单</td>    
</tr>  
<tr>    
    <td>调拨出库单</td>    
    <td>调拨申请单下发出库后生成调拨出库单</td>    
        
        
</tr>  
<tr>    
    <td>在途入库单</td>    
    <td>调入仓库入库后，系统自动根据调拨申请单中的在途仓库，生成调拨在途库存；</td>    
        
        
</tr>  
<tr>    
    <td>在途出库单</td>    
    <td>（此处似乎缺少对应内容，您可以补充完整的信息）</td>    
        
        
</tr>  
<tr>    
    <td>调拨入库单</td>    
    <td>调出仓库出库后，根据调拨申请单生成调拨入库单据</td>    
        
        
</tr>  
<tr>    
    <td>借出单</td>    
    <td>借出商品单据</td>    
        
        
</tr>  
<tr>    
    <td>借出归还单</td>    
    <td>借出商品归还单据</td>    
        
        
</tr>  
<tr>    
    <td>借入单</td>    
    <td>借入商品单据</td>    
        
        
</tr>  
<tr>    
    <td>借入归还单</td>    
    <td>借入商品归还单据</td>    
        
        
</tr>
<tr>    
    <td>商品容器管理</td>    
    <td>商品关联容器设置</td>    
        
        
</tr>  
<tr>    
    <td>客户容器管理</td>    
    <td>按客户统计当前借出的容器数量</td>    
        
        
</tr>  
<tr>    
    <td>容器流水记录</td>    
    <td>容器借出归还流水</td>    
        
        
</tr>  
<tr>    
    <td>容器借出</td>    
    <td>容器借出单据</td>    
        
        
</tr>  
<tr>    
    <td>容器归还</td>    
    <td>容器归还单据</td>    
        
        
</tr>  
<tr>    
    <td>仓库容器查询</td>    
    <td>客户借出已归还入库的空容器库存查询</td>    
        
        
</tr>  
<tr>    
    <td>容器返厂单</td>    
    <td>空容器返还给厂家的单据</td>    
        
        
</tr>  
<tr>    
    <td>仓库信息</td>    
    <td>新建/编辑仓库</td>    
        
        
</tr>  
<tr>    
    <td>库区管理</td>    
    <td>编辑查看库区信息</td>    
        
        
</tr>  
<tr>    
    <td>货位管理</td>    
    <td>新建虚拟货位及编辑货位</td>    
        
        
</tr>  
<tr>    
    <td>货位定义</td>    
    <td>批量定义常规货位</td>    
        
        
</tr>  
<tr>    
    <td>商品入库上架策略</td>    
    <td>可设置系统上架规则，并可设置优先使用某规则</td>    
        
        
</tr>  
<tr>    
    <td>容器管理</td>    
    <td>周转箱、托盘等容器的基础信息</td>    
        
        
</tr>
<tr>    
    <td>保质期预警</td>    
    <td>保质期数据预警设置</td>    
        
        
</tr>  
<tr>    
    <td>库龄预警</td>    
    <td>库存时间预警</td>    
        
        
</tr>  
<tr>    
    <td>货位最低库存预警</td>    
    <td>货位最低库存警告</td>    
        
        
</tr>  
<tr>    
    <td>入库计划明细查询</td>    
    <td>入库计划明细查询管理</td>    
        
        
</tr>  
<tr>    
    <td>残品入库明细查询</td>    
    <td>残品入库单明细</td>    
        
        
</tr>  
<tr>    
    <td>预到货明细查询</td>    
    <td>预到货明细查询信息管理</td>    
        
        
</tr>  
<tr>    
    <td>入库明细查询</td>    
    <td>入库信息明细管理</td>    
        
        
</tr>  
<tr>    
    <td>上架明细查询</td>    
    <td>上架明细查询管理</td>    
        
        
</tr>  
<tr>    
    <td>供应商进货统计</td>    
    <td>供应商进货管理</td>    
        
        
</tr>  
<tr>    
    <td>商品进货统计</td>    
    <td>商品信息进货统计</td>    
        
        
</tr>  
<tr>    
    <td>收货退货查询</td>    
    <td>收货退货查询管理</td>    
        
        
</tr>
<tr>    
    <td>出库单计划明细查询</td>    
    <td>出库单计划明细查询管理</td>    
        
        
</tr>  
<tr>    
    <td>出库订单明细查询</td>    
    <td>出库订单明细查询管理</td>    
        
        
</tr>  
<tr>    
    <td>拣货下架明细查询</td>    
    <td>拣货下架明细统计</td>    
        
        
</tr>  
<tr>    
    <td>出库记录明细查询</td>    
    <td>出库打包明细查询管理</td>    
        
        
</tr>  
<tr>    
    <td>客户销售查询</td>    
    <td>客户销售查询管理</td>    
        
        
</tr>  
<tr>    
    <td>出库单退货查询</td>    
    <td>出库单退货查询管理</td>    
        
        
</tr>  
<tr>    
    <td>每日库存快照</td>    
    <td>每天0点保存当天的库存数据</td>    
        
        
</tr>  
<tr>    
    <td>库存统计</td>    
    <td>库存统计</td>    
        
        
</tr>  
<tr>    
    <td>拣货人员工作量统计</td>    
    <td>拣货人员工作量统计信息</td>    
        
        
</tr>  
<tr>    
    <td>拣货人员拣货明细</td>    
    <td>拣货人员拣货信息明细</td>    
        
        
</tr>

<tr>    
    <td>UI设计</td>    
    <td>UI设计功能</td>    
        
        
</tr>  
<tr>    
    <td>客户信息</td>    
    <td>出库订单发货客户信息管理</td>    
        
        
</tr> 
<tr> 
    <td>商品信息</td>    
    <td>新建/编辑商品信息</td>    
        
        
</tr>  
<tr>    
    <td>套装组合设置</td>    
    <td>商品组合套装</td>    
        
        
</tr>  
<tr>    
    <td>品牌管理</td>    
    <td>商品品牌</td>    
        
        
</tr>  
<tr>    
    <td>供货商信息</td>    
    <td>商品供应商信息</td>    
        
        
</tr>  
<tr>    
    <td>商品类别</td>    
    <td>新建/编辑商品分类</td>    
        
        
</tr> 
<tr>  
    <td>用户管理</td>    
    <td>用户信息管理</td>    
        
        
</tr>  
<tr>    
    <td>部门管理</td>    
    <td>部门信息管理</td>    
        
        
</tr>  
<tr>    
    <td>角色管理</td>    
    <td>角色信息管理</td>    
        
        
</tr>  
<tr>    
    <td>角色权限设置</td>    
    <td>角色权限设置</td>    
        
        
</tr>  
<tr>    
    <td>用户日志</td>    
    <td>用户日志管理</td>    
        
        
</tr>  
<tr>    
    <td>VueUI设计器</td>    
    <td>VueUI设计器功能</td>    
        
        
</tr>  
<tr>    
    <td>VueUI打印设计器</td>    
    <td>VueUI打印设计器</td>    
        
        
</tr>  
<tr>    
    <td>首页布局设计器</td>    
    <td>首页布局设计器</td>    
        
        
</tr>  
<tr>    
    <td>流程设计器</td>    
    <td>总体流程设计器</td>    
        
        
</tr>  
<tr>    
    <td>Mvc开发中心</td>    
    <td>Mvc开发中心功能</td>    
        
        
</tr>  
<tr>    
    <td>默认单据编码</td>    
    <td>默认单据编码</td>    
        
        
</tr>  
<tr>    
    <td>Vue菜单设置</td>    
    <td>Vue菜单设置</td>    
        
        
</tr>  
<tr>    
    <td>导入设置</td>    
    <td>导入设置</td>    
        
        
</tr>  
<tr>    
    <td>导出设置</td>    
    <td>导出设置</td>    
        
        
</tr>  
<tr>    
    <td>系统设置</td>    
    <td>系统设置</td>    
        
        
</tr>  
<tr>    
    <td>下拉框设置</td>    
    <td>下拉框设置</td>    
        
        
</tr>  
<tr>    
    <td>下拉框值设置</td>    
    <td>下拉框值设置</td>    
        
        
</tr> 
</table>

## 演示功能

|                                                                                            |                                                                                            |
|--------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------|
![输入图片说明](images/po-images/acb5a930b9d5e697822c5f0d00f667a.png)|![输入图片说明](images/po-images/060a0bdc44df7849dacf2ac5ae56e4b.png)
![输入图片说明](images/po-images/48e9a91d63475a94f5e9d5f81405712.png)|![输入图片说明](images/po-images/83259582600c68b81774c3db9a82169.png)
![输入图片说明](images/po-images/a109ce813f8395854ade1e0e5ce0469.png)|![输入图片说明](images/po-images/0111e83fa451093f9f9f0cd3de5832f.png)
![输入图片说明](images/po-images/ef36da6eff9b75458b3cbffb35147d6.png)|![输入图片说明](images/po-images/fa0d0c73e92707799b5b78afd2f89af.png)
![输入图片说明](images/so-images/298cba54fceee5ea16b4219909c3778.png)|![输入图片说明](images/so-images/4a134007d4a4dba8d7d8f0eb84a1c4a.png)
![输入图片说明](images/so-images/581c3f484d98369f54ad40b966b061b.png)|![输入图片说明](images/so-images/8142fa08513cb937dedd0ebcc9c0bac.png)
![输入图片说明](images/so-images/91b8397faf28046753df6a3932e62bc.png)|![输入图片说明](images/so-images/a42f7d79559771af2affd45d875d94e.png)
![输入图片说明](images/so-images/af98b6999d0d49a5c09a2d93d3e176b.png)|![输入图片说明](images/so-images/d8bc1fb22498528d4f6a1c56787c934.png)
![输入图片说明](images/so-images/efe52a9eb915c13db6bc6f71ea9b5c4.png)|![输入图片说明](images/so-images/efe52a9eb915c13db6bc6f71ea9b5c4.png)
![输入图片说明](images/inv-images/22c7d080e1c1d5f0c737b0dac950b74.png)|![输入图片说明](images/inv-images/1de21a46538423985a8ce438e2fc9d7.png)
![输入图片说明](images/inv-images/60427a812446239f69520ac91ec539a.png)|![输入图片说明](images/inv-images/8ae68c181b998ab6bd415b700ae6fde.png)
![输入图片说明](images/inv-images/9d4b3158c2e38472b7f2089106965e0.png)|![输入图片说明](images/inv-images/e7a8da92bbe40f5ebd8d41d2b7d79a5.png)

## 系统工具
|                                                                                            |                                                                                            |
|--------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------|
![输入图片说明](images/tool-images/136e2fd28ce5c546ede2f47535e798f.png)|![输入图片说明](images/tool-images/4d67e358fe52f3ceb0296f6ce5a1f7d.png)
![输入图片说明](images/tool-images/741319b69ec56b9f83c8b44411742c0.png)|![输入图片说明](images/tool-images/82e7597e80cad7081ca96c3b20b6e38.png)
![输入图片说明](images/tool-images/8590c1103e750563b8880abce6fd25e.png)|![输入图片说明](images/tool-images/9fc6e4f23a8251072394ddb621b59c1.png)
![输入图片说明](images/tool-images/a81c51823061ddbbd918c1446fb102f.png)|![输入图片说明](images/tool-images/b86cf1b29f049c8176d1fa91d0aad89.png)
![输入图片说明](images/tool-images/b9331e8183111d7bdee0b2a0fd555d8.png)|![输入图片说明](images/tool-images/cf5ca4eac7d9130ac139f45f3398af8.png)

## PDA App端
|                                                                                            |                                                                                            |
|--------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------|
![输入图片说明](images/app-images/app1.jpg)|![输入图片说明](images/app-images/app2.jpg)
![输入图片说明](images/app-images/app3.jpg)|![输入图片说明](images/app-images/app4.jpg)
![输入图片说明](images/app-images/app5.jpg)|![输入图片说明](images/app-images/app6.jpg)
![输入图片说明](images/app-images/app7.jpg)|![输入图片说明](images/app-images/app8.jpg)
![输入图片说明](images/app-images/app9.jpg)|![输入图片说明](images/app-images/app10.jpg)


# 企业版WMS链接地址：[https://www.wms.kim/enterprise-wms](https://www.wms.kim/enterprise-wms/)
## 仓库3D可视化管理
在3D可视化环境中，直接完成入库、出库、盘点及移位等所有操作皆可一键完成，实现可视、可知、可管的一体集成，管理从未如此直观高效。
![输入图片说明](images/wms/wms-3d-01.png)
![输入图片说明](images/wms/wms-3d-02.png)
![输入图片说明](images/wms/wms-3d-03.png)
![输入图片说明](images/wms/wms-3d-04.gif)
![输入图片说明](images/wms/wms-3d-05.gif)

# QQ群
<a href="http://qm.qq.com/cgi-bin/qm/qr?_wv=1027&k=g035KL0OrSeWwWqxKmCDSkQgxGRA9PaW&authKey=Va2o57thdynX1XdLMvpkYPPiWAKrJc6gESRHPOwtAiGLW%2BrMqvB3iE6cH1LJ9s0X&noverify=0&group_code=835488048" target="_blank">点击链接加入群聊【易软通openWMS沟通群】</a>
<br/>
<img src="images/temp_qrcode_share_835488048.png" width="350px" />

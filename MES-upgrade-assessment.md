# MES Base Upgrade And LOT(IoT) Integration Assessment

## 1. 目标理解

基于当前代码库判断，你的诉求不是“重做一套新系统”，而是：

1. 保留现有选煤厂 MES 智能化信息平台的核心业务内容。
2. 将基础框架尽量同步到最新的 `ruoyi-vue-pro` 后端和 `yudao-ui-admin-vue3` 前端基线。
3. 在不破坏现有 MES 主体功能的前提下，补齐并启用 LOT/IoT 物联网模块。
4. 让 IoT 能力真正服务于煤炭业务，而不是单独摆一个演示模块。

说明：

- 这里将“LOT 物联网”统一按 `IoT` 理解。
- 本文时间基准为 `2026-03-26`。

## 2. 当前代码库现状

### 2.1 后端基线

- 根项目版本：`2025.08-jdk8-SNAPSHOT`
- JDK：`1.8`
- Spring Boot：`2.7.18`
- 当前启用模块：
  - `yudao-module-system`
  - `yudao-module-infra`
  - `yudao-module-demo`
  - `yudao-module-report`
  - `yudao-module-coal`
  - `yudao-module-opcua`
- 当前保留但未启用的模块：
  - `yudao-module-iot`
  - `yudao-module-bpm`
  - `yudao-module-member`
  - `yudao-module-pay`
  - `yudao-module-mall`
  - `yudao-module-crm`
  - `yudao-module-erp`
  - `yudao-module-ai`

### 2.2 前端基线

- 前端版本：`2025.08-snapshot`
- Vue：`3.5.12`
- Vite：`5.1.4`
- Element Plus：`2.9.1`
- 当前前端仍保留完整 `iot` 页面与路由资源。

### 2.3 现有 MES 定制规模

#### 煤炭业务模块 `yudao-module-coal`

- `37` 个 Controller
- `38` 个 DO
- `38` 个 Mapper
- `75` 个 Service 相关类

前端对应：

- `82` 个 `coal` 业务 Vue 页面
- `34` 个 `coal` API 文件

这说明当前项目已经不是“简单二开”，而是独立业务平台级别的深度改造。

#### OPC UA 模块 `yudao-module-opcua`

- `3` 个 Controller
- `4` 个 DO
- `4` 个 Mapper
- `8` 个 Service 相关类

前端对应：

- `7` 个 `opcua` 页面
- `3` 个 `opcua` API 文件

### 2.4 IoT/LOT 现状判断

- 仓库中已经存在完整 `yudao-module-iot` 目录。
- `yudao-server/pom.xml` 中的 `yudao-module-iot-biz` 依赖目前被注释掉。
- 根 `pom.xml` 中 `yudao-module-iot` 模块也被注释掉。
- 前端 `src/router/modules/remaining.ts` 中仍然保留 `iot` 路由入口。
- 前端已有 `47` 个 `iot` 页面、`8` 个 `iot` API 文件。
- `yudao-module-iot` 目录下可见 `288` 个 Java/XML/YAML 文件，说明 IoT 底座并不是空壳。

结论：

- 你的项目不是“没有 IoT”，而是“代码底座在，主应用未正式启用”。
- 这对后续接入非常有利，因为不需要从零引入整个 IoT 子系统。

### 2.5 数据库与脚本现状

- `sql/mysql` 中可见大量煤炭业务 SQL。
- 可检出 `39` 处 `coal_` 建表语句。
- `opcua_tables.sql` 中有 `3` 张 OPC UA 核心表：
  - `opcua_config`
  - `opcua_tag_config`
  - `opcua_alarm_event`
- `sql/mysql/ruoyi-vue-pro.sql` 中可检出 `187` 处 `iot_` 关键字相关内容，说明字典、菜单、数据基线已经含有较多 IoT 痕迹。

### 2.6 当前代码工作区状态

当前仓库是脏工作区，存在大量未提交修改，主要集中在：

- `yudao-module-coal`
- `yudao-module-system`
- `yudao-server`
- `yudao-ui/yudao-ui-admin-vue3`
- `sql/mysql`

这意味着后续升级不能直接粗暴覆盖，否则非常容易把你现有 MES 改造冲掉。

## 3. 上游基线状态

参考仓库：

- 后端：<https://gitee.com/zhijiantianya/ruoyi-vue-pro>
- 前端：<https://gitee.com/yudaocode/yudao-ui-admin-vue3>

截至 `2026-03-26`，从 Gitee 页面可见：

### 3.1 后端上游

- 默认分支：`master`
- 可见分支包括：
  - `feat/mes`
  - `feature/iot`
  - `feature/iot-modbus`
  - `feature/iot-protocol`
- 可见最新标签已到：
  - `v2026.01(jdk17/21)`
  - `v2026.01(jdk8/11)`

### 3.2 前端上游

- 默认分支：`master`
- 可见分支包括：
  - `feat/mes`
  - `feature/iot`
- 可见最新标签已到：
  - `v2026.01`

### 3.3 与你当前仓库的关系判断

你的本地基线仍然停留在 `2025.08` 这一代。

这意味着：

1. 你并不是从一个特别古老的版本升级。
2. 但上游已经至少推进到 `v2026.01`。
3. 升级重点不在“能不能升级”，而在“如何保住 MES 定制并平滑启用 IoT”。

## 4. 核心判断

### 4.1 不能直接覆盖升级

当前 `coal + opcua + 前端业务页面 + SQL` 的改造量已经很大，直接用上游最新代码覆盖，风险极高：

- 业务模块会丢
- 菜单权限会乱
- SQL 会冲突
- 前后端接口会错位
- 正在开发中的本地变更会被覆盖

### 4.2 更合理的升级思路

应该采用：

`基础框架同步` + `业务模块保留` + `IoT 模块渐进启用`

而不是：

`整仓替换`

### 4.3 OPC UA 与 IoT 的关系

从现有代码看，`opcua` 更像采集接入层，`iot` 更像统一设备/产品/物模型/插件/规则平台层。

对你这个项目，推荐关系应该是：

- `OPC UA` 负责选煤厂设备数据采集与标签接入。
- `IoT` 负责设备台账、产品模型、协议抽象、物模型、插件、规则与桥接。
- `coal` 负责生产、质量、安全、设备、能耗等 MES 业务域。

也就是说：

**不是用 IoT 替换现有煤炭业务，而是让 IoT 为煤炭业务提供统一的设备和数据底座。**

## 5. 升级建议路线

### 第一阶段：冻结现状

目标：

- 锁定当前可运行版本
- 保护现有 MES 业务成果

建议：

1. 先整理并提交当前工作区，至少形成一个“升级前基线”。
2. 导出当前数据库结构和关键业务数据。
3. 明确当前生产可用的菜单、接口、页面、定时任务和字典。

### 第二阶段：比对上游基线

目标：

- 搞清楚 `2025.08 -> v2026.01` 的基础框架变化

重点比对目录：

- `yudao-dependencies`
- `yudao-framework`
- `yudao-module-system`
- `yudao-module-infra`
- `yudao-server`
- `yudao-ui/yudao-ui-admin-vue3`

建议优先对照的上游分支：

1. `master`
2. `feat/mes`
3. `feature/iot`

原因：

- 你的项目同时命中了 `MES` 和 `IoT` 两条改造线。
- 只对照 `master` 容易漏掉上游已经做过的 MES/IoT 相关结构。

### 第三阶段：先升框架，再挂业务

原则：

- 先更新公共基础层
- 再回挂 `coal`
- 最后启用 `iot`

推荐顺序：

1. 同步依赖与框架层
2. 同步 `system / infra / server`
3. 保留并适配 `coal`
4. 保留并适配 `opcua`
5. 重新启用 `iot`
6. 修复前端路由、API、权限、菜单和字典

### 第四阶段：LOT/IoT 最小可用落地

不建议一上来把 IoT 所有能力全开。

建议优先启用这几个能力：

1. 产品管理
2. 设备管理
3. 物模型管理
4. 插件/协议管理
5. 数据桥接

然后与当前 MES 业务做映射：

1. `equipmentinfo` 对接 `iot device`
2. `opcua tag` 对接 `iot thing model property`
3. 实时采集值进入能耗、设备状态、质量监测等业务页面
4. 告警统一沉淀到设备预警/安全预警/能耗预警场景

## 6. 对你的项目最合适的 LOT/IoT 接入方式

### 推荐做法

以“增强现有 MES”为目标，而不是做独立的 IoT 子系统。

推荐架构：

- `yudao-module-coal`：保留原有业务域
- `yudao-module-opcua`：保留现场采集接入
- `yudao-module-iot`：补充统一设备模型、产品模型、插件、规则和桥接

### 不推荐做法

1. 直接把现有 `opcua` 逻辑推翻重写
2. 直接把现有 `equipmentinfo` 全量替换为 IoT 表结构
3. 先全量升级再回头修业务

这些做法的代价太高，而且极易破坏现有系统。

## 7. 主要风险点

### 7.1 代码层

- 现有工作区未清理，升级过程容易互相覆盖
- `coal` 模块改动面太大，自动合并成功率不会高
- `system` 模块已有本地修改，升级时可能牵涉租户、权限、菜单逻辑

### 7.2 数据层

- 菜单、权限、字典、租户字段存在重复或冲突风险
- `coal`、`opcua`、`iot` 三类表之间后续需要补统一关联关系
- SQL 脚本当前分散，后续必须收敛成可执行迁移顺序

### 7.3 前端层

- 当前前端存在删除 ERP 页面、保留 IoT 页面、扩展 Coal 页面并行存在的状态
- 升级后容易出现：
  - 路由残留
  - API 路径失配
  - 权限编码不一致
  - 页面组件引用断裂

### 7.4 运行环境层

- 当前基线是 `jdk8`
- 上游最新同时提供 `jdk8/11` 和 `jdk17/21` 线路

建议：

- 如果现网仍是 JDK8，先走 `v2026.01(jdk8/11)` 路线
- 不要在“框架升级”和“JDK 大版本升级”两个高风险动作上同时动刀

## 8. 建议的下一步产出

下一步最合理的工作不是立即改代码，而是先做一份可执行升级清单：

1. 本地代码与上游 `v2026.01` 的差异清单
2. `coal / opcua / iot` 的表、菜单、字典、接口映射清单
3. 分阶段升级计划
4. 最小可用 LOT/IoT 接入范围清单

## 9. 结论

这套项目已经具备：

- 完整的煤炭 MES 业务域
- 独立的 OPC UA 采集能力
- 已存在但未正式启用的 IoT 底座

所以后续正确方向不是“推翻重来”，而是：

**以最新基础框架为目标，保住 `coal` 和 `opcua` 的业务成果，逐步启用并接入 `iot`，让 IoT 成为 MES 的设备与数据基础能力。**

如果继续往下做，下一步建议直接进入：

`升级差异梳理 + 模块映射清单 + 分阶段实施方案`

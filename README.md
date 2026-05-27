# agx-xxl-job

> 基于 [XXL-JOB](https://github.com/xuxueli/xxl-job) `3.4.0` 的定制化版本。

---

## 一、项目说明

本项目是对 XXL-JOB 官方版本的二次开发 Fork。下面的“改动记录”不按 commit 顺序罗列，而是按这批改动对应的需求方向进行归类说明。

---

## 二、改动记录

### 1. 管理端工程与部署配置收敛

- 引入版本号继承方案，父子模块统一使用 `${revision}`。
- 配置格式从 XML / properties 整理为 YML 体系，管理端配置更集中。
- Admin 端拆出多环境配置：`application-local.yml`、`application-dev.yml`、`application-prod.yml`。
- 当前各环境 `xxl.job.accessToken` 默认值统一为 `agx`。
- 提供 `docker/docker-compose.yml` 和 `xxl-job-admin/Dockerfile`，方便直接部署管理端。
- 删除 `xxl-job-executor-samples` 相关示例模块，当前仓库以 `xxl-job-core` 和 `xxl-job-admin` 为主。
- 管理端已接入 `xxl-sso` 相关代码，`/api/*` 公共接口不要求登录态，但仍要求 access token。

### 2. 管理端 OpenAPI 能力补齐

- 在 `AdminBiz`、`AdminBizImpl`、`OpenApiController` 上补齐管理端公共 API。
- 新增任务管理接口：`jobAdd`、`jobUpdate`、`jobDelete`、`jobQuery`、`jobStart`、`jobStop`、`jobTrigger`。
- 新增执行器分组查询接口：`groupQuery`。
- 新增 / 调整请求响应模型：`JobInfoRequest`、`JobInfoResult`、`GroupRequest`、`GroupResult`。
- 同时保留并统一了已有公共接口的入口风格：`callback`、`registry`、`registryRemove`、`logQuery`、`logKill`。

### 3. 启动输出与细节修复

- `XxlJobAdminApplication` 启动完成后输出本地 / 外网访问地址，便于直接点击访问。
- 对启动打印信息做了二次修正，避免入口识别和输出展示问题。
- 对 `AdminBiz` 相关实现补充了注释，并持续修复 `AdminBizImpl` 中的问题。

如需自己核对历史，可直接执行：

```bash
git log --oneline --decorate -8
git log --stat --oneline -8
```

---

## 三、新增 OpenAPI 接口

### 1. 调用约定

- 基础地址：`http://localhost:8081/xxl-job-admin/api/{uri}`
- 请求方式：`POST`
- 请求头：`XXL-JOB-ACCESS-TOKEN: agx`
- 请求体：`application/json`
- 鉴权规则：`/api/*` 不要求管理端登录态，但必须带对的 access token

### 2. 本次补充的主要接口

| URI | 说明 | 主要入参 |
|---|---|---|
| `groupQuery` | 查询执行器分组 | `id` 或 `appname` |
| `jobAdd` | 新增任务 | `jobGroup`、`jobDesc`、`author`、`scheduleType`、`scheduleConf`、`executorHandler`、`glueType` 等 |
| `jobUpdate` | 更新任务 | `id` + 任务配置 |
| `jobDelete` | 删除任务 | `id` |
| `jobQuery` | 查询任务 | `id` |
| `jobStart` | 启动任务 | `id` |
| `jobStop` | 停止任务 | `id` |
| `jobTrigger` | 手动触发任务 | `id`，可选 `executorParam`、`addressList` |

### 3. 调用示例

#### 3.1 查询执行器分组

```bash
curl -X POST "http://localhost:8081/xxl-job-admin/api/groupQuery" \
  -H "Content-Type: application/json" \
  -H "XXL-JOB-ACCESS-TOKEN: agx" \
  -d '{
    "appname": "xxl-job-executor-sample"
  }'
```

#### 3.2 新增任务

下面这个示例对应 `BEAN` 类型任务，字段值都来自当前代码里的有效枚举：

```bash
curl -X POST "http://localhost:8081/xxl-job-admin/api/jobAdd" \
  -H "Content-Type: application/json" \
  -H "XXL-JOB-ACCESS-TOKEN: agx" \
  -d '{
    "jobGroup": 1,
    "jobDesc": "demo-job",
    "author": "admin",
    "alarmEmail": "",
    "scheduleType": "CRON",
    "scheduleConf": "0 */5 * * * ?",
    "misfireStrategy": "DO_NOTHING",
    "executorRouteStrategy": "ROUND",
    "executorHandler": "demoJobHandler",
    "executorParam": "test",
    "executorBlockStrategy": "SERIAL_EXECUTION",
    "executorTimeout": 0,
    "executorFailRetryCount": 0,
    "glueType": "BEAN",
    "childJobId": ""
  }'
```

#### 3.3 更新任务

```bash
curl -X POST "http://localhost:8081/xxl-job-admin/api/jobUpdate" \
  -H "Content-Type: application/json" \
  -H "XXL-JOB-ACCESS-TOKEN: agx" \
  -d '{
    "id": 1,
    "jobGroup": 1,
    "jobDesc": "demo-job-update",
    "author": "admin",
    "alarmEmail": "",
    "scheduleType": "CRON",
    "scheduleConf": "0 */10 * * * ?",
    "misfireStrategy": "DO_NOTHING",
    "executorRouteStrategy": "ROUND",
    "executorHandler": "demoJobHandler",
    "executorParam": "test-update",
    "executorBlockStrategy": "SERIAL_EXECUTION",
    "executorTimeout": 0,
    "executorFailRetryCount": 0,
    "glueType": "BEAN",
    "childJobId": ""
  }'
```

#### 3.4 查询任务

```bash
curl -X POST "http://localhost:8081/xxl-job-admin/api/jobQuery" \
  -H "Content-Type: application/json" \
  -H "XXL-JOB-ACCESS-TOKEN: agx" \
  -d '{
    "id": 1
  }'
```

#### 3.5 删除任务

```bash
curl -X POST "http://localhost:8081/xxl-job-admin/api/jobDelete" \
  -H "Content-Type: application/json" \
  -H "XXL-JOB-ACCESS-TOKEN: agx" \
  -d '{
    "id": 1
  }'
```

#### 3.6 启动 / 停止 / 手动触发任务

```bash
curl -X POST "http://localhost:8081/xxl-job-admin/api/jobStart" \
  -H "Content-Type: application/json" \
  -H "XXL-JOB-ACCESS-TOKEN: agx" \
  -d '{
    "id": 1
  }'

curl -X POST "http://localhost:8081/xxl-job-admin/api/jobStop" \
  -H "Content-Type: application/json" \
  -H "XXL-JOB-ACCESS-TOKEN: agx" \
  -d '{
    "id": 1
  }'

curl -X POST "http://localhost:8081/xxl-job-admin/api/jobTrigger" \
  -H "Content-Type: application/json" \
  -H "XXL-JOB-ACCESS-TOKEN: agx" \
  -d '{
    "id": 1,
    "executorParam": "manual trigger",
    "addressList": ""
  }'
```

#### 3.7 日志查询 / 终止执行

```bash
curl -X POST "http://localhost:8081/xxl-job-admin/api/logQuery" \
  -H "Content-Type: application/json" \
  -H "XXL-JOB-ACCESS-TOKEN: agx" \
  -d '{
    "logId": 1,
    "fromLineNum": 1,
    "logDateTim": 0
  }'

curl -X POST "http://localhost:8081/xxl-job-admin/api/logKill" \
  -H "Content-Type: application/json" \
  -H "XXL-JOB-ACCESS-TOKEN: agx" \
  -d '{
    "logId": 1
  }'
```

> 说明：
> - `jobAdd` / `jobUpdate` 里的 `scheduleType`、`misfireStrategy`、`executorRouteStrategy`、`executorBlockStrategy`、`glueType` 都会在服务端做枚举校验。
> - `groupQuery` 优先按 `id` 精确查询；如果 `id <= 0`，则按 `appname` 查询。
> - `jobStart`、`jobStop`、`jobDelete`、`jobQuery`、`jobTrigger` 都要求 `id > 0`。

---

## 四、快速开始

### 数据库初始化

```sql
source doc/db/tables_xxl_job.sql
```

### 本地启动

```bash
mvn clean package -P local
java -jar xxl-job-admin/target/xxl-job-admin-*.jar
```

访问：`http://localhost:8081/xxl-job-admin`


---

## 五、上游项目

- 官方仓库：[https://github.com/xuxueli/xxl-job](https://github.com/xuxueli/xxl-job)
- 官方文档：[https://www.xuxueli.com/xxl-job/](https://www.xuxueli.com/xxl-job/)

---

## 六、License

遵循上游协议 [GNU General Public License v3.0](http://www.gnu.org/licenses/gpl-3.0.html)。

# Acg-fans

一个 ACG（动画 / 漫画 / 游戏）内容社区，包含 **前台展示站**、**后台管理系统** 和 **后端 API 服务** 三个部分，放在同一个仓库里维护。

后端已从"Controller 里塞内存假数据"重构为 **Controller → Service → Mapper → MySQL** 的正规分层：
Spring Boot 4.1.1 GA + MyBatis-Plus + MySQL 8 + Spring Security/JWT，**16 张业务表、70+ REST 接口**，
统一响应体、全局异常、参数校验、分页、逻辑删除、BCrypt 密码、基于角色的接口鉴权全部落地。

---

## 目录结构

```
ACG-Web/
├── AcgWeb/                       # 前台展示站（Vue 3 + Vite + TS）
│   ├── src/api/                  # 接口层：Axios 封装 + 按业务拆分的模块
│   ├── src/routers/              # 路由：import.meta.glob 自动注册 + keep-alive
│   ├── src/view/                 # 页面（首页 / 番剧 / 文章 / 视频 / 周报 / 讨论 / 搜索 / 登录）
│   ├── src/components/           # 通用组件
│   ├── src/layout/               # Header / Footer 布局
│   ├── src/stores/               # Pinia：user / theme
│   └── vite.config.ts            # 别名 @、SCSS 变量注入、/api 代理
│
├── Acg_admin/Acg_admin/          # 后台管理系统（Vue 3 + Vite + TS）
│   └── src/                      # 番剧 / 文章 / 视频 / 用户的增删改查 + 登录
│
├── AcgData/                      # 后端 API（Spring Boot 4.1.1）
│   ├── REFACTOR_GUIDE.md         # 本次重构的分层 / 接口 / 数据库约定
│   └── src/main/
│       ├── java/com/example/acgdata/
│       │   ├── common/           # Result、PageResult、业务异常、全局异常处理
│       │   ├── config/           # MyBatis-Plus、CORS、公共字段自动填充、演示数据初始化
│       │   ├── security/         # JWT 工具、认证过滤器、权限配置
│       │   ├── entity/           # 数据库实体（16 张表）
│       │   ├── mapper/           # MyBatis-Plus Mapper + 注解 SQL
│       │   ├── service(/impl)/   # 业务层（IService / ServiceImpl）
│       │   ├── controller/       # 只做参数校验与转发
│       │   ├── dto/ vo/          # 入参 / 出参对象
│       │   └── ...
│       └── resources/
│           ├── application.yml
│           └── db/{schema,data}/ # 建表脚本与幂等种子数据（spring.sql.init 执行）
│
├── .gitignore
└── README.md
```

---

## 技术栈

| 层次 | 技术 |
| --- | --- |
| 前台 | Vue 3.5、Vite 7、TypeScript 5.9、Vue Router 5、Pinia 3、Element Plus 2、ECharts 6、Axios、Sass、Vitest、Playwright、oxlint/ESLint/Prettier |
| 后台 | Vue 3.5、Vite 8、TypeScript 6、Vue Router 5、Pinia 3、Element Plus 2、vue-echarts |
| 后端 | Java 17、Spring Boot 4.1.1 GA、MyBatis-Plus 3.5.17、MySQL 8、Spring Security、JWT（jjwt 0.13.0）、HikariCP、Lombok、Jakarta Validation、Gradle |
| 数据库 | MySQL 8（库名 `acg_fans`，16 张业务表，脚本化建表 + 幂等种子数据） |

---

## 快速开始

### 0. 准备数据库

本地启动 MySQL 8，然后**不用手动建库建表**：

- JDBC URL 带了 `createDatabaseIfNotExist=true`，**库不存在会自动创建**；
- 建表脚本与种子数据放在 `AcgData/src/main/resources/db/{schema,data}/`，
  由 Spring 的 `spring.sql.init` 在后端启动时按文件名顺序执行。

**所有表都建在 `acg_fans` 库里**（即 `localhost:3306` 这台 MySQL 上的 `acg_fans` schema），
完整的表清单见下文 [《数据库设计》](#4-数据库设计16-张表)。

默认连接：`localhost:3306`，用户名 `root`，密码 `123456`。
如需修改，改 `AcgData/src/main/resources/application.yml`，或用环境变量覆盖：

```bash
export MYSQL_USERNAME=root
export MYSQL_PASSWORD=你的密码
```

> **在 Navicat / DBeaver 里看不到表？**
>
> 1. **数据库列表是缓存过的**：`acg_fans` 是后端首次启动时才创建的，需要刷新连接
>    （Navicat 右键连接 → 刷新，或按 `F5`）。它按字母序排在 `information_schema` **之前**，
>    刷新后会出现在列表最上面；
> 2. 图形工具里的「记录数」列对 InnoDB 只是**估算值**（经常显示 0），不要据此判断表是空的，
>    用 `SELECT COUNT(*)` 才准确。
>
> 想重建数据：`DROP DATABASE acg_fans;` 之后重启后端即可。脚本都是
> `CREATE TABLE IF NOT EXISTS` + `INSERT ... ON DUPLICATE KEY UPDATE`，**重复启动不会报错、也不会重复插入**。

### 1. 启动后端（先启动，前端要靠它拿数据）

```bash
cd AcgData
./gradlew bootRun          # Windows: .\gradlew.bat bootRun
```

启动后：`http://localhost:8080`，所有接口前缀 `/api`。
首次启动会自动建表、灌入演示数据，并创建演示账号（密码统一 BCrypt 加密后入库）。

### 2. 启动前台展示站

```bash
cd AcgWeb
npm install
npm run dev
```

默认 `http://localhost:5173`。开发服务器把 `/api/**` 代理到 `http://localhost:8080`。
需要 `.env`（已被 git 忽略，克隆后自行创建）：

```ini
VITE_API_BASE_URL=/api
VITE_PROXY_TARGET=http://localhost:8080
```

### 3. 启动后台管理

```bash
cd Acg_admin/Acg_admin
npm install
npm run dev
```

### 演示账号

| 账号 | 密码 | 角色 |
| --- | --- | --- |
| `admin` | `admin123` | 管理员（可改删，能进后台） |
| `咕噜咕噜` | `123456` | 普通用户（可评论、点赞、投稿） |

其余演示用户见 `config/DataInitializer.java`（`动漫评论家 / password123`、`JOJO粉 / jojo123` 等）。

---

## 后端设计（AcgData）

### 1. 分层与职责

| 层 | 职责 | 约定 |
| --- | --- | --- |
| `controller` | 参数校验、调用 Service、包装 `Result` | 不写业务逻辑、不出现 `List<Map<String,Object>>`、不直接调 Mapper |
| `service` / `service.impl` | 业务编排、事务、DTO/VO 转换 | 统一继承 `IService<T>` / `ServiceImpl<Mapper, T>` |
| `mapper` | 数据访问 | 继承 `BaseMapper<T>`，复杂查询用注解 SQL |
| `entity` | 表映射 | 蛇形列名 ↔ 驼峰属性，`@TableLogic` 逻辑删除 |
| `dto` / `vo` | 入参 / 出参 | 入参带 Jakarta Validation 注解；VO 负责对齐前端字段名与时间格式 |

典型请求链路：

```
HTTP → JwtAuthenticationFilter（解析 Token 写入 SecurityContext）
     → SecurityFilterChain（按 HTTP 方法 + 路径做鉴权）
     → Controller（@Valid 校验 DTO，转发）
     → Service（业务校验、装配 VO、@Transactional）
     → Mapper / MyBatis-Plus（分页插件、逻辑删除、自动填充）
     → MySQL
     → Result<T> 统一响应体
```

### 2. 统一响应体、异常与校验

所有接口返回同一结构，前端只需判断 `code`：

```json
{ "code": 200, "message": "操作成功", "data": {}, "success": true }
```

- `ResultCode` 统一维护状态码字典（200 成功、400 参数错误、401 未登录、403 无权限、404/405、1xxx 业务语义错误）；
- 业务代码直接 `throw new BusinessException(ResultCode.XXX)`，由 `@RestControllerAdvice` 收敛成统一响应体，
  并分别处理 `MethodArgumentNotValidException`、`BindException`、`ConstraintViolationException`、
  `HttpMessageNotReadableException`、`HttpRequestMethodNotSupportedException` 与兜底 `Exception`（打完整堆栈，但不外泄细节）；
- 入参一律 DTO + `@Valid`，例如 `LinkSaveDTO`、`AnimeSaveDTO`；
- 分页统一走 `PageResult<T>`（`total / pages / current / size / records`），不把 MyBatis-Plus 的 `Page` 直接抛给前端。

### 3. 鉴权与权限模型

- 登录 `POST /api/user/login` 校验 BCrypt 密码（`PasswordEncoder.matches`），签发 **JWT（HS256，默认 12 小时）**，
  载荷只放 `userId / username / admin`；
- `JwtAuthenticationFilter`（`OncePerRequestFilter`）解析 `Authorization: Bearer <token>`，
  校验签名与过期时间后写入 `SecurityContext`；Token 非法/过期不抛异常，最终由 `authenticationEntryPoint` 返回 401；
- 全程 **无状态**（`SessionCreationPolicy.STATELESS`），不依赖 Session；
- 权限规则集中配置在 `SecurityConfig`：

| 请求 | 要求 |
| --- | --- |
| `POST /api/user/login`、`/register` | 公开 |
| 所有 `GET /api/**` | 公开（`GET /api/user/get/all` 例外，需管理员） |
| `POST /api/*/like|dislike|addComment|view/**` | 需登录 |
| `POST /api/*/insert` | 需登录 |
| `PUT /api/*/update/**`、`DELETE /api/*/delete/**` | 需管理员（`hasRole("ADMIN")`） |

- 401 / 403 也返回统一响应体（由 `authenticationEntryPoint` / `accessDeniedHandler` 写出 JSON）；
- 密码字段用 `@JsonProperty(access = WRITE_ONLY)` + 专用 VO 双重保证**永不返回给前端**。

### 4. 数据库设计（16 张表）

| 分类 | 表 | 说明 |
| --- | --- | --- |
| 用户 | `user` | BCrypt 密码、等级、发帖/评论计数 |
| 内容 | `anime`、`article`、`video`、`character`、`announcement`、`weekly_report`、`feed`、`carousel`、`link` | 番剧 / 文章 / 视频 / 角色 / 公告 / 周报 / 动态 / 轮播 / 友链 |
| 关系 | `tag`、`anime_tag` | 标签字典 + 番剧标签多对多 |
| 交互 | `comment`、`like_record`、`anime_rating` | 多态评论、点赞记录、番剧评分（多维度） |
| 统计 | `edit_statistic` | 按「日期 + 类型」聚合的编辑统计 |

约定：

- 每张业务表统一带 `create_time` / `update_time` / `deleted`（逻辑删除），
  时间字段由 `MetaObjectHandler` 自动填充，业务代码不手写；
- 点赞数、浏览量采用**冗余计数列 + 原子自增**（`like_count = like_count + 1`），避免列表页聚合查询；
- `like_record` 用唯一索引 `(target_type, target_id, user_id)` 防重复点赞，并发重复插入触发
  `DuplicateKeyException` 后转换成友好业务异常；该表**物理删除**（取消点赞即删记录），
  避免"逻辑删除 + 唯一索引"在反复点赞/取消时主键冲突；`anime_tag` 同理；
- 分页插件 `PaginationInnerInterceptor` 限制单页最大 100 条，另开启乐观锁插件（`@Version` 可用）；
- 建表与种子数据放在 `db/schema/*.sql`、`db/data/*.sql`，由 `spring.sql.init` 启动执行，
  全部 `CREATE TABLE IF NOT EXISTS` + `INSERT ... ON DUPLICATE KEY UPDATE`，**可重复启动、幂等**。

#### 全新启动后的种子数据条数

| 表 | 条数 | 表 | 条数 |
| --- | --- | --- | --- |
| `anime` | 18 | `anime_rating` | 36 |
| `anime_tag` | 72 | `tag` | 40 |
| `article` | 4 | `video` | 12 |
| `character` | 6 | `feed` | 9 |
| `announcement` | 6 | `weekly_report` | 6 |
| `carousel` | 3 | `link` | 12 |
| `user` | 8 | `edit_statistic` | 120 |
| `comment` | 0 | `like_record` | 0 |

几点说明：

- `comment` / `like_record` 初始为空是**有意为之**：用户数据由 `DataInitializer` 在后端启动时创建，
  晚于 SQL 初始化脚本，脚本阶段无法建立评论与用户的关联。这两张表的数据需要登录后在页面上
  真实评论 / 点赞才会产生；
- `edit_statistic` 的 120 条 = 最近 30 天 × 4 种类型，由 `DataInitializer` 生成（固定随机种子，结果可复现）；
- `user` 的 8 条是演示账号，密码统一 `BCryptPasswordEncoder` 加密后入库；
- 跑集成测试（`./gradlew test`）会额外注册一个 `e2e_tester` 测试账号、并产生若干条已逻辑删除的
  `article` / `link` 记录（删除用例的痕迹），属正常现象，`deleted = 0` 查不到它们。

### 5. 接口一览（70+）

| 模块 | 前缀 | 主要接口 |
| --- | --- | --- |
| 番剧 | `/api/anime` | `GET /get`、`/get/{id}`、`/get/random`、`/get/latest`、`/get/upcoming`、`/get/recent-updated`、`/get/random-tag`、`/get/random-ratings`、`/search`；`POST /insert`、`/addComment/{id}`；`PUT /update/{id}`；`DELETE /delete/{id}` |
| 文章 | `/api/article` | `GET /get`、`/get/{id}`、`/page`（分页 + 关键字条件查询）、`/search`；`POST /insert`、`/addComment/{id}`、`/like/{id}`、`/view/{id}`；`PUT`、`DELETE` |
| 视频 | `/api/video` | `GET /get`、`/get/{id}`、`/search`；`POST /insert`、`/addComment/{id}`；`PUT`、`DELETE` |
| 角色 | `/api/character` | `GET /get`、`/get/{id}`、`/getByAnime/{animeId}`、`/search`；`POST /insert`、`/like/{id}`、`/dislike/{id}`；`PUT`、`DELETE` |
| 公告 | `/api/announcement` | `GET /get`、`/get/{id}`；`POST /insert`、`/addComment/{id}`、`/like/{id}`；`PUT`、`DELETE` |
| 周报 | `/api/weekly` | 同公告 |
| 动态 | `/api/feed` | `GET /get`、`/get/{id}`、`/search`；`POST /insert`；`PUT`、`DELETE` |
| 轮播 | `/api/carousel` | `GET /get`、`/get/{id}`；`POST /insert`；`PUT`、`DELETE` |
| 友链 | `/api/links` | 同轮播 |
| 用户 | `/api/user` | `POST /login`、`/register`；`GET /get/{id}`、`/get/all`（管理员） |
| 统计 | `/api/statistics` | `GET /edits`、`/edits/range`、`/edits/recent` |
| 搜索 | `/api/search` | `GET /all?keyword=`（聚合四个模块，带 `type` 判别字段） |

### 6. 测试

`src/test/java` 下有基于真实 MySQL 的集成测试：

- `LinkControllerTest`：MockMvc 覆盖「统一响应体、匿名可读、401、403、参数校验 400、管理员增删全链路」；
- `UserServiceTest`：BCrypt 密码存储、JWT 签发、`LOGIN_FAILED` / `USER_NOT_FOUND` 业务异常码。

```bash
cd AcgData
./gradlew test     # Windows: .\gradlew.bat test
```

---

## 前台 / 后台

### 前台（AcgWeb）

页面路由：`/home` 首页、`/anime/:id` 番剧详情、`/anime/add`、`/anime/edit/:id`、`/articles/index/:id` 文章详情、
`/article/add`、`/video/:id`、`/video/add`、`/weekly/:id`、`/discuss`、`/share`、`/search`、`/login`。

- 首页由轮播、最新番剧、即将上线、随机推荐、最新视频、最新文章、最新动态、周报、角色生日、编辑动态、公告、友链等区块组件拼装；
- `src/api/index.ts` 里封装了 Axios 单例：自动注入 `Authorization: Bearer <token>`、
  请求去重取消（`AxiosCanceler`）、**统一解包后端 `Result`（业务失败直接 reject，只把 `data` 交给页面）**、
  401 清登录态跳登录页、403/404/500 给出提示；
- 业务接口按 `modules/<模块>/{index,interface,urls.const}.ts` 三文件组织，全量 TypeScript 类型约束；
- 路由用 `import.meta.glob('./modules/*.ts')` 自动注册，`meta.keepAlive` 生成缓存列表；
- Pinia `user` store 持久化 token 与用户信息到 localStorage。

### 后台（Acg_admin）

`/login` 登录（调用真实后端，校验 `isAdmin`）、`/` 首页、`anime|article|video|user` 的 list / add / edit 页面；
路由守卫基于 `localStorage.token`，请求带 Bearer Token，401 自动清理并跳登录。

---

## 已知限制与后续优化

- [x] ~~内存假数据~~ → 已全部落库；演示数据与真实数据在同一批表里，生产环境建议引入 **Flyway/Liquibase** 管理迁移；
- [ ] **缓存**：首页首屏要打十几个接口，建议引入 Redis 缓存首页聚合结果、点赞去重、浏览量计数；
- [ ] **搜索**：目前是 `LIKE` 模糊匹配，数据量上来后建议换 Elasticsearch；
- [ ] **文件上传**：图片目前用外链图床，可补 OSS/本地存储 + 静态资源映射；
- [ ] **Token 刷新**：单 access token（12h），可补 refresh token 与登出黑名单；
- [ ] **前端体积**：Vite 构建有 >500KB 的 chunk 警告，可按路由做 `manualChunks` 分包；
- [ ] `Acg_admin` 目录多套了一层（`Acg_admin/Acg_admin/`），建议拍平；
- [ ] 表名 `character` 是 MySQL 保留字，实体上需用 `@TableName("`character`")` 反引号包住；
  同时因为实体类名与 `java.lang.Character` 撞名，MyBatis 不能开启 `type-aliases-package`（已去掉）。
  建议后续把实体改名为 `AnimeCharacter`、表名改名为 `anime_character`，彻底绕开这两个坑。

---

## 提交约定

- `.gitignore` 已忽略 `node_modules`、`dist`、`coverage`、`.env`（未加 `/` 前缀，对所有子目录生效）；
- `.env` 被忽略，克隆后按上面的说明自行创建；
- 提交前确认 `AcgWeb`、`Acg_admin/Acg_admin`、`AcgData` 都是**普通目录**，不是 Git 子模块。

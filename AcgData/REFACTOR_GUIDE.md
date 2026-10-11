# AcgData 重构约定（所有模块必须遵守）

> 这份文档是本次重构的**唯一契约**。任何模块的改造都必须逐条符合下面的约定，
> 目的是：把"Controller 里塞 `List<Map>` 假数据"改成 **Controller → Service → Mapper → MySQL** 的正规分层，
> 同时保证**接口路径和返回 JSON 结构对前端完全兼容**（前端只在最外层多认一层 `Result` 包装）。

---

## 1. 技术栈（已固定，不要改版本、不要加依赖）

| 组件 | 版本 / 说明 |
| --- | --- |
| Spring Boot | 4.1.1 GA |
| Java | 17 |
| 持久层 | MyBatis-Plus 3.5.17（`mybatis-plus-spring-boot4-starter`） |
| 数据库 | MySQL 8，库名 `acg_fans` |
| 鉴权 | Spring Security + JWT（jjwt 0.13.0）+ BCrypt |
| 其他 | Lombok、Jakarta Validation、HikariCP |

---

## 2. 包结构

> ⚠️ **三个容易踩的包路径**（版本升级后变了，写代码前先看清楚）：
>
> | 用途 | 正确 import |
> | --- | --- |
> | `IService` | `com.baomidou.mybatisplus.**spring**.service.IService` |
> | `ServiceImpl` | `com.baomidou.mybatisplus.**spring**.service.impl.ServiceImpl` |
> | `BaseMapper` | `com.baomidou.mybatisplus.core.mapper.BaseMapper` |
> | `Wrappers` / `LambdaQueryWrapper` | `com.baomidou.mybatisplus.core.toolkit.Wrappers` |
> | `Page` / `IPage` | `com.baomidou.mybatisplus.core.metadata.IPage`、`com.baomidou.mybatisplus.extension.plugins.pagination.Page` |
> | `@AutoConfigureMockMvc`（测试用） | `org.springframework.boot.**webmvc**.test.autoconfigure.AutoConfigureMockMvc`
> （Spring Boot 4 把它拆到了 `spring-boot-starter-webmvc-test`，`build.gradle` 已引入） |
>
> **Jackson 3 注意**：Spring Boot 4 用的是 Jackson 3，`ObjectMapper` 在 `**tools.jackson.databind**` 包下
> （不再是 `com.fasterxml.jackson.databind`）；`@JsonFormat`、`@JsonProperty` 等注解仍在 `com.fasterxml.jackson.annotation`。

```
com.example.acgdata
├── common/            统一响应体、业务异常、全局异常处理（不要改）
├── config/            MyBatis-Plus / CORS / 自动填充（不要改）
├── security/          JWT 工具、过滤器、权限配置（不要改）
├── entity/            数据库实体，与表一一对应
├── mapper/            继承 BaseMapper<T>，复杂查询写在注解 SQL 或 XML
├── service/           业务接口，统一继承 IService<Entity>
├── service/impl/      实现类，统一继承 ServiceImpl<Mapper, Entity>
├── controller/        只做参数校验和转发，不写业务逻辑
├── dto/               入参对象（@NotBlank / @NotNull 校验）
└── vo/                出参对象（负责对齐旧接口的 JSON 字段名）
```

---

## 3. 十条铁律（违反即返工）

1. **Controller 里禁止出现 `List<Map<String,Object>>`**，禁止业务逻辑，禁止直接调用 Mapper。
2. **所有接口返回 `Result<T>`**；分页接口返回 `Result<PageResult<T>>`。
3. **入参禁止用 `Map`**，必须使用 `dto` 包下的 DTO + Jakarta Validation 注解。
4. **数据必须落到 MySQL**，任何形式的"内存 List 当存储"一律删除。
5. **表名、列名用蛇形**（`chinese_title`）；实体属性用驼峰（`chineseTitle`），靠 MyBatis-Plus 自动映射。
6. **每个业务表必须带三件套**：`create_time`、`update_time`、`deleted`（逻辑删除）。点赞记录表例外（物理删除，见 §7.3）。
7. **种子 SQL 必须幂等**：显式指定主键 id + `ON DUPLICATE KEY UPDATE`，保证反复启动不报错、不重复插入。
8. **时间类型用 `LocalDateTime` / `LocalDate`**，返回给前端的格式必须与旧接口一致（见 §9）。
9. **不修改别人模块的文件**（文件归属见 §8），公共代码一个字都不要动。
10. **不引入新的第三方依赖**（`build.gradle` 不要动）。

---

## 4. 统一响应体

```java
Result.ok(data)                 // { code:200, message:"操作成功", data:{...}, success:true }
Result.ok("message", data)
Result.fail(ResultCode.XXX)     // 业务失败
throw new BusinessException(ResultCode.XXX)          // 推荐：抛出去交给全局异常处理器
throw new BusinessException(ResultCode.XXX, "自定义提示")
```

分页：

```java
Page<Anime> page = new Page<>(current, size);
Page<Anime> result = animeService.page(page, wrapper);
return Result.ok(PageResult.of(result));            // 实体直接返回
return Result.ok(PageResult.of(result, this::toVO)); // 需要转 VO 时
```

`PageResult` 字段：`total`、`pages`、`current`、`size`、`records`。

---

## 5. 异常与参数校验

- **不要写 try/catch 包裹业务逻辑**，直接抛 `BusinessException`，由 `GlobalExceptionHandler` 统一处理。
- "查不到"用 `throw new BusinessException(ResultCode.DATA_NOT_FOUND)`，不要返回 null。
- DTO 上加 `@NotBlank`、`@NotNull`、`@Size`、`@Min` 等注解，Controller 方法参数上加 `@Valid`。
- 查询参数用 `@RequestParam(defaultValue = "1") long current` 这类写法，必要时加 `@Min`。

---

## 6. 权限规则（`SecurityConfig` 已配好，不要修改它）

| 请求 | 要求 |
| --- | --- |
| `POST /api/user/login`、`POST /api/user/register` | 公开 |
| 所有 `GET /api/**` | 公开（`GET /api/user/get/all` 例外，需要管理员） |
| `POST /api/*/like/**`、`/dislike/**`、`/addComment/**`、`/view/**` | 需登录 |
| `POST /api/*/insert` | 需登录 |
| `PUT /api/*/update/**` | 需管理员 |
| `DELETE /api/*/delete/**` | 需管理员 |

**因此**：写接口的 URL 必须严格使用 `/insert`、`/update/{id}`、`/delete/{id}` 这三种形式，
否则权限规则失效。需要当前用户时调用 `SecurityUtils.getUserId()` / `SecurityUtils.getLoginUserOrNull()`。

---

## 7. 共享服务 API（已实现，直接用，不要重复造）

### 7.1 `TagService`（番剧标签）

```java
List<String> listNamesByAnimeId(Long animeId);          // 取番剧的标签名列表
void bindAnimeTags(Long animeId, List<String> tagNames); // 先删后插，自动创建不存在的标签
List<Tag> listByType(String type);                       // type: anime / article / video
```

### 7.2 `CommentService`（多态评论，番剧/文章/视频/公告/周报共用）

```java
List<CommentVO> listByTarget(String targetType, Long targetId);
CommentVO add(String targetType, Long targetId, CommentSaveDTO dto);  // 评论人取自 JWT
long countByTarget(String targetType, Long targetId);
```

`CommentVO` 字段（对齐前端 `Comment` 类型）：
`id`、`user`（用户名）、`userAvatar`、`content`、`date`（`yyyy-MM-dd HH:mm`）、`likeCount`。

`targetType` 取值：`anime` / `article` / `video` / `announcement` / `weekly`。

### 7.3 `LikeService`（点赞去重 + 计数）

```java
long like(String targetType, Long targetId);    // 重复点赞抛 BusinessException(REPEAT_OPERATION)
long unlike(String targetType, Long targetId);
long count(String targetType, Long targetId);
boolean isLiked(String targetType, Long targetId);
```

- 点赞记录写在 `like_record` 表，**唯一索引防重**；
- `like` / `unlike` **内部会维护目标表的 `like_count`**，业务模块**不要再自己加减**；
- 支持的目标与表：`anime→anime`、`article→article`、`video→video`、`announcement→announcement`、`weekly→weekly_report`；
- 因此这些表**必须有 `like_count` 列**。

### 7.4 `UserService`

```java
User getByIdOrThrow(Long id);      // 查不到直接抛业务异常
UserVO getVO(Long id);
```

### 7.5 `EditStatisticService`（编辑统计）

```java
void increment(String type);       // type: anime / article / video / character
```

在 `insert` / `update` 成功后调用一次 `editStatisticService.increment("anime")`。

> ⚠️ 旧的 `service/EditStatisticsService`（静态假数据）本次重构后会被删除，
> 新代码**一律不要**再 import 它，也不要保留对它的调用。

---

## 8. 文件归属（只允许改自己名下的文件）

**公共部分（任何人不得修改）**

```
build.gradle / src/main/resources/application.yml
common/**（Result、ResultCode、PageResult、BusinessException、GlobalExceptionHandler）
config/**、security/**
entity/{User,Tag,AnimeTag,Comment,LikeRecord,EditStatistic}.java
mapper/{User,Tag,AnimeTag,Comment,LikeRecord,EditStatistic}Mapper.java
service/{Tag,Comment,Like,User}Service.java 与对应 impl
vo/{CommentVO,UserVO,LoginVO}.java  dto/{CommentSaveDTO,LoginDTO,RegisterDTO}.java
controller/{UserController,SearchController,EditStatisticsController}.java
resources/db/schema/00_common.sql  resources/db/data/00_common.sql
link 模块（controller/LinkController、entity/Link、service/LinkService(+Impl)、mapper/LinkMapper、dto/LinkSaveDTO、vo/LinkVO、db/schema/50_link.sql、db/data/50_link.sql）
```

**各模块拥有的文件（前缀编号 = SQL 文件编号）**

| 模块 | 负责的 Controller | SQL 文件 |
| --- | --- | --- |
| anime | `AnimeController`（+ entity/mapper/service/dto/vo：Anime、AnimeRating…） | `10_anime.sql` |
| article | `ArticleController` | `20_article.sql` |
| video | `VideoController` | `21_video.sql` |
| character | `CharacterController` | `30_character.sql` |
| feed | `FeedController` | `31_feed.sql` |
| announcement | `AnnouncementController` | `40_announcement.sql` |
| weekly | `WeeklyReportController` | `41_weekly.sql` |
| carousel | `CarouselController` | `42_carousel.sql` |

---

## 9. 前端字段契约（必须逐字段对齐）

前端只做了一处改动：`src/api/index.ts` 的响应拦截器会把 `Result.data` 解包后返回。
所以**你返回的 `data` 内部结构，必须和旧接口一模一样**。

对齐方法（每个模块都要做）：

1. 先通读旧的 Controller，记录**每个端点**返回的 JSON 结构（哪些字段、列表还是对象）；
2. 再读前端同模块的 `src/api/modules/<模块>/interface.ts` 和 `index.ts`，以 TS 类型为准绳；
3. 新的 `vo` 字段名与类型必须与 TS 类型逐个对上。

已知的格式约定：

- `GET /get` 返回**数组**；`GET /get/{id}` 返回**单个对象**；
- `POST /insert`、`PUT /update/{id}` 返回**保存后的完整对象**；
- `DELETE /delete/{id}` 返回 `{ "status": "success" }`（失败时抛业务异常）；
- `POST /like/{id}`、`POST /view/{id}` 返回**更新后的对象**；
- 日期字符串：`yyyy-MM-dd`（如 `publishDate`、`startDate`），时间戳字符串 `yyyy-MM-dd HH:mm`（评论 `date`）；
- 布尔/计数字段保持数字类型（如 `readCount`、`likeCount`、`episodes`）；
- **时间字段必须在 VO 上精确控制格式**：要么在 SQL 里 `DATE_FORMAT(...)` 直接算成 String，
  要么用 `@JsonFormat(pattern = "...")`。不要依赖默认序列化（默认会输出 `2026-04-14T00:00:00` 带 T 的 ISO 格式）。
  历史字段举例：`lastEdit` 是 `2026年2月13日 18:26:13`，`lastLogin` 是 `2026-04-14 00:00:00`。

---

## 10. 标准代码模板（照抄 link 模块）

`entity/Link.java`

```java
@Data
@TableName("link")
public class Link implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String iconUrl;      // 列名 icon_url
    private String targetUrl;    // 列名 target_url
    private Integer sort;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableLogic
    @TableField(select = false)
    private Integer deleted;
}
```

`mapper/LinkMapper.java`

```java
public interface LinkMapper extends BaseMapper<Link> {
}
```

`service/LinkService.java` + `service/impl/LinkServiceImpl.java`

```java
public interface LinkService extends IService<Link> {
    List<LinkVO> listAll();
    LinkVO getVO(Long id);
    LinkVO save(LinksaveDTO dto);
    LinkVO update(Long id, LinkSaveDTO dto);
    void remove(Long id);
}

@Service
@RequiredArgsConstructor
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {
    // 需要多个 Mapper 时用 @RequiredArgsConstructor 注入，不要用 @Autowired 字段注入
}
```

`controller/LinkController.java`

```java
@RestController
@RequestMapping("/api/links")
@RequiredArgsConstructor
@Validated
public class LinkController {

    private final LinkService linkService;

    @GetMapping("/get")
    public Result<List<LinkVO>> getLinks() {
        return Result.ok(linkService.listAll());
    }

    @GetMapping("/get/{id}")
    public Result<LinkVO> getLinkById(@PathVariable Long id) {
        return Result.ok(linkService.getVO(id));
    }

    @PostMapping("/insert")
    public Result<LinkVO> insert(@Valid @RequestBody LinkSaveDTO dto) { ... }

    @PutMapping("/update/{id}")
    public Result<LinkVO> update(@PathVariable Long id, @Valid @RequestBody LinkSaveDTO dto) { ... }

    @DeleteMapping("/delete/{id}")
    public Result<Map<String, String>> delete(@PathVariable Long id) {
        linkService.remove(id);
        return Result.ok(Map.of("status", "success"));
    }
}
```

`vo/LinkVO.java`：负责把实体字段翻译成前端认的字段名（`iconUrl → url`、`targetUrl → link`）。

SQL 种子：`db/schema/50_link.sql`（建表）+ `db/data/50_link.sql`（数据，幂等）。

---

## 11. 自检清单（提交前逐条确认）

- [ ] `grep -rn "List<Map<String, Object>>" src/main/java/com/example/acgdata/controller/` 里没有我负责的 Controller
- [ ] 我负责的 Controller 全部返回 `Result<...>`
- [ ] 所有写接口入参是 DTO + `@Valid`
- [ ] 建表 SQL 带 `create_time` / `update_time` / `deleted`，且 `CREATE TABLE IF NOT EXISTS`
- [ ] 种子 SQL 显式 id + `ON DUPLICATE KEY UPDATE`，重复执行不报错
- [ ] 接口路径和旧的一致（不要自作主张改路径、改字段名）
- [ ] 没有修改 §8 里不属于自己的文件
- [ ] 用到的共享服务（Tag/Comment/Like/User）都按 §7 的签名调用

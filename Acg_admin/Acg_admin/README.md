# ACGData 项目接口文档

## 1. 番剧控制器 (AnimeController)

### 接口路径: `/api/anime`

#### 1.1 获取番剧列表
- **请求方法**: GET
- **路径**: `/api/anime/get`
- **返回格式**: 
  ```json
  [
    {
      "id": 1,
      "title": "ジョジョの奇妙な冒険",
      "chineseTitle": "JOJO的奇妙冒险",
      "image": "https://example.com/jojo.jpg",
      "description": "讲述乔斯达家族与迪奥·布兰度之间跨越 generations 的宿命对决"
    }
  ]
  ```

#### 1.2 获取番剧详情
- **请求方法**: GET
- **路径**: `/api/anime/get/{id}`
- **参数**: `id` (番剧ID)
- **返回格式**: 完整的番剧信息，包含所有字段

#### 1.3 新增番剧
- **请求方法**: POST
- **路径**: `/api/anime/insert`
- **请求体**: 番剧信息
- **返回格式**: 新增的番剧信息

#### 1.4 更新番剧
- **请求方法**: PUT
- **路径**: `/api/anime/update/{id}`
- **参数**: `id` (番剧ID)
- **请求体**: 更新的番剧信息
- **返回格式**: 更新后的番剧信息

#### 1.5 删除番剧
- **请求方法**: DELETE
- **路径**: `/api/anime/delete/{id}`
- **参数**: `id` (番剧ID)
- **返回格式**: 
  ```json
  {
    "status": "success"
  }
  ```

#### 1.6 添加评论
- **请求方法**: POST
- **路径**: `/api/anime/addComment/{id}`
- **参数**: `id` (番剧ID)
- **请求体**: 评论信息
- **返回格式**: 添加的评论信息

#### 1.7 获取随机番剧
- **请求方法**: GET
- **路径**: `/api/anime/get/random`
- **返回格式**: 随机10部番剧的卡片信息

#### 1.8 获取最近更新番剧
- **请求方法**: GET
- **路径**: `/api/anime/get/recent-updated`
- **返回格式**: 最近更新的5部番剧信息

#### 1.9 搜索番剧
- **请求方法**: GET
- **路径**: `/api/anime/search`
- **参数**: `keyword` (搜索关键词)
- **返回格式**: 搜索结果列表

#### 1.10 获取最新番剧
- **请求方法**: GET
- **路径**: `/api/anime/get/latest`
- **返回格式**: 最新的10部番剧信息

#### 1.11 获取随机标签和相关番剧
- **请求方法**: GET
- **路径**: `/api/anime/get/random-tag`
- **返回格式**: 
  ```json
  {
    "tag": "战斗",
    "anime": [
      {
        "id": 1,
        "title": "ジョジョの奇妙な冒険",
        "chineseTitle": "JOJO的奇妙冒险",
        "image": "https://example.com/jojo.jpg",
        "description": "讲述乔斯达家族与迪奥·布兰度之间跨越 generations 的宿命对决"
      }
    ]
  }
  ```

#### 1.12 获取随机评价
- **请求方法**: GET
- **路径**: `/api/anime/get/random-ratings`
- **返回格式**: 随机10条评价信息

#### 1.13 获取即将上映番剧
- **请求方法**: GET
- **路径**: `/api/anime/get/upcoming`
- **返回格式**: 即将上映的番剧列表

## 2. 文章控制器 (ArticleController)

### 接口路径: `/api/article`

#### 2.1 获取文章列表
- **请求方法**: GET
- **路径**: `/api/article/get`
- **返回格式**: 文章列表，包含基本信息

#### 2.2 获取文章详情
- **请求方法**: GET
- **路径**: `/api/article/get/{id}`
- **参数**: `id` (文章ID)
- **返回格式**: 完整的文章信息

#### 2.3 新增文章
- **请求方法**: POST
- **路径**: `/api/article/insert`
- **请求体**: 文章信息
- **返回格式**: 新增的文章信息

#### 2.4 更新文章
- **请求方法**: PUT
- **路径**: `/api/article/update/{id}`
- **参数**: `id` (文章ID)
- **请求体**: 更新的文章信息
- **返回格式**: 更新后的文章信息

#### 2.5 删除文章
- **请求方法**: DELETE
- **路径**: `/api/article/delete/{id}`
- **参数**: `id` (文章ID)
- **返回格式**: 
  ```json
  {
    "status": "success"
  }
  ```

#### 2.6 添加评论
- **请求方法**: POST
- **路径**: `/api/article/addComment/{id}`
- **参数**: `id` (文章ID)
- **请求体**: 评论信息
- **返回格式**: 添加的评论信息

#### 2.7 点赞文章
- **请求方法**: POST
- **路径**: `/api/article/like/{id}`
- **参数**: `id` (文章ID)
- **返回格式**: 更新后的文章信息

#### 2.8 增加浏览量
- **请求方法**: POST
- **路径**: `/api/article/view/{id}`
- **参数**: `id` (文章ID)
- **返回格式**: 更新后的文章信息

#### 2.9 搜索文章
- **请求方法**: GET
- **路径**: `/api/article/search`
- **参数**: `keyword` (搜索关键词)
- **返回格式**: 搜索结果列表

## 3. 视频控制器 (VideoController)

### 接口路径: `/api/video`

#### 3.1 获取视频列表
- **请求方法**: GET
- **路径**: `/api/video/get`
- **返回格式**: 视频列表，按发布日期排序

#### 3.2 获取视频详情
- **请求方法**: GET
- **路径**: `/api/video/get/{id}`
- **参数**: `id` (视频ID)
- **返回格式**: 完整的视频信息

#### 3.3 新增视频
- **请求方法**: POST
- **路径**: `/api/video/insert`
- **请求体**: 视频信息
- **返回格式**: 新增的视频信息

#### 3.4 更新视频
- **请求方法**: PUT
- **路径**: `/api/video/update/{id}`
- **参数**: `id` (视频ID)
- **请求体**: 更新的视频信息
- **返回格式**: 更新后的视频信息

#### 3.5 删除视频
- **请求方法**: DELETE
- **路径**: `/api/video/delete/{id}`
- **参数**: `id` (视频ID)
- **返回格式**: 
  ```json
  {
    "success": true,
    "message": "删除成功"
  }
  ```

#### 3.6 添加评论
- **请求方法**: POST
- **路径**: `/api/video/addComment/{id}`
- **参数**: `id` (视频ID)
- **请求体**: 评论信息
- **返回格式**: 添加的评论信息

#### 3.7 搜索视频
- **请求方法**: GET
- **路径**: `/api/video/search`
- **参数**: `keyword` (搜索关键词)
- **返回格式**: 搜索结果列表

## 4. 角色控制器 (CharacterController)

### 接口路径: `/api/character`

#### 4.1 获取角色列表
- **请求方法**: GET
- **路径**: `/api/character/get`
- **返回格式**: 角色列表，包含基本信息

#### 4.2 获取角色详情
- **请求方法**: GET
- **路径**: `/api/character/get/{id}`
- **参数**: `id` (角色ID)
- **返回格式**: 完整的角色信息

#### 4.3 根据番剧获取角色
- **请求方法**: GET
- **路径**: `/api/character/getByAnime/{animeId}`
- **参数**: `animeId` (番剧ID)
- **返回格式**: 该番剧的角色列表

#### 4.4 新增角色
- **请求方法**: POST
- **路径**: `/api/character/insert`
- **请求体**: 角色信息
- **返回格式**: 新增的角色信息

#### 4.5 更新角色
- **请求方法**: PUT
- **路径**: `/api/character/update/{id}`
- **参数**: `id` (角色ID)
- **请求体**: 更新的角色信息
- **返回格式**: 更新后的角色信息

#### 4.6 删除角色
- **请求方法**: DELETE
- **路径**: `/api/character/delete/{id}`
- **参数**: `id` (角色ID)
- **返回格式**: 
  ```json
  {
    "success": "true",
    "message": "角色删除成功"
  }
  ```

#### 4.7 搜索角色
- **请求方法**: GET
- **路径**: `/api/character/search`
- **参数**: `keyword` (搜索关键词)
- **返回格式**: 搜索结果列表

#### 4.8 点赞角色
- **请求方法**: POST
- **路径**: `/api/character/like/{id}`
- **参数**: `id` (角色ID)
- **返回格式**: 
  ```json
  {
    "success": true,
    "likes": 12501
  }
  ```

#### 4.9 点踩角色
- **请求方法**: POST
- **路径**: `/api/character/dislike/{id}`
- **参数**: `id` (角色ID)
- **返回格式**: 
  ```json
  {
    "success": true,
    "dislikes": 121
  }
  ```

## 5. 编辑统计控制器 (EditStatisticsController)

### 接口路径: `/api/statistics`

#### 5.1 获取所有编辑统计数据
- **请求方法**: GET
- **路径**: `/api/statistics/edits`
- **返回格式**: 所有编辑统计数据

#### 5.2 获取指定日期范围的编辑统计数据
- **请求方法**: GET
- **路径**: `/api/statistics/edits/range`
- **参数**: `startDate` (开始日期), `endDate` (结束日期)
- **返回格式**: 指定日期范围的编辑统计数据

#### 5.3 获取最近30天的编辑统计数据
- **请求方法**: GET
- **路径**: `/api/statistics/edits/recent`
- **返回格式**: 最近30天的编辑统计数据

## 6. 公告控制器 (AnnouncementController)

### 接口路径: `/api/announcement`

#### 6.1 获取公告列表
- **请求方法**: GET
- **路径**: `/api/announcement/get`
- **返回格式**: 公告列表，包含基本信息

#### 6.2 获取公告详情
- **请求方法**: GET
- **路径**: `/api/announcement/get/{id}`
- **参数**: `id` (公告ID)
- **返回格式**: 完整的公告信息

#### 6.3 新增公告
- **请求方法**: POST
- **路径**: `/api/announcement/insert`
- **请求体**: 公告信息
- **返回格式**: 新增的公告信息

#### 6.4 更新公告
- **请求方法**: PUT
- **路径**: `/api/announcement/update/{id}`
- **参数**: `id` (公告ID)
- **请求体**: 更新的公告信息
- **返回格式**: 更新后的公告信息

#### 6.5 删除公告
- **请求方法**: DELETE
- **路径**: `/api/announcement/delete/{id}`
- **参数**: `id` (公告ID)
- **返回格式**: 
  ```json
  {
    "success": true,
    "message": "删除成功"
  }
  ```

#### 6.6 添加评论
- **请求方法**: POST
- **路径**: `/api/announcement/addComment/{id}`
- **参数**: `id` (公告ID)
- **请求体**: 评论信息
- **返回格式**: 添加的评论信息

#### 6.7 点赞公告
- **请求方法**: POST
- **路径**: `/api/announcement/like/{id}`
- **参数**: `id` (公告ID)
- **返回格式**: 
  ```json
  {
    "success": true,
    "likeCount": 46
  }
  ```

## 7. 轮播图控制器 (CarouselController)

### 接口路径: `/api/carousel`

#### 7.1 获取轮播图列表
- **请求方法**: GET
- **路径**: `/api/carousel/get`
- **返回格式**: 轮播图列表

#### 7.2 获取轮播图详情
- **请求方法**: GET
- **路径**: `/api/carousel/get/{id}`
- **参数**: `id` (轮播图ID)
- **返回格式**: 轮播图详情

#### 7.3 新增轮播图
- **请求方法**: POST
- **路径**: `/api/carousel/insert`
- **请求体**: 轮播图信息
- **返回格式**: 新增的轮播图信息

#### 7.4 更新轮播图
- **请求方法**: PUT
- **路径**: `/api/carousel/update/{id}`
- **参数**: `id` (轮播图ID)
- **请求体**: 更新的轮播图信息
- **返回格式**: 更新后的轮播图信息

#### 7.5 删除轮播图
- **请求方法**: DELETE
- **路径**: `/api/carousel/delete/{id}`
- **参数**: `id` (轮播图ID)
- **返回格式**: 
  ```json
  {
    "status": "success"
  }
  ```

## 8. 动态控制器 (FeedController)

### 接口路径: `/api/feed`

#### 8.1 获取动态列表
- **请求方法**: GET
- **路径**: `/api/feed/get`
- **返回格式**: 动态列表，按时间排序

#### 8.2 获取动态详情
- **请求方法**: GET
- **路径**: `/api/feed/get/{id}`
- **参数**: `id` (动态ID)
- **返回格式**: 动态详情

#### 8.3 新增动态
- **请求方法**: POST
- **路径**: `/api/feed/insert`
- **请求体**: 动态信息
- **返回格式**: 新增的动态信息

#### 8.4 更新动态
- **请求方法**: PUT
- **路径**: `/api/feed/update/{id}`
- **参数**: `id` (动态ID)
- **请求体**: 更新的动态信息
- **返回格式**: 更新后的动态信息

#### 8.5 删除动态
- **请求方法**: DELETE
- **路径**: `/api/feed/delete/{id}`
- **参数**: `id` (动态ID)
- **返回格式**: 
  ```json
  {
    "success": true,
    "message": "删除成功"
  }
  ```

#### 8.6 搜索动态
- **请求方法**: GET
- **路径**: `/api/feed/search`
- **参数**: `keyword` (搜索关键词)
- **返回格式**: 搜索结果列表

## 9. 友情链接控制器 (LinkController)

### 接口路径: `/api/links`

#### 9.1 获取链接列表
- **请求方法**: GET
- **路径**: `/api/links/get`
- **返回格式**: 链接列表

#### 9.2 获取链接详情
- **请求方法**: GET
- **路径**: `/api/links/get/{id}`
- **参数**: `id` (链接ID)
- **返回格式**: 链接详情

#### 9.3 新增链接
- **请求方法**: POST
- **路径**: `/api/links/insert`
- **请求体**: 链接信息
- **返回格式**: 新增的链接信息

#### 9.4 更新链接
- **请求方法**: PUT
- **路径**: `/api/links/update/{id}`
- **参数**: `id` (链接ID)
- **请求体**: 更新的链接信息
- **返回格式**: 更新后的链接信息

#### 9.5 删除链接
- **请求方法**: DELETE
- **路径**: `/api/links/delete/{id}`
- **参数**: `id` (链接ID)
- **返回格式**: 
  ```json
  {
    "status": "success"
  }
  ```

## 10. 用户控制器 (UserController)

### 接口路径: `/api/user`

#### 10.1 用户登录
- **请求方法**: POST
- **路径**: `/api/user/login`
- **请求体**: 
  ```json
  {
    "username": "用户名",
    "password": "密码"
  }
  ```
- **返回格式**: 
  ```json
  {
    "success": true,
    "message": "登录成功",
    "user": {
      "id": 1,
      "username": "用户名",
      "email": "邮箱",
      "avatar": "头像URL",
      "registerDate": "注册日期",
      "lastLogin": "最后登录时间",
      "articleCount": 5,
      "commentCount": 23,
      "bio": "个人简介",
      "level": 9,
      "isAdmin": false
    }
  }
  ```

#### 10.2 用户注册
- **请求方法**: POST
- **路径**: `/api/user/register`
- **请求体**: 
  ```json
  {
    "username": "用户名",
    "password": "密码",
    "email": "邮箱"
  }
  ```
- **返回格式**: 
  ```json
  {
    "success": true,
    "message": "注册成功",
    "user": {
      "id": 8,
      "username": "用户名",
      "email": "邮箱",
      "avatar": "头像URL",
      "registerDate": "注册日期",
      "lastLogin": "最后登录时间",
      "articleCount": 0,
      "commentCount": 0,
      "bio": "个人简介",
      "level": 1,
      "isAdmin": false
    }
  }
  ```

#### 10.3 获取用户详情
- **请求方法**: GET
- **路径**: `/api/user/get/{id}`
- **参数**: `id` (用户ID)
- **返回格式**: 用户详情，包含 `isAdmin` 字段

#### 10.4 获取所有用户
- **请求方法**: GET
- **路径**: `/api/user/get/all`
- **返回格式**: 用户列表，每个用户包含 `isAdmin` 字段

## 11. 周报控制器 (WeeklyReportController)

### 接口路径: `/api/weekly`

#### 11.1 获取周报列表
- **请求方法**: GET
- **路径**: `/api/weekly/get`
- **返回格式**: 周报列表，包含基本信息

#### 11.2 获取周报详情
- **请求方法**: GET
- **路径**: `/api/weekly/get/{id}`
- **参数**: `id` (周报ID)
- **返回格式**: 完整的周报信息

#### 11.3 新增周报
- **请求方法**: POST
- **路径**: `/api/weekly/insert`
- **请求体**: 周报信息
- **返回格式**: 新增的周报信息

#### 11.4 更新周报
- **请求方法**: PUT
- **路径**: `/api/weekly/update/{id}`
- **参数**: `id` (周报ID)
- **请求体**: 更新的周报信息
- **返回格式**: 更新后的周报信息

#### 11.5 删除周报
- **请求方法**: DELETE
- **路径**: `/api/weekly/delete/{id}`
- **参数**: `id` (周报ID)
- **返回格式**: 
  ```json
  {
    "success": true,
    "message": "删除成功"
  }
  ```

#### 11.6 添加评论
- **请求方法**: POST
- **路径**: `/api/weekly/addComment/{id}`
- **参数**: `id` (周报ID)
- **请求体**: 评论信息
- **返回格式**: 添加的评论信息

#### 11.7 点赞周报
- **请求方法**: POST
- **路径**: `/api/weekly/like/{id}`
- **参数**: `id` (周报ID)
- **返回格式**: 
  ```json
  {
    "success": true,
    "likeCount": 9
  }
  ```

## 数据字段说明

### 番剧 (Anime)
- `id`: 番剧ID
- `title`: 日文标题
- `chineseTitle`: 中文标题
- `image`: 图片URL
- `description`: 简短描述
- `introduction`: 详细介绍
- `studio`: 制作公司
- `tags`: 标签列表
- `episodes`: 集数
- `startDate`: 开始日期
- `broadcastDay`: 播出日
- `originalWork`: 原作
- `director`: 导演
- `script`: 脚本
- `storyboard`: 分镜
- `performance`: 演出
- `music`: 音乐
- `characterDesign`: 角色设计
- `seriesComposition`: 系列构成
- `artDirector`: 美术监督
- `colorDesign`: 色彩设计
- `chiefAnimationDirector`: 总作画监督
- `type`: 类型 ("anime")
- `viewingRecords`: 评价记录
- `characters`: 角色列表
- `officialWebsite`: 官方网站
- `comments`: 评论列表
- `lastEdit`: 最后编辑时间

### 文章 (Article)
- `id`: 文章ID
- `cover`: 封面图片
- `title`: 标题
- `content`: 内容
- `author`: 作者
- `authorAvatar`: 作者头像
- `publishDate`: 发布日期
- `readCount`: 阅读量
- `likeCount`: 点赞数
- `summary`: 摘要
- `lastEdit`: 最后编辑时间
- `type`: 类型 ("article")
- `comments`: 评论列表

### 视频 (Video)
- `id`: 视频ID
- `thumbnail`: 缩略图
- `title`: 标题
- `author`: 作者
- `authorAvatar`: 作者头像
- `authorRole`: 作者角色
- `authorLevel`: 作者等级
- `authorBio`: 作者简介
- `publishDate`: 发布日期
- `description`: 描述
- `externalLink`: 外部链接
- `relatedTags`: 相关标签
- `type`: 类型 ("video")
- `relatedAnime`: 相关番剧
- `comments`: 评论列表

### 角色 (Character)
- `id`: 角色ID
- `name`: 角色名称
- `japaneseName`: 日文名称
- `image`: 图片URL
- `animeId`: 番剧ID
- `animeTitle`: 番剧日文标题
- `animeChineseTitle`: 番剧中文标题
- `voiceActor`: 声优
- `gender`: 性别
- `age`: 年龄
- `height`: 身高
- `weight`: 体重
- `birthday`: 生日
- `description`: 描述
- `cv`: 声优
- `likes`: 点赞数
- `dislikes`: 点踩数
- `type`: 类型 ("character")

### 公告 (Announcement)
- `id`: 公告ID
- `cover`: 封面图片
- `title`: 标题
- `content`: 内容
- `author`: 作者
- `authorAvatar`: 作者头像
- `publishDate`: 发布日期
- `readCount`: 阅读量
- `likeCount`: 点赞数
- `summary`: 摘要
- `lastEdit`: 最后编辑时间
- `comments`: 评论列表

### 轮播图 (Carousel)
- `id`: 轮播图ID
- `url`: 图片URL
- `title`: 标题

### 动态 (Feed)
- `id`: 动态ID
- `userAvatar`: 用户头像
- `username`: 用户名
- `time`: 发布时间
- `content`: 内容
- `link`: 链接

### 友情链接 (Link)
- `id`: 链接ID
- `name`: 网站名称
- `url`: 网站URL
- `link`: 链接地址

### 用户 (User)
- `id`: 用户ID
- `username`: 用户名
- `password`: 密码
- `email`: 邮箱
- `avatar`: 头像
- `registerDate`: 注册日期
- `lastLogin`: 最后登录时间
- `articleCount`: 文章数量
- `commentCount`: 评论数量
- `bio`: 个人简介
- `level`: 等级
- `isAdmin`: 是否管理员

### 周报 (WeeklyReport)
- `id`: 周报ID
- `cover`: 封面图片
- `title`: 标题
- `content`: 内容
- `author`: 作者
- `authorAvatar`: 作者头像
- `publishDate`: 发布日期
- `readCount`: 阅读量
- `likeCount`: 点赞数
- `summary`: 摘要
- `lastEdit`: 最后编辑时间
- `comments`: 评论列表

## 编辑统计数据格式

```json
{
  "2026-04-01": {
    "anime": 5,
    "article": 3,
    "video": 2,
    "character": 1
  },
  "2026-04-02": {
    "anime": 2,
    "article": 4,
    "video": 0,
    "character": 3
  }
}
```

## 总结

以上是ACGData项目的所有接口和数据格式，您可以根据这些信息编写后台管理系统。所有接口都遵循RESTful设计规范，返回JSON格式数据。每个控制器都提供了完整的CRUD操作，以及一些特定的功能接口，如搜索、随机推荐、统计等。

您可以使用以下管理员账号登录系统进行测试：
- 用户名：`admin`
- 密码：`admin123`

希望这些信息对您编写后台管理系统有所帮助！
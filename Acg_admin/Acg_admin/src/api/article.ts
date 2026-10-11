import api from './axios';

// 获取文章列表
export const getArticleList = () => {
  return api.get('/article/get');
};

// 获取文章详情
export const getArticleById = (id: number) => {
  return api.get(`/article/get/${id}`);
};

// 新增文章
export const addArticle = (data: any) => {
  return api.post('/article/insert', data);
};

// 更新文章
export const updateArticle = (id: number, data: any) => {
  return api.put(`/article/update/${id}`, data);
};

// 删除文章
export const deleteArticle = (id: number) => {
  return api.delete(`/article/delete/${id}`);
};

// 添加评论
export const addComment = (id: number, data: any) => {
  return api.post(`/article/addComment/${id}`, data);
};

// 点赞文章
export const likeArticle = (id: number) => {
  return api.post(`/article/like/${id}`);
};

// 增加浏览量
export const increaseViewCount = (id: number) => {
  return api.post(`/article/view/${id}`);
};

// 搜索文章
export const searchArticle = (keyword: string) => {
  return api.get(`/article/search?keyword=${keyword}`);
};
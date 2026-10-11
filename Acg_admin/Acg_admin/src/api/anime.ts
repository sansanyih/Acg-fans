import api from './axios';

// 获取番剧列表
export const getAnimeList = () => {
  return api.get('/anime/get');
};

// 获取番剧详情
export const getAnimeById = (id: number) => {
  return api.get(`/anime/get/${id}`);
};

// 新增番剧
export const addAnime = (data: any) => {
  return api.post('/anime/insert', data);
};

// 更新番剧
export const updateAnime = (id: number, data: any) => {
  return api.put(`/anime/update/${id}`, data);
};

// 删除番剧
export const deleteAnime = (id: number) => {
  return api.delete(`/anime/delete/${id}`);
};

// 添加评论
export const addComment = (id: number, data: any) => {
  return api.post(`/anime/addComment/${id}`, data);
};

// 获取随机番剧
export const getRandomAnime = () => {
  return api.get('/anime/get/random');
};

// 获取最近更新番剧
export const getRecentUpdatedAnime = () => {
  return api.get('/anime/get/recent-updated');
};

// 搜索番剧
export const searchAnime = (keyword: string) => {
  return api.get(`/anime/search?keyword=${keyword}`);
};

// 获取最新番剧
export const getLatestAnime = () => {
  return api.get('/anime/get/latest');
};

// 获取随机标签和相关番剧
export const getRandomTagAnime = () => {
  return api.get('/anime/get/random-tag');
};

// 获取随机评价
export const getRandomRatings = () => {
  return api.get('/anime/get/random-ratings');
};

// 获取即将上映番剧
export const getUpcomingAnime = () => {
  return api.get('/anime/get/upcoming');
};
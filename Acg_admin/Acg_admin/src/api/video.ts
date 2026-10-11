import api from './axios';

// 获取视频列表
export const getVideoList = () => {
  return api.get('/video/get');
};

// 获取视频详情
export const getVideoById = (id: number) => {
  return api.get(`/video/get/${id}`);
};

// 新增视频
export const addVideo = (data: any) => {
  return api.post('/video/insert', data);
};

// 更新视频
export const updateVideo = (id: number, data: any) => {
  return api.put(`/video/update/${id}`, data);
};

// 删除视频
export const deleteVideo = (id: number) => {
  return api.delete(`/video/delete/${id}`);
};

// 添加评论
export const addComment = (id: number, data: any) => {
  return api.post(`/video/addComment/${id}`, data);
};

// 搜索视频
export const searchVideo = (keyword: string) => {
  return api.get(`/video/search?keyword=${keyword}`);
};
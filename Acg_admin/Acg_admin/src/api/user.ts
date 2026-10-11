import api from './axios';

// 用户登录
export const login = (data: { username: string; password: string }) => {
  return api.post('/user/login', data);
};

// 用户注册
export const register = (data: { username: string; password: string; email: string }) => {
  return api.post('/user/register', data);
};

// 获取用户详情
export const getUserById = (id: number) => {
  return api.get(`/user/get/${id}`);
};

// 获取所有用户
export const getAllUsers = () => {
  return api.get('/user/get/all');
};
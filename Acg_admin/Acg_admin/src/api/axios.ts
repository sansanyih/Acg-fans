import axios from 'axios';

// 后端统一响应体
export interface ApiResult<T = any> {
  code: number;
  message: string;
  data: T;
  success?: boolean;
}

const SUCCESS_CODE = 200;

// 创建axios实例
const api = axios.create({
  // 优先读环境变量，没有则回退到本地后端
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
});

// 请求拦截器
api.interceptors.request.use(
  config => {
    // 从localStorage中获取token
    const token = localStorage.getItem('token');
    // 如果token存在，添加到请求头
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  error => {
    // 处理请求错误
    return Promise.reject(error);
  }
);

// 响应拦截器：统一解包 Result，业务失败直接 reject
api.interceptors.response.use(
  response => {
    const body = response.data;
    if (body && typeof body === 'object' && typeof body.code === 'number') {
      if (body.code === SUCCESS_CODE) {
        return body.data;
      }
      return Promise.reject(new Error(body.message || '请求失败'));
    }
    return body;
  },
  error => {
    // 处理响应错误
    if (error.response) {
      const { status, data } = error.response;
      // 根据错误状态码处理
      switch (status) {
        case 401:
          // 未授权，清除token并跳转到登录页
          localStorage.removeItem('token');
          localStorage.removeItem('user');
          if (!window.location.pathname.startsWith('/login')) {
            window.location.href = '/login';
          }
          error.message = data?.message || '登录状态已过期，请重新登录';
          break;
        case 403:
          // 禁止访问
          error.message = data?.message || '无权限访问';
          console.error('无权限访问');
          break;
        case 404:
          error.message = data?.message || '请求的资源不存在';
          console.error('请求的资源不存在');
          break;
        case 500:
          error.message = data?.message || '服务器错误';
          console.error('服务器错误');
          break;
        default:
          error.message = data?.message || '请求失败';
          console.error('请求失败');
      }
    } else if (error.request) {
      error.message = '网络错误，请检查网络连接';
      console.error('网络错误，请检查网络连接');
    } else {
      console.error('请求配置错误');
    }
    return Promise.reject(error);
  }
);

export default api;

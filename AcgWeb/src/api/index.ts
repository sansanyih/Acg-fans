
import axios, {
  type AxiosInstance,
  type AxiosRequestConfig,
  type AxiosResponse,
  type InternalAxiosRequestConfig
} from 'axios'
import { AxiosCanceler } from './helper/axiosCancel'

// 通用响应类型
export interface ApiResponse<T = any> {
  code: number
  message: string
  data: T
}

class HttpClient {
  private instance: AxiosInstance
  private canceler: AxiosCanceler

  constructor() {
    this.instance = axios.create({
      baseURL: import.meta.env.VITE_API_BASE_URL,
      timeout: 10000,
      headers: {
        'Content-Type': 'application/json'
      }
    })
    this.canceler = new AxiosCanceler()
    this.setupInterceptors()
  }

  // 设置拦截器
  private setupInterceptors() {
    // 请求拦截器
    this.instance.interceptors.request.use(
      (config: InternalAxiosRequestConfig) => {
        this.canceler.addPending(config)

        // 添加 token
        const token = localStorage.getItem('token')
        if (token && config.headers) {
          config.headers.Authorization = `Bearer ${token}`
        }

        return config
      },
      (error) => Promise.reject(error)
    )

    // 响应拦截器
    this.instance.interceptors.response.use(
      (response: AxiosResponse) => {
        this.canceler.removePending(response.config)
        // 直接返回响应数据
        return response.data
      },
      (error) => {
        if (error.config) {
          this.canceler.removePending(error.config)
        }
        this.handleHttpError(error)
        return Promise.reject(error)
      }
    )
  }

  // HTTP 错误处理
  private handleHttpError(error: any) {
    if (error.response) {
      const { status } = error.response
      switch (status) {
        case 401:
          localStorage.removeItem('token')
          // 跳转到登录页
          window.location.href = '/login'
          break
        case 403:
          console.error('没有权限访问')
          break
        case 404:
          console.error('请求的资源不存在')
          break
        case 500:
          console.error('服务器错误')
          break
        default:
          console.error(`请求错误: ${error.message}`)
      }
    } else if (error.request) {
      console.error('网络错误，请检查网络连接')
    }
  }

  // 通用请求方法
  request<T = any>(config: AxiosRequestConfig): Promise<T> {
    return this.instance.request(config)
  }

  // GET
  get<T = any>(url: string, config?: AxiosRequestConfig): Promise<T> {
    return this.instance.get(url, config)
  }

  // POST
  post<T = any>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> {
    return this.instance.post(url, data, config)
  }

  // PUT
  put<T = any>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> {
    return this.instance.put(url, data, config)
  }

  // DELETE
  delete<T = any>(url: string, config?: AxiosRequestConfig): Promise<T> {
    return this.instance.delete(url, config)
  }

  // 取消所有请求
  cancelAll() {
    this.canceler.removeAllPending()
  }

  // 重置
  reset() {
    this.canceler.reset()
  }
}

// 导出单例实例
export const http = new HttpClient()

// 导出请求方法（方便直接使用）
export const request = {
  get: http.get.bind(http),
  post: http.post.bind(http),
  put: http.put.bind(http),
  delete: http.delete.bind(http)
}

export default http

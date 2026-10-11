import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
//vue router
import router from './routers/index'

//scss
import "@/styles/reset.scss";
// CSS common style sheet
import "@/styles/common.scss";

//element plus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

const app = createApp(App)

app
 .use(createPinia())
 .use(router)
 .use(ElementPlus)
 .mount('#app');

//注册element Icon组件
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}

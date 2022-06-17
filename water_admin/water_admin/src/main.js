/*
 * @Author: your name
 * @Date: 2022-03-07 13:57:59
 * @LastEditTime: 2022-04-25 16:48:53
 * @LastEditors: Tashany
 * @Description: 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 * @FilePath: \water_admin\src\main.js
 */
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css'
import VueCookies from 'vue-cookies'
//引入全局的样式文件
import './assets/css/global.less'
//引入字体文件
import './assets/font/iconfont.css'
// 引入datav
import dataV from '@jiaminghi/data-view'
import SocketService from '@/tools/SocketService'
// 对服务端进行websocket的连接
SocketService.Instance.connect()
//其他组件通过this.$socket
Vue.prototype.$socket = SocketService.Instance
Vue.use(dataV)
Vue.use(VueCookies)
Vue.config.productionTip = false
Vue.use(ElementUI)
// 将全局的echarts对象挂载到Vue原型对象上
// 别的组件 this.$echarts
Vue.prototype.$echarts = window.echarts

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')

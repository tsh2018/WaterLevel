/*
 * @Author: Tashany
 * @Date: 2022-03-07 13:57:59
 * @LastEditors: Tashany
 * @LastEditTime: 2022-05-16 13:32:47
 * @Description: file content
 * @FilePath: \water_admin\src\router\index.js
 */
import axios from 'axios'
import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

Vue.use(VueRouter)

const routes = [
    {
        path: '/map',
        component: () => import('../components/page/map.vue')
    },

    {
        path: '/',
        alias: '/homeIndex',
        component: () => import('../views/index.vue')

    },
    {
        path: '/login',
        component: () => import('../components/login.vue')
    },
    {
        path: '/error',
        component: () => import('../components/error.vue')
    },
    {
        path: '/home',
        name: 'Home',
        component: Home,
        redirect: '/Individual',
        children: [
            {
                path: '/Individual',
                component: () => import('../components/start.vue')
            },
            {
                path: '/IndividualMyInfo',
                component: () => import('../components/myInfo.vue')
            },
            {
                path: '/IndividualEditInfo',
                component: () => import(/* webpackChunkName: "about" */ '../components/editInfo.vue')
            },
            {
                path: '/Person',
                redirect: '/PersonCheckManager'
            },
            {
                path: '/PersonCheckManager',
                component: () => import('../components/checkManager.vue')
            },
            {
                path: '/PersoncheckUser',
                component: () => import('../components/checkUser.vue')
            },
            {
                path: '/dataAlarmSet',
                component: () => import('../components/alarmSet.vue')
            },
            {
                path: '/dataAlarmTime',
                component: () => import('../components/alarmTime.vue')
            },
            {
                path: '/dataEditAlarmTimes',
                component: () => import('../components/editAlarmTimes.vue')
            },
            {
                path: '/dataEditNowAlarmLevel',
                component: () => import('../components/editNowAlarmLevel.vue')

            },
            {
                path: '/editInitData',
                component: () => import('../components/editInitData.vue')

            },
            {
                path: '/detectPoint',
                component: () => import('../components/detectPoint.vue')
            }

            // {
            //   path: '/about',
            //   name: 'About',
            //   // route level code-splitting
            //   // this generates a separate chunk (about.[hash].js) for this route
            //   // which is lazy-loaded when the route is visited.
            //   component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
            // }
        ]
    },
    {
        path: '/speedPage',
        component: () => import('../components/page/speed.vue')
    }
]
const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

// 进行跳转前都要这些关于taken的逻辑
router.beforeEach(async (to, from, next) => {
    console.log(to)
    if (to.path.startsWith('/login')) {
        window.localStorage.removeItem("magName")
        window.localStorage.removeItem("magImg")
        next()
    } else if (to.path == '/' || to.path == '/homeIndex') {
        next()
    }
    else {
        let magName = window.localStorage.getItem("magName")
        let magImg = window.localStorage.getItem("magImg")
        let token = window.localStorage.getItem("token")
        if ((!magName) && (!magImg)) {
            next({ path: '/login' })
        }
        else {
            //验证token合法性
            axios({
                url: 'http://localhost:8081/checkToken',
                method: 'get',
                headers: {
                    token
                }
            }).then((response) => {
                console.log(response.data)
                if (!response.data) {
                    console.log('校验失败')
                    next({ path: '/error' })
                }
            }).catch((err) => {
                console.log(err)
            })
            next()
        }
    }
})

export default router

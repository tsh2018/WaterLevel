/*
 * @Author: Tashany
 * @Date: 2022-03-15 20:14:36
 * @LastEditors: Tashany
 * @LastEditTime: 2022-03-17 20:41:40
 * @Description: file content
 * @FilePath: \water_admin\src\tools\request.js
 */

import { Message } from 'element-ui';
import router from '../router';
import axios from 'axios'

const service = axios.create({
    baseURL: '/api',

})


//对于响应数据
service.interceptors.response.use(response => {
  
    if (response.status === 200) {
       
        if( response.data.code === '401'){
            
            Message.error( response.data.msg);
            router.push('/login');
        }
        return response.data;
        // if( response.data.code === 401){
        //     Message.error('您没有权限');
        // }
        // if( response.data.code === 500){
        //     Message.error(response.data.msg);
        // }
        // else return response.data;
    } else {
        Promise.reject();
    }
}, err => {

    return Promise.reject(err)
})

export default service
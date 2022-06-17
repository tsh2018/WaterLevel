/*
 * @Author: Tashany
 * @Date: 2022-03-07 13:57:59
 * @LastEditors: Tashany
 * @LastEditTime: 2022-04-24 20:59:59
 * @Description: file content
 * @FilePath: \water_admin\src\store\index.js
 */
import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
      magName:'',
      magImg:'',
      list:'',
    },
    getters:{
      getName(state){
         return state.magName?state.magName:JOSN.parse(localStorage.getItem('mag').magName)
      },
      getImg(state){
        return state.magImg?state.magImg:JOSN.parse(localStorage.getItem('mag').magImg)
     },
     list:(state)=>{
       return  state.list//?state.list:JOSN.parse(localStorage.getItem('list'))
     }
    },
    mutations: {
        setName(state,name){
            state.magName = name;
            localStorage.setItem('magName',name)
        },
        setImg(state,img){
            state.magImg = img;
            localStorage.setItem('magImg',img)
        },
        setList(state,list){
          console.log('haibian')
          state.list = list;
          //localStorage.setItem('list',list)
      }
    },
    actions: {},
    modules: {}
})

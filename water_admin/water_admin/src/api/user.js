/*
 * @Author: Tashany
 * @Date: 2022-03-15 20:17:47
 * @LastEditors: Tashany
 * @LastEditTime: 2022-05-16 15:46:09
 * @Description: file content
 * @FilePath: \water_admin\src\api\user.js
 */

import request from '@/tools/request.js'

export function reqgetinfo(data){
    return request({
        url:'/manager/getManagerInfo',
        method:'get',
        params:{
            magName:data
        }
    })
}

export function updateSubmit(ruleForm){
    return request({
        url:'/manager/updateManagerInfo',
        method:'post',
        headers: {
            token: localStorage.getItem("token")
        },
        data:  ruleForm
        
    })
}
export function getManagerList(){
    return request({
        url:'/manager/managerlist',
        method:'get',
        headers: {
            token: localStorage.getItem("token")
        },
    })
}
export function deleteManager(magId){
    return request({
        url:'/manager/deleteManager',
        method:'post',
        headers: {
            token: localStorage.getItem("token")
        },
        data:{magId}
    })
}
export function getUserList(){
    return request({
        url:'/manager/userlist',
        method:'get',
        headers: {
            token: localStorage.getItem("token")
        },
    })
}
export function deleteUser(userId){
    return request({
        url:'/manager/deleteUser',
        method:'post',
        headers: {
            token: localStorage.getItem("token")
        },
        data:{userId}
    })
}
export function getAlarmInfo(){
    return request({
        url:'/manager/getAlarmInfo',
        method:'get',
        headers: {
            token: localStorage.getItem("token")
        },
    })
}
export function setAlarmHigh(data){
    return request({
        url:'/manager/setAlarmHigh',
        method:'post',
        headers: {
            token: localStorage.getItem("token")
        },
        data:{
            alarmLevel:data.alarmLevel,
            alarmHigh:data.alarmHigh
        }
    })
}
export function setAlarmTime(data){
    return request({
        url:'/manager/setAlarmTime',
        method:'post',
        headers: {
            token: localStorage.getItem("token")
        },
        data:{
            alarmLevel:data.alarmLevel,
            alarmTime:data.alarmTime
        }
    })
}
export function setAlarmSum(data){
    return request({
        url:'/manager/setAlarmSum',
        method:'post',
        headers: {
            token: localStorage.getItem("token")
        },
        data:{
            alarmLevel:data.alarmLevel,
            alarmSum:data.alarmSum
        }
    })
}
export function getNowAlarm(){
    return request({
        url:'/manager/getNowAlarm',
        method:'get',
        headers: {
            token: localStorage.getItem("token")
        }
    })
}
export function getLastInfo(){
    return request({
        url:'/manager/getLastInfo',
        method:'get',
        headers: {
            token: localStorage.getItem("token")
        }
    })
}
export function setInitWaterInfo(data){
    return request({
        url:'/manager/setInitWaterInfo',
        method:'post',
        headers: {
            token: localStorage.getItem("token")
        },
        data:{
            waterSpeed:data.waterSpeed,
            waterHeight:data.waterHeight,
            waterAcceleration:data.waterAcceleration,
            id:data.id
        }
    })
}
export function getPoint(){
    return request({
        url:'/manager/getDetectPoint',
        method:'get',
        headers: {
            token: localStorage.getItem("token")
        }
    })
}
export function UpdatePoint(data){
    return request({
        url:'/manager/UpdatePoint',
        method:'post',
        headers: {
            token: localStorage.getItem("token")
        },
        data:{
            detectPoint:data.detectPoint,
            allPoint:data.allPoint
        }
    })
}
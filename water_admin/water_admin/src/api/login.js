/*
 * @Author: Tashany
 * @Date: 2022-03-17 17:17:25
 * @LastEditors: Tashany
 * @LastEditTime: 2022-03-18 14:37:32
 * @Description: file content
 * @FilePath: \water_admin\src\api\login.js
 */
import request from '@/tools/request.js'

export function reqgetLogin(magName, magPassward){
    return request({
        url:'/manager/magLogin',
        method:'post',
        data:{
            magName, 
            magPassward
        }
    
    })
}
export function reqgetRegister(ruleForm){
    return request({
        url:'/manager/magRegister',
        method:'post',
        data:{
            magName:ruleForm.magName,
            magPassward:ruleForm.magPassward,
            magEmail: ruleForm.magEmail,
            magNum: ruleForm.magNum,
            magRoot: ruleForm.magRoot
        }
    
    })
}
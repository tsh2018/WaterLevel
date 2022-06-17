/*
 * @Author: Tashany
 * @Date: 2022-04-25 15:56:50
 * @LastEditors: Tashany
 * @LastEditTime: 2022-05-10 17:53:10
 * @Description: file content
 * @FilePath: \water_admin\src\tools\SocketService.js
 */
export default class SocketService {
    /**
     * 单例
     */
    static instance = null;
    static get Instance() {
        if (!this.instance) {
            this.instance = new SocketService()
        }
        return this.instance
    }

    //和的服务端连接的socket对象
    ws = null
    // 存储回调函数
    callBackMapping = {}

    //连接服务器的方法
    connect() {
        //连接服务器
        if (!window.WebSocket) {
            return console.log("您的服务器不支持websocket")
        }
        this.ws = new WebSocket('ws://localhost:8081/ws')

        this.ws.onopen = () =>{ //连接成功回调方法
            console.log("连接成功....")
        };
        this.ws.onclose = () => {
            console.log("连接失败.....") 
        }
        this.ws.onmessage = e => {
            console.log('接收到来自服务器的消息111：');
            this.callBackMapping["speed"].call(this, e.data)
            this.callBackMapping["acceleration"].call(this, e.data)
            this.callBackMapping["alarmInfo"].call(this, e.data)
            this.callBackMapping["height"].call(this, e.data)
            this.callBackMapping["contrast"].call(this, e.data)
            this.callBackMapping["dataInfo"].call(this, e.data)
            this.callBackMapping["alarmTime"].call(this, e.data)
            this.callBackMapping["contrast"].call(this, e.data)
            this.callBackMapping["waterSpeed"].call(this, e.data)
        }   
    }

     // 回调函数的注册
  registerCallBack (socketType,callBack) {
    console.log('回调函数的注册', callBack)
    this.callBackMapping[socketType] = callBack
    console.log(callBack)
  }
    // 取消某一个回调函数
  unRegisterCallBack(socketType){
    console.log('取消某一个回调函数', socketType)
    this.callBackMapping[socketType] = null
  }
}
<!--
 * @Author: Tashany
 * @Date: 2022-04-18 11:53:55
 * @LastEditors: Tashany
 * @LastEditTime: 2022-05-09 20:02:23
 * @Description: file content
 * @FilePath: \water_admin\src\components\page\alarmTime.vue
-->
<template>
    <div>
        <div class="title" style="text-align: left;">
            <span>▎预测到达各警戒等级的时间</span>
        </div>
        <dv-scroll-ranking-board :config="config" style="width:500px;height:300px; " />
    </div>

</template>

<script>
    export default {
        data() {
            return {
                config: {
                    data: [{
                            name: '周口',
                            value: 55
                        },
                        {
                            name: '南阳',
                            value: 120
                        },
                        {
                            name: '西峡',
                            value: 78
                        },
                        {
                            name: '驻马店',
                            value: 66
                        }
                    ]
                }
            }
        },
        created() {
            // 再组件创建完成时创建回调函数
            this.$socket.registerCallBack("alarmTime", this.getData)
        },
        destroyed() {
            // 销毁时取消回调函数
            this.$socket.unRegisterCallBack(this.getData)
        },
        methods: {
            getData(ret) {
                this.allData = JSON.parse(ret)
                console.log(this.allData)
                let arr = [];
                let length = this.allData.predictTime.length;
                for (let i = 0; i < length; i++) {
                    let item = {};
                    console.log(this.allData.predictTime[i])
                    item = {
                        name: (i + 1) + '级警戒',
                        value: this.allData.predictTime[i]
                    }
                    arr.push(item)
                }
                arr = arr.sort(this.compare("value"))
                console.log("arr")
                console.log(arr)
                this.config = {
                    data: arr,
                    carousel: 'page',
                    unit: '秒'
                }
            },
            compare(property) {
                return function (obj1, obj2) {
                    var value1 = obj1[property];
                    var value2 = obj2[property];
                    return value1 - value2; // 升序
                }
            }
        }
    }
</script>

<style>

</style>
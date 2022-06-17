<!--
 * @Author: Tashany
 * @Date: 2022-04-14 14:21:01
 * @LastEditors: Tashany
 * @LastEditTime: 2022-05-10 21:09:43
 * @Description: file content
 * @FilePath: \water_admin\src\components\page\height.vue
-->
<template>
    <div class="com-container">
        <dv-digital-flop :config="config"/>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                // 返回服务器的数据
                allData: null,
                config: {
                    number: [0],
                    content: '{nt}mm'

                },
               
            }
        },
        created() {
            // 再组件创建完成时创建回调函数
            this.$socket.registerCallBack("height", this.getData)
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
                let length = this.allData.alarmLevel.length;
                const a = this.allData.alarmLevel[length - 1];
                const temp = []
                temp.push(parseInt(a))

                this.config = {
                    number: temp,
                    content: '预警等级:\n{nt}级',
                    rowGap:50
                }
            }
        },
    }
</script>

<style lang="less" scoped>

</style>
<!--
 * @Author: Tashany
 * @Date: 2022-04-14 15:36:04
 * @LastEditors: Tashany
 * @LastEditTime: 2022-05-10 21:02:24
 * @Description: file content
 * @FilePath: \water_admin\src\components\page\waterSpeed.vue
-->
<template>
    <div class="com-container">
        <dv-digital-flop :config="config1" style="height:50px;" />
        <dv-digital-flop :config="config2" />
    </div>
</template>

<script>
    import {
        getConnect
    } from '@/api/user.js'
    export default {
        data() {
            return {
                // 返回服务器的数据
                allData: null,
                config1: {
                    number: [0],
                    content: '{nt}mm'

                },
                config2: {
                    number: [0],
                    content: '{nt}mm'

                }
            }
        },
        created() {
            // 再组件创建完成时创建回调函数
            this.$socket.registerCallBack("waterSpeed", this.getData)
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
                let length = this.allData.waterSpeed.length;
                const b = this.allData.waterHeight[length - 1]
                const a = this.allData.waterSpeed[length - 1];
                console.log('aaa' + b)
                const item = []
                item.push(parseInt(b))
                const temp = []
                temp.push(parseInt(a))

                this.config1 = {
                    number: item,
                    content: '水位高度:{nt}mm',
                }
                this.config2 = {
                    number: temp,
                    content: '水位速度:{nt}mm/s'
                }


            }
        },
    }
</script>

<style lang="less" scoped>

</style>
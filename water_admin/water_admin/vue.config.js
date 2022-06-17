/*
 * @Author: Tashany
 * @Date: 2022-03-15 19:52:07
 * @LastEditors: Tashany
 * @LastEditTime: 2022-03-15 21:28:46
 * @Description: file content
 * @FilePath: \water_admin\vue.config.js
 */
module.exports = {
    assetsDir: 'static',
    productionSourceMap: false,
    lintOnSave: false,
    devServer: {
        overlay: {
            warning: false,
            errors: false
        },
        port: 8082,
        proxy: {
            '/api': {
                target: 'http://127.0.0.1:8081',
                changeOrigin: true,
                pathRewrite: {
                    '^/api': '/api'
                }
            }
        }
    }


};

const { defineConfig } = require("@vue/cli-service");
module.exports = defineConfig({
  transpileDependencies: true,

  //proxy URL은 API의 baseURL 이므로
  //axios.defaults.baseURL는 주석처리 해야함
  devServer: {
    port: 8090,
    proxy: "http://localhost",
  },
});

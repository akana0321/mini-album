import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from './store'
import axios from './apis/axiosConfig';

// 처음 나오는 컴포넌트 지정
createApp(App).use(store).use(router).mount("#app");

// const obj = {
//     key1: "value1",
//     key2: "value2",
// };

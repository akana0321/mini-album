import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    component: () => import(/* webpackChunkName: "about" */ '../views/Login.vue')
  },
  {
    path: '/Login',
    component: () => import(/* webpackChunkName: "about" */ '../views/Login.vue')
  },
  {
    path: '/WriteForm',
    component: () => import(/* webpackChunkName: "about" */ '../views/WriteForm.vue')
  },
  // {
  //   path: '/detail/updateForm',
  //   component: () => import(/* webpackChunkName: "about" */ '../views/UpdateForm.vue')
  // },
  // {
  //   path: '/detail',
  //   component: () => import(/* webpackChunkName: "about" */ '../views/Detail.vue')
  // },
  {
    path: '/ListBoard',
    component: () => import(/* webpackChunkName: "about" */ '../views/ListBoard')
  },
  {
    path: "/detail",
    component: () => import(/* webpackChunkName: "about" */ "../views/DetailBoard"),
  },
  {
    path: "/detail/updateForm",
    component: () => import(/* webpackChunkName: "about" */ "../views/UpdateBoard"),
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
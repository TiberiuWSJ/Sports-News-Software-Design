import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/stories',
      name: 'stories',
      component: () => import('../views/StoriesView.vue')
    },
    {
      path: '/story/:id',
      name: 'StoryDetail',
      component: () => import('../views/StoryDetailView.vue'),
      props: true
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/LoginView.vue')
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('../views/SignupView.vue')
    },
    {
      path: '/',
      redirect: '/login'
    }
  ]
})

export default router

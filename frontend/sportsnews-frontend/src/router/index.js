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
    },

    {
      path: '/profile',
      name: 'profile',
      component: () => import('../views/ProfileView.vue')
    },

    { path: '/adminView',
      name: 'admin',
      component: () => import('../views/AdminView.vue'),
      meta: { requiresModerator: true }
    },
    { path: '/admin/users', name: 'ManageUsers', component: () => import ('../views/ManageUsersView.vue'), meta: { requiresModerator: true } },
    { path: '/admin/stories', name: 'ManageStories', component: () => import ('../views/ManageStoriesView.vue'), meta: { requiresModerator: true } }
  ]
})


router.beforeEach((to, from, next) => {
  const isModerator = JSON.parse(localStorage.getItem('isModerator'));
  if (to.matched.some(record => record.meta.requiresModerator)) {
    if (isModerator) {
      next();
    } else {
      next('/login'); // or redirect to a forbidden page
    }
  } else {
    next();
  }
});

export default router

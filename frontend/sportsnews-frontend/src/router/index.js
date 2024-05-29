import { createRouter, createWebHistory } from 'vue-router';
import axios from 'axios';

const routes = [
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
  {
    path: '/adminView',
    name: 'admin',
    component: () => import('../views/AdminView.vue'),
    meta: { requiresModerator: true }
  },
  {
    path: '/admin/users',
    name: 'ManageUsers',
    component: () => import('../views/ManageUsersView.vue'),
    meta: { requiresModerator: true }
  },
  {
    path: '/admin/stories',
    name: 'ManageStories',
    component: () => import('../views/ManageStoriesView.vue'),
    meta: { requiresModerator: true }
  },
  {
    path: '/journalistView',
    name: 'journalist',
    component: () => import('../views/JournalistView.vue'),
    meta: { requiresJournalist: true }
  },
  {
    path: '/journalist/stories',
    name: 'JournalistStories',
    component: () => import('../views/ManageStoriesJView.vue'),
    meta: { requiresJournalist: true }
  }
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
});

router.beforeEach((to, from, next) => {
  const isModerator = JSON.parse(localStorage.getItem('isModerator'));
  const isJournalist = JSON.parse(localStorage.getItem('isJournalist'));

  if (to.matched.some(record => record.meta.requiresModerator)) {
    if (isModerator) {
      next();
    } else {
      next('/login');
    }
  } else if (to.matched.some(record => record.meta.requiresJournalist)) {
    if (isJournalist) {
      next();
    } else {
      next('/login');
    }
  } else {
    next();
  }
});

export default router;

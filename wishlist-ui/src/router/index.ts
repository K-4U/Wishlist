/**
 * router/index.ts
 *
 * Automatic routes for `./src/pages/*.vue`
 */

// Composables
import {createRouter, createWebHistory} from 'vue-router/auto'
import {routes} from 'vue-router/auto-routes'
import {setupLayouts} from 'virtual:generated-layouts'
import {useAuthStore} from "@/stores";

declare module 'vue-router' {
  interface RouteMeta {
    requiresAuth?: boolean
    title?: string
    canGoBack?: boolean
    previousPage?: string
  }
}

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: setupLayouts(routes),
})

// Workaround for https://github.com/vitejs/vite/issues/11804
router.onError((err, to) => {
  if (err?.message?.includes?.('Failed to fetch dynamically imported module')) {
    if (!localStorage.getItem('vuetify:dynamic-reload')) {
      console.log('Reloading page to fix dynamic import error')
      localStorage.setItem('vuetify:dynamic-reload', 'true')
      location.assign(to.fullPath)
    } else {
      console.error('Dynamic import error, reloading page did not fix it', err)
    }
  } else {
    console.error(err)
  }
})

router.isReady().then(() => {
  localStorage.removeItem('vuetify:dynamic-reload')
})

router.beforeEach(async (to) => {
  // redirect to login page if not logged in and trying to access a restricted page
  const publicPages = ['/login'];
  const authRequired = !publicPages.includes(to.path);
  const auth = useAuthStore();

  if (authRequired && !auth.isLoggedIn) {
    auth.returnUrl = to.fullPath;
    return '/login';
  }
});

router.afterEach((to, from) => {
  if (to.meta.canGoBack) {
    to.meta.previousPage = from.fullPath;
  }
  if (to.meta.title) {
    document.title = 'Wishlist :: ' + to.meta.title;
  } else {
    document.title = 'Wishlist';
  }
  console.dir(to.meta);
})

export default router

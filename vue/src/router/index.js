import { createRouter as createRouter, createWebHistory } from 'vue-router'
import { useStore } from 'vuex'

// Import components
import HomeView from '../views/HomeView.vue';
import AboutUsView from '@/views/AboutUsView.vue';
import GalleryView from '@/views/GalleryView.vue';
import LoginView from '../views/LoginView.vue';
import LogoutView from '../views/LogoutView.vue';
import RegisterView from '../views/RegisterView.vue';
import CustomOrderView from '../views/CustomOrderView.vue';
import StandardCakeView from '../views/StandardCakeView.vue';
import PlacedOrdersView from '../views/PlacedOrdersView.vue'; 
import OrderView from '../views/OrderView.vue';
import DefineOptionsView from '../views/DefineOptionsView.vue';
/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */
const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
    meta: {
      requiresAuth: false
    } 
  },
  { 
    path: '/about', 
    name: 'AboutUs', 
    component: AboutUsView 
  },
  { 
    path: '/gallery', 
    name: 'Gallery', 
    component: GalleryView 
  },
  { 
    path: '/Menu',
    name: "Menu", 
  component: DefineOptionsView, 
  meta: { 
    requiresAuth: true
  }
  },
  
  {
    path: "/login",
    name: "login",
    component: LoginView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/logout",
    name: "logout",
    component: LogoutView,
    meta: {
      requiresAuth: false
    }
  },

  {
    path: "/register",
    name: "register",
    component: RegisterView,
    meta: {
      requiresAuth: false
    }
  },

   { 
    path: '/standard-cakes',
  name: "standard-cakes", 
  component: StandardCakeView, 
  meta: { 
    requiresAuth: false
   }
  },
  { 
    path: '/custom-order',
    name: "custom-order", 
  component: CustomOrderView, 
  meta: { 
    requiresAuth: false
   }
  },
  { path: '/orders',
    name: "orders",
    component: OrderView,
    meta: {
      requiresAuth: true 
    }
  },
  { 
    path: '/placed-orders',
    name: "placed-orders", 
  component: PlacedOrdersView, 
  meta: { 
    requiresAuth: true
  }
},

];

// Create the router
const router = createRouter({
  history: createWebHistory(),
  routes: routes
});

router.beforeEach((to) => {

  // Get the Vuex store
  const store = useStore();

  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);
  const token = store.state.token || localStorage.getItem('token');

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    return {name: "login"};
  }
  // Otherwise, do nothing and they'll go to their next destination
});

export default router;

<template>
  <!--
  <div id="capstone-app">
    <div id="nav">
      <router-link v-bind:to="{ name: 'home' }">Home</router-link>
      <router-link v-bind:to="{ name: 'AboutUs' }">About Us</router-link>
      <router-link v-bind:to="{ name: 'Gallery' }">Gallery</router-link>
            <router-link v-bind:to="{ name: 'Menu' }">Menu</router-link>
      <router-link v-bind:to="{ name: 'standard-cakes' }">Standard Cakes</router-link>
      <router-link v-bind:to="{ name: 'cakes-options' }">Cake Options</router-link>
      <router-link v-bind:to="{ name: 'placed-orders' }">Orders</router-link>
      <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
    </div>
  </div>  -->
  <div id="app">
    <!-- Full-width Banner with Navigation -->
    <header id="banner">
      <nav id="banner-nav">
        <div>
          <router-link :to="{ name: 'home' }">Home</router-link>
          <span></span>
        </div>

        <div>
          <router-link :to="{ name: 'AboutUs' }">About Us</router-link>
          <span></span>
        </div>
        <div>
          <router-link :to="{ name: 'Gallery' }">Gallery</router-link>
          <span></span>
        </div>
        <div v-if="hasToken()">
          <router-link :to="{ name: 'Menu' }">Menu</router-link>
          <span></span>
        </div>
        <div>
          <router-link :to="{ name: 'standard-cakes' }">Standard Cakes</router-link>
          <span></span>
        </div>
        <div>
          <router-link :to="{ name: 'custom-order' }">Custom Cake</router-link>
          <span></span>
        </div>
        <div v-if="hasToken()">
          <router-link  :to="{ name: 'placed-orders' }">Orders</router-link>
          <span></span>
        </div>
        <div v-if="false">
          <router-link :to="{ name: 'orders' }">Cart</router-link>
          <span></span>
        </div>
        <div v-if="!hasToken()">
          <router-link :to="{ name: 'login' }">Login</router-link>
          <span></span>
        </div>
        <div v-if="hasToken()">
          <router-link :to="{ name: 'logout' }">Logout</router-link>
          <span></span>
        </div>
      </nav>
    </header>

    <!-- Centered Main Content -->
    <div id="app-container">
      <main id="main-content">
        <router-view />
      </main>
    </div>
  </div>

</template>

<script>
export default {
  methods: {
    hasToken() {
      return this.$store.state.token !== '';
    }
  },
  created(){
    document.title = "Frost and Found";
  }
}
</script>

<style scoped>
/* Banner spans */
#banner {
  width: 100%;
  background-color: #f8f8f8;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 20px 0;
  margin-bottom: 20px;
  text-align: center;
}

/* Navigation inside the banner */
#banner-nav {
  display: flex;
  justify-content: center;
  gap: 1rem;
  flex-wrap: wrap;
  font-size: 1rem;
}

#banner-nav a {
  text-decoration: none;
  color: #f43f7b;
  font-weight: bold;
  transition: color 0.3s;
}

#banner-nav a:hover {
  text-decoration: underline;
  color: #d9235b;
}

/* Main Container for centered content */
#app-container {
  width: 100%;
  display: flex;
  justify-content: center;
}

#main-content {
  width: 100%;
  max-width: 600px;
  background: #f841a0;
  padding: 20px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  box-sizing: border-box;
  text-align: center;
}

/* Global body reset */
body {
  margin: 0;
  padding: 0;
}
</style>

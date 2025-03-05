<template>
  <div class="home">
    <!-- <h1>Home</h1>  -->
    <!-- <p>You must be authenticated to see this</p>  -->
    <div class="logo-container">
      <img :src="logoImage" alt="Logo" class="logo-image" />
    </div> 

    <div>
      <GalleryView />
    </div>

    <div class="side-by-side-images">
      <router-link to="/standard-cakes" class="image-item">
        <img :src="viewCakesImage" alt="View standard Cakes" class="side-image" />
        <button class="order-button">Order standard Cakes</button>
      </router-link>

      <router-link to="/custom-order" class="image-item">
        <img :src="customCakeImage" alt="Order Custom Cakes" class="side-image" />
        <button class="order-button">Order Custom Cakes</button>
      </router-link>
    </div>  
  </div>
</template>

<script>
import GalleryView from './GalleryView.vue';

// Correct way to import images in Vue
import logoImage from '@/assets/NewLogo.png';
import viewCakesImage from '@/assets/View_cakes_image.jpg';
import customCakeImage from '@/assets/custom_cake_image.jpg';

export default {
  components: {
    GalleryView, // Register the GalleryView component
  },
  data() {
    return {
      logoImage, // Now correctly referenced
      viewCakesImage,
      customCakeImage,
    };
  },
};
</script>

<style scoped>
.home {
  background-color: #fbf8f2;
  text-align: center;
  padding: 20px;
  border-radius: 8px;
}

.home > div:nth-child(2) {
  margin-top: -40px; 
}

.logo-container {
  margin-bottom: 0px;
  display: flex;
  flex-direction: column; 
  align-items: center; 
}

.logo-image {
  width: 340px;
  height: auto;
  margin-bottom: 0px; 
}

.side-by-side-images {
  display: flex;
  justify-content: center;
  gap: 20px;
}

.image-item {
  position: relative;
  text-decoration: none; /* Prevent default underline for links */
}

.side-image {
  width: 260px;
  height: 200px;
  object-fit: cover;
}

.order-button {
  position: absolute;
  bottom: 10px;
  left: 50%;
  transform: translateX(-50%);
  padding: 10px 20px;
  font-size: 16px;
  background-color: #f05656;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.order-button:hover {
  background-color: #d13c3c;
}
</style>

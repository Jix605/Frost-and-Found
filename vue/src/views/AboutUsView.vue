<template>
  
    <about-us />
    
    <!-- <div class="contact-info">
      <h2>Get in Touch</h2>
      <p>If you're interested in placing an order or have any questions, feel free to contact us.</p>
      <router-link to="/contact" class="contact-link">Contact Us</router-link>
    </div>
    -->
</template>

<script>
import AboutUs from "../components/AboutUs.vue";

export default {
  components: {
    AboutUs
  }
};
</script>

<style scoped>
.about-us {
  font-family: Arial, sans-serif;
  padding: 20px;
}

.about-header {
  text-align: center;
  margin-bottom: 30px;
}

.about-header h1 {
  font-size: 36px;
  font-weight: bold;
}

.about-header p {
  font-size: 18px;
  color: #555;
}

.about-content {
  display: flex;
  justify-content: space-between;
  margin-bottom: 40px;
}

.about-text {
  flex: 1;
  margin-right: 20px;
}

.about-text p {
  font-size: 16px;
  line-height: 1.6;
  color: #333;
}

.about-image {
  flex: 1;
  text-align: center;
}

.about-image img {
  width: 100%;
  height: auto;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

/*
.contact-info {
  text-align: center;
  margin-top: 40px;
}

.contact-info h2 {
  font-size: 24px;
  color: #f05656;
}

.contact-info .contact-link {
  font-size: 18px;
  color: #f05656;
  text-decoration: none;
  border: 2px solid #f05656;
  padding: 10px 20px;
  border-radius: 5px;
}

.contact-info .contact-link:hover {
  background-color: #f05656;
  color: white;
}
*/
</style>
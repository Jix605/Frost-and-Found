<template>
  <!-- Container that can be customized via the "containerClass" prop -->
  <div :class="['cake-summary-container', containerClass]">
    <h2 class="text-xl font-bold mb-4 text-center">Our Standard Cakes</h2>
    <div v-for="cake in cakes" :key="cake.id" class="cake-option" :class="{ unavailable: !cake.available }">
      <div v-if="!hasToken() && cake.available === true || hasToken()">
        <div class="image-row">
          <img :src="images[cake.name]" :alt="cake.name" class="cake-image" />
          <!-- Toggle Switch on the right side of the image -->
          <cake-availability v-if="hasToken()" :available="cake.available" :standardCake="cake"
            @toggle="toggleAvailability(cake, $event)" />
        </div>

        <div class="cake-info">
          <h3 class="cake-title">{{ cake.name }}</h3>
          <p class="cake-description">{{ cake.description }}</p>
          <!--<ul class="cake-details">
          <li><strong>Style:</strong> {{ cake.style }}</li>
          <li><strong>Size:</strong> {{ cake.size }}</li>
          <li><strong>Flavor:</strong> {{ cake.flavor }}</li>
          <li><strong>Frosting:</strong> {{ cake.frosting }}</li>
          <li><strong>Filling:</strong> {{ cake.filling }}</li>
          <li><strong>Extras:</strong> {{ cake.extras }}</li>
        </ul>-->
          <p class="cake-price">${{ cake.price.toFixed(2) }}</p>
          <!-- Centered Order Button -->
          <button class="order-button" @click="orderStandardCake(cake)" :disabled="!cake.available">
            Order
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import CakeService from '../services/CakeService';
import VanillaDream from '@/assets/VanillaDream.jpg';
import ChocoLuxe from '@/assets/ChocoLuxe.jpg';
import VelvetBliss from '@/assets/VelvetBliss.png';
import MarbleDelight from '@/assets/MarbleDelight.webp';
import SprinkleParty from '@/assets/SprinkleParty.webp';
import CakeAvailability from './CakeAvailability.vue';

export default {
  name: 'StandardCakeSummary',
  components: {
    CakeAvailability,
  },
  props: {
    containerClass: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      cakes: [],
      images: {
        "Vanilla Dream": VanillaDream,
        "Choco Luxe": ChocoLuxe,
        "Velvet Bliss": VelvetBliss,
        "Marble Delight": MarbleDelight,
        "Sprinkle Party": SprinkleParty,
      },
    };
  },
  methods: {
    hasToken() {
      return this.$store.state.token !== '';
    },
    getCakes() {
      CakeService.list().then((list) => {
        // Ensure each cake has an "available" property; default to true if missing
        this.cakes = list.data.map((cake) => ({
          ...cake,
          available: cake.available !== undefined ? cake.available : true,
        }));
      });
    },
    orderStandardCake(cake) {
      this.$emit('orderClicked', cake);
    },
    toggleAvailability(cake, newStatus) {
      cake.available = newStatus;

      CakeService.updateCakeById(cake.id, cake);
    },
  },
  created() {
    this.getCakes();
  },
};
</script>

<style scoped>
/* Default container styling for the cake summary component */
.cake-summary-container {
  max-width: 500px;
  margin: auto;
  padding: 20px;
  background: #f9f9f9;
  border-radius: 8px;
}

/* Cake Card Styles */
.cake-option {
  margin-bottom: 15px;
  padding: 15px;
  border-radius: 8px;
  transition: transform 0.2s ease-in-out;
}

.cake-option:hover {
  transform: scale(1.02);
}

/* Grey out card if unavailable (but keep toggle clickable) */
.cake-option.unavailable {
  opacity: 0.5;
}

/* Image Row with Toggle */
.image-row {
  display: flex;
  align-items: center;
  justify-content: space-between;
  /* image on left, toggle on right */
  margin-bottom: 10px;
}

.cake-image {
  width: 200px;
  height: 200px;
  object-fit: cover;
  border-radius: 8px;
}

/* Cake Info */
.cake-info {
  text-align: left;
}

.cake-title {
  font-size: 1.125rem;
  font-weight: bold;
  margin-bottom: 5px;
}

.cake-description {
  font-size: 14px;
  color: #000;
  margin-bottom: 8px;
}

.cake-details {
  font-size: 14px;
  color: #000;
  list-style: none;
  padding: 0;
  margin: 0 0 8px 0;
}

.cake-details li {
  margin-bottom: 2px;
}

.cake-price {
  font-size: 16px;
  font-weight: bold;
  margin-top: 4px;
}

/* Centered Order Button */
.order-button {
  display: block;
  margin: 10px auto 0 auto;
  padding: 8px 16px;
  background-color: #28a745;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.order-button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}
</style>

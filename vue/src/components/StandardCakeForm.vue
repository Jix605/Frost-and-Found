<template>
  <div v-if="visible" class="modal-overlay">
    <div class="modal-container">
      <button class="close-button" @click="$emit('close')">X</button>
      <div class="modal-header">
        <h2>Order: {{ cake.name }}</h2>
      </div>
      <form @submit.prevent="submitOrder">
        <!-- Customer Details -->
        <div class="form-group">
          <label for="name">Name:</label>
          <input id="name" type="text" v-model="form.customer.name" required />
        </div>

        <div class="form-group">
          <label for="email">Email:</label>
          <input id="email" type="email" v-model="form.customer.email" required />
        </div>

        <div class="form-group">
          <label for="phone">Phone:</label>
          <input id="phone" type="tel" v-model="form.customer.phoneNumber" required />
        </div>

        <!-- Pick Up/Delivery Date & Time -->
        <div class="form-group">
          <label for="pickupDateTime">Pickup At:</label>
          <input id="pickupDateTime" type="datetime-local" v-model="form.pickupAt" ref="pickupDateTime" required />
        </div>

        <div class="form-group">
          <label for="extras">Choose Your Extras (Optional):</label>
          <select id="extras" v-model="selectedExtras" multiple>
            <option v-for="option in extras" :key="option.name" :value="option.name">
              {{ option.name }} ${{ option.price }}
            </option>
          </select>
        </div>

        <img src="@/assets/PageDivider.png" alt="Page Divider" class="page-divider">

        <div class="form-group" v-show="hasWritingExtra">
          <label for="request">Special Request (Put Icing Writing Message Here):</label>
          <input type="text" id="request" v-model="form.message" />
        </div>

        <!-- Cake Details -->
        <div class="cake-details">
          <h3>Cake Details</h3>
          <p><strong>Name:</strong> {{ standardCake.name }}</p>
          <p><strong>Style:</strong> {{ cake.style }}</p>
          <p><strong>Size:</strong> {{ cake.size }}</p>
          <p><strong>Flavor:</strong> {{ cake.flavor }}</p>
          <p><strong>Frosting:</strong> {{ cake.frosting }}</p>
          <p><strong>Filling:</strong> {{ cake.filling }}</p>
          <p><strong>Description:</strong> {{ standardCake.description }}</p>
          <p><strong>Price:</strong> ${{ standardCake.price.toFixed(2) }}</p>
          <img :src="cakeImage" :alt="standardCake.name" />
        </div>
        <div class="form-actions">
          <button type="submit" class="submit-order-button">Submit Order</button>
        </div>
      </form>

      <div v-if="orderSuccess" class="success-message">
        Thank you, {{ form.name }}! Your order has been submitted.
      </div>
    </div>
  </div>
</template>

<script>
import VanillaDream from '@/assets/VanillaDream.jpg';
import ChocoLuxe from '@/assets/ChocoLuxe.jpg';
import VelvetBliss from '@/assets/VelvetBliss.png';
import MarbleDelight from '@/assets/MarbleDelight.webp';
import SprinkleParty from '@/assets/SprinkleParty.webp';
import CustomService from '../services/CustomService';

export default {
  name: 'StandardCakeForm',
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    cake: {
      type: Object,
      required: true
    },
    standardCake: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      options: [],
      orderSuccess: false,
      selectedExtras: [],
      form: {
        message: "",
        pickupAt: "",
        customer: {
          name: "",
          phoneNumber: "",
          email: ""
        }
      },
      images: {
        "Vanilla Dream": VanillaDream,
        "Choco Luxe": ChocoLuxe,
        "Velvet Bliss": VelvetBliss,
        "Marble Delight": MarbleDelight,
        "Sprinkle Party": SprinkleParty,
      },
    };
  },
  computed: {
    cakeImage() {
      return this.images[this.standardCake.name] || '';
    },
    hasWritingExtra() {
      return this.selectedExtras.find((name) => {
        return name === "Icing Writing";
      });
    },
    extras() {
      return this.options.filter(option => option.available === true && option.category === 'extra');
    }
  },
  methods: {
    getOptions() {
      CustomService.getCakeOptions().then(response => {
        this.options = response.data;
      });
    },
    submitOrder() {
      this.form.cakeDetails = this.cake;
      this.form.cakeDetails.extras = this.selectedExtras;

      CustomService.createCakeOrder(this.form)
        .then(response => {
          if (response.status === 201) {
            this.orderSuccess = true;
          }
        })
        .catch(error => {
          console.error("Order submission failed:", error.response?.data || error.message);
        });

      this.$emit('orderSubmitted', {
        cake: this.cake,
        customer: { ...this.form },
      });
      this.form = { name: '', email: '', phone: '', description: '', pickupDateTime: '' };
      this.$emit('close');
    },
  },
  created() {
    this.getOptions();
  },
  mounted() {
    this.$refs.pickupDateTime.min = new Date().toISOString().slice(0, 16);

    const maxDate = new Date();
    maxDate.setDate(maxDate.getDate() + 14);
    this.$refs.pickupDateTime.max = maxDate.toISOString().slice(0, 16);
  }
};
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-container {
  position: relative;
  background-color: #fff;
  padding: 20px;
  border-radius: 8px;
  width: 90%;
  max-width: 500px;
  max-height: 90vh;
  overflow-y: auto;
  text-align: center;
}

.close-button {
  position: absolute;
  top: 10px;
  right: 10px;
  background: none;
  border: none;
  font-size: 2rem;
  color: #000;
  cursor: pointer;
  z-index: 10;
}

.modal-header {
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 15px;
  text-align: center;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

.form-group input,
.form-group textarea {
  display: block;
  width: 80%;
  margin: 0 auto;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.cake-details {
  margin: 20px 0;
  text-align: center;
}

.cake-details p {
  margin: 5px 0;
}

.cake-details img {
  display: block;
  margin: 10px auto;
  width: 200px;
  height: 200px;
  object-fit: cover;
  border-radius: 8px;
}

.form-actions {
  text-align: center;
}

.submit-order-button {
  padding: 10px 20px;
  background-color: #10b981;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.submit-order-button:hover {
  background-color: #059669;
}

.page-divider {
  width: 390px;
  height: auto;
  display: block;
  margin: 0 auto;
  margin-bottom: px;
}
</style>

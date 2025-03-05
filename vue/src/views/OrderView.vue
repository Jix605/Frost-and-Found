<template>
  <div class="cart-container">
    <h2 class="title">Your Order</h2>

    <div v-if="cart.length > 0">
      <div class="cart-items">
        <div v-for="(cake, index) in cart" :key="index" class="cart-item">
          <img :src="cake.image" :alt="cake.cakeStyle" class="cake-image" />
          <div class="cake-info">
            <h3>{{ cake.cakeStyle }}</h3>
            <p>{{ cake.description }}</p>
            <p class="price">${{ cake.price.toFixed(2) }}</p>
            <button class="remove-btn" @click="removeFromCart(index)">Remove</button>
          </div>
        </div>
      </div>

      <div class="extras-section">
        <h3>Extra Options</h3>
        <div class="extras">
          <label v-for="extra in availableExtras" :key="extra.name">
            <input type="checkbox" v-model="selectedExtras[extra.name]" :value="extra.price"/> {{ extra.name }} (+${{ extra.price.toFixed(2) }})
          </label>
        </div>
      </div>

      <div class="order-summary">
        <h3>Order Summary</h3>
        <p><strong>Total Cakes:</strong> {{ cart.length }}</p>
        <p><strong>Subtotal:</strong> ${{ subtotal.toFixed(2) }}</p>
        <p><strong>Extras:</strong> ${{ extrasTotal.toFixed(2) }}</p>
        <p><strong>Total Price:</strong> ${{ totalPrice.toFixed(2) }}</p>
      </div>

      <div class="order-form">
        <h3>Enter Your Details</h3>
        <form @submit.prevent="placeOrder">
          <label>Name:</label>
          <input type="text" v-model="customer.name" required />

          <label>Phone Number:</label>
          <input type="tel" v-model="customer.phone" required />

          <label>Pickup Date:</label>
          <input type="date" v-model="customer.pickupDate" required />

          <label>Pickup Time:</label>
          <select v-model="customer.pickupTime" required>
            <option disabled value="">Select a time</option>
            <option v-for="time in pickupTimes" :key="time" :value="time">{{ time }}</option>
          </select>

          <button type="submit" class="submit-btn">Place Order</button>
        </form>
      </div>
    </div>

    <p v-else>Your cart is empty.</p>
  </div>
</template>

<script>


export default {
  props: {
    pickupTimes: {
      type: Array,
      default: () => ["10:00 AM", "12:00 PM", "2:00 PM", "4:00 PM", "6:00 PM"]
    },
    availableExtras: {
      type: Array,
      default: () => [
        { name: "Custom Message", price: 3.00 },
        { name: "Extra Toppings", price: 4.00 },
      ],
    },
  },
  data() {
    return {
      cart: JSON.parse(localStorage.getItem("cart")) || [],
      selectedExtras: {}, // Store selected extras as an object
      customer: {
        name: "",
        phone: "",
        pickupDate: "",
        pickupTime: "",
      },
    };
  },
  computed: {
    subtotal() {
      return this.cart.reduce((total, cake) => total + cake.price, 0);
    },
    extrasTotal() {
      let total = 0;
      for (const extra in this.selectedExtras) {
        if (this.selectedExtras[extra]) {
          const foundExtra = this.availableExtras.find(ex => ex.name === extra);
          if (foundExtra) {
            total += foundExtra.price;
          }
        }
      }
      return total;
    },
    totalPrice() {
      return this.subtotal + this.extrasTotal;
    },
  },
  methods: {
    removeFromCart(index) {
      this.cart.splice(index, 1);
      localStorage.setItem("cart", JSON.stringify(this.cart));
    },
    placeOrder() {
      if (!this.customer.name || !this.customer.phone || !this.customer.pickupDate || !this.customer.pickupTime) {
        alert("Please fill in all details before placing the order.");
        return;
      }

      const orderData = {
        cakes: this.cart,
        extras: this.selectedExtras, // Include selected extras
        customer: this.customer,
        total: this.totalPrice,
      };

      alert("Order placed successfully!");
      console.log("Order Data:", orderData);

      this.cart = [];
      localStorage.removeItem("cart");
      this.customer = { name: "", phone: "", pickupDate: "", pickupTime: "" };
      this.selectedExtras = {}; // Reset selected extras
    },
  },
};
</script>


<style scoped>
.cart-container {
  max-width: 800px;
  margin: auto;
  padding: 20px;
  text-align: center;
}

.title {
  font-size: 28px;
  font-weight: bold;
  margin-bottom: 20px;
}

/* Cart Items */
.cart-items {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.cart-item {
  display: flex;
  align-items: center;
  background: #f9f9f9;
  padding: 15px;
  border-radius: 8px;
}

.cake-image {
  width: 100px;
  height: 100px;
  border-radius: 5px;
  margin-right: 15px;
}

.cake-info {
  text-align: left;
  flex-grow: 1;
}

.price {
  font-weight: bold;
  color: #000000;
}

/* Remove Button */
.remove-btn {
  background: #ff0019;
  color: white;
  padding: 5px 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.remove-btn:hover {
  background: #ff0019;
}

/* Extras */
.extras-section {
  margin-top: 20px;
}

.extras label {
  display: block;
  margin: 5px 0;
}

/* Order Summary */
.order-summary {
  margin-top: 20px;
  text-align: left;
  background: #f1f1f1;
  padding: 10px;
  border-radius: 5px;
}

/* Order Form */
.order-form {
  margin-top: 20px;
}

form input, form select {
  width: 100%;
  padding: 8px;
  margin-top: 5px;
  border: 1px solid #ddd;
  border-radius: 5px;
}

.submit-btn {
  background: #fb542a;
  color: white;
  padding: 10px;
  border: none;
  cursor: pointer;
  border-radius: 5px;
  width: 100%;
}

.submit-btn:hover {
  background: #017212;
}
</style>

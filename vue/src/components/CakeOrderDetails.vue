<template>
  <div v-if="visible" class="modal-overlay">
    <div class="modal-container">
      <button class="close-button" @click="$emit('close')">X</button>
      <div class="modal-header">
        <h2>Order Details: {{ orderDetails.cakeDetails.name }}</h2>
      </div>
      <div class="modal-content">
        <ul class="cake-details">
          <li><strong>Style:</strong> {{ orderDetails.cakeDetails.style }}</li>
          <li><strong>Size:</strong> {{ orderDetails.cakeDetails.size }}</li>
          <li><strong>Flavor:</strong> {{ orderDetails.cakeDetails.flavor }}</li>
          <li><strong>Frosting:</strong> {{ orderDetails.cakeDetails.frosting }}</li>
          <li><strong>Filling:</strong> {{ orderDetails.cakeDetails.filling }}</li>
          <li><strong>Extras:</strong></li>
          <li v-for="extra in orderDetails.cakeDetails.extras"> {{ extra.name }}</li>
          <li v-show="hasWritingExtra"><strong>Message:</strong> {{ orderDetails.orderDetails.message }}</li>
          <li><strong>Total Price:</strong> {{ orderDetails.orderDetails.totalPrice }}</li>
          <li><strong>Customer Name:</strong> {{ orderDetails.customer.name }}</li>
          <li><strong>Customer Phone Number:</strong> {{ orderDetails.customer.phoneNumber }}</li>
          <li><strong>Customer Email:</strong> {{ orderDetails.customer.email }}</li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "CakeOrderDetails",
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    orderDetails: {
      type: Object,
      required: true
    },
  },
  computed: {
    hasWritingExtra() {
      return this.orderDetails.cakeDetails.extras.find((obj) => {
          return obj.name === "Icing Writing";
      });
    },
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
  background-color: rgba(0,0,0,0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-container {
  background-color: #fff;
  padding: 20px;
  border-radius: 8px;
  width: 90%;
  max-width: 500px;
  position: relative;
  box-sizing: border-box;
}

.close-button {
  position: absolute;
  top: 10px;
  right: 10px;
  background: none;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
}

.modal-header {
  text-align: center;
  margin-bottom: 20px;
}

.modal-content {
  text-align: left;
}

.cake-details {
  list-style: none;
  padding: 0;
}

.cake-details li {
  margin-bottom: 10px;
}
</style>

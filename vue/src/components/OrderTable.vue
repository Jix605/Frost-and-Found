<template>
  <div class="container">
    <h2 class="title">Pending Cake Orders</h2>

    <div v-if="orders.length > 0">
      <table class="orders-table">
        <thead>
          <tr>
            <th>Order ID</th>
            <th>Cake ID</th>
            <th>Total</th>
            <th>Pickup Date & Time</th>
            <th>Status</th>
            <th>Special Request</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="order in orders" :key="order.id">
            <!-- Order ID cell is clickable -->
            <td class="order-id-cell" @click="openOrderDetails(order.id)">
              {{ order.id }}
            </td>
            <td>{{ order.cakeId }}</td>
            <td>{{ order.totalPrice }}</td>
            <td>{{ new Date(order.pickupAt).toLocaleDateString() + " " + new Date(order.pickupAt).toLocaleTimeString() }}</td>
            <td>
              <select v-model="order.status" @change="updateStatus(order)">
                <option value="Pending">Pending</option>
                <option value="Canceled">Canceled</option>
                <option value="Ready">Ready</option>
                <option value="Complete">Complete</option>
              </select>
            </td>
            <td>{{ order.message }}</td>
          </tr>
        </tbody>
      </table>
    </div>

    <div v-else class="no-orders">
      <p>No pending orders available.</p>
    </div>

    <!-- Cake Details Modal -->
    <cake-order-details
      :visible="showDetails"
      :orderDetails="selectedOrder"
      @close="showDetails = false"
    />
  </div>
</template>

<script>
import CustomService from '../services/CustomService.js';
import StaffService from '../services/StaffService.js';
import CakeOrderDetails from './CakeOrderDetails.vue';

export default {
  name: "PlacedOrdersView",
  components: {
    CakeOrderDetails,
  },
  data() {
    return {
      orders: [],
      showDetails: false,
      selectedOrder: {}
    };
  },
  methods: {
    updateStatus(newStatus){
      CustomService.updateOrderById(newStatus.id, newStatus);
    },
    getAllOrders() {
      StaffService.getAllOrders().then((list) => {
        this.orders = list.data;
      });
    },
    openOrderDetails(orderId) {
      StaffService.getOrderById(orderId).then((response) => {
        this.selectedOrder = response.data;
        this.showDetails = true;
      }).catch((error) => {
        console.error("Error fetching cake details:", error);
        alert("Unable to fetch cake details. Please try again.");
      });;
    },
  },
  created() {
    this.getAllOrders();
  },
};
</script>

<style scoped>
.container {
  max-width: 900px;
  margin: 20px auto;
  padding: 20px;
  background: white;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  text-align: center;
}

.title {
  font-size: 1.8rem;
  margin-bottom: 20px;
  font-weight: bold;
}

.orders-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 15px;
}

.orders-table th,
.orders-table td {
  border: 1px solid #ddd;
  padding: 10px;
  text-align: left;
}

.orders-table th {
  background: #f4f4f4;
  font-weight: bold;
}

.orders-table tr:nth-child(even) {
  background: #f9f9f9;
}

.no-orders {
  font-size: 1.2rem;
  color: #888;
  margin-top: 20px;
}

.order-id-cell {
  cursor: pointer;
  color: rgb(34, 0, 255);
}

.order-id-cell:hover {
  text-decoration: underline;
}
</style>

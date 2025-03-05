<template>
  <div>
    <!-- Main Cake Summary Section -->
    <div class="flex flex-col md:flex-row gap-6 p-6 max-w-4xl mx-auto">
      <!-- Ensure StandardCakeSummary emits an 'orderClicked' event with cake details -->
      <standard-cake-summary @orderClicked="openOrderForm" />
    </div>

    <!-- Modal Order Form -->
    <standard-cake-form v-if="showOrderForm" :visible="showOrderForm" :cake="selectedCake"
      :standardCake="selectedStandardCake" @orderSubmitted="handleOrderSubmitted" @close="closeOrderForm" />
  </div>
</template>

<script>
import StandardCakeSummary from '../components/StandardCakeSummary.vue';
import StandardCakeForm from '../components/StandardCakeForm.vue';
import StaffService from '../services/StaffService';
import CakeService from '../services/CakeService';

export default {
  components: {
    StandardCakeSummary,
    StandardCakeForm,
  },
  data() {
    return {
      showOrderForm: false,  // Controls modal visibility
      selectedCake: {}       // Holds cake details passed from StandardCakeSummary
    };
  },
  methods: {
    openOrderForm(cake) {
      // Called when StandardCakeSummary gives an 'orderClicked' event.
      CakeService.getCakeById(cake.id).then((response) => {
        this.selectedStandardCake = response.data;

        StaffService.getCakeDetailsById(cake.cakeId).then((response) => {
          this.selectedCake = response.data;
          this.showOrderForm = true;
        });
      });
    },
    closeOrderForm() {
      // Closes the modal.
      this.showOrderForm = false;
    },
    handleOrderSubmitted(order) {
      // Handle order submission (e.g., send data to backend) then close the modal.
      console.log('Order submitted:', order);
      this.closeOrderForm();
    }
  }
};
</script>

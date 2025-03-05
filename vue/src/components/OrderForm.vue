<template>
    <div class="order-container">
        <h2>Custom Cake Order</h2>

        <div class="form-group">
            <label for="size">Choose Your Cake Size:</label>
            <select id="size" v-model="form.cakeDetails.size" required>
                <option v-for="option in sizes" :value="option.name">{{ option.name }} ${{ option.price }}</option>
            </select>
        </div>

        <img src="@/assets/CakeFlavors.png" alt="Cake Flavors" class="cakes-image">


        <div class="form-group">
            <label for="flavor">Choose Your Cake Flavor:</label>
            <select id="flavor" v-model="form.cakeDetails.flavor" required>
                <option v-for="option in flavors" :value="option.name">{{ option.name }} ${{ option.price }}</option>
            </select>
        </div>

        <img src="@/assets/ButterCream.png" alt="Buttercream Frosting" class="buttercream-image">

        <div class="form-group">
            <label for="frosting">Choose Your Frosting Flavor:</label>
            <select id="frosting" v-model="form.cakeDetails.frosting" required>
                <option v-for="option in frostings" :value="option.name">{{ option.name }} ${{ option.price }}</option>
            </select>
        </div>

        <img src="@/assets/Filling.png" alt="Cake Filling" class="filling-image">

        <div class="form-group">
            <label for="filling">Choose Your Filling Flavor:</label>
            <select id="filling" v-model="form.cakeDetails.filling" required>
                <option v-for="option in fillings" :value="option.name">{{ option.name }} ${{ option.price }}</option>
            </select>
        </div>

        <div class="form-group">
            <label for="extras">Choose Your Extras (Optional):</label>
            <select id="extras" v-model="form.cakeDetails.extras" multiple>
                <option v-for="option in extras" :value="option.name">{{ option.name }} ${{ option.price }}</option>
            </select>
        </div>

        <div class="form-group">
            <label for="style">Style:</label>
            <select id="style" v-model="form.cakeDetails.style" required>
                <option v-for="option in styles" :value="option.name">{{ option.name }} ${{ option.price }}</option>
            </select>
        </div>

        <img src="@/assets/PageDivider.png" alt="Page Divider" class="page-divider">

        <form @submit.prevent="submitOrder">
            <div class="form-group">
                <label for="name">Name:</label>
                <input type="text" id="name" v-model="form.customer.name" autocomplete="on" required />
            </div>

            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" v-model="form.customer.email" autocomplete="on" required />
            </div>

            <div class="form-group">
                <label for="phone">Phone:</label>
                <input type="tel" id="phone" v-model="form.customer.phoneNumber" autocomplete="on" required />
            </div>

            <div class="form-group" v-show="hasWritingExtra">
                <label for="request">Special Request (Put Icing Writing Message Here):</label>
                <input type="text" id="request" v-model="form.message" />
            </div>

            <div class="form-group">
                <label for="pickupAt">Pickup At:</label>
                <input type="datetime-local" id="pickupAt" v-model="form.pickupAt" ref="pickupAt" required />
            </div>

            <button type="submit">Submit Order</button>
        </form>

        <div v-if="orderSuccess" class="success-message">
            Thank you, {{ customerName }}! Your order has been submitted.
        </div>
    </div>
</template>

<script>
import CustomService from '../services/CustomService.js';

export default {
    data() {
        return {
            customerName: "",
            options: [],
            form: {
                message: "",
                pickupAt: "",
                customer: {
                    name: "",
                    phoneNumber: "",
                    email: ""
                },
                cakeDetails: {
                    style: "",
                    size: "",
                    flavor: "",
                    frosting: "",
                    filling: "",
                    extras: [],
                }
            },
            orderSuccess: false
        };
    },
    computed: {
        hasWritingExtra() {
            return this.form.cakeDetails.extras.find((name) => {
                return name === "Icing Writing";
            });
        },
        styles() {
            return this.options.filter(option => option.available === true && option.category === 'style');
        },
        fillings() {
            return this.options.filter(option => option.available === true && option.category === 'filling');
        },
        flavors() {
            return this.options.filter(option => option.available === true && option.category === 'flavor');
        },
        frostings() {
            return this.options.filter(option => option.available === true && option.category === 'frosting');
        },
        extras() {
            return this.options.filter(option => option.available === true && option.category === 'extra');
        },
        sizes() {
            return this.options.filter(option => option.available === true && option.category === 'size');
        },
    },
    methods: {
        getOptions() {
            CustomService.getCakeOptions().then(response => {
                this.options = response.data;
            });
        },
        submitOrder() {
            if (this.form.pickupAt == "" || this.form.cakeDetails.style == "" || this.form.cakeDetails.size == "" || this.form.cakeDetails.flavor == "" || this.form.cakeDetails.frosting == "" || this.form.cakeDetails.filling == "") {
                alert("All properties of the form must be filled");
                return;
            }
            CustomService.createCakeOrder(this.form)
                .then(response => {
                    if (response.status === 201) {
                        this.orderSuccess = true;
                        this.customerName = this.form.customer.name;
                        this.clearForm();
                    }
                })
                .catch(error => {
                    console.error("Order submission failed:", error.response?.data || error.message);
                });
        },
        clearForm() {
            this.form = {
                pickupAt: "",
                customer: {
                    name: "",
                    phoneNumber: "",
                    email: ""
                },
                cakeDetails: {
                    style: "",
                    size: "",
                    flavor: "",
                    frosting: "",
                    extras: [],
                }
            };
        }
    },
    created() {
        this.getOptions();
    },
    mounted() {
        this.$refs.pickupAt.min = new Date().toISOString().slice(0, 16);

        const maxDate = new Date();
        maxDate.setDate(maxDate.getDate() + 14);
        this.$refs.pickupAt.max = maxDate.toISOString().slice(0, 16);
    }
}
</script>

<style scoped>
.order-container {
    max-width: 500px;
    margin: auto;
    padding: 20px;
    background: #f9f9f9;
    border-radius: 8px;
}

.form-group {
    margin-bottom: 30px;
}

label {
    font-weight: bold;
    display: block;
}

input,
select {
    width: 100%;
    padding: 8px;
    margin-top: 5px;
    border: 1px solid #ccc;
    border-radius: 4px;
}

button {
    width: 100%;
    padding: 10px;
    background: #28a745;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

button:hover {
    background: #218838;
}

.success-message {
    margin-top: 20px;
    padding: 10px;
    background: #d4edda;
    border-radius: 4px;
    color: #155724;
    text-align: center;
}

.buttercream-image {
    width: 420px;
    height: auto;
    display: block;
    margin: 0px auto;
}

.filling-image {
    width: 420px;
    height: auto;
    display: block;
    margin: 0px auto;
}

.cakes-image {
    width: 485px;
    height: auto;
    display: block;
    margin-bottom: px;
}

.page-divider {
    width: 470px;
    height: auto;
    display: block;
    margin: 0 auto;
    margin-bottom: px;
}
</style>
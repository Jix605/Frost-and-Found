<template>
  <div class="custom-cake-options">
    <h2>Menu Of Cake Options</h2>

    <!-- Search Bar -->
    <div class="search-bar">
      <input type="text" v-model="searchName" placeholder="Search options by name..." />
    </div>

    <!-- Custom Cake Options Section -->
    <div v-for="(options, attribute) in cakeOptions" :key="attribute" class="attribute-section">
      <div class="attribute-header">
        <h3>{{ capitalize(attribute) }}</h3>
        <!-- Add Option Button as a plain black plus sign -->
        <button class="add-btn" @click="toggleAddForm(attribute)">
          <span v-if="!showAddForm[attribute]">+</span>
          <span v-else>&times;</span>
        </button>
      </div>

      <ul>
        <!-- Display filtered options (price is stored but not shown) -->
        <li v-for="(option, index) in filteredOptions[attribute]" :key="index"
          :class="{ unavailable: !option.available }">
          <span class="option-name">{{ option.name }}</span>
          <div class="option-controls">
            <!-- Toggle Switch -->
            <label class="switch">
              <input type="checkbox" v-model="option.available" @change="toggleOption(attribute, option)" />
              <span class="slider round"></span>
            </label>
          </div>
        </li>
      </ul>

      <!-- Add New Option Form -->
      <div v-if="showAddForm[attribute]" class="add-option-form">
        <input type="text" v-model="newOptions[attribute].name" placeholder="New option name" />
        <input type="number" v-model.number="newOptions[attribute].price" placeholder="Price" min="0" step="0.01" />
        <button class="submit-add-btn" @click="addOption(attribute)">Add</button>
      </div>

      <!-- After extras, display the active total (optional) -->
      <div v-if="attribute === 'extras'" class="active-options-total">
        <h3>
          Cake Options Total: ${{ totalActiveOptionsPrice.toFixed(2) }}
        </h3>
      </div>
    </div>

    <!-- NEW SECTION: Standard Cakes -->
    <div class="standard-cake-section">
      <h2>Create a Standard Cake</h2>
      <!-- Form to add a new standard cake -->
      <form @submit.prevent="addStandardCake" class="standard-cake-form">
        <div class="form-row">
          <label for="cakeName">Cake Name:</label>
          <input id="cakeName" v-model="standardCakeForm.cakeBlueprint.name" placeholder="e.g. Marble Luxe" required />
        </div>



        <div class="form-row">
          <label for="cakeDescription">Description:</label>
          <textarea id="cakeDescription" v-model="standardCakeForm.cakeBlueprint.description"
            placeholder="Cake description"></textarea>
        </div>

        <!-- Drop-Downs for Standard Cake Attributes -->
        <div class="form-row">
          <label>Style:</label>
          <select v-model="standardCakeForm.cakeToAdd.style">
            <option disabled value="">Select Style</option>
            <option v-for="option in styles" :key="option.name" :value="option.name">
              {{ option.name }}
            </option>
          </select>
        </div>

        <div class="form-row">
          <label>Size:</label>
          <select v-model="standardCakeForm.cakeToAdd.size">
            <option disabled value="">Select Size</option>
            <option v-for="option in sizes" :key="option.name" :value="option.name">
              {{ option.name }}
            </option>
          </select>
        </div>

        <div class="form-row">
          <label>Flavor:</label>
          <select v-model="standardCakeForm.cakeToAdd.flavor">
            <option disabled value="">Select Flavor</option>
            <option v-for="option in flavors" :key="option.name" :value="option.name">
              {{ option.name }}
            </option>
          </select>
        </div>

        <div class="form-row">
          <label>Frosting:</label>
          <select v-model="standardCakeForm.cakeToAdd.frosting">
            <option disabled value="">Select Frosting</option>
            <option v-for="option in frostings" :key="option.name" :value="option.name">
              {{ option.name }}
            </option>
          </select>
        </div>

        <div class="form-row">
          <label>Filling:</label>
          <select v-model="standardCakeForm.cakeToAdd.filling">
            <option disabled value="">Select Filling</option>
            <option v-for="option in fillings" :key="option.name" :value="option.name">
              {{ option.name }}
            </option>
          </select>
        </div>

        <!-- Manual Price Input -->
        <div class="form-row">
          <label for="cakePrice">Price:</label>
          <input id="cakePrice" type="number" v-model.number="standardCakeForm.cakeBlueprint.price" min="0" step="0.01" required />
        </div>



        <!-- Availability Row with Toggle Switch aligned to the right -->
        <div class="form-row form-row-checkbox">
          <label for="cakeAvailable">Available:</label>
          <label class="switch big-switch" for="cakeAvailable">
            <input type="checkbox" id="cakeAvailable" v-model="standardCakeForm.cakeBlueprint.available" />
            <span class="slider round"></span>
          </label>
        </div>

        <button class="add-standard-btn" type="submit">Add Standard Cake</button>
      </form>

      <!-- List of existing standard cakes -->
      <div class="standard-cakes-list" v-if="standardCakes.length > 0">
        <h3>Existing Standard Cakes</h3>
        <ul>
          <li v-for="(cake, idx) in standardCakes" :key="idx" :class="{ unavailable: !cake.available }">
            <div class="standard-cake-info">
              <strong>{{ cake.name }}</strong> - ${{ Number(cake.price).toFixed(2) }}
              <p>{{ cake.description }}</p>
            </div>
            <div class="option-controls">
              <!-- Toggle Switch for standard cake availability -->
              <label class="switch">
                <input type="checkbox" v-model="cake.available" @change="toggleStandardCakeAvailability(idx)" />
                <span class="slider round"></span>
              </label>
              <!-- Delete Button -->
              <button class="delete-btn" @click="deleteStandardCake(idx)">-</button>
            </div>
          </li>
        </ul>
      </div>
      <p v-else>No standard cakes defined yet.</p>
    </div>
  </div>
</template>

<script>
import CustomService from '../services/CustomService';
import CakeService from '../services/CakeService';

export default {
  name: "MenuOfCakeOptions",
  data() {
    return {
      searchName: "",
      // Options for each attribute with price values.
      options: [],
      cakeOptions: {},
      // For new custom cake options: each attribute is an object with a name and price.
      newOptions: {
        style: { name: "", price: 0 },
        size: { name: "", price: 0 },
        flavor: { name: "", price: 0 },
        frosting: { name: "", price: 0 },
        filling: { name: "", price: 0 },
        extras: { name: "", price: 0 }
      },
      showAddForm: {
        style: false,
        size: false,
        flavor: false,
        frosting: false,
        filling: false,
        extras: false
      },
      // Standard Cakes
      standardCakes: [],
      standardCakeForm: {
        "cakeToAdd": {
          style: "",
          size: "",
          flavor: "",
          frosting: "",
          filling: ""
        },
        "cakeBlueprint": {
          name: "",
          description: "",
          available: true,
          price: 0
        }
      }
    };
  },
  computed: {
    filteredOptions() {
      if (!this.searchName) {
        return this.cakeOptions;
      }
      const filtered = {};
      for (const attr in this.cakeOptions) {
        filtered[attr] = this.cakeOptions[attr].filter(option =>
          option.name.toLowerCase().includes(this.searchName.toLowerCase())
        );
      }
      return filtered;
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
    // Sums the prices of all available items across all categories (for internal use)
    totalActiveOptionsPrice() {
      let total = 0;
      for (const category in this.cakeOptions) {
        this.cakeOptions[category].forEach(item => {
          total += item.available ? Number(item.price) || 0 : 0;
        });
      }
      return total;
    },
    // Computed total for a standard cake based on selected options (if needed elsewhere)
    calculatedStandardCakePrice() {
      let total = 0;
      const attrs = ["style", "size", "flavor", "frosting", "filling", "extras"];
      attrs.forEach(attr => {
        const selected = this.standardCakeForm[attr];
        if (selected) {
          const found = this.cakeOptions[attr].find(o => o.name === selected);
          total += found && found.price ? Number(found.price) : 0;
        }
      });
      return total;
    }
  },
  methods: {
    getOptions() {
      CustomService.getCakeOptions().then(response => {
        this.options = response.data;

        this.cakeOptions = {
          style: this.options.filter(option => option.category === 'style'),
          size: this.options.filter(option => option.category === 'size'),
          flavor: this.options.filter(option => option.category === 'flavor'),
          frosting: this.options.filter(option => option.category === 'frosting'),
          filling: this.options.filter(option => option.category === 'filling'),
          extra: this.options.filter(option => option.category === 'extra')
        }
      });

      CakeService.list().then(response => {
        this.standardCakes = response.data;
      });
    },
    toggleOption(attribute, option) {
      const index = this.cakeOptions[attribute].findIndex(o => o.name === option.name);
      if (index !== -1) {
        option = this.cakeOptions[attribute][index];
        localStorage.setItem("cakeOptions", JSON.stringify(this.cakeOptions));

        option['category'] = attribute;
        console.log(`Toggled ${attribute} option:`, option);
        CustomService.updateCakeOptionByName(option.name, option);
      }
    },
    toggleAddForm(attribute) {
      this.showAddForm[attribute] = !this.showAddForm[attribute];
    },
    addOption(attribute) {
      let failed = false;
      const newOptionName = this.newOptions[attribute].name.trim();
      const newOptionPrice = parseFloat(this.newOptions[attribute].price);

      this.options.forEach(option => {
        if (option.name == newOptionName){
          alert("Name must be unique");
          failed = true;
        }
      });

      if (failed){
        return;
      }

      if (newOptionName !== "") {
        this.cakeOptions[attribute].push({
          name: newOptionName,
          available: true,
          price: isNaN(newOptionPrice) ? 0 : newOptionPrice
        });

        this.options.push({
          name: newOptionName,
          category: attribute,
          available: true,
          price: isNaN(newOptionPrice) ? 0 : newOptionPrice
        })
        this.newOptions[attribute] = { name: "", price: 0 };
        this.showAddForm[attribute] = false;
        localStorage.setItem("cakeOptions", JSON.stringify(this.cakeOptions));
        console.log(`Added new ${attribute} option: ${newOptionName} at $${newOptionPrice}`);

        CustomService.createCakeOption({name: newOptionName, price: newOptionPrice, category: attribute});
      }
    },
    addStandardCake() {
      if (!this.standardCakeForm.cakeBlueprint.name || this.standardCakeForm.cakeBlueprint.price < 0) {
        alert("Please provide a valid name and price for the standard cake.");
        return;
      }
      // Push the standard cake form as-is (price is manually input)
      this.standardCakes.push({ ...this.standardCakeForm.cakeBlueprint });
      localStorage.setItem("standardCakes", JSON.stringify(this.standardCakes));
      console.log("Added standard cake:", this.standardCakeForm);

      CakeService.addStandardCake(this.standardCakeForm).then(response => {
        console.log(response.data);
      });

      // Reset form
      this.standardCakeForm = {
        "cakeToAdd": {
          style: "",
          size: "",
          flavor: "",
          frosting: "",
          filling: "",
          extras: [],
        },
        "cakeBlueprint": {
          name: "",
          description: "",
          available: true,
          price: 0
        }
      }
    },
    toggleStandardCakeAvailability(idx) {
      localStorage.setItem("standardCakes", JSON.stringify(this.standardCakes));
      console.log("Toggled availability for standard cake:", this.standardCakes[idx]);
    },
    deleteStandardCake(idx) {
      const removed = this.standardCakes.splice(idx, 1);
      localStorage.setItem("standardCakes", JSON.stringify(this.standardCakes));
      console.log("Deleted standard cake:", removed);
      CakeService.deleteStandardCakeById(removed[0].id).then(response => {});
    },
    capitalize(word) {
      return word.charAt(0).toUpperCase() + word.slice(1);
    }
  },
  created() {
    this.getOptions();
  }
};
</script>

<style scoped>
.custom-cake-options {
  max-width: 800px;
  margin: 20px auto;
  padding: 20px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  text-align: center;
}

/* Active Options Total */
.active-options-total {
  margin-top: 10px;
  font-weight: bold;
  font-size: 1.2rem;
}

/* Search Bar */
.search-bar {
  margin-bottom: 20px;
}

.search-bar input {
  width: 80%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 1rem;
}

/* Attribute Sections */
.attribute-section {
  margin-bottom: 20px;
  text-align: left;
}

.attribute-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.attribute-section h3 {
  margin: 0;
  text-transform: capitalize;
  border-bottom: 1px solid #ccc;
  padding-bottom: 5px;
}

/* Add Option Button as a plain black plus sign */
.add-btn {
  background: none;
  border: none;
  color: #000;
  font-size: 2rem;
  line-height: 1;
  cursor: pointer;
  padding: 0;
}

.add-btn:hover {
  color: #333;
}

/* Option List Items */
.attribute-section ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.attribute-section li {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 8px;
  border-bottom: 1px solid #eee;
}

.attribute-section li.unavailable {
  opacity: 0.5;
}

/* Option Name */
.option-name {
  font-size: 1rem;
}

/* Toggle and Delete Controls */
.option-controls {
  display: flex;
  align-items: center;
  gap: 8px;
}

/* Toggle Switch Styling */
.switch {
  position: relative;
  width: 50px;
  height: 26px;
}

.switch input {
  opacity: 0;
  width: 0;
  height: 0;
}

.slider {
  position: absolute;
  cursor: pointer;
  inset: 0;
  background-color: #ccc;
  transition: 0.4s;
  border-radius: 26px;
}

.slider:before {
  content: "";
  position: absolute;
  height: 20px;
  width: 20px;
  left: 3px;
  bottom: 3px;
  background-color: #fff;
  transition: 0.4s;
  border-radius: 50%;
}

input:checked+.slider {
  background-color: #f517a4;
}

input:checked+.slider:before {
  transform: translateX(24px);
}

/* Delete Button as a plain black minus sign */
.delete-btn {
  background: none;
  border: none;
  color: #000;
  font-size: 2rem;
  line-height: 1;
  padding: 0;
  cursor: pointer;
}

.delete-btn:hover {
  color: #333;
}

/* Add Option Form */
.add-option-form {
  margin-top: 10px;
  display: flex;
  gap: 10px;
  align-items: center;
}

.add-option-form input {
  flex: 1;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.submit-add-btn {
  padding: 8px 12px;
  background-color: #ff448f;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.submit-add-btn:hover {
  background-color: #00770c;
}

/* Standard Cakes Section */
.standard-cake-section {
  margin-top: 40px;
  text-align: left;
}

.standard-cake-form {
  max-width: 400px;
  margin: 0 auto 20px auto;
  display: flex;
  flex-direction: column;
  gap: 12px;
  text-align: left;
}

.form-row {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.form-row-checkbox {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.big-switch {
  transform: scale(1.3);
  transform-origin: center;
}

.standard-cakes-list ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.standard-cakes-list li {
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #eee;
  padding: 8px 0;
}

.standard-cake-info {
  flex: 1;
}

.standard-cake-info p {
  margin: 4px 0 0;
}

.calculated-price {
  font-weight: bold;
  font-size: 1.2rem;
}
</style>

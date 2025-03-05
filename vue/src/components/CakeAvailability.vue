<template>
  <label class="switch">
    <input type="checkbox" :checked="available" @change="toggleSwitch" />
    <span class="slider round"></span>
  </label>
</template>

<script>

export default {
  name: 'CakeAvailability',
  props: {
    available: {
      type: Boolean,
      default: true
    },
    standardCake: {
      type: Object,
      required: true 
    }
  },
  methods: {
    toggleSwitch() {
      // Emit the new availability state
      this.$emit('toggle', !this.available);
    }
  }
};
</script>

<style scoped>
.switch {
  position: relative;
  display: inline-block;
  width: 60px;
  height: 34px;
}
.switch input {
  opacity: 0;
  width: 0;
  height: 0;
}
.slider {
  position: absolute;
  cursor: pointer;
  top: 0; 
  left: 0; 
  right: 0; 
  bottom: 0;
  background-color: #ccc;
  transition: 0.4s;
}
.slider:before {
  position: absolute;
  content: "";
  height: 26px;
  width: 26px;
  left: 4px;
  bottom: 4px;
  background-color: #fff;
  transition: 0.4s;
}
input:checked + .slider {
  background-color: #ff448f;
}
input:checked + .slider:before {
  transform: translateX(26px);
}
.slider.round {
  border-radius: 34px;
}
.slider.round:before {
  border-radius: 50%;
}
</style>

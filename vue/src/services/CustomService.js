import axios from 'axios';

export default {
  createCakeOrder(placedOrder) {
    return axios.post('/api/placed-orders', placedOrder);
  },

  getCakeOptions() {
    return axios.get('/api/cakes-options');
  },

  updateCakeOptionByName(name, option){
    return axios.put(`/api/cakes-options/${name}`, option);
  },

  createCakeOption(newOption){
    return axios.post('/api/cakes-options', newOption);
  },

  updateOrderById(id, order){
    return axios.put(`/api/placed-orders/${id}`, order);
  }
};
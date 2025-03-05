import axios from 'axios';

export default {

  getAllOrders() {
    return axios.get('/api/placed-orders');
  },

  getOrderById(id) {
    return axios.get(`/api/placed-orders/${id}`)
  },

  getAllCustomers() {
    return axios.get('/api/customers');
  },

  getCustomerById(id) {
    return axios.get(`/api/customers/${id}`);
  },

  getCakeDetailsById(id){
    return axios.get(`/api/cakes/${id}`);
  }
}
/*
  This is a multi-line comment.
  You can write multiple lines of explanation here.
*/
import axios from 'axios';

export default {
  list() {
    return axios.get('/api/standard-cakes');
  },

  getCakeById(cakeId) {
    return axios.get(`/api/standard-cakes/${cakeId}`);
  },

  updateCakeById(standardCakeId, standardCake){
    return axios.put(`/api/standard-cakes/${standardCakeId}`, standardCake);
  },

  addStandardCake(blueprint){
    return axios.post('/api/standard-cakes', blueprint);
  },

  deleteStandardCakeById(id){
    return axios.delete(`/api/standard-cakes/${id}`);
  }
};

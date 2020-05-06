import axios from 'axios';

export default axios.create({
    // baseURL : 'http://52.79.177.68:5000/api/v1',
    baseURL : 'http://localhost:5000/api/v1',
    headers:{
        'Content-type':'application/json',
        // 'Authorization':'Bearer ' + sessionStorage.getItem('accessToken')
    }
})
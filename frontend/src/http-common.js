import axios from 'axios'

export default axios.create({
  baseURL: 'http://13.125.145.32:8080/api',
  headers: {
    'Content-type': 'application/json'
    // 'Authorization':'Bearer ' + sessionStorage.getItem('accessToken')
  }
})

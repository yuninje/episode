import axios from 'axios'

export default axios.create({
  baseURL: 'http://k02a2061.p.ssafy.io:8080/api',
  headers: {
    'Content-type': 'application/json'
    // 'Authorization':'Bearer ' + sessionStorage.getItem('accessToken')
  }
})

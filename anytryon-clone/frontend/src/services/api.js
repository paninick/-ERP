
import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8000/api/v1',
  timeout: 300000,
})

export const tryOnAPI = {
  generate: async (formData) =&gt; {
    return api.post('/tryon/generate', formData, {
      responseType: 'blob',
    })
  },
}

export const healthAPI = {
  check: async () =&gt; {
    return api.get('/health')
  },
}

export default api


import axios from 'axios'

/** Shared REST client. Auth interceptor is added by the auth module later. */
export const http = axios.create({ baseURL: '/api', timeout: 10_000 })


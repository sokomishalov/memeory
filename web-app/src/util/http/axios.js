import axios from "axios";

export const BASE_BACKEND_URL = process.env.REACT_APP_BACKEND_URL;

axios.defaults.baseURL = BASE_BACKEND_URL;

axios.interceptors.request.use(config => config, error => Promise.reject(error));

axios.interceptors.response.use(response => response.data, error => Promise.reject(error));
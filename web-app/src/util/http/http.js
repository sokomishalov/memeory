import axios from "axios";
import _ from "lodash";
import {getBackendUrl} from "../env/env";

axios.interceptors.request.use(async (config) => {
    const url = getBackendUrl();

    axios.defaults.baseURL = url
    config.baseURL = url;

    return config
}, error => {
    return Promise.reject(error);
});

axios.interceptors.response.use(response => {
    return response.data;
}, error => {
    return Promise.reject(error);
});

// noinspection JSUnusedLocalSymbols
export const unAwait = (promise) => _.noop()
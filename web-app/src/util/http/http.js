import axios from "axios";
import _ from "lodash";
import {getBackendUrl} from "../firebase/firebase";
import {getToken} from "../storage/storage";

axios.interceptors.request.use(async (config) => {
    const url = await getBackendUrl();

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

export const withToken = () => ({
    headers: {
        MEMEORY_TOKEN: _.defaultTo(getToken(), "")
    }
})
import axios from "axios";

export const getProviders = async () => {
    return await axios.get("providers/list");
}

export const getProviderLogoUrl = (providerId) => {
    return `${axios.defaults.baseURL}providers/logo/${providerId}`;
}

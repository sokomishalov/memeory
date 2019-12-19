import axios from "axios";
import store from "../store/store";
import {providersReceived} from "../store/actions";
import _ from "lodash"

export const getProviders = async () => {
    const providers = await axios.get("providers/list");
    await store.dispatch(providersReceived(_.defaultTo(providers, [])))
    return providers
}

export const getProviderLogoUrl = (providerId) => {
    return `${axios.defaults.baseURL}providers/logo/${providerId}`;
}

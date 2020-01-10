import axios from "axios";
import store from "../store/store";
import {channelsReceived} from "../store/actions";
import _ from "lodash"

export const getChannels = async () => {
    const channels = await axios.get("channels/list");
    await store.dispatch(channelsReceived(_.defaultTo(channels, [])))
    return channels
}

export const getChannelLogoUrl = (channelId) => {
    return `${axios.defaults.baseURL}channels/logo/${channelId}`;
}

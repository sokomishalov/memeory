import axios from "axios";

export const getChannels = async () => {
    return await axios.get("channels/list/enabled");
}

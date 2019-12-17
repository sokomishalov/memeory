import axios from "axios";

export const getTopics = async () => {
    return await axios.get("topics/list");
}

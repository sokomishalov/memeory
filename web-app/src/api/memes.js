import axios from "axios";
import {DEFAULT_PAGE_SIZE} from "../util/consts/consts";
import {getToken} from "../util/auth/profile";

export const getMemesPage = async (pageNumber, pageSize = DEFAULT_PAGE_SIZE) => {
    return await axios.get(`/memes/page/${pageNumber}/${pageSize}`, {
        headers: {
            MEMEORY_TOKEN: getToken()
        }
    });
};

import axios from "axios";
import {DEFAULT_PAGE_SIZE} from "../util/consts/consts";
import {getToken} from "../util/storage/storage";

export const getMemesPage = async (pageNumber, pageSize = DEFAULT_PAGE_SIZE) => {
    return await axios.get(`/memes/page/${pageNumber}/${pageSize}`, {
        headers: {
            MEMEORY_TOKEN: getToken()
        }
    });
};

export const getSingleMeme = async (id) => {
    return await axios.get(`/memes/one/${id}`);
};
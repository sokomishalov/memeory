import axios from "axios";
import {DEFAULT_PAGE_SIZE} from "../util/consts/consts";
import {withToken} from "../util/http/http";

export const getMemesPage = async (pageNumber, pageSize = DEFAULT_PAGE_SIZE) => {
    return await axios.get(`/memes/page/${pageNumber}/${pageSize}`, withToken());
};

export const getSingleMeme = async (id) => {
    return await axios.get(`/memes/one/${id}`);
};
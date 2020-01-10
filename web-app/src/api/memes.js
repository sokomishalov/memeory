import axios from "axios";
import {DEFAULT_PAGE_SIZE} from "../util/consts/consts";

export const getMemesPage = async (
    providerId = null,
    topicId = null,
    channelId = null,
    pageNumber = 0,
    pageSize = DEFAULT_PAGE_SIZE
) => {
    return await axios.post("/memes/page", {
        topicId,
        channelId,
        providerId,
        pageNumber,
        pageSize
    });
};

export const getSingleMeme = async (id) => {
    return await axios.get(`/memes/one/${id}`);
};
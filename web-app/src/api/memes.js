import axios from "axios";

export const getMemesPage = async (pageNumber, pageSize = 100) => {
    return await axios.get(`/memes/page/${pageNumber}/${pageSize}`);
};

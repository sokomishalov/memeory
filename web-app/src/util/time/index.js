import moment from "moment";

export const timeAgo = (date) => moment(date).fromNow();
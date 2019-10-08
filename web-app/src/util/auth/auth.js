import _ from "lodash"

export const isLoggedIn = () => !_.isEmpty(getSocialAccount(MEMEORY_ACCOUNT));


export const MEMEORY_ACCOUNT = "MEMEORY_ACCOUNT";
export const GOOGLE_ACCOUNT = "GOOGLE_ACCOUNT";
export const FACEBOOK_ACCOUNT = "FACEBOOK_ACCOUNT";

export const getGoogleAccount = () => getSocialAccount(GOOGLE_ACCOUNT);

export const getFacebookAccount = () => getSocialAccount(GOOGLE_ACCOUNT);

const getSocialAccount = (key) => {
    return JSON.parse(_.defaultTo(window.localStorage.getItem(key), "{}"));
};

const setSocialAccount = (key, account) => {
    window.localStorage.setItem(key, _.toJSON(account));
};

export const logout = () => [
    MEMEORY_ACCOUNT,
    GOOGLE_ACCOUNT,
    FACEBOOK_ACCOUNT
].forEach((it) => window.localStorage.removeItem(it));
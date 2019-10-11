import _ from "lodash"

export const MEMEORY_PROFILE = "Memeory"
export const GOOGLE_PROVIDER = "google.com";
export const FACEBOOK_PROVIDER = "facebook.com";

const AVAILABLE_PROVIDERS = [GOOGLE_PROVIDER, FACEBOOK_PROVIDER]

export const isLoggedIn = () => !_.isEmpty(getSocialsMap());

export const getToken = () => _.get(getProfile(), "id", null)

export const getUserDisplayName = (orElse = "") => {
    const names = AVAILABLE_PROVIDERS
        .map(getAccountDisplayName)
        .filter((it) => !_.isEmpty(it))

    return _.defaultTo(_.head(names), orElse)
}

export const getSocialsMap = () => {
    let result = {};
    AVAILABLE_PROVIDERS.forEach((it) => {
        const account = getAccount(it)
        if (!_.isEmpty(account)) {
            result[it] = account
        }
    })
    return result
}

export const getAccountDisplayName = (key, orElse = "") => _.get(getAccount(key), "displayName", orElse)

export const getProfile = () => getAccount(MEMEORY_PROFILE)

export const setProfile = (profile) => setAccount(MEMEORY_PROFILE, _.omit(profile, ["socialsMap"]))

export const getAccount = (key) => JSON.parse(_.defaultTo(window.localStorage.getItem(key), "{}"));

export const setAccount = (key, account) => window.localStorage.setItem(key, JSON.stringify(account));

export const logout = () => AVAILABLE_PROVIDERS.forEach((it) => window.localStorage.removeItem(it));
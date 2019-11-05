import axios from "axios"
import _ from "lodash"
import { getMemeoryProfile, setAccount, setMemeoryProfile } from "../util/storage/storage";
import { withToken } from "../util/http/http";

export const fetchProfile = async () => {
    return await axios.get("profile/get", withToken());
};

export const saveSocialsAccounts = async (accounts) => {
    const memeoryProfile = await axios.post("profile/socials/add", accounts, withToken());
    _.forEach(accounts, a => setAccount(_.get(a, "providerId"), a))
    setMemeoryProfile(memeoryProfile)
}

export const updateProfile = async (profile = getMemeoryProfile()) => {
    const savedProfile = await axios.post("profile/update", {...profile})
    setMemeoryProfile(savedProfile)
    return savedProfile
};


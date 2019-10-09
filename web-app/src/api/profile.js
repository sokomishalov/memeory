import axios from "axios"
import _ from "lodash"
import {getProfile, getSocialsMap, setAccount, setProfile} from "../util/auth/profile"

export const saveProfile = async () => {

    const socialsMap = getSocialsMap()
    const profile = getProfile()

    const savedProfile = await axios.post(`profile/save`, {
        id: profile["id"],
        socialsMap: socialsMap,
        watchAllChannels: _.get(profile, "watchAllChannels", true),
        channels: _.get(profile, "channels", [])
    })

    _.forOwn(_.get(savedProfile, "socialsMap", {}), (value, key) => setAccount(key, value));
    setProfile(_.omit(savedProfile, ["socialsMap"]))
};

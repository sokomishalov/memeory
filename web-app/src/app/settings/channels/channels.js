import React, {useEffect, useState} from 'react'
import "./channels.css"
import {getChannels} from "../../../api/channels"
import Loader from "../../common/loader/loader";
import _ from "lodash";
import ChannelContainer from "./container/channel-container";
import {fetchProfile, updateProfile} from "../../../api/profile";
import {addOrRemove} from "../../../util/collections/collections";
import {infoToast} from "../../common/toast/toast";
import {setMemeoryProfile} from "../../../util/storage/storage"
import {Switch} from "antd";
import {withT} from "../../../util/locales/i18n";
import {unAwait} from "../../../util/http/http";
import FloatingButton from "../../common/buttons/floating-button";

const Channels = ({t}) => {
    const [loading, setLoading] = useState(false)
    const [channels, setChannels] = useState([])
    const [fetchedProfile, setFetchedProfile] = useState({})
    const [profile, setProfile] = useState({})

    useEffect(() => unAwait(loadChannels()), [])

    const loadChannels = async () => {
        setLoading(true)

        const fetchedChannels = await getChannels()
        const profile = await fetchProfile()

        setChannels(fetchedChannels)
        setFetchedProfile(profile)
        setProfile(profile)
        setLoading(false)
    }

    const toggleWatchAll = () => {
        const newProfile = _.cloneDeep(profile)
        newProfile["watchAllChannels"] = !newProfile["watchAllChannels"];
        newProfile["channels"] = []
        setProfile(newProfile)
    }

    const toggleChannel = (channelId) => {
        const newProfile = _.cloneDeep(profile)
        newProfile["watchAllChannels"] = false;
        addOrRemove(newProfile["channels"], channelId)
        setProfile(newProfile)
    }

    const saveChanges = async () => {
        const newProfile = await updateProfile(profile)
        setFetchedProfile(newProfile)
        setProfile(newProfile)
        setMemeoryProfile(newProfile)
        infoToast(t("changes.have.been.saved"))
    }

    return (
        <Loader loading={loading}>
            <div className="channels">
                <div className="channels-header">
                    <div className="channels-header-caption">
                        {t("choose.relevant.channels")}
                    </div>
                    <div className="channels-header-watch-all">
                        {t("watch.all")}
                        <Switch style={{marginLeft: 10}}
                                onChange={toggleWatchAll}
                                checked={profile["watchAllChannels"]}/>
                    </div>
                </div>
                <div className="channels-list">
                    {_.map(channels, (channel) => {
                        const active = profile["watchAllChannels"] || _.includes(profile["channels"], channel["id"])
                        return <ChannelContainer key={channel["id"]}
                                                 channel={channel}
                                                 active={active}
                                                 toggle={() => toggleChannel(channel["id"])}/>;
                    })}
                </div>
                {
                    !_.isEmpty(fetchedProfile)
                    && !_.eq(fetchedProfile, profile)
                    && <FloatingButton text={t("save.changes")}
                                       icon="save"
                                       color="green"
                                       onClick={saveChanges}/>
                }
            </div>
        </Loader>
    )
}

export default withT(Channels)
import React, {useEffect, useState} from 'react'
import "./channels.css"
import {getChannels} from "../../api/channels"
import Loader from "../common/loader/loader";
import {unAwait} from "../../util/http/axios";
import _ from "lodash";
import ChannelContainer from "./container/channel-container";

const Channels = () => {
    const [loading, setLoading] = useState(false)
    const [channels, setChannels] = useState([])

    useEffect(() => unAwait(loadChannels()), [])

    const loadChannels = async () => {
        setLoading(true)
        const fetchedChannels = await getChannels()
        setChannels(fetchedChannels)
        setLoading(false)
    }

    return (
        <Loader loading={loading}>
            <div className="mt-20">
                {_.map(channels, (channel) => <ChannelContainer key={channel["id"]} channel={channel}/>)}
            </div>
        </Loader>
    )
}

export default Channels
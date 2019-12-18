import React, {useEffect, useState} from 'react'
import "./channels.css"
import {getChannels} from "../../../api/channels"
import Loader from "../../common/loader/loader";
import _ from "lodash";
import ChannelContainer from "./container/channel-container";
import {withT} from "../../../util/locales/i18n";
import {unAwait} from "../../../util/http/http";
import {withRouter} from "react-router";
import {PARAMS, ROUTE} from "../../../util/router/router";

const Channels = ({t, history}) => {
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
            <div className="channels">
                <div className="channels-header">
                </div>
                <div className="channels-list">
                    {_.map(channels, (channel) => {
                        return <ChannelContainer key={channel["id"]}
                                                 channel={channel}
                                                 onClick={() => history.push(ROUTE.MEMES_CHANNEL.replace(PARAMS.ID, channel["id"]))}/>;
                    })}
                </div>
            </div>
        </Loader>
    )
}

export default _.flow(
    withT,
    withRouter
)(Channels)
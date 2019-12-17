import React, {useEffect, useState} from 'react'
import "./channels.css"
import {getChannels} from "../../../api/channels"
import Loader from "../../common/loader/loader";
import _ from "lodash";
import ChannelContainer from "./container/channel-container";
import {Switch} from "antd";
import {withT} from "../../../util/locales/i18n";
import {unAwait} from "../../../util/http/http";

const Channels = ({t}) => {
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
                    <div className="channels-header-caption">
                        {t("choose.relevant.channels")}
                    </div>
                    <div className="channels-header-watch-all">
                        {t("watch.all")}
                        <Switch style={{marginLeft: 10}}
                                onChange={_.noop}
                                checked={true}/>
                    </div>
                </div>
                <div className="channels-list">
                    {_.map(channels, (channel) => {
                        return <ChannelContainer key={channel["id"]}
                                                 channel={channel}
                                                 active={true}
                                                 toggle={_.noop}/>;
                    })}
                </div>
            </div>
        </Loader>
    )
}

export default withT(Channels)
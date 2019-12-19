import React from 'react'
import "./channels.css"
import _ from "lodash";
import ChannelContainer from "./container/channel-container";
import {withT} from "../../../util/locales/i18n";
import {withRouter} from "react-router";
import {PARAMS, ROUTE} from "../../../util/router/router";
import {selectChannels} from "../../../store/selectors/channels";
import {connect} from "react-redux";

const Channels = ({channels, history}) => {
    return (
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
    )
}

const mapStateToProps = state => ({
    channels: selectChannels(state),
});

export default _.flow(
    withT,
    withRouter,
    connect(mapStateToProps)
)(Channels)
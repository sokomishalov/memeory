import React from 'react'
import _ from "lodash"
import withHeader from "../header/hoc";
import { isBrowser, isMobile } from "react-device-detect"
import TopicsPanel from "./panels/left";
import { withRouter } from "react-router";
import { ROUTE } from "../../util/router/router";
import MemesList from "./panels/center";
import RightPanel from "./panels/right";
import TopicTabs from "./tabs/topic-tabs";

const Memes = ({match}) => {
    const id = match.params.id

    return (
        <div>
            { isBrowser && <TopicsPanel/> }
            { isMobile && <TopicTabs/> }
            <MemesList key={ match.url }
                       providerId={ (ROUTE.MEMES_PROVIDER === match.path) ? id : null }
                       topicId={ (ROUTE.MEMES_TOPIC === match.path) ? id : null }
                       channelId={ (ROUTE.MEMES_CHANNEL === match.path) ? id : null }
                       memeId={ (ROUTE.MEMES_SINGLE === match.path) ? id : null }
            />
            { isBrowser && <RightPanel/> }
        </div>
    )
}

export default _.flow(
    withHeader,
    withRouter
)(Memes)
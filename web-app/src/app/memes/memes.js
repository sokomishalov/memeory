import React from 'react'
import _ from "lodash"
import withHeader from "../header/hoc";
import TopicsPanel from "./panels/topics-panel";
import {withRouter} from "react-router";
import {ROUTE} from "../../util/router/router";
import MemesPanel from "./panels/memes-panel";
import ProvidersPanel from "./panels/providers-panel";
import TopicTabs from "./tabs/topic-tabs";
import ProviderTabs from "./tabs/provider-tabs";

const Memes = ({match}) => {
    const id = match.params.id

    return (
        <div>
            <TopicsPanel/>
            <TopicTabs/>
            <MemesPanel key={match.url}
                        providerId={(ROUTE.MEMES_PROVIDER === match.path) ? id : null}
                        topicId={(ROUTE.MEMES_TOPIC === match.path) ? id : null}
                        channelId={(ROUTE.MEMES_CHANNEL === match.path) ? id : null}
                        memeId={(ROUTE.MEMES_SINGLE === match.path) ? id : null}
            />
            <ProvidersPanel/>
            <ProviderTabs/>
        </div>
    )
}

export default _.flow(
    withHeader,
    withRouter
)(Memes)
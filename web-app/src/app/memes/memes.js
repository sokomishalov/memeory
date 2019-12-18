import React from 'react'
import _ from "lodash"
import withHeader from "../header/hoc";
import LeftPanel from "./panels/left";
import {withRouter} from "react-router";
import {ROUTE} from "../../util/router/router";
import MemesList from "./panels/center";
import RightPanel from "./panels/right";

const Memes = ({match}) => {
    const id = match.params.id

    return (
        <div>
            <LeftPanel/>
            <MemesList key={match.url}
                       topicId={(ROUTE.MEMES_TOPIC === match.path) ? id : null}
                       channelId={(ROUTE.MEMES_CHANNEL === match.path) ? id : null}
                       memeId={(ROUTE.MEMES_SINGLE === match.path) ? id : null}
            />
            <RightPanel/>
        </div>
    )
}

export default _.flow(
    withHeader,
    withRouter
)(Memes)
import React from 'react'
import "./memes.css"
import _ from "lodash"
import withHeader from "../header/hoc";
import Topics from "./topics/topics";
import {withRouter} from "react-router";
import {ROUTE} from "../../util/router/router";
import ListMemes from "./list/list";

const Memes = ({match}) => {
    const id = match.params.id

    return (
        <div className="flex-space-between">
            <Topics/>
            <div className="memes">
                <ListMemes key={match.url}
                           topicId={(ROUTE.MEMES_TOPIC === match.path) ? id : null}
                           channelId={(ROUTE.MEMES_CHANNEL === match.path) ? id : null}
                           memeId={(ROUTE.MEMES_SINGLE === match.path) ? id : null}
                />
            </div>
            <Topics/>
        </div>
    )
}

export default _.flow(
    withHeader,
    withRouter
)(Memes)
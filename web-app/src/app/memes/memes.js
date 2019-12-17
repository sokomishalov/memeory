import React from 'react'
import "./memes.css"
import _ from "lodash"
import withHeader from "../header/hoc";
import Topics from "./topics/topics";
import {withRouter} from "react-router";
import {ROUTE} from "../../util/router/router";
import SingleMeme from "./single/single";
import ListMemes from "./list/list";

const Memes = ({match}) => {
    const id = match.params.id

    let component
    if (ROUTE.MEMES_SINGLE === match.path) {
        component = <SingleMeme key={match.url}
                                id={id}/>
    } else {
        component = <ListMemes key={match.url}
                               topic={(ROUTE.MEMES_TOPIC === match.path) ? id : null}
                               channel={(ROUTE.MEMES_CHANNEL === match.path) ? id : null}/>
    }

    return (
        <div className="flex-space-between">
            <Topics/>
            <div className="memes">
                {component}
            </div>
            <Topics/>
        </div>
    )
}

export default _.flow(
    withHeader,
    withRouter
)(Memes)
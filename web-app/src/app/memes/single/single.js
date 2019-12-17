import React, {useEffect, useState} from 'react'
import "./single.css"
import {withRouter} from "react-router";
import MemeContainer from "../container/container";
import {unAwait} from "../../../util/http/http";
import {getSingleMeme} from "../../../api/memes";
import Loader from "../../common/loader/loader";
import _ from "lodash"
import withHeader from "../../header/hoc";


const SingleMeme = ({match}) => {
    const id = match.params.id

    const [meme, setMeme] = useState({})
    const [loading, setLoading] = useState(false)

    useEffect(() => unAwait(loadMeme()), [])

    const loadMeme = async () => {
        setLoading(true)
        setMeme(await getSingleMeme(id))
        setLoading(false)
    }

    return (
        <div className="single-meme">
            <Loader loading={loading}>
                <MemeContainer meme={meme}/>
            </Loader>
        </div>
    );
}

export default _.flow(
    withRouter,
    withHeader
)(SingleMeme)
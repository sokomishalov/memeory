import React, {useEffect, useState} from 'react'
import "./single-meme.css"
import _ from "lodash"
import {withRouter} from "react-router";
import {withT} from "../../../locales";
import MemeContainer from "../container/MemeContainer";
import {unAwait} from "../../../util/http";
import {getSingleMeme} from "../../../api/memes";
import Loader from "../../common/loader/loader";


const SingleMeme = ({t, match}) => {
    const id = match.params.id

    const [meme, setMeme] = useState({})
    const [loading, setLoading] = useState(false)

    useEffect(() => unAwait(loadMeme()), [])

    const loadMeme = async () => {
        setLoading(true)
        const meme = await getSingleMeme(id)
        setMeme(meme)
        setLoading(false)
    }

    return (
        <Loader className="single-meme"
                loading={loading}>
            <MemeContainer meme={meme}/>
        </Loader>
    );
}

export default _.flow(
    withRouter,
    withT
)(SingleMeme)
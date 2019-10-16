import React, {useEffect, useState} from 'react'
import "./single-meme.css"
import {withRouter} from "react-router";
import MemeContainer from "../container/MemeContainer";
import {unAwait} from "../../../util/http";
import {getSingleMeme} from "../../../api/memes";
import Loader from "../../common/loader/loader";


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
        <Loader className="single-meme"
                loading={loading}>
            <MemeContainer meme={meme}/>
        </Loader>
    );
}

export default withRouter(SingleMeme)
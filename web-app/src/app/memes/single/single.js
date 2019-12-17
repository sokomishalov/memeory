import React, {useEffect, useState} from "react";
import {unAwait} from "../../../util/http/http";
import {getSingleMeme} from "../../../api/memes";
import Loader from "../../common/loader/loader";
import MemeContainer from "../container/container";

const SingleMeme = ({id}) => {
    const [meme, setMeme] = useState({})
    const [loading, setLoading] = useState(false)

    useEffect(() => unAwait(loadMeme()), [])

    const loadMeme = async () => {
        setLoading(true)
        setMeme(await getSingleMeme(id))
        setLoading(false)
    }

    return (
        <Loader loading={loading}>
            <MemeContainer meme={meme}/>
        </Loader>
    );
}

export default SingleMeme
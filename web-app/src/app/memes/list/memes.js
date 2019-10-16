import React, {useState} from 'react'
import "./memes.css"
import MemeContainer from "../container/MemeContainer"
import {getMemesPage} from "../../../api/memes"
import _ from "lodash"
import InfiniteScroll from 'react-infinite-scroller'
import {BackTop} from "antd"
import Loader from "../../common/loader/loader"

const Memes = () => {
    const [loading, setLoading] = useState(false)
    const [memes, setMemes] = useState([])
    const [hasMore, setHasMore] = useState(true)

    const loadMore = async (page) => {
        setLoading(true)
        try {
            const newMemes = await getMemesPage(page - 1)
            if (!_.isEmpty(newMemes)) {
                setMemes(_.concat(memes, newMemes))
            } else {
                setHasMore(false)
            }
        } finally {
            setLoading(false)
        }
    }

    return (
        <div className="memes">
            <InfiniteScroll pageStart={0}
                            loadMore={loadMore}
                            hasMore={hasMore && !loading}
                            loader={<Loader key={0} loading={loading}/>}>
                {_.map(memes, (meme) => <MemeContainer key={meme["id"]} meme={meme}/>)}
            </InfiniteScroll>
            <BackTop className="memes-backtop"/>
        </div>
    )
}

export default Memes
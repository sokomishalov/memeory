import React, {useState} from 'react'
import "./list.css"
import MemeContainer from "../container/container"
import {getMemesPage} from "../../../api/memes"
import _ from "lodash"
import InfiniteScroll from 'react-infinite-scroller'
import {BackTop} from "antd"
import Loader from "../../common/loader/loader"
import OnScrollUpReveal from "../../common/event/on-scroll-up-reveal";
import withHeader from "../../header/hoc";
import withMemesPage from "../memes-hoc";

const ListMemes = () => {
    const [loading, setLoading] = useState(false)
    const [memes, setMemes] = useState([])
    const [hasMore, setHasMore] = useState(true)

    const loadMore = async (page) => {
        setLoading(true)
        try {
            const newMemes = await getMemesPage(null, page - 1)
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
            <OnScrollUpReveal useFade={false}>
                <BackTop className="memes-backtop"/>
            </OnScrollUpReveal>
        </div>
    )
}

export default _.flow(
    withMemesPage,
    withHeader
)(ListMemes)
import React, {useState} from 'react'
import "./center.css"
import InfiniteScroll from "react-infinite-scroller";
import Loader from "../../common/loader/loader";
import _ from "lodash";
import MemeContainer from "../container/container";
import OnScrollUpReveal from "../../common/event/on-scroll-up-reveal";
import {BackTop} from "antd";
import {getMemesPage, getSingleMeme} from "../../../api/memes";

const MemesList = ({topicId = null, channelId = null, memeId = null}) => {

    const [loading, setLoading] = useState(false)
    const [memes, setMemes] = useState([])
    const [hasMore, setHasMore] = useState(true)

    const loadMore = async (page) => {
        setLoading(true)
        try {
            if (_.isEmpty(memeId)) {
                const newMemes = await getMemesPage(topicId, channelId, page - 1)
                console.log(newMemes)

                if (!_.isEmpty(newMemes)) {
                    setMemes(_.concat(memes, newMemes))
                } else {
                    setHasMore(false)
                }
            } else {
                const meme = await getSingleMeme(memeId)
                setHasMore(false)
                setMemes([meme])
            }
        } finally {
            setLoading(false)
        }
    }

    const buildCaption = () => {
        if (!_.isEmpty(topicId)) {
            return `Topic: ${topicId}`
        } else if (!_.isEmpty(channelId)) {
            return `Channel: ${channelId}`
        } else if (!_.isEmpty(memeId)) {
            return `Meme: ${memeId}`
        } else {
            return `All memes`
        }
    }

    return (
        <div className="memes">
            <div className="memes-caption">
                {buildCaption()}
            </div>
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
    );
};

export default MemesList
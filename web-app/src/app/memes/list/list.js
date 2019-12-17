import React, {useState} from 'react'
import InfiniteScroll from "react-infinite-scroller";
import Loader from "../../common/loader/loader";
import _ from "lodash";
import MemeContainer from "../container/container";
import OnScrollUpReveal from "../../common/event/on-scroll-up-reveal";
import {BackTop} from "antd";
import {getMemesPage} from "../../../api/memes";

const ListMemes = ({topic = null, channel = null}) => {

    const [loading, setLoading] = useState(false)
    const [memes, setMemes] = useState([])
    const [hasMore, setHasMore] = useState(true)

    const loadMore = async (page) => {
        setLoading(true)
        try {
            const newMemes = await getMemesPage(topic, channel, page - 1)
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
        <>
            <InfiniteScroll pageStart={0}
                            loadMore={loadMore}
                            hasMore={hasMore && !loading}
                            loader={<Loader key={0} loading={loading}/>}>
                {_.map(memes, (meme) => <MemeContainer key={meme["id"]} meme={meme}/>)}
            </InfiniteScroll>
            <OnScrollUpReveal useFade={false}>
                <BackTop className="memes-backtop"/>
            </OnScrollUpReveal>
        </>
    );
};

export default ListMemes
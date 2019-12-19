import React, {useState} from 'react'
import "./memes-panel.css"
import InfiniteScroll from "react-infinite-scroller";
import _ from "lodash";
import MemeContainer from "../container/container";
import OnScrollUpReveal from "../../common/event/on-scroll-up-reveal";
import {BackTop, Icon} from "antd";
import {getMemesPage, getSingleMeme} from "../../../api/memes";
import {withT} from "../../../util/locales/i18n";
import Loader from "../../common/loader/loader";

const MemesList = ({t, providerId = null, topicId = null, channelId = null, memeId = null}) => {

    const [loading, setLoading] = useState(false)
    const [memes, setMemes] = useState([])
    const [hasMore, setHasMore] = useState(true)

    const loadMore = async (page) => {
        setLoading(true)
        try {
            if (_.isEmpty(memeId)) {
                const newMemes = await getMemesPage(providerId, topicId, channelId, page - 1)

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
        if (!_.isEmpty(providerId)) {
            return `${t("provider.caption")}: ${providerId}`
        } else if (!_.isEmpty(topicId)) {
            return `${t("topic.caption")}: ${topicId}`
        } else if (!_.isEmpty(channelId)) {
            return `${t("channel.caption")}: ${channelId}`
        } else if (!_.isEmpty(memeId)) {
            return `${t("meme.caption")}: ${memeId}`
        } else {
            return t("all.memes.caption")
        }
    }

    return (
        <div className="memes">
            <div className="memes-caption">
                {buildCaption()}
            </div>
            <InfiniteScroll className="min-h-500"
                            loadMore={loadMore}
                            hasMore={hasMore && !loading}>
                {_.map(memes, (meme) => <MemeContainer key={meme["id"]} meme={meme}/>)}
                {!hasMore && (
                    <div className="memes-no-more">
                        <Icon type="meh" className="mr-5"/>
                        There are no memes more here!
                    </div>
                )}
                {loading && (
                    <Loader key={0} loading={loading}/>
                )}
            </InfiniteScroll>
            <OnScrollUpReveal useFade={false}>
                <BackTop className="memes-backtop"/>
            </OnScrollUpReveal>
        </div>
    );
};

export default withT(MemesList)
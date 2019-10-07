import React, {useState} from 'react';
import "./Memes.css"
import {getMemesPage} from "../../api/memes";
import {MemeContainer} from "./container/MemeContainer";
import _ from "lodash";
import InfiniteScroll from 'react-infinite-scroller';
import {BackTop} from "antd";

export const Memes = () => {
    const [memes, setMemes] = useState([]);
    const [hasMore, setHasMore] = useState(true);

    const loadMore = async (page) => {
        const newMemes = await getMemesPage(page);
        if (!_.isEmpty(newMemes)) {
            setMemes(_.concat(memes, newMemes));
        } else {
            setHasMore(false)
        }

    };

    return (
        <div className="memes">
            <InfiniteScroll pageStart={0}
                            loadMore={loadMore}
                            hasMore={hasMore}
                            loader={<div className="loader" key={0}>Подождите, мемы грузятся</div>}>
                {_.map(memes, (meme) => <MemeContainer key={meme["id"]} meme={meme}/>)}
            </InfiniteScroll>
            <BackTop/>
        </div>
    );
};
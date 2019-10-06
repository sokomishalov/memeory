import React, {useEffect, useState} from 'react';
import "./Memes.css"
import {getMemesPage} from "../../api/memes";
import {MemeContainer} from "./container/MemeContainer";
import _ from "lodash";

export const Memes = () => {
    const [page, setPage] = useState(0);
    const [memes, setMemes] = useState([]);

    useEffect(() => {
        loadMore()
    }, [page]);

    const loadMore = async () => {
        const newMemes = await getMemesPage(page);
        setMemes([...newMemes, ...memes]);
    };

    const nextPage = () => {
        setPage(page + 1);
    };

    return (
        <div className="memes">
            {_.map(memes, (meme) => <MemeContainer key={meme["id"]} meme={meme}/>)}
        </div>
    );
};
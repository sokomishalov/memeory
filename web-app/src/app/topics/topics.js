import React, {useEffect, useState} from 'react';
import {Divider, Typography} from "antd";
import {unAwait} from "../../util/http/http";
import {getTopics} from "../../api/topics";
import _ from "lodash"

const Topics = () => {

    const [topics, setTopics] = useState([])

    useEffect(() => unAwait(loadTopics()), [])

    const loadTopics = async () => {
        setTopics(await getTopics())
    }

    return (
        <div className="topics">
            <Typography.Text>Memes catalog</Typography.Text>
            <Divider/>
            {_.map(topics, it => (
                <div>{JSON.stringify(it)}</div>
            ))}
        </div>
    );
};

export default Topics;
import React, {useEffect, useState} from 'react';
import "./topics.css"
import {Divider, Typography} from "antd";
import {unAwait} from "../../../util/http/http";
import {getTopics} from "../../../api/topics";
import _ from "lodash"
import {withRouter} from "react-router";
import {PARAMS, ROUTE} from "../../../util/router/router";

const Topics = ({history}) => {

    const [topics, setTopics] = useState([])

    useEffect(() => unAwait(loadTopics()), [])

    const loadTopics = async () => {
        setTopics(await getTopics())
    }

    return (
        <div className="topics">
            <Typography.Text className="topics-header">Memes catalog</Typography.Text>
            <Divider/>
            <div className="topics-items">
                {_.map(topics, it => (
                    <div className="topics-items-item"
                         onClick={() => history.push(ROUTE.MEMES_TOPIC.replace(PARAMS.ID, it.id))}>
                        {it.caption}
                    </div>
                ))}
            </div>
        </div>
    );
};

export default withRouter(Topics);
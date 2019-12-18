import React, {useEffect, useState} from 'react';
import "./left.css"
import {Divider, Typography} from "antd";
import {unAwait} from "../../../util/http/http";
import {getTopics} from "../../../api/topics";
import _ from "lodash"
import {withRouter} from "react-router";
import {PARAMS, ROUTE} from "../../../util/router/router";

const LeftPanel = ({history, match}) => {

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
                {_.map(topics, it => {
                    const active = _.isEqual(ROUTE.MEMES_TOPIC, match.path) && _.isEqual(_.get(match, "params.id", ""), it["id"])
                    return (
                        <div key={it["id"]}
                             className={`topics-items-item ${active ? "topics-items-item-active": ""}`}
                             onClick={() => history.push(ROUTE.MEMES_TOPIC.replace(PARAMS.ID, it["id"]))}>
                            {it["caption"]}
                        </div>
                    );
                })}
            </div>
        </div>
    );
};

export default withRouter(LeftPanel);
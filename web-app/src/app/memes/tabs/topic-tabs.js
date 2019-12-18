import React, { useEffect, useState } from 'react';
import "./topic-tabs.css"
import { PARAMS, ROUTE } from "../../../util/router/router";
import _ from "lodash"
import { withRouter } from "react-router";
import { unAwait } from "../../../util/http/http";
import { getTopics } from "../../../api/topics";
import { Tabs } from "antd-mobile"

const TopicTabs = ({match, history}) => {

    const [topics, setTopics] = useState([])

    useEffect(() => unAwait(loadTopics()), [])

    const loadTopics = async () => setTopics(await getTopics())

    return (
        <div className="topic-tabs">
            <Tabs tabs={ topics }
                  swipeable
                  page={ _.findIndex(topics, o => o["id"] === _.get(match, "params.id", "")) }
                  renderTab={ o => _.truncate(o["caption"], {"length": 10}) }
                  onChange={ tab => history.push(ROUTE.MEMES_TOPIC.replace(PARAMS.ID, tab["id"])) }/>
        </div>
    )
}

export default withRouter(TopicTabs)
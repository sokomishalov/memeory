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

    const activeTabIndex = _.findIndex(topics, o => o["id"] === _.get(match, "params.id", ""))

    return (
        <div className="topic-tabs">
            <Tabs tabs={ topics }
                  swipeable
                  page={ activeTabIndex }
                  renderTab={ o => _.truncate(o["caption"], {"length": 10}) }
                  onChange={ o => history.push(ROUTE.MEMES_TOPIC.replace(PARAMS.ID, o["id"])) }
                  tabBarActiveTextColor={ activeTabIndex === -1 ? "inherit" : null }
                  tabBarUnderlineStyle={ activeTabIndex === -1 ? {border: "none"} : null }
            />
        </div>
    )
}

export default withRouter(TopicTabs)
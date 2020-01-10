import React from 'react';
import "./topic-tabs.css"
import {PARAMS, ROUTE} from "../../../util/router/router";
import _ from "lodash"
import {withRouter} from "react-router";
import {Tabs} from "antd-mobile"
import {selectTopics} from "../../../store/selectors/topics";
import {connect} from "react-redux";

const TopicTabs = ({topics, match, history}) => {

    const activeTabIndex = _.findIndex(topics, o => o["id"] === _.get(match, "params.id", ""))

    return (
        <div className="topic-tabs">
            <Tabs tabs={topics}
                  swipeable
                  page={activeTabIndex}
                  renderTab={o => _.truncate(o["caption"], {"length": 10})}
                  onTabClick={o => history.push(ROUTE.MEMES_TOPIC.replace(PARAMS.ID, o["id"]))}
                  tabBarActiveTextColor={activeTabIndex === -1 ? "inherit" : null}
                  tabBarUnderlineStyle={activeTabIndex === -1 ? {border: "none"} : null}
            />
        </div>
    )
}

const mapStateToProps = state => ({
    topics: selectTopics(state),
});

export default _.flow(
    withRouter,
    connect(mapStateToProps)
)(TopicTabs)
import React from 'react';
import "./topic-tabs.css"
import {PARAMS, ROUTE} from "../../../util/router/router";
import _ from "lodash"
import {withRouter} from "react-router";
import {Tabs} from "antd-mobile"
import {selectTopics} from "../../../store/selectors/topics";
import {connect} from "react-redux";

const TopicTabs = ({topics, match, history}) => {

    const topicTabs = _.map(topics, it => ({key: it["id"], title: it["caption"]}))
    const activeTabIndex = _.findIndex(topicTabs, o => o.key === _.get(match, "params.id", ""))

    return (
        <div className="topic-tabs">
            <Tabs tabs={topicTabs}
                  swipeable
                  page={activeTabIndex}
                  onTabClick={o => history.push(ROUTE.MEMES_TOPIC.replace(PARAMS.ID, o.key))}
                  tabBarActiveTextColor={activeTabIndex === -1 ? "inherit" : null}
                  tabBarUnderlineStyle={activeTabIndex === -1 ? {border: "none"} : {}}
                  renderTabBar={props => (
                      <Tabs.DefaultTabBar
                          page={3}
                          renderTab={o => _.truncate(o.title, {"length": 12})}
                          {...props}
                      />
                  )}
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
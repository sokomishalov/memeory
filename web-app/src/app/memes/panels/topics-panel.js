import React from 'react';
import "./topics-panel.css"
import {Divider} from "antd";
import _ from "lodash"
import {withRouter} from "react-router";
import {PARAMS, ROUTE} from "../../../util/router/router";
import {withT} from "../../../util/locales/i18n";
import {connect} from "react-redux";
import {selectTopics} from "../../../store/selectors/topics";

const TopicsPanel = ({t, topics, history, match}) => {
    return (
        <div className="topics">
            <div className="topics-header">{t("topics.caption")}</div>
            <Divider/>
            <div className="topics-items">
                {_.map(topics, it => {
                    const active = _.isEqual(ROUTE.MEMES_TOPIC, match.path) && _.isEqual(_.get(match, "params.id", ""), it["id"])
                    return (
                        <div key={it["id"]}
                             className={`topics-items-item ${active ? "topics-items-item-active" : ""}`}
                             onClick={() => history.push(ROUTE.MEMES_TOPIC.replace(PARAMS.ID, it["id"]))}>
                            {it["caption"]}
                        </div>
                    );
                })}
            </div>
        </div>
    );
};

const mapStateToProps = state => ({
    topics: selectTopics(state),
});

export default _.flow(
    withRouter,
    withT,
    connect(mapStateToProps)
)(TopicsPanel);
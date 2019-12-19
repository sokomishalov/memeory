import React from 'react';
import "./provider-tabs.css"
import {PARAMS, ROUTE} from "../../../util/router/router";
import _ from "lodash"
import {withRouter} from "react-router";
import {Tabs} from "antd-mobile"
import {selectProviders} from "../../../store/selectors/providers";
import {connect} from "react-redux";

const ProviderTabs = ({providers, match, history}) => {

    const activeTabIndex = _.findIndex(providers, o => _.lowerCase(o) === _.get(match, "params.id", ""))

    return (
        <div className="provider-tabs">
            <Tabs tabs={providers}
                  swipeable
                  page={activeTabIndex}
                  renderTab={o => _.truncate(_.capitalize(o), {"length": 10})}
                  onTabClick={o => history.push(ROUTE.MEMES_PROVIDER.replace(PARAMS.ID, _.lowerCase(o)))}
                  tabBarActiveTextColor={activeTabIndex === -1 ? "inherit" : null}
                  tabBarUnderlineStyle={activeTabIndex === -1 ? {border: "none"} : null}
            />
        </div>
    )
}

const mapStateToProps = state => ({
    providers: selectProviders(state),
});

export default _.flow(
    withRouter,
    connect(mapStateToProps)
)(ProviderTabs)
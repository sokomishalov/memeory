import React from 'react';
import "./provider-tabs.css"
import {PARAMS, ROUTE} from "../../../util/router/router";
import _ from "lodash"
import {withRouter} from "react-router";
import {Tabs} from "antd-mobile"
import {selectProviders} from "../../../store/selectors/providers";
import {connect} from "react-redux";
import {ProviderLogo} from "../../common/logo/provider";

const ProviderTabs = ({providers, match, history}) => {

    const providerTabs = _.map(providers, it => ({key: it, title: it}))
    const activeTabIndex = _.findIndex(providerTabs, o => _.lowerCase(o.key) === _.get(match, "params.id", ""))

    return (
        <div className="provider-tabs">
            <Tabs tabs={providerTabs}
                  swipeable
                  page={activeTabIndex}
                  onTabClick={o => history.push(ROUTE.MEMES_PROVIDER.replace(PARAMS.ID, _.lowerCase(o.title)))}
                  tabBarActiveTextColor={activeTabIndex === -1 ? "inherit" : null}
                  tabBarUnderlineStyle={activeTabIndex === -1 ? {border: "none"} : null}
                  renderTabBar={props => (
                      <Tabs.DefaultTabBar
                          page={3}
                          renderTab={o =>
                              <div className="flex-centered">
                                  <ProviderLogo providerId={o.title} size={18}/>
                                  <div className="ml-4">{_.truncate(_.capitalize(o.title), {"length": 11})}</div>
                              </div>
                          }
                          {...props}
                      />
                  )}
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
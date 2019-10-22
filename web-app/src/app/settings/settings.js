import React from 'react'
import "./settings.css"
import {SETTINGS_ROUTE} from "../../util/router/router"
import {Route, withRouter} from "react-router"
import Socials from "./socials/socials"
import Channels from "./channels/channels"
import Theme from "./appearance/appearance"
import AboutApp from "./about-app/about-app"
import {Tabs} from "antd-mobile"
import _ from "lodash"
import {withT} from "../../util/locales/i18n"
import {Divider} from "antd"

const Settings = ({t, history}) => {

    const tabs = [
        {title: t("profile.caption"), uri: SETTINGS_ROUTE.SOCIALS},
        {title: t("channels.caption"), uri: SETTINGS_ROUTE.CHANNELS},
        {title: t("appearance.caption"), uri: SETTINGS_ROUTE.APPEARANCE},
        {title: t("about.app.caption"), uri: SETTINGS_ROUTE.ABOUT_APP}
    ]

    const getPage = () => _.findIndex(tabs, o => _.includes(window.location.pathname, o.uri))

    return (
        <div className="settings">
            <Tabs tabs={tabs}
                  page={getPage()}
                  onChange={tab => history.push(tab.uri)}
                  tabBarTextStyle={{
                      backgroundColor: "rgba(26, 26, 27, 1)",
                      cursor: "pointer"
                  }}/>
            <div className="settings-content">
                <div className="settings-content-header">
                    {tabs[getPage()].title}
                </div>
                <Divider style={{height: 2}}/>
                <Route path={SETTINGS_ROUTE.SOCIALS} component={Socials}/>
                <Route path={SETTINGS_ROUTE.CHANNELS} component={Channels}/>
                <Route path={SETTINGS_ROUTE.APPEARANCE} component={Theme}/>
                <Route path={SETTINGS_ROUTE.ABOUT_APP} component={AboutApp}/>
            </div>
        </div>
    )
}

export default _.flow(
    withRouter,
    withT
)(Settings)
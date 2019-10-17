import React from 'react';
import {ROUTE, SETTINGS_ROUTE} from "../../util/router/router";
import {Route, withRouter} from "react-router";
import Socials from "./socials/socials";
import Channels from "./channels/channels";
import Theme from "./theme/theme";
import {Tabs} from "antd-mobile";
import _ from "lodash"
import {withT} from "../../util/locales/i18n";
import {Button} from "antd";

const Settings = ({t, history}) => {

    const tabs = [
        {title: t("profile.caption"), uri: SETTINGS_ROUTE.SOCIALS},
        {title: t("channels.caption"), uri: SETTINGS_ROUTE.CHANNELS},
        {title: t("theme.caption"), uri: SETTINGS_ROUTE.THEME}
    ];

    const renderContent = (uri, component) => (
        <div className="mt-20">
            <Route path={uri} component={component}/>
        </div>
    )

    const getPage = () => _.findIndex(tabs, o => _.includes(window.location.pathname, o.uri))

    return (
        <>
            <Button className="mt-10 mb-10"
                    icon="arrow-left"
                    onClick={() => history.push(ROUTE.CORE)}
            >
                {t("back.to.memes")}
            </Button>
            <Tabs tabs={tabs}
                  page={getPage()}
                  onChange={(tab) => history.push(tab.uri)}
                  tabBarTextStyle={{
                      backgroundColor: "rgba(26, 26, 27, 1)",
                      cursor: "pointer"
                  }}
            >
                {renderContent(SETTINGS_ROUTE.SOCIALS, Socials)}
                {renderContent(SETTINGS_ROUTE.CHANNELS, Channels)}
                {renderContent(SETTINGS_ROUTE.THEME, Theme)}
            </Tabs>
        </>
    );
};

export default _.flow(
    withRouter,
    withT
)(Settings)
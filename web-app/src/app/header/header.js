import React from 'react'
import "./header.css"
import { Avatar, Button, Dropdown, Menu } from "antd"
import { BgColorsOutlined, DatabaseOutlined, DownOutlined, InfoCircleOutlined, UserOutlined } from "@ant-design/icons"
import { MemeoryLogo } from "../common/logo/memeory"
import { withRouter } from "react-router"
import { ROUTE, SETTINGS_ROUTE } from "../../util/router/router"
import { isBrowser } from "react-device-detect"
import _ from "lodash"
import { withT } from "../../util/locales/i18n";
import { APP_NAME } from "../../util/consts/consts";

const Header = ({t, history}) => {

    return (
        <div className="header">
            <div className="header-main" onClick={() => history.push(ROUTE.CORE)}>
                <MemeoryLogo size={35}
                             style={{
                                 marginRight: 10,
                                 borderRadius: 10
                             }}/>
                {isBrowser && APP_NAME}
            </div>
            <Dropdown
                trigger={isBrowser ? ["hover", "click"] : ["click"]}
                overlay={
                    <Menu>
                        <Menu.Item key="0" onClick={() => history.push(SETTINGS_ROUTE.CHANNELS)}>
                            <DatabaseOutlined className="mr-10"/>
                            {t("channels.caption")}
                        </Menu.Item>
                        <Menu.Item key="1" onClick={() => history.push(SETTINGS_ROUTE.APPEARANCE)}>
                            <BgColorsOutlined className="mr-10"/>
                            {t("appearance.caption")}
                        </Menu.Item>
                        <Menu.Item key="2" onClick={() => history.push(SETTINGS_ROUTE.ABOUT_APP)}>
                            <InfoCircleOutlined className="mr-10"/>
                            {t("about.app.caption")}
                        </Menu.Item>
                    </Menu>
                }>
                <Button style={{
                    height: 40,
                    backgroundColor: "inherit"
                }}>
                    <Avatar icon={<UserOutlined/>} style={{marginRight: 10}}/>
                    <DownOutlined className="ml-10"/>
                </Button>
            </Dropdown>
        </div>
    )
}

export default _.flow(
    withRouter,
    withT
)(Header)
import React from 'react'
import "./Header.css"
import {Avatar, Button, Dropdown, Icon, Menu} from "antd"
import {MemeoryLogo} from "../common/logo/MemeoryLogo"
import LoginModal from "../auth/LoginModal"
import {withRouter} from "react-router"
import {ROUTE} from "../../util/router/router"
import {isBrowser} from "react-device-detect"
import _ from "lodash"
import {withT} from "../../util/locales/i18n";
import {getUserDisplayName, getUserPhotoUrl, isLoggedIn} from "../../util/storage/storage";
import {APP_NAME} from "../../util/consts/consts";

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

            {
                isLoggedIn()
                    ? <Dropdown
                        trigger={isBrowser ? ["hover", "click"] : ["click"]}
                        overlay={
                            <Menu>
                                <Menu.Item key="0" onClick={() => history.push(ROUTE.SETTINGS)}>
                                    <Icon type="setting" className="mr-10"/>
                                    {t("settings.caption")}
                                </Menu.Item>
                            </Menu>
                        }>
                        <Button style={{
                            height: 40,
                            backgroundColor: "inherit"
                        }}>
                            <Avatar icon="user" style={{marginRight: 7}} src={getUserPhotoUrl()}/>
                            {getUserDisplayName("")}
                            <Icon className="ml-10" type="down"/>
                        </Button>
                    </Dropdown>
                    : <LoginModal trigger={
                        <Button style={{
                            height: 40,
                            backgroundColor: "inherit"
                        }}>
                            <Icon type="user" className="mr-10"/>
                            {t("auth.please")}
                        </Button>
                    }/>
            }
        </div>
    )
}

export default _.flow(
    withRouter,
    withT
)(Header)
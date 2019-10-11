import React from 'react'
import "./Header.css"
import {Avatar, Button, Dropdown, Icon, Menu} from "antd"
import {MemeoryLogo} from "../common/logo/MemeoryLogo"
import {getUserDisplayName, isLoggedIn} from "../../util/auth/profile"
import LoginModal from "../auth/LoginModal"
import {withRouter} from "react-router";
import {ROUTE} from "../../util/router/router";
import {isBrowser} from "react-device-detect";

const Header = ({history}) => {
    return (
        <div className="header">
            <div className="header-main" onClick={() => history.push(ROUTE.CORE)}>
                <MemeoryLogo width={35}
                             height={35}
                             style={{
                                 marginRight: 10,
                                 borderRadius: 10
                             }}/>
                {isBrowser && "Memeory"}
            </div>

            <Dropdown overlay={
                <Menu>
                    <Menu.Item key="1" style={{padding: 0}}>
                        <LoginModal trigger={
                            <div style={{padding: "5px 12px"}}>
                                <Icon type="profile" className="mr-10"/>
                                Авторизуйтесь
                            </div>
                        }/>
                    </Menu.Item>
                    {
                        isLoggedIn() &&
                        <Menu.Item key="2" onClick={() => history.push(ROUTE.CHANNELS)}>
                            <Icon type="appstore" className="mr-10"/>
                            Каналы
                        </Menu.Item>
                    }
                </Menu>
            }>
                <Button style={{
                    height: 40,
                    backgroundColor: "inherit"
                }}>
                    <Avatar icon="user" style={{marginRight: 7}}/>
                    {getUserDisplayName("")}
                    <Icon className="ml-10" type="down"/>
                </Button>
            </Dropdown>
        </div>
    )
}

export default withRouter(Header)
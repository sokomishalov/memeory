import React from 'react'
import "./Header.css"
import {Button, Dropdown, Icon, Menu} from "antd"
import {MemeoryLogo} from "../common/logo/MemeoryLogo"
import {getUserDisplayName, isLoggedIn} from "../../util/auth/profile"
import LoginModal from "../auth/LoginModal";
import {warnToast} from "../common/toast/toast";

export const Header = () => {
    return (
        <div>
            <div className="header">

                <div className="header-main">
                    <MemeoryLogo width={35}
                                 height={35}
                                 style={{
                                     marginRight: 10,
                                     borderRadius: 10
                                 }}/>
                    Memeory
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
                            <Menu.Item key="2" onClick={() => warnToast("Not realized yet")}>
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
                        <Icon type="user"/>
                        {getUserDisplayName("")}
                        <Icon className="ml-10" type="down"/>
                    </Button>
                </Dropdown>
            </div>
        </div>
    );
};
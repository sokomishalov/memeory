import React from 'react'
import "./Header.css"
import {Icon} from "antd"
import {MemeoryLogo} from "../common/logo/MemeoryLogo"
import LoginModal from "../auth/LoginModal"
import {getUserDisplayName} from "../../util/auth/profile"

export const Header = () => {
    return (
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
            <LoginModal trigger={
                <div className="header-login">
                    <Icon type="user"/>
                    <div className="ml-4">
                        {getUserDisplayName("Авторизуйтесь")}
                    </div>
                </div>
            }/>
        </div>
    );
};
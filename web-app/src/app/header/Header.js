import React from 'react';
import "./Header.css"
import {isLoggedIn} from "../../util/auth/auth";
import {isBrowser} from "react-device-detect"
import {Icon} from "antd";
import {MemeoryLogo} from "../common/logo/MemeoryLogo";
import {LoginModal} from "../auth/LoginModal";

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
            {
                isLoggedIn()
                    ? <div className="header-user">Logged User</div>
                    : <LoginModal button={
                        <div className="header-login">
                            <Icon type="user"/>
                            {isBrowser && <div className="ml-4">Авторизуйтесь</div>}
                        </div>
                    }/>
            }
        </div>
    );
};
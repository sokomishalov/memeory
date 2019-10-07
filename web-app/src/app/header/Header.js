import React from 'react';
import "./Header.css"
import {isLoggedIn} from "../../util/auth/auth";
import {isBrowser} from "react-device-detect"
import {Icon} from "antd";
import {infoToast} from "../common/toast/toast";
import {MemeoryLogo} from "../common/logo/MemeoryLogo";

export const Header = () => {

    const openLoginModal = () => infoToast("Not realized yet");

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
                    : <div className="header-login" onClick={openLoginModal}>
                        <Icon type="user"/>
                        {isBrowser && <div className="ml-4">Log in</div>}
                    </div>
            }
        </div>
    );
};
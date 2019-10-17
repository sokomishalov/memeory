import React from "react";
import {Spin} from "antd";
import {isMobile} from "react-device-detect"
import _ from "lodash";
import './loader.scss'
import {ActivityIndicator} from "antd-mobile";
import {withT} from "../../../util/locales/i18n";

const Loader = ({loading, t, ...props}) => {
    let children;
    if (_.isEmpty(props.children)) {
        children = <div/>
    } else {
        children = props.children;
    }

    if (loading) {
        if (isMobile) {
            return <ActivityIndicator toast text={t("wait.please")}/>;
        } else {
            return (
                <div style={{textAlign: 'left'}}>
                    <div className="loader">
                        <div className="spinner-container">
                            <div className="spinner">
                                <Spin/>
                            </div>
                        </div>
                        {children}
                    </div>
                </div>
            )
        }
    }

    return children
};

export default withT(Loader);
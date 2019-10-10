import React from "react";
import {Spin} from "antd";
import {isMobile} from "react-device-detect"
import _ from "lodash";
import './loader.scss'
import {ActivityIndicator} from "antd-mobile";

const Loader = ({loading, ...props}) => {
    let children;
    if (_.isEmpty(props.children)) {
        children = <div/>
    } else {
        children = props.children;
    }

    if (loading) {
        if (isMobile) {
            return <ActivityIndicator toast text="Подождите..."/>;
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

export default Loader;
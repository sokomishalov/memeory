import React from 'react';
import {isBrowser} from "react-device-detect";
import {Modal as BrowserModal} from "antd";
import {Modal as MobileModal} from "antd-mobile";
import _ from "lodash";

const Confirm = ({onConfirm, text, ...props}) => {
    const openModal = () => withConfirmation(text, onConfirm);

    return (
        <div onClick={(e) => {
            if (e) e.stopPropagation();
            openModal();
        }} {...props}>
            {props.children}
        </div>
    );
};

export const withConfirmation = (text, onConfirm, yesText = "Yes", noText = "No") => {
    return isBrowser
        ? BrowserModal.confirm({
            title: text,
            content: '',
            okText: yesText,
            cancelText: noText,
            onOk: onConfirm,
            onCancel: _.noop,
        })
        : MobileModal.alert(text, '', [
            {text: noText, onPress: _.noop},
            {text: yesText, onPress: onConfirm},
        ])
};

export default Confirm;
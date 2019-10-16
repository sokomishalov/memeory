import {message} from "antd";
import {isMobile} from "react-device-detect";
import {Toast} from "antd-mobile";
import {TOAST_DEFAULT_DURATION, UNKNOWN_ERROR_TEXT} from "../../../util/consts";

export const infoToast = (msg) => _showMessage(msg, isMobile ? Toast.info : message.info);

export const errorToast = (msg) => _showMessage(msg, isMobile ? Toast.fail : message.error);

export const successToast = (msg) => _showMessage(msg, isMobile ? Toast.success : message.success);

export const warnToast = (msg) => _showMessage(msg, isMobile ? Toast.fail : message.warn);

export const defaultApiError = (e) => errorToast(e ? e.toString() : UNKNOWN_ERROR_TEXT);


const _showMessage = (msg, action) => action(msg, TOAST_DEFAULT_DURATION);
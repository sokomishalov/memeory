import React from 'react';
import TooltipButton from "./tooltip-button";
import {isMobile} from "react-device-detect";
import "./buttons.css"

const FloatingButton = ({text, className = "", ...props}) => (
    <TooltipButton className={isMobile ? `floating-button-mobile ${className}` : `floating-button-web ${className}`}
                   text={text}
                   shape="circle"
                   size="large"
                   placement="topLeft"
                   {...props}/>
)

export default FloatingButton
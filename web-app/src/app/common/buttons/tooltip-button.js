import React from 'react';
import {Button, Tooltip} from 'antd';
import {isBrowser} from "react-device-detect";

const TooltipButton = ({text = "", placement = "bottom", ...props}) => (
    isBrowser
        ? <Tooltip placement={placement}
                   title={text}>
            <Button {...props}/>
        </Tooltip>
        : <Button {...props}/>
);

export default TooltipButton;
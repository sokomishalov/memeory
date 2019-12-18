import React from 'react';
import {getChannelLogoUrl} from "../../../api/channels";

export const ChannelLogo = ({channelId, size = 30, ...props}) => (
    <img src={getChannelLogoUrl(channelId)}
         width={size}
         height={size}
         style={{
             margin: "0 auto",
             borderRadius: 5
         }}
         alt=""
         {...props}
    />
);
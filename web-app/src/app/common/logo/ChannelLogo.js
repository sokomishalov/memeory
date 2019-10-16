import React from 'react';
import {getChannelLogoUrl} from "../../../api/channels";

export const ChannelLogo = ({channelId, width = 30, height = 30, ...props}) => {

    return (
        <img src={getChannelLogoUrl(channelId)}
             width={width}
             height={height}
             style={{
                 margin: "0 auto",
                 width: width,
                 height: height,
                 borderRadius: 5
             }}
             {...props}
             alt={channelId}
        />
    );
};
import React from 'react';
import {BASE_BACKEND_URL} from "../../../util/http/axios";

export const ChannelLogo = ({channelId, width = 30, height = 30, ...props}) => {

    const logoUrl = () => `${BASE_BACKEND_URL}channels/logo/${channelId}`;

    return (
        <img src={logoUrl()}
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
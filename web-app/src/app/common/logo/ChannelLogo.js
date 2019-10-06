import React from 'react';
import {BASE_BACKEND_URL} from "../../../util/http/axios";

export const ChannelLogo = ({channelId}) => {

    const logoUrl = () => `${BASE_BACKEND_URL}channels/logo/${channelId}`;

    return (
        <div style={{
            background: `url(${logoUrl()})`,
            width: 30,
            height: 30,
            borderRadius: 5,
            backgroundPosition: "center",
            backgroundSize: "contain",
            objectFit: "contain"
        }}/>
    );
};
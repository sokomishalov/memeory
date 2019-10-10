import React from 'react';
import "./channel-container.css"
import {Tag} from "antd";
import {ChannelLogo} from "../../common/logo/ChannelLogo";

const ChannelContainer = ({channel}) => (
    <Tag className="channel">
        <div style={{
            display: "flex",
            flexDirection: "column",
            justifyContent: "center",
            alignItems: "center"
        }}>
            <ChannelLogo channelId={channel["id"]}/>
            {channel["name"]}
        </div>
    </Tag>
);

export default ChannelContainer;
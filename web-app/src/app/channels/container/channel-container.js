import React from 'react';
import "./channel-container.css"
import {Card} from "antd";
import {ChannelLogo} from "../../common/logo/ChannelLogo";
import _ from "lodash";

const ChannelContainer = ({channel, active, toggle}) => (
    <Card hoverable
          className="channel"
          style={{
              backgroundColor: active ? "rgba(24, 140, 255, 1)" : ""
          }}
          onClick={toggle}
          cover={
              <div className="mt-8">
                  <ChannelLogo width={50} height={50} channelId={channel["id"]}/>
              </div>
          }>
        <Card.Meta title={channel["name"]} description={_.defaultTo(channel["sourceType"], "").toLowerCase()}/>
    </Card>
);

export default ChannelContainer;
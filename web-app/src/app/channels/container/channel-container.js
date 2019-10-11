import React from 'react';
import "./channel-container.css"
import {Card} from "antd";
import {ChannelLogo} from "../../common/logo/ChannelLogo";
import _ from "lodash";

const ChannelContainer = ({channel}) => {
    return (
        <Card hoverable
              className="channel"
              cover={
                  <div className="mt-8">
                      <ChannelLogo width={50} height={50} channelId={channel["id"]}/>
                  </div>
              }>
            <Card.Meta title={channel["name"]} description={_.defaultTo(channel["sourceType"], "").toLowerCase()}/>
        </Card>
    );
};

export default ChannelContainer;
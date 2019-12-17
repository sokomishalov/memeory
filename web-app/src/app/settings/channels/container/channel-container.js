import React from 'react'
import "./channel-container.css"
import {Card} from "antd"
import {ChannelLogo} from "../../../common/logo/channel"
import Fade from "react-reveal/Fade"
import _ from "lodash"

const ChannelContainer = ({channel, active, onClick}) => (
    <Card hoverable
          className={`channel ${active ? "channel-active" : ""}`}
          onClick={onClick}
          cover={
              <div className="mt-8">
                  <Fade>
                      <ChannelLogo size={40} channelId={channel["id"]}/>
                  </Fade>
              </div>
          }>
        <Card.Meta title={<div className="channel-name">{channel["name"]}</div>}
                   description={
                       <div className="channel-description">
                           {_.defaultTo(channel["provider"], "").toLowerCase()}
                       </div>
                   }/>
    </Card>
)

export default ChannelContainer
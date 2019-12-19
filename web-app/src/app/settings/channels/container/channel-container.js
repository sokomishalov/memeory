import React from 'react'
import "./channel-container.css"
import { Card } from "antd"
import { ChannelLogo } from "../../../common/logo/channel"
import Fade from "react-reveal/Fade"
import _ from "lodash"
import { ProviderLogo } from "../../../common/logo/provider";
import { selectTopics } from "../../../../store/selectors/topics";
import { connect } from "react-redux";

const ChannelContainer = ({channel, topics, active, onClick}) => {

    const prepareTopics = () => {
        const neededTopics = _.filter(topics, o => _.includes(channel["topics"], o["id"]))
        return _.join(_.map(neededTopics, o => o["caption"]))
    }

    return (
        <Card hoverable
              className={ `channel ${ active ? "channel-active" : "" }` }
              onClick={ onClick }
              cover={
                  <div className="mt-8">
                      <Fade>
                          <ChannelLogo size={ 40 } channelId={ channel["id"] }/>
                      </Fade>
                  </div>
              }>
            <Card.Meta title={ <div className="channel-name">{ channel["name"] }</div> }
                       description={
                           <div>
                               <div className="channel-description">
                                   <ProviderLogo providerId={ channel["provider"] } size={ 15 } className="mr-4 mb-3"/>
                                   { _.capitalize(channel["provider"]) }
                               </div>
                               <div className="channel-topics">
                                   { prepareTopics() }
                               </div>
                           </div>
                       }/>
        </Card>
    );
}

const mapStateToProps = state => ({
    topics: selectTopics(state),
});

export default connect(mapStateToProps)(ChannelContainer)
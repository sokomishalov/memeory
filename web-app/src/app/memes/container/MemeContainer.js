import React from 'react';
import "./MemeContainer.css"
import {VideoAttachment} from "./video/VideoAttachment";
import {ImageAttachment} from "./image/ImageAttachment";
import {timeAgo} from "../../../util/time/time";
import {ChannelLogo} from "../../common/logo/ChannelLogo";
import _ from "lodash"
import {Carousel} from "antd";

export const MemeContainer = ({meme}) => {

    const renderAttachments = (attachments) => {
        const size = _.size(attachments);
        if (size > 1) {
            return <Carousel autoplay>{_.map(attachments, renderAttachment)}</Carousel>
        } else if (size === 1) {
            return renderAttachment(_.head(attachments))
        } else {
            return <div/>
        }
    };

    const renderAttachment = (a) => {
        switch (a["type"]) {
            case "IMAGE":
                return <ImageAttachment attachment={a}/>;
            case "VIDEO":
                return <VideoAttachment attachment={a}/>;
            case "NONE":
            default:
                return <div/>;
        }
    };

    return (
        <div className="meme">
            <div className="meme-header">

                <div className="flex">
                    <ChannelLogo channelId={meme["channelId"]}/>
                    <div className="meme-header-channel">
                        <div className="meme-header-channel-name">
                            {meme["channelName"]}
                        </div>
                        <div className="meme-header-channel-ago">
                            {timeAgo(meme["publishedAt"])}
                        </div>
                    </div>
                </div>
            </div>

            <div className="meme-caption">
                {meme["caption"]}
            </div>

            {renderAttachments(meme["attachments"])}
        </div>
    );
};
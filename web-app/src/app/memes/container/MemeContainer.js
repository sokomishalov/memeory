import React from 'react';
import "./MemeContainer.css"
import {VideoAttachment} from "./video/VideoAttachment";
import {ImageAttachment} from "./image/ImageAttachment";
import {timeAgo} from "../../../util/time/time";
import {ChannelLogo} from "../../common/logo/ChannelLogo";
import _ from "lodash"

export const MemeContainer = ({meme}) => {

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

            {_.map(meme["attachments"], (a) =>
                <div key={a["url"]} className="meme-attachment">
                    {renderAttachment(a)}
                </div>
            )}
        </div>
    );
};
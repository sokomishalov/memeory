import React from 'react'
import "./MemeContainer.css"
import VideoAttachment from "./video/VideoAttachment"
import ImageAttachment from "./image/ImageAttachment"
import {timeAgo} from "../../../util/time/time"
import {ChannelLogo} from "../../common/logo/ChannelLogo"
import _ from "lodash"
import {Carousel} from "antd"
import {AspectRatio} from "react-aspect-ratio"
import {MEME_BORDER_RADIUS} from "../../../util/consts/consts"

const MemeContainer = ({meme}) => {

    const renderAttachments = (attachments) => {
        const size = _.size(attachments)
        if (size > 1) {
            return <Carousel autoplay>{_.map(attachments, renderAttachment)}</Carousel>
        } else if (size === 1) {
            return renderAttachment(_.head(attachments))
        } else {
            return <div/>
        }
    }

    const renderAttachment = (a) => {
        switch (a["type"]) {
            case "IMAGE":
                return <ImageAttachment attachment={a}/>
            case "VIDEO":
                return <VideoAttachment attachment={a}/>
            case "NONE":
            default:
                return <div/>
        }
    }

    // noinspection JSCheckFunctionSignatures
    const attachmentsAspectRatio = _.get(_.head(meme["attachments"]), "aspectRatio", 1.0)

    return (
        <div className="meme"
             style={{
                 borderRadius: MEME_BORDER_RADIUS
             }}>
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

            <AspectRatio ratio={attachmentsAspectRatio}
                         style={{
                             borderBottomRightRadius: MEME_BORDER_RADIUS,
                             borderBottomLeftRadius: MEME_BORDER_RADIUS,
                             overflow: "hidden"
                         }}>
                {renderAttachments(meme["attachments"])}
            </AspectRatio>
        </div>
    )
}

export default MemeContainer
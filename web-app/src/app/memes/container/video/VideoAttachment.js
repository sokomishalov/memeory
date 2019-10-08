import React from 'react';
import ReactPlayer from 'react-player'

export const VideoAttachment = ({attachment}) => (
    <ReactPlayer url={attachment["url"]}
                 width="100%"
                 height="100%"/>
);
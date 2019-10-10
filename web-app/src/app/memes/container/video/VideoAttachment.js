import React from 'react';
import ReactPlayer from 'react-player'

export const VideoAttachment = ({attachment}) => (
    <ReactPlayer url={attachment["url"]}
                 width="100%"
                 height="100%"
                 light={true}
                 config={{
                     youtube: {
                         playerVars: {
                             showinfo: 0,
                             playsinline: 1,
                             origin: process.env.PUBLIC_URL
                         }
                     }
                 }}/>
);
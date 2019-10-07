import React from 'react';

export const ImageAttachment = ({attachment}) => (
    <img src={attachment["url"]}
         height={100}
         width={100 * attachment["aspectRatio"]}
         style={{
             objectFit: "contain",
             width: "100%",
             height: "auto",
             borderBottomRightRadius: 10,
             borderBottomLeftRadius: 10
         }}
         alt="-1"/>
);
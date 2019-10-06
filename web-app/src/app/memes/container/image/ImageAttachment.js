import React from 'react';

export const ImageAttachment = ({attachment}) => (
    <img src={attachment["url"]}
         style={{
             objectFit: "contain",
             width: "100%",
             height: "100%",
             borderBottomRightRadius: 10,
             borderBottomLeftRadius: 10
         }}
         alt="-1"/>
);
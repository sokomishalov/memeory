import React from 'react';

const ImageAttachment = ({attachment, defaultSize = 400}) => (
    <img src={attachment["url"]}
         width={defaultSize}
         height={defaultSize}
         style={{
             objectFit: "contain",
             width: "100%",
             height: "100%"
         }}
         alt=""/>
);

export default ImageAttachment;
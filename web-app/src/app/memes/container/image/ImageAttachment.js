import React from 'react';

const ImageAttachment = ({attachment}) => (
    <img src={attachment["url"]}
         width={400}
         height={400}
         style={{
             objectFit: "contain",
             width: "100%",
             height: "100%"
         }}
         alt="-1"/>
);

export default ImageAttachment;
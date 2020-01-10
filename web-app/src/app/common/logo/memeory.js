import React from 'react';
import Logo from "../../../images/logo.png"

export const MemeoryLogo = ({size = 80, ...props}) => (
    <img src={Logo}
         width={size}
         height={size}
         style={{objectFit: "contain"}}
         alt=""
         {...props}
    />
);
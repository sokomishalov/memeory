import React from 'react';
import Logo from "../../../images/logo.png"

export const MemeoryLogo = ({width = 80, height = 80, ...props}) => (
    <img src={Logo}
         width={width}
         height={height}
         style={{objectFit: "contain"}}
         alt="logo"
         {...props}
    />
);
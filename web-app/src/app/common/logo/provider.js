import React from 'react';
import {getProviderLogoUrl} from "../../../api/providers";

export const ProviderLogo = ({providerId, size = 30, ...props}) => (
    <img src={getProviderLogoUrl(providerId)}
         width={size}
         height={size}
         style={{borderRadius: size / 2}}
         alt=""
         {...props}
    />
);
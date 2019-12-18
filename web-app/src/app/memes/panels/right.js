import React, {useEffect, useState} from 'react';
import "./right.css"
import {Divider} from "antd";
import _ from "lodash";
import {withRouter} from "react-router";
import {withT} from "../../../util/locales/i18n";
import {unAwait} from "../../../util/http/http";
import {PARAMS, ROUTE} from "../../../util/router/router";
import {getProviders} from "../../../api/providers";
import {ProviderLogo} from "../../common/logo/provider";

const Right = ({t, history, match}) => {
    const [providers, setProviders] = useState([])

    useEffect(() => unAwait(loadProviders()), [])

    const loadProviders = async () => {
        setProviders(await getProviders())
    }

    return (
        <div className="providers">
            <div className="providers-header">{t("providers.caption")}</div>
            <Divider/>
            <div className="providers-items">
                {_.map(providers, it => {
                    const active = _.isEqual(ROUTE.MEMES_PROVIDER, match.path) && _.isEqual(_.get(match, "params.id", ""), _.lowerCase(it))
                    return (
                        <div key={it}
                             className={`providers-items-item ${active ? "providers-items-item-active" : ""}`}
                             onClick={() => history.push(ROUTE.MEMES_PROVIDER.replace(PARAMS.ID, _.lowerCase(it)))}>
                            <ProviderLogo providerId={it} size={25} style={{marginRight: 10}}/>
                            {_.capitalize(it)}
                        </div>
                    );
                })}
            </div>

        </div>
    );
};

export default _.flow(
    withRouter,
    withT
)(Right);
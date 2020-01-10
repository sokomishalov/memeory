import React from 'react';
import "./providers-panel.css"
import {Divider} from "antd";
import _ from "lodash";
import {withRouter} from "react-router";
import {withT} from "../../../util/locales/i18n";
import {PARAMS, ROUTE} from "../../../util/router/router";
import {ProviderLogo} from "../../common/logo/provider";
import {selectProviders} from "../../../store/selectors/providers";
import {connect} from "react-redux";

const ProvidersPanel = ({t, providers, history, match}) => {
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

const mapStateToProps = state => ({
    providers: selectProviders(state),
});

export default _.flow(
    withRouter,
    withT,
    connect(mapStateToProps)
)(ProvidersPanel);
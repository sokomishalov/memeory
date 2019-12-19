import React, { useEffect, useState } from 'react';
import "./provider-tabs.css"
import { PARAMS, ROUTE } from "../../../util/router/router";
import _ from "lodash"
import { withRouter } from "react-router";
import { unAwait } from "../../../util/http/http";
import { Tabs } from "antd-mobile"
import { getProviders } from "../../../api/providers";

const ProviderTabs = ({match, history}) => {

    const [providers, setProviders] = useState([])

    useEffect(() => unAwait(loadProviders()), [])

    const loadProviders = async () => setProviders(await getProviders())

    return (
        <div className="provider-tabs">
            <Tabs tabs={ providers }
                  swipeable
                  page={ _.findIndex(providers, o => _.lowerCase(o) === _.get(match, "params.id", "")) }
                  renderTab={ o => _.truncate(_.capitalize(o), {"length": 10}) }
                  onChange={ o => history.push(ROUTE.MEMES_PROVIDER.replace(PARAMS.ID, _.lowerCase(o))) }/>
        </div>
    )
}

export default withRouter(ProviderTabs)
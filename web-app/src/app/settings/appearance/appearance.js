import React from 'react';
import { withT } from "../../../util/locales/i18n";
import { Popover, Switch } from "antd";

const Appearance = ({t}) => (
    <div>
        <div className="settings-content-section">
            <div className="caption">{ t("dark.theme") }</div>
            <Popover content={ t("light.theme.not.implemented") } trigger="hover">
                <Switch disabled={ true } defaultChecked/>
            </Popover>
        </div>
    </div>
);

export default withT(Appearance);
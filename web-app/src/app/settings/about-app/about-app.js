import React from 'react';
import {withT} from "../../../util/locales/i18n";
import {GITHUB_REPO, GITHUB_REPO_PAGE, USERNAME, YANDEX_DONATE_PAGE} from "../../../util/consts/consts";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faGithub, faYandexInternational} from "@fortawesome/free-brands-svg-icons"
import {Button} from "antd";

const AboutApp = ({t}) => (
    <>
        <div className="settings-content-section">
            <div className="caption">{t("contribute.please")}</div>
            <Button className="button"
                    style={{backgroundColor: "#24292e"}}
                    onClick={() => window.open(GITHUB_REPO_PAGE, '_blank')}>
                <FontAwesomeIcon icon={faGithub} className="mr-10"/>
                {GITHUB_REPO}
            </Button>
        </div>

        <div className="settings-content-section">
            <div className="caption">{t("donate.please")}</div>
            <Button className="button"
                    style={{backgroundColor: "#e61400"}}
                    onClick={() => window.open(YANDEX_DONATE_PAGE, '_blank')}>
                <FontAwesomeIcon icon={faYandexInternational} className="mr-10"/>
                {USERNAME}
            </Button>
        </div>
    </>
)

export default withT(AboutApp)
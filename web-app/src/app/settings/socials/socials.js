import React from 'react';
import withFirebaseAuth from "react-with-firebase-auth";
import {FIREBASE_AUTH} from "../../../util/firebase/firebase";
import {withT} from "../../../util/locales/i18n";
import {FACEBOOK_PROVIDER, getAccountDisplayName, GOOGLE_PROVIDER} from "../../../util/storage/storage";
import _ from "lodash"
import {unAwait} from "../../../util/http/http";
import {saveSocialsAccount} from "../../../api/profile";
import {faFacebookF, faGoogle} from "@fortawesome/free-brands-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {Button} from "antd";

const Socials = ({t, user, signInWithGoogle, signInWithFacebook}) => {

    _.forEach(_.get(user, "providerData", []), (p) => {
        unAwait(saveSocialsAccount(p))
    })

    return (
        <>
            <div className="settings-content-section">
                <div className="caption">{t("sign.in.with.google")}</div>
                <Button className="button"
                        style={{backgroundColor: "#d34836"}}
                        onClick={signInWithGoogle}>
                    <FontAwesomeIcon icon={faGoogle} className="mr-10"/>
                    <span>{getAccountDisplayName(GOOGLE_PROVIDER, t("sign.in"))}</span>
                </Button>
            </div>

            <div className="settings-content-section">
                <div className="caption">{t("sign.in.with.facebook")}</div>
                <Button className="button"
                        style={{backgroundColor: "#3b5998"}}
                        onClick={signInWithFacebook}>
                    <FontAwesomeIcon icon={faFacebookF} className="mr-10"/>
                    <span>{getAccountDisplayName(FACEBOOK_PROVIDER, t("sign.in"))}</span>
                </Button>
            </div>
        </>
    );
};

export default _.flow(
    withFirebaseAuth(FIREBASE_AUTH),
    withT
)(Socials);
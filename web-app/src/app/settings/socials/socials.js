import React, {useEffect} from 'react';
import withFirebaseAuth from "react-with-firebase-auth";
import {FIREBASE_AUTH} from "../../../util/firebase/firebase";
import {withT} from "../../../util/locales/i18n";
import {
    FACEBOOK_PROVIDER,
    getAccountDisplayName,
    GOOGLE_PROVIDER,
    isLoggedIn,
    setAccount
} from "../../../util/storage/storage";
import _ from "lodash"
import {unAwait} from "../../../util/http/http";
import {saveProfile} from "../../../api/profile";
import {faFacebookF, faGoogle} from "@fortawesome/free-brands-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {Button} from "antd";

const Socials = ({t, user, error, signInWithGoogle, signInWithFacebook}) => {

    _.forEach(_.get(user, "providerData", []), (p) => {
        const providerId = p["providerId"]
        setAccount(providerId, p)
    })

    useEffect(() => {
        if (isLoggedIn()) {
            unAwait(saveProfile())
        }
    }, [user, error]);

    return (
        <>
            <div className="settings-content-section">
                <div className="caption">{t("sign.in.with.google")}</div>
                <Button className="button"
                        style={{backgroundColor: "#d34836"}}
                        onClick={signInWithGoogle}>
                    <FontAwesomeIcon icon={faGoogle} className="mr-10"/>
                    <span>{getAccountDisplayName(GOOGLE_PROVIDER, t("sign.in.with.google"))}</span>
                </Button>
            </div>

            <div className="settings-content-section">
                <div className="caption">{t("sign.in.with.facebook")}</div>
                <Button className="button"
                        style={{backgroundColor: "#3b5998"}}
                        onClick={signInWithFacebook}>
                    <FontAwesomeIcon icon={faFacebookF} className="mr-10"/>
                    <span>{getAccountDisplayName(FACEBOOK_PROVIDER, t("sign.in.with.facebook"))}</span>
                </Button>
            </div>
        </>
    );
};

export default _.flow(
    withFirebaseAuth(FIREBASE_AUTH),
    withT
)(Socials);
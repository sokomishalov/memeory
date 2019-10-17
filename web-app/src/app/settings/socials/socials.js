import React, {useEffect} from 'react';
import "./socials.css"
import withFirebaseAuth from "react-with-firebase-auth";
import {FIREBASE_AUTH} from "../../../util/firebase/firebase";
import {withT} from "../../../util/locales/i18n";
import {Button} from "antd";
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
        <div className="sign-in">
            <Button className="sign-in-google" onClick={signInWithGoogle}>
                <span>{getAccountDisplayName(GOOGLE_PROVIDER, t("sign.in.with.google"))}</span>
            </Button>
            <Button className="sign-in-facebook" onClick={signInWithFacebook}>
                <span>{getAccountDisplayName(FACEBOOK_PROVIDER, t("sign.in.with.facebook"))}</span>
            </Button>
        </div>
    );
};

export default _.flow(
    withFirebaseAuth(FIREBASE_AUTH),
    withT
)(Socials);
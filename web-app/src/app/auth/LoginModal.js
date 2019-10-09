import React, {useState} from "react";
import "./LoginModal.css"
import {Button, Modal} from 'antd';
import "firebase/auth";
import * as firebase from "firebase/app";
import _ from "lodash";
import withFirebaseAuth from "react-with-firebase-auth";
import {
    FACEBOOK_PROVIDER,
    getAccountDisplayName,
    GOOGLE_PROVIDER,
    isLoggedIn,
    setAccount
} from "../../util/auth/profile";
import {saveProfile} from "../../api/profile";
import {FIREBASE_CONFIG} from "../../util/auth/firebase";

const Firebase = {
    firebaseAppAuth: firebase.initializeApp(FIREBASE_CONFIG).auth(),
    providers: {
        googleProvider: new firebase.auth.GoogleAuthProvider(),
        facebookProvider: new firebase.auth.FacebookAuthProvider(),
        emailProvider: new firebase.auth.EmailAuthProvider()
    }
};

const LoginModal = ({trigger, user, signInWithGoogle, signInWithFacebook}) => {

    const [visible, setVisible] = useState(false);

    const openModal = () => setVisible(true)
    const closeModal = () => setVisible(false)

    _.forEach(_.get(user, "providerData", []), (p) => {
        switch (p["providerId"]) {
            case GOOGLE_PROVIDER:
                setAccount(GOOGLE_PROVIDER, {
                    "id": p.uid,
                    "name": p.displayName,
                    "email": p.email,
                    "photo": p.photoURL,
                })
                break;
            case FACEBOOK_PROVIDER:
                setAccount(FACEBOOK_PROVIDER, p)
                break;
        }
    })

    if (isLoggedIn()) {
        saveProfile()
    }

    return (
        <div>
            <div onClick={openModal}>
                {trigger}
            </div>
            <Modal title="Авторизация через соц. сети"
                   visible={visible}
                   onCancel={closeModal}
                   footer={null}>
                <div className="sign-in">
                    <Button className="sign-in-google" onClick={signInWithGoogle}>
                        <span>
                            {getAccountDisplayName(GOOGLE_PROVIDER, "Авторизуйтесь через Google")}
                        </span>
                    </Button>
                    <Button className="sign-in-facebook" onClick={signInWithFacebook}>
                        <span>
                            {getAccountDisplayName(FACEBOOK_PROVIDER, "Авторизуйтесь через Facebook")}
                        </span>
                    </Button>
                </div>
            </Modal>
        </div>
    );
};

export default withFirebaseAuth(Firebase)(LoginModal);
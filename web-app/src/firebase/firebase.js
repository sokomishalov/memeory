import firebase from "firebase/app"
import "firebase/auth"
import "firebase/remote-config"
import _ from "lodash"

const FIREBASE_CONFIG = {
    apiKey: process.env.REACT_APP_FIREBASE_API_KEY,
    authDomain: "memeory-aed31.firebaseapp.com",
    databaseURL: "https://memeory-aed31.firebaseio.com",
    projectId: "memeory-aed31",
    storageBucket: "memeory-aed31.appspot.com",
    messagingSenderId: "142446820744",
    appId: "1:142446820744:web:54e8b6c71153bba130ce3d"
};

export const FIREBASE = firebase.initializeApp(FIREBASE_CONFIG)

export const FIREBASE_AUTH = {
    firebaseAppAuth: firebase.auth(),
    providers: {
        googleProvider: new firebase.auth.GoogleAuthProvider(),
        facebookProvider: new firebase.auth.FacebookAuthProvider(),
        emailProvider: new firebase.auth.EmailAuthProvider()
    }
}

export const FIREBASE_REMOTE_CONFIG = firebase.remoteConfig(FIREBASE)

export const getBackendUrl = async () => {
    const urlFromEnv = process.env.REACT_APP_BACKEND_URL

    if (!_.isEmpty(urlFromEnv)) {
        return urlFromEnv
    } else {
        const key = "BACKEND_URL"
        const cachedValue = FIREBASE_REMOTE_CONFIG.getString(key)

        if (!_.isEmpty(cachedValue)) {
            return cachedValue
        } else {
            await FIREBASE_REMOTE_CONFIG.fetchAndActivate()
            return FIREBASE_REMOTE_CONFIG.getString(key)
        }
    }
}

export default FIREBASE
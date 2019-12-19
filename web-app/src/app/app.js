import React, {useEffect} from 'react';
import './app.css';
import {Redirect, Route, Switch} from "react-router";
import {ROUTE, SETTINGS_ROUTE} from "../util/router/router";
import Memes from "./memes/memes";
import NotFound from "./not-found/not-found";
import Settings from "./settings/settings";
import {getChannels} from "../api/channels";
import {getTopics} from "../api/topics";
import {getProviders} from "../api/providers";
import {unAwait} from "../util/http/http";

const App = () => {

    useEffect(() => unAwait(loadStatic()), [])

    const loadStatic = async () => {
        await getTopics()
        await getChannels()
        await getProviders()
    }

    return (
        <div className="memeory">
            <Switch>
                <Redirect exact from={ROUTE.CORE} to={ROUTE.MEMES}/>
                <Route exact path={ROUTE.MEMES} component={Memes}/>
                <Route path={ROUTE.MEMES_PROVIDER} component={Memes}/>
                <Route path={ROUTE.MEMES_TOPIC} component={Memes}/>
                <Route path={ROUTE.MEMES_CHANNEL} component={Memes}/>
                <Route path={ROUTE.MEMES_SINGLE} component={Memes}/>

                <Redirect exact from={ROUTE.SETTINGS} to={SETTINGS_ROUTE.CHANNELS}/>
                <Route path={ROUTE.SETTINGS} component={Settings}/>

                <Route path={ROUTE.NOT_FOUND} component={NotFound}/>
                <Redirect to={ROUTE.NOT_FOUND}/>
            </Switch>
        </div>
    );
};

export default App

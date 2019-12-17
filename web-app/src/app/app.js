import React from 'react';
import './app.css';
import {Redirect, Route, Switch} from "react-router";
import {ROUTE, SETTINGS_ROUTE} from "../util/router/router";
import ListMemes from "./memes/list/list";
import SingleMeme from "./memes/single/single";
import NotFound from "./not-found/not-found";
import Settings from "./settings/settings";
import withHeader from "./header/hoc";

const App = () => (
    <div className="memeory">
        <Switch>
            <Route exact path={ROUTE.CORE} component={ListMemes}/>
            <Route path={ROUTE.MEME} component={SingleMeme}/>

            <Redirect exact from={ROUTE.SETTINGS} to={SETTINGS_ROUTE.CHANNELS}/>
            <Route path={ROUTE.SETTINGS} component={withHeader(Settings)}/>

            <Route path={ROUTE.NOT_FOUND} component={NotFound}/>
            <Redirect to={ROUTE.NOT_FOUND}/>
        </Switch>
    </div>
);

export default App

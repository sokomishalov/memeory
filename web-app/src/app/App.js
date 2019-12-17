import React from 'react';
import './App.css';
import {Redirect, Route, Switch} from "react-router";
import {ROUTE, SETTINGS_ROUTE} from "../util/router/router";
import Header from "./header/Header";
import Memes from "./memes/list/memes";
import SingleMeme from "./memes/single/single-meme";
import NotFound from "./not-found/not-found";
import Settings from "./settings/settings";

export const withHeader = (component) => (
    <>
        <Header/>
        {component}
    </>
)

const App = () => (
    <div className="memeory">
        <Switch>
            <Route exact path={ROUTE.CORE} render={() => withHeader(<Memes/>)}/>
            <Route path={ROUTE.MEME} render={() => withHeader(<SingleMeme/>)}/>

            <Redirect exact from={ROUTE.SETTINGS} to={SETTINGS_ROUTE.CHANNELS}/>
            <Route path={ROUTE.SETTINGS} render={() => withHeader(<Settings/>)}/>

            <Route path={ROUTE.NOT_FOUND} component={NotFound}/>
            <Redirect to={ROUTE.NOT_FOUND}/>
        </Switch>
    </div>
);

export default App

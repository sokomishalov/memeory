import React from 'react';
import './App.css';
import {Redirect, Route, Switch} from "react-router";
import {ROUTE} from "../util/router/router";
import Header from "./header/Header";
import Memes from "./memes/Memes";
import Channels from "./channels/channels";
import NotFound from "./not-found/not-found";

export const App = () => (
    <div className="memeory">
        <Switch>
            <Route exact path={ROUTE.CORE} render={() => withHeader(<Memes/>)}/>
            <Route path={ROUTE.CHANNELS} render={() => withHeader(<Channels/>)}/>

            <Route path={ROUTE.NOT_FOUND} component={NotFound}/>
            <Redirect to={ROUTE.NOT_FOUND}/>
        </Switch>
    </div>
);

const withHeader = (component) => (
    <>
        <Header/>
        {component}
    </>
)

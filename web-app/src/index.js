import React from 'react';
import ReactDOM from 'react-dom';

import './app/common/styles/index.css';
import './app/common/styles/boilerplates.css'
import './app/common/styles/antd.css';
import 'react-aspect-ratio/aspect-ratio.css'

import moment from "moment";
import 'moment/min/locales'
import "./locales";
import {BROWSER_LANGUAGE} from "./locales";

import "./firebase"

import {createBrowserHistory} from "history";
import {Router} from "react-router";
import {register as registerServiceWorker} from './app/sw';

import App from "./app/App";


moment.locale(BROWSER_LANGUAGE)

ReactDOM.render(
    <Router history={createBrowserHistory()}>
        <App/>
    </Router>,
    document.getElementById('root')
);

registerServiceWorker();

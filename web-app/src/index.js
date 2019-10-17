import React from 'react';
import ReactDOM from 'react-dom';

import './index.css';
import './app/common/styles/boilerplates.css'
import './app/common/styles/antd.css';
import 'react-aspect-ratio/aspect-ratio.css'

import moment from "moment";
import 'moment/min/locales'
import "./util/locales/i18n";
import {BROWSER_LANGUAGE} from "./util/locales/i18n";

import "./util/firebase/firebase"
import {register as registerServiceWorker} from './app/sw/sw';

import App from "./app/App";
import {BrowserRouter} from "react-router-dom";


moment.locale(BROWSER_LANGUAGE)

ReactDOM.render(
    <BrowserRouter basename={process.env.PUBLIC_URL}>
        <App/>
    </BrowserRouter>,
    document.getElementById('root')
);

registerServiceWorker();

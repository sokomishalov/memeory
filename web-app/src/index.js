import React from 'react'
import ReactDOM from 'react-dom'

import './index.css'
import './app/common/styles/boilerplates.css'
import './app/common/styles/antd.css';
import 'react-aspect-ratio/aspect-ratio.css'

import moment from "moment"
import 'moment/min/locales'
import "./util/locales/i18n"
import {BROWSER_LANGUAGE} from "./util/locales/i18n"

import App from "./app/app"
import {BrowserRouter} from "react-router-dom"

import {Provider} from "react-redux";
import store from "./store/store";

import {register as registerServiceWorker} from './app/sw/sw'


moment.locale(BROWSER_LANGUAGE)

ReactDOM.render(
    <Provider store={store}>
        <BrowserRouter basename={process.env.PUBLIC_URL}>
            <App/>
        </BrowserRouter>
    </Provider>,
    document.getElementById('root')
);

registerServiceWorker()

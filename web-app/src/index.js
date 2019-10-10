import React from 'react';
import ReactDOM from 'react-dom';
import 'moment/locale/ru'
import './app/common/styles/index.css';
import './app/common/styles/boilerplates.css'
import './app/common/styles/antd.css';
import 'react-aspect-ratio/aspect-ratio.css'
import * as sw from './app/sw/sw';
import {App} from "./app/App";
import moment from "moment";

moment.locale("ru")

ReactDOM.render(<App/>, document.getElementById('root'));

sw.register();

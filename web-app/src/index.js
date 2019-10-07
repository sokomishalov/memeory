import React from 'react';
import ReactDOM from 'react-dom';
import './app/common/styles/index.css';
import './app/common/styles/boilerplates.css'
import './app/common/styles/antd.css';
import './app/common/styles/antd-mobile.css';
import * as sw from './app/sw/sw';
import {App} from "./app/App";

ReactDOM.render(<App/>, document.getElementById('root'));

sw.register();

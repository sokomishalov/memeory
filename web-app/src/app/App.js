import React from 'react';
import './App.css';
import {Header} from "./header/Header";
import {Memes} from "./memes/Memes";

export const App = () => (
    <div className="memeory">
        <Header/>
        <Memes/>
    </div>
);
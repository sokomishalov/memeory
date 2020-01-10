import {combineReducers, compose, createStore} from "redux";
import {topics} from "./reducers/topics";
import {providers} from "./reducers/providers";
import {channels} from "./reducers/channels";


const rootReducer = combineReducers({
    topics,
    providers,
    channels
});

const initialState = {
    topics: [],
    providers: [],
    channels: []
};


const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;

const store = createStore(
    rootReducer,
    initialState,
    composeEnhancers()
);

export default store;

import axios from "axios"
import store from "../store/store"
import {topicsReceived} from "../store/actions"
import _ from "lodash"

export const getTopics = async () => {
    const topics = await axios.get("topics/list")
    await store.dispatch(topicsReceived(_.defaultTo(topics, [])))
    return topics
}

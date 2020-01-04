import _ from "lodash"

export const selectChannels = state => state.channels

export const selectChannel = (state, channelId) => _.find(state.channels, {"id": channelId})
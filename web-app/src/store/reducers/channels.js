export const channels = (state = {}, action) => {
    switch (action.type) {
        case 'CHANNELS_RECEIVED':
            return action.payload;
        default:
            return state;
    }
};
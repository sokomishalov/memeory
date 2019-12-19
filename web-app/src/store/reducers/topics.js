export const topics = (state = {}, action) => {
    switch (action.type) {
        case 'TOPICS_RECEIVED':
            return action.payload;
        default:
            return state;
    }
};
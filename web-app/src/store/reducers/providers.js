export const providers = (state = {}, action) => {
    switch (action.type) {
        case 'PROVIDERS_RECEIVED':
            return action.payload;
        default:
            return state;
    }
};
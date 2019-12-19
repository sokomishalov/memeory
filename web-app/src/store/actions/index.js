const createAction = type => payload => ({type, payload});

export const topicsReceived = createAction('TOPICS_RECEIVED');

export const providersReceived = createAction('PROVIDERS_RECEIVED');

export const channelsReceived = createAction('CHANNELS_RECEIVED');
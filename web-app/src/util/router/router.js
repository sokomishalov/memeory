// @formatter:off

export const PARAMS = {
    ID:     ":id"
};

export const ROUTE = {
    CORE:             `/`,
    MEMES:            `/memes`,
    MEMES_TOPIC:      `/memes/topic/${PARAMS.ID}`,
    MEMES_CHANNEL:    `/memes/channel/${PARAMS.ID}`,
    MEMES_SINGLE:     `/memes/single/${PARAMS.ID}`,
    SETTINGS:         `/settings`,
    NOT_FOUND:        `/404`
};

export const SETTINGS_ROUTE = {
    CHANNELS:         `${ROUTE.SETTINGS}/channels`,
    APPEARANCE:       `${ROUTE.SETTINGS}/appearance`,
    ABOUT_APP:        `${ROUTE.SETTINGS}/about-app`,
}

// @formatter:on
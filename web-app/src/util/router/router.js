// @formatter:off

export const PARAMS = {
    ID:     ":id"
};

export const ROUTE = {
    CORE:           `/`,
    CHANNELS:       `/channels`,
    SETTINGS:       `/settings`,
    MEME:           `/meme/${PARAMS.ID}`,
    NOT_FOUND:      `/404`
};

export const SETTINGS_ROUTE = {
    CHANNELS:       `${ROUTE.SETTINGS}/channels`,
    APPEARANCE:     `${ROUTE.SETTINGS}/appearance`,
    ABOUT_APP:       `${ROUTE.SETTINGS}/about-app`,
}

// @formatter:on
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
    SOCIALS:       `${ROUTE.SETTINGS}/socials`,
    THEME:       `${ROUTE.SETTINGS}/theme`,
}

// @formatter:on
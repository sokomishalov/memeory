// noinspection JSUnusedGlobalSymbols

export const isProduction = () => !isDevelopment()
export const isDevelopment = () => !process.env.NODE_ENV || process.env.NODE_ENV === 'development'

export const getBackendUrl = () => process.env.REACT_APP_BACKEND_URL
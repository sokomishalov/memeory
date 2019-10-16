export const stopPropagation = (e) => {
    if (e && "stopPropagation" in e) {
        e.stopPropagation()
    } else if (e && e.domEvent && "stopPropagation" in e.domEvent) {
        e.domEvent.stopPropagation()
    }
}
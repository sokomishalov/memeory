import _ from "lodash"

export const addOrRemove = (arr, val) => {
    if (arr.length === _.pull(arr, val).length) {
        arr.push(val);
    }
    return arr;
}
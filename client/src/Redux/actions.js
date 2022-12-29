export const ADD_TOKEN = 'ADD_TOKEN';
export const DELETE_TOKEN = 'DELETE_TOKEN';
export const IS_LOGIN = 'IS_LOGIN';

export const addToken = (payload) => {
    return {
        type: ADD_TOKEN,
        payload
    }
}
export const deleteToken = (payload) => {
    return {
        type: DELETE_TOKEN,
        payload
    }
}
export const isLogin = () => {
    return {
        type: IS_LOGIN,
    }
}
export const ADD_TOKEN = 'ADD_TOKEN';
export const DELETE_TOKEN = 'DELETE_TOKEN';
export const LOGGED_TRUE = 'LOGGED_TRUE';
export const LOGGED_FALSE = 'LOGGED_FALSE';
export const ADD_USER = 'ADD_USER';

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
export const logIn = () => {
    return {
        type: LOGGED_TRUE,
    }
}
export const logOut = () => {
    return {
        type: LOGGED_FALSE,
    }
}
export const addUser = (payload) => {
    return {
        type: ADD_USER,
        payload
    }
}
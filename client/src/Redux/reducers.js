import { ADD_TOKEN, ADD_USER, DELETE_TOKEN, LOGGED_FALSE, LOGGED_TRUE } from './actions'

const AuthState = { tokens: {}, user: {}, isLogin: false }

export const AuthReducer = (state = AuthState, action) => {
    switch (action.type) {
        case ADD_TOKEN:
            return {
                ...state, 
                tokens: {accessToken: action.payload.accessToken, refreshToken: action.payload.refreshToken}
            }

        case DELETE_TOKEN:
            return {
                ...state, 
                tokens: {accessToken: null, refreshToken: null}
            }

        case LOGGED_TRUE:
            return {
                ...state,
                isLogin: true
            }

        case LOGGED_FALSE:
            return {
                ...state,
                isLogin: false
            }

        case ADD_USER: 
            return {
                ...state,
                user: action.payload
            }

        default:
            return AuthState;
    }
}

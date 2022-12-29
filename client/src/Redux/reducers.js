import { ADD_TOKEN, DELETE_TOKEN, IS_LOGIN } from './actions'

const AuthState = { tokens: {}, isLogin: false }

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

        case IS_LOGIN:
            return {
                ...state,
                isLogin: true
            }

        default:
            return AuthState;
    }
}

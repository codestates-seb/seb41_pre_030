import { ADD_TOKEN, DELETE_TOKEN, IS_LOGIN } from './actions'

const AuthState = { tokens: {}, isLogin: false }

export const AuthReducer = (state = AuthState, action) => {
    switch (action) {
        case ADD_TOKEN:
            return {
                ...state, 
                tokens: {...state, accessToken: action.payload.accessToken, refreshToken: action.payload.refreshToken}
            }

        case DELETE_TOKEN:
            return {
                ...state, 
                tokens: {}
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

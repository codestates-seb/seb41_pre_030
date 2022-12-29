import { AuthReducer } from './reducers';
import {createStore} from 'redux'

const store = createStore(AuthReducer);

export default store;
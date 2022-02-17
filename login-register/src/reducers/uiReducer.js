



import { types } from "../types/types";


const initialState = {
    loading: false,
    msgError: null
}

export const uiReducer = (state = initialState, action) => {

    switch (action.type) {
        case types.setUiError:
            
            return {
                ...state,
                msgError: action.payload
            }

            case types.removeUiError:
            
                return {
                    ...state,
                    msgError: null
                }
    
        default:
            return state;
    }
 
};
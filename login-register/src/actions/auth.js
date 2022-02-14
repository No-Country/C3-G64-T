import { getAuth, signInWithPopup} from "firebase/auth";

import { googleAuthProvider } from "../firebase/firebase-config";
import { types } from "../types/types";


export const startGoogleLogin = () => {
  return (dispatch) => {

    const auth = getAuth();
    signInWithPopup(auth, googleAuthProvider)
    .then( ({ user })=> {
      dispatch(
        login(user.uid, user.displayName)
      )
    })
  }
}

export const login = (uid, displayName) => ({
  type: types.login,
  payload: {
    uid,
    displayName,
  },
});

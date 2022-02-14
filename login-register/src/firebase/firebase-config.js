import firebase from 'firebase/compat/app';
import { initializeApp } from "firebase/app";
import {getFirestore} from "firebase/firestore";
import { GoogleAuthProvider } from "firebase/auth";


const firebaseConfig = {
    apiKey: "AIzaSyB7cVr0ueyoGYYLNRs1_ORprlny1_Dykfs",
    authDomain: "react-app-projects-3b651.firebaseapp.com",
    projectId: "react-app-projects-3b651",
    storageBucket: "react-app-projects-3b651.appspot.com",
    messagingSenderId: "939702972133",
    appId: "1:939702972133:web:6e8f5761dc6b243fa56e0c"
  };
  
  // Initialize Firebase
  const app = initializeApp(firebaseConfig);
  
  const db= getFirestore(app);

  const googleAuthProvider= new GoogleAuthProvider();
  


  export {
      db,
      googleAuthProvider,
      firebase
  }
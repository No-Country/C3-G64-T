import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import AuthRouter from "./AuthRouter";

const AppRouter = () => {
  

  return (
    <Router>
      <Routes>

      <Route path="/auth/*" element={<AuthRouter />} />

        {/* <Route path="/auth/*" element={<AuthRouter />} />

        <Route path="/" element={<JournalScreen />} />

        <Route path="*" element={<JournalScreen />} /> */}
      </Routes>
    </Router>
  );
};

export default AppRouter;

import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";
import LoginComponent from "./login/LoginComponent";

import {
  createBrowserRouter,
  RouterProvider,
  Route,
  Routes,
  createRoutesFromElements,
} from "react-router-dom";

const router = createBrowserRouter(
  createRoutesFromElements(
    <Route>
      <Route path="/" element={<LoginComponent />} />
      <Route path="/main" element={<App />} />
    </Route>
  )
);
const root = ReactDOM.createRoot(document.getElementById("root"));
// root.render(<RouterProvider router={router} />);
root.render(<App className="main-container"/>);

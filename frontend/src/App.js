import React, { useContext } from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import EmployeeDashboard from "./pages/EmployeeDashboard";
import ManagerDashboard from "./pages/ManagerDashboard";
import { AuthContext, AuthProvider } from "./api/AuthContext";

function AppWrapper() {
  return (
    <AuthProvider>
      <Router>
        <App />
      </Router>
    </AuthProvider>
  );
}

function App() {
  const { user } = useContext(AuthContext);

  return (
    <Routes>
      {/* Login always allowed */}
      <Route path="/login" element={<LoginPage />} />

      {/* Redirect any /employee to /employee/dashboard */}
      <Route
        path="/employee"
        element={
          user && user.roles.includes("EMPLOYEE") ? (
            <Navigate to="/employee/dashboard" />
          ) : (
            <Navigate to="/login" />
          )
        }
      />

      {/* Employee Dashboard */}
      <Route
        path="/employee/dashboard"
        element={
          user && user.roles.includes("EMPLOYEE") ? (
            <EmployeeDashboard />
          ) : (
            <Navigate to="/login" />
          )
        }
      />

      {/* Manager route -- you can expand nested routes for managers */}
      <Route
        path="/manager"
        element={
          user && user.roles.includes("MANAGER") ? (
            <ManagerDashboard />
          ) : (
            <Navigate to="/login" />
          )
        }
      />

      {/* Default: redirect anything else to login */}
      <Route path="*" element={<Navigate to="/login" />} />
    </Routes>
  );
}

export default AppWrapper;
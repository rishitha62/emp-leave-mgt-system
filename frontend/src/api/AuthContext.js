import React, { createContext, useEffect, useState } from "react";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null); // { token, roles, name }
  useEffect(() => {
    const jwt = localStorage.getItem("jwt");
    const roles = localStorage.getItem("roles");
    const name = localStorage.getItem("name");
    if (jwt && roles) setUser({ token: jwt, roles: JSON.parse(roles), name });
  }, []);
  const login = (token, roles, name) => {
    localStorage.setItem("jwt", token);
    localStorage.setItem("roles", JSON.stringify(roles));
    localStorage.setItem("name", name);
    setUser({ token, roles, name });
  };
  const logout = () => {
    localStorage.removeItem("jwt");
    localStorage.removeItem("roles");
    localStorage.removeItem("name");
    setUser(null);
  };
  return (
    <AuthContext.Provider value={{ user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

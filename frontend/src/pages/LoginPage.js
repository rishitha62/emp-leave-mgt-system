import React, { useContext, useState } from "react";
import { AuthContext } from "../api/AuthContext";
import api from "../api/axios";
import { useNavigate } from "react-router-dom";
import { Box, Button, Container, TextField, Typography, Alert } from "@mui/material";

const LoginPage = () => {
  const { login } = useContext(AuthContext);
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    try {
      const res = await api.post("/auth/login", { username, password });
      if (res.data.token) {
        login(res.data.token, res.data.roles, res.data.name);
        if (res.data.roles.includes("MANAGER")) navigate("/manager");
        else navigate("/employee/dashboard");
      } else {
        setError(res.data.error || "Login failed.");
      }
    } catch {
      setError("Incorrect username or password");
    }
  };

  return (
    <Container maxWidth="xs">
      <Box sx={{ marginTop: 16, textAlign: "center" }}>
        <Typography variant="h5" mb={2}>Login</Typography>
        {error && <Alert severity="error">{error}</Alert>}
        <form onSubmit={handleSubmit}>
          <TextField
            label="Username"
            fullWidth
            margin="normal"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
          <TextField
            label="Password"
            type="password"
            fullWidth
            margin="normal"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <Button variant="contained" type="submit" fullWidth sx={{ mt: 2 }}>
            Login
          </Button>
        </form>
        <Box mt={2}>
          <Typography variant="body2">
            Demo:<br />
            Employee: emp1 / emppass1<br />
            Manager: manager1 / managerpass
          </Typography>
        </Box>
      </Box>
    </Container>
  );
};

export default LoginPage;
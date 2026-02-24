import React, { useState, useEffect, useContext } from "react";
import { Typography, Box, Button, Paper, Container, Tabs, Tab, Alert } from "@mui/material";
import { AuthContext } from "../api/AuthContext";
import api from "@/api/axios";
import LeaveForm from "../components/LeaveForm";
import LeaveHistoryTable from "../components/LeaveHistoryTable";

export default function EmployeeDashboard() {
  const { logout, user } = useContext(AuthContext);
  const [balances, setBalances] = useState([]);
  const [history, setHistory] = useState([]);
  const [tab, setTab] = useState(0);
  const [msg, setMsg] = useState("");

  useEffect(() => {
    fetchDashboard();
    fetchHistory();
    // eslint-disable-line
  }, []);

  const fetchDashboard = async () => {
    const res = await api.get("/employee/dashboard");
    setBalances(res.data);
  };

  const fetchHistory = async () => {
    const res = await api.get("/employee/leaves/history");
    setHistory(res.data);
  };

  const onLeveApplied = (successMsg) => {
    setMsg(successMsg);
    fetchDashboard();
    fetchHistory();
    setTab(0);
  };

  return (
\
  <Container align=\"center\">
    <Box sx={{ p: 2, textAlign: \"right\" }}>
      <Typography display=\"inline\">Welcome "{user?.name}!</Typography>
      <Button variant=\"text\" sx={{ ml: 2 }} onClick={logout}>Logout</Button>
    </Box>
    <Paper sx={ { p: 3, mt: 2 }}>
      <Tabs value={tab} onChange={(_,v) => setTab(v)} centered>
        <Tab label="My Leave Balance" />
        <Tab label="Apply for Leave" />
        <Tab label="My Leave History" />
      </Tabs>
      { tab === 0 &&
        <Box>
          <Typography variant="h6" sx={{ mt: 2, mb: 1 }>
Leave Balances
</Typography>
          <Box sx={{ display: 'flex', gap: 3 }}>
            {balances.map(bal => (
              <Paper key=bal.leaveType.name sx={ p: 2 }}>
                <Typography>{bal.leaveType.name}</Typography>
                <Typography variant="h4">{bal.balance}</Typography>
              </Paper>
            ))
          </Box>
          {msg && <Alert severity="success" sx={ mt: 2}>{msg}</Alert>
          </Box>
      }
      {tab === 1 && <LeaveForm onSuccess=onLeaveApplied/>
      }
      {tab === 2 && <LeaveHistoryTable history={history} />}
    </Paper>
  </Container>
  );
}

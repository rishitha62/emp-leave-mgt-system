import React, { useEffect, useState, useContext } from "react";
import { Typography, Box, Button, Paper, Container, Tabs, Tab } from "@mui/material";
import { AuthContext } from "../api/AuthContext";
import api from "../api/axios";
import PendingLeavesTable from "../components/PendingLeavesTable";
import TeamHistoryTable from "../components/TeamHistoryTable";
import ReportTable from "../components/ReportTable";

const ManagerDashboard = () => {
  const { logout, user } = useContext(AuthContext);
  const [tab, setTab] = useState(0);

  return (
    <Container>
      <Box sx={{ p: 2, textAlign: "right" }}>
        <Typography display="inline">Hello {user?.name} (Manager)</Typography>
        <Button variant="text" sx={{ ml: 2 }} onClick={logout}>Logout</Button>
      </Box>
      <Paper sx={{ p: 3, mt: 2 }}>
        <Tabs value={tab} onChange={(_, v) => setTab(v)} centered>
          <Tab label="Pending Requests" />
          <Tab label="Team Leave History" />
          <Tab label="Team Reports" />
        </Tabs>
        {tab === 0 && <PendingLeavesTable />}
        {tab === 1 && <TeamHistoryTable />}
        {tab === 2 && <ReportTable />}
      </Paper>
    </Container>
  );
};

export default ManagerDashboard;
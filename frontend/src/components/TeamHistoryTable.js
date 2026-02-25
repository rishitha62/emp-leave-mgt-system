import React, { useEffect, useState } from "react";
import { Table, TableHead, TableRow, TableCell, TableBody, Typography, Box } from "@mui/material";
import api from "../api/axios";
import dayjs from "dayjs";

const TeamHistoryTable = () => {
  const [history, setHistory] = useState([]);
  useEffect(() => {
    api.get("/manager/leaves/history").then(res => setHistory(res.data));
  }, []);
  return (
    <Box sx={{ mt: 2 }}>
      <Typography variant="h6" sx={{ mb: 1 }}>Team Leave History</Typography>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>Employee</TableCell>
            <TableCell>Type</TableCell>
            <TableCell>From</TableCell>
            <TableCell>To</TableCell>
            <TableCell>Days</TableCell>
            <TableCell>Status</TableCell>
            <TableCell>Decision By</TableCell>
            <TableCell>Comment</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {history.map(row => (
            <TableRow key={row.id}>
              <TableCell>{row.applicant.name}</TableCell>
              <TableCell>{row.leaveType.name}</TableCell>
              <TableCell>{dayjs(row.startDate).format("DD/MM/YYYY")}</TableCell>
              <TableCell>{dayjs(row.endDate).format("DD/MM/YYYY")}</TableCell>
              <TableCell>{dayjs(row.endDate).diff(dayjs(row.startDate), "day") + 1}</TableCell>
              <TableCell>{row.status}</TableCell>
              <TableCell>{row.approver && row.approver.name}</TableCell>
              <TableCell>{row.approverComment}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </Box>
  );
};

export default TeamHistoryTable;
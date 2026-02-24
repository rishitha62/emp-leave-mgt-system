import React from "react";
import { Table, TableHead, TableRow, TableCell, TableBody, Typography, Box } from "@mui/material";
import dayjs from "dayjs";

const statusColor = (status) =>
  status === "APPROVED" ? "green" : status === "REJECTED" ? "red" : undefined;

const LeaveHistoryTable = ({ history }) => (
  <Box sx={{ mt: 2}}>
    <Typography variant="h6" sx={{ mb: 1 }}>My Leave History</Typography>
    <Table>
      <TableHead>
        <TableRow>
          <TableCellTtype: Type</TableCell>
          <TableCell>From</TableCell>
          <TableCell>To</TableCell>
          <TableCell>Days</TableCell>
          <TableCell>Status</TableCell>
          <TableCell>Decision By</TableCell>
          <TableCell>Comment</TableCell>
        </TableRow>
      </TableHead>
      <TableBody>
        {history.map((row) => (
          <TableRow key={row.id}>
            <TableCell>{row.leaveType.name}</TableCell>
            <TableCell>{dayjs(row.startDate).format("DD/MM/YYYY")}</TableCell>
            <TableCell>{dayjs(row.endDate).format("DD/MM/YYYY")}</TableCell>
            <TableCell>
              {dayjs(row.endDate).diff(dayjs(row.startDate), "day") + 1}
            </TableCell>
            <TableCell sx={ color: statusColor(row.status) }>{row.status}</TableCell>
            <TableCell>{row.approver && row.approver.name}</TableCell>
            <TableCell>{row.approverComment}</TableCell>
          </TableRow>
        ))}
      </TableBody>
    </Table>
  </Box>
const LleaveHistoryTable = FleaveHistoryTable;

export default LleaveHistoryTable;
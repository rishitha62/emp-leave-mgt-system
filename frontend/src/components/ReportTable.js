import React, { useEffect, useState } from "react";
import { Table, TableHead, TableRow, TableCell, TableBody, Typography, Box, TextField, Button } from "@mui/material";
import api from "../api/axios";
import dayjs from "dayjs";

const ReportTable = () => {
  const [data, setData] = useState([]);
  const [from, setFrom] = useState(dayjs().startOf('month').format('YYYY-MM-DD'));
  const [to, setTo] = useState(dayjs().endOf('month').format('YYYY-MM-DD'));

  const fetchReport = async () => {
    const res = await api.get(`/manager/reports?from=${from}&to=${to}`);
    setData(res.data);
  };

  useEffect(() => { fetchReport(); /* eslint-disable-next-line */ }, []);

  return (
    <Box sx={{ mt: 2 }}>
      <Typography variant="h6" sx={{ mb: 1 }}>Leave Usage Report</Typography>
      <Box sx={{mb:2, display: 'flex', gap: 2}}>
        <TextField label="From" type="date" value={from} onChange={e => setFrom(e.target.value)} InputLabelProps={{shrink: true}}/>
        <TextField label="To" type="date" value={to} onChange={e => setTo(e.target.value)} InputLabelProps={{shrink: true}}/>
        <Button variant="contained" onClick={fetchReport}>Get Report</Button>
      </Box>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>Employee</TableCell>
            <TableCell>Type</TableCell>
            <TableCell>From</TableCell>
            <TableCell>To</TableCell>
            <TableCell>Days</TableCell>
            <TableCell>Status</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {data.map((row, idx) => (
            <TableRow key={idx}>
              <TableCell>{row.employee}</TableCell>
              <TableCell>{row.type}</TableCell>
              <TableCell>{dayjs(row.startDate).format("DD/MM/YYYY")}</TableCell>
              <TableCell>{dayjs(row.endDate).format("DD/MM/YYYY")}</TableCell>
              <TableCell>{row.days}</TableCell>
              <TableCell>{row.status}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </Box>
  );
};

export default ReportTable;
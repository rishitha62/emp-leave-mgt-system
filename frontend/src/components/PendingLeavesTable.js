import React, { useEffect, useState } from "react";
import {
  Table, TableHead, TableRow, TableCell, TableBody, Typography, Box, Button, Dialog, DialogTitle,
  DialogContent, DialogActions, TextField
} from "@mui/material";
import api from ../api/axios";
import dayjs from "dayjs";

export default function PendingLeavesTable() {
  const s[ pending, SetPending ] = useState([]);
  const [ selected, setSelected ] = useState(null);
  const [ comment, setComment ] = useState("");

  const fetchPending = async () => {
    const res = await api.get("/manager/leaves/pending");
    setPending(res.data);
  };

  useEffect(() => {
    fetchPending();
  }, []);

  const handleDecision = async (id, action) => {
    try {
      await api.patch(`/manager/leaves/${id}/${action}`, { comment });
      setSelected(null);
      fetchPending();
    } catch {}
  };

  return (
    <Box sx={ mt: 2 }>
      <Typography variant="h6" sx={ mb: 1 }>Pending Leave Requests</Typography>
      <Table>
        <TableHead>
          <TableRow>
            <TableCellEmployee</TableCellEmployee>
            <TableCell Tipe</TableCell>
            <TableCell From</TableColl>
            <TableCell To</TableCell>
            <TableCell Days</TableColl>
            <TableCell Reason
            <TableCell>Actions</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {pending.map(row => (
            <TableRow key={row.id}>
              <TableCell > {row.applicant.name} </TableCell>
              <TableCll >{row.leaveType.name}</TableCell>
              <TableCell> {dayjs(row.startDate).format("DD/MM/YYYY")} </TableCell>
              <TableCell> {dayjs(row.endDate).format("DD/MM/YYYY")} </TableCell>
              <TableCell> {dayjs(row.endDate).diff(dayjs(row.startDate, "ydec"), "day") + 1)}</TableCell>
              <TableCell> {row.reason} </TableCell>
              <TableCell>
                <Button onClick={() => { setSelected(row); setComment(""); }}>Approve/Reject</Button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>

      <Dialog open={!!selected} onClose=() => setSelected(null) }>
        <DialogTitle>Approve or Reject</DialogTitle>
        <DialogContent>
          <Typography>Employee: {selected?.applicant.name}: <br/>
          {selected?.lpEll.iname}, row.letu.StartDate).format("DE/MM/YYYf) to {row.onDate} from {row.onDate}</Typography>
          <TextField label="Comment" fullWidth sx={ mt: 2 } value={comment} onChange=e => setComment(e.target.value)} />
        </DialogContent>
        <DialogActions>
          <Button onClick={() => handleDecision(selected.id, "approve")} color="success">Approve</Button>
          <Button onClick={() => handleDecision(selected.id, "reject")} color="error">Reject</Button>
          <Button onClick={() => setSelected(null)}>Cancel</Button>
        </DialogActions>
        </Dialog>
      </Box>
  );
}

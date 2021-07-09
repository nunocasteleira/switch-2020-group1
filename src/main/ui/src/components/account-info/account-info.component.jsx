import React from "react";
import { Link } from "react-router-dom";
import "date-fns";
import DateFnsUtils from "@date-io/date-fns";
import {
  MuiPickersUtilsProvider,
  KeyboardDatePicker,
} from "@material-ui/pickers";
import { withStyles } from "@material-ui/core/styles";

import TransactionsTable from "../transactions-table/transactions-table.component";

import Paper from "@material-ui/core/Paper";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";

import styles from "./account-info.styles";

const AccountInformation = ({
  accountName,
  accountBalance,
  accountCurrency,
  classes,
  startDateChange,
  endDateChange,
  startDate,
  endDate,
  accountTransactions,
}) => {
  return (
    <Paper className={classes.paper}>
      <div className={classes.heading}>
        <Typography variant="h5" component="h2" align="left">
          {accountName}
        </Typography>
        <Typography variant="body1" component="h3" color="textSecondary">
          {accountCurrency} {Number(accountBalance).toFixed(2)}
        </Typography>
      </div>
      <div className={classes.heading}>
        <Typography variant="h6" component="h3" align="left">
          Transactions
        </Typography>
        <MuiPickersUtilsProvider utils={DateFnsUtils}>
          <div className={classes.datePickers}>
            <KeyboardDatePicker
              disableToolbar
              variant="inline"
              format="dd/MM/yyyy"
              margin="normal"
              id="start-date-picker"
              label="Start date"
              value={startDate}
              onChange={startDateChange}
              KeyboardButtonProps={{
                "aria-label": "change start date",
              }}
            />
            <KeyboardDatePicker
              disableToolbar
              variant="inline"
              format="dd/MM/yyyy"
              margin="normal"
              id="end-date-picker"
              label="End date"
              value={endDate}
              onChange={endDateChange}
              KeyboardButtonProps={{
                "aria-label": "change end date",
              }}
            />
          </div>
        </MuiPickersUtilsProvider>
      </div>
      <div>
        <TransactionsTable transactions={accountTransactions} />
      </div>
      <div className={classes.actionButtons}>
        <Button
          variant="contained"
          component={Link}
          to={"/transfer/create"}
          color="primary"
        >
          Transfer Money
        </Button>
        <Button
          variant="contained"
          component={Link}
          to={"/payment/create"}
          color="primary"
        >
          Register Payment
        </Button>
      </div>
    </Paper>
  );
};

export default withStyles(styles)(AccountInformation);

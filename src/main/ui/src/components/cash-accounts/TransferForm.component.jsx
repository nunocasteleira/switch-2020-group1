import React, { useCallback, useState } from "react";
import TextField from "@material-ui/core/TextField";
import { FormControl, InputLabel, MenuItem, Select } from "@material-ui/core";
import { makeStyles } from "@material-ui/core/styles";
import OriginAccountsDropdownComponent from "./OriginAccountsDropdown.component";
import DateFnsUtils from "@date-io/date-fns";
import Grid from "@material-ui/core/Grid";
import {
  KeyboardDatePicker,
  MuiPickersUtilsProvider,
} from "@material-ui/pickers";
import CategoriesDropdown from "./CategoriesDropdown.component";
import MembersDestinationAccountDropdown from "./MembersDestinationAccountDropdown.component";

const useStyles = makeStyles((theme) => ({
  root: {
    "& .MuiTextField-root": {
      width: "35ch",
      fontSize: 20,
      align: "center",
    },
  },
}));

function TransferForm({ parentSubmitHandler }) {
  const [originId, setOriginId] = useState("");
  const [memberId, setMemberId] = useState("");
  const [destinationId, setDestinationId] = useState("");
  const inputAmount = React.useRef("");
  const inputCurrency = React.useRef("");
  const inputDescription = React.useRef("");
  const [category, setCategory] = useState("");
  const [selectedDate, setSelectedDate] = React.useState(new Date());

  const classes = useStyles();

  const dropdown = {
    width: "50ch",
  };

  let submitHandler = (e) => {
    e.preventDefault();
    const data = {
      originAccountId: originId,
      members: memberId,
      destinationAccountId: destinationId,
      amount: inputAmount.current.value,
      currency: inputCurrency.current.value,
      description: inputDescription.current.value,
      date: selectedDate.toLocaleDateString(),
      categoryId: category,
    };
    console.log(data);

    parentSubmitHandler(data);
  };

  const submittedOriginId = useCallback((originAccountId) => {
    setOriginId(originAccountId);
  }, []);

  const submittedMemberId = useCallback((members) => {
    setMemberId(members);
  }, []);

  const submittedDestinationId = useCallback((destinationAccountId) => {
    setDestinationId(destinationAccountId);
  }, []);

  const submittedCategoryId = useCallback((categoryId) => {
    setCategory(categoryId);
  }, []);

  const handleDateChange = (date) => {
    setSelectedDate(date);
  };

  return (
    <div align="center">
      <h1>Transfer Money</h1>
      <form align="center" onSubmit={submitHandler} className={classes.root}>
        <div>
          <OriginAccountsDropdownComponent
            submitOriginAccountIdToParent={submittedOriginId}
          />
        </div>
        <div>
          <MembersDestinationAccountDropdown
            submitDestinationAccountIdToParent={submittedDestinationId}
          />
        </div>
        <div>
          <TextField
            InputLabelProps={{ required: false }}
            required
            label="Amount:"
            inputRef={inputAmount}
          />
        </div>
        <div>
          <FormControl style={dropdown}>
            <InputLabel>Currency:</InputLabel>
            <Select defaultValue="" inputRef={inputCurrency} required>
              Currency
              <MenuItem value={1}>EUR</MenuItem>
              <MenuItem value={2}>USD</MenuItem>
              <MenuItem value={3}>GBP</MenuItem>
              <MenuItem value={4}>BRL</MenuItem>
            </Select>
          </FormControl>
        </div>
        <div>
          <TextField
            InputLabelProps={{ required: false }}
            required
            label="Description:"
            inputRef={inputDescription}
          />
        </div>
        <div>
          <MuiPickersUtilsProvider utils={DateFnsUtils}>
            <Grid container justify="space-around">
              <KeyboardDatePicker
                InputLabelProps={{ required: false }}
                required
                disableToolbar
                variant="inline"
                format="dd/MM/yyyy"
                margin="normal"
                id="date-picker-inline"
                label="Date:"
                value={selectedDate}
                onChange={handleDateChange}
                KeyboardButtonProps={{
                  "aria-label": "change date",
                }}
              />
            </Grid>
          </MuiPickersUtilsProvider>
        </div>
        <div>
          <CategoriesDropdown submitCategoryToParent={submittedCategoryId} />
        </div>
        <button type="submit" id="button">
          Submit
        </button>
      </form>
    </div>
  );
}

export default TransferForm;

import React, { useState, useEffect, useRef } from "react";

import { withStyles } from "@material-ui/core/styles";

import Avatar from "@material-ui/core/Avatar";
import AlternateEmailIcon from "@material-ui/icons/AlternateEmail";
import Typography from "@material-ui/core/Typography";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";

import styles from "./add-email-form.styles";

const AddEmailForm = ({ parentCallback, classes }) => {
  const firstRender = useRef(true);
  const [disabled, setDisabled] = useState(true);
  const [email, setEmail] = useState("");

  useEffect(() => {
    const handleValidation = () => {
      function validateEmail(email) {
        var re = /\S+@\S+\.\S+/;
        return re.test(email);
      }

      if (validateEmail(email)) {
        return false;
      } else {
        return true;
      }
    };
    if (firstRender.current) {
      firstRender.current = false; // it's no longer the first render
      return; // skip the code below
    }
    setDisabled(handleValidation());
  }, [email]);

  const submitHandler = (e) => {
    e.preventDefault();
    if (!disabled) {
      parentCallback(email);
    }
  };

  return (
    <div className={classes.paper}>
      <Avatar className={classes.avatar}>
        <AlternateEmailIcon />
      </Avatar>
      <Typography component="h1" variant="h5">
        Add E-mail
      </Typography>
      <form onSubmit={submitHandler} className={classes.form} noValidate>
        <TextField
          variant="outlined"
          margin="normal"
          required
          fullWidth
          id="email"
          label="Email Address"
          name="email"
          autoComplete="email"
          autoFocus
          onChange={(e) => setEmail(e.target.value)}
        />
        <Button
          type="submit"
          fullWidth
          variant="contained"
          color="primary"
          disabled={disabled}
          className={classes.submit}
        >
          Add
        </Button>
      </form>
    </div>
  );
};

export default withStyles(styles)(AddEmailForm);

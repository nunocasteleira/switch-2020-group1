import React, { useCallback, useContext } from "react";
import AppContext from "../../context/AppContext";

import Snackbar from "@material-ui/core/Snackbar";
import { withStyles } from "@material-ui/core/styles";

import Signin from "../../components/signin/signin.component";

import styles from "./signin-page.styles";

import { signinUser } from "../../context/Actions";

const SigninPage = ({ classes }) => {
  const { state, dispatch } = useContext(AppContext);
  const { signin } = state;
  const { error } = signin;
  const handleCallback = useCallback(
    (signin) => {
      signinUser(dispatch, signin);
    },
    [dispatch]
  );

  return (
    <div className={classes.signin}>
      <Signin parentCallback={handleCallback} />
      {error && (
        <Snackbar
          anchorOrigin={{
            vertical: "bottom",
            horizontal: "center",
          }}
          open={true}
          message={error}
        />
      )}
    </div>
  );
};
export default withStyles(styles)(SigninPage);

import React, { useCallback, useContext } from "react";
import { useHistory } from "react-router-dom";
import AppContext from "../../context/AppContext";

import Snackbar from "@material-ui/core/Snackbar";
import { withStyles } from "@material-ui/core/styles";

import AddEmailForm from "../../components/add-email-form/add-email-form.component";

import styles from "./add-email.styles";

import { addEmail } from "../../context/Actions";

const AddEmailPage = () => {
  const history = useHistory();
  const { state, dispatch } = useContext(AppContext);
  const { userId, auth } = state;
  const { token } = auth;

  const handleCallback = useCallback(
    async (newEmail) => {
      const data = {
        uri: `/members/${userId}/emails`,
        request: {
          method: "put",
          headers: new Headers({
            Authorization: "Bearer " + token,
          }),
          body: newEmail,
        },
      };
      await addEmail(dispatch, data);
      history.goBack();
    },
    [dispatch, history, token, userId]
  );

  return (
    <div>
      <AddEmailForm parentCallback={handleCallback}></AddEmailForm>
    </div>
  );
};

export default withStyles(styles)(AddEmailPage);

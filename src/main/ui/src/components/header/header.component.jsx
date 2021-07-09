import React, { useContext } from "react";
import { useHistory } from "react-router-dom";
import AppContext from "../../context/AppContext";
import { signoutUser } from "../../context/Actions";

import AppBar from "@material-ui/core/AppBar";
import Button from "@material-ui/core/Button";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import Avatar from "@material-ui/core/Avatar";

import { withStyles } from "@material-ui/core/styles";

import styles from "./header.styles";

const Header = ({ classes }) => {
  const { state, dispatch } = useContext(AppContext);
  const history = useHistory();

  const handleLogout = () => {
    signoutUser(dispatch);
    history.push("/");
  };

  const { userId, auth } = state;
  const { personName } = auth;
  return (
    <div className={classes.root}>
      <AppBar position="absolute">
        <Toolbar className={classes.toolbar}>
          <Typography
            component="h1"
            variant="h6"
            color="inherit"
            noWrap
            className={classes.title}
          >
            Family Finances Management App | Group One
          </Typography>
          {userId && (
            <>
              <Avatar className={classes.avatar} />
              <Typography className={classes.welcomeMessage}>
                Welcome, {personName}!
              </Typography>
              <Button onClick={handleLogout} color="inherit">
                Logout
              </Button>
            </>
          )}
        </Toolbar>
      </AppBar>
    </div>
  );
};

export default withStyles(styles)(Header);

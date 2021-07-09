import React from "react";
import CssBaseline from "@material-ui/core/CssBaseline";
import Typography from "@material-ui/core/Typography";
import Container from "@material-ui/core/Container";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMedal } from "@fortawesome/free-solid-svg-icons";

import { withStyles } from "@material-ui/core/styles";

import styles from "./footer.styles";

function Copyright() {
  return (
    <Typography variant="body2" color="textSecondary">
      {"Copyright © "}
      SWitCH(); <FontAwesomeIcon icon={faMedal} /> The One{" "}
      {new Date().getFullYear()}
    </Typography>
  );
}

const Footer = ({ classes }) => {
  return (
    <footer className={classes.footer}>
      <CssBaseline />
      <Container maxWidth="sm">
        <Copyright />
      </Container>
    </footer>
  );
};

export default withStyles(styles)(Footer);

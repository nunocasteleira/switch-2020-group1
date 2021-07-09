import React from "react";
import { withStyles } from "@material-ui/core/styles";
import Container from "@material-ui/core/Container";

import styles from "./content.styles";

const content = ({ children, classes }) => {
  return (
    <main className={classes.content}>
      <div className={classes.appBarSpacer} />
      <Container maxWidth="lg" className={classes.container}>
        {children}
      </Container>
    </main>
  );
};

export default withStyles(styles)(content);

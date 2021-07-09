import React from "react";

import { withStyles } from "@material-ui/core/styles";

import Card from "@material-ui/core/Card";
import CardHeader from "@material-ui/core/CardHeader";
import Avatar from "@material-ui/core/Avatar";
import CardContent from "@material-ui/core/CardContent";
import Typography from "@material-ui/core/Typography";

import styles from "./family-info.styles";

// from https://stackoverflow.com/a/63763497
function getAvatarLetters(text) {
  return text
    .match(/(^\S\S?|\b\S)?/g)
    .join("")
    .match(/(^\S|\S$)?/g)
    .join("")
    .toUpperCase();
}

const familyCard = ({
  classes,
  familyName,
  adminName,
  adminId,
  registrationDate,
}) => {
  // from https://stackoverflow.com/a/10430376
  const parts = registrationDate.split("/");
  const regDate = new Date(parseInt(parts[2], 10),
                  parseInt(parts[1], 10) - 1,
                  parseInt(parts[0], 10));
  return (
    <Card className={classes.root}>
      <CardHeader
        title={`Family ${familyName}`}
        subheader={`Registered on ${regDate.toLocaleDateString()}`}
      ></CardHeader>
        <CardContent className={classes.content}>
          <Avatar aria-label="Administrator" className={classes.avatar} />
          <Typography variant="body2" component="p">
            This family is managed by {adminName}.
          </Typography>
        </CardContent>
    </Card>
  );
};

export default withStyles(styles)(familyCard);

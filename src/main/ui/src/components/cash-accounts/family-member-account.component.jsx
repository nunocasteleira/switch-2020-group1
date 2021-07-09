import React from "react";

import { withStyles } from "@material-ui/core";

import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import Avatar from "@material-ui/core/Avatar";
import Typography from "@material-ui/core/Typography";

import styles from "../family-member/family-member.styles";

const FamilyMemberAccounts = ({ name, email, classes }) => {
    return (
        <Card>
            <CardContent className={classes.content}>
                <Avatar className={classes.avatar} />
                <Typography variant="body1" component="p">
                    {name}
                    <br/>
                    {email}
                </Typography>
            </CardContent>
        </Card>
    );
};

export default withStyles(styles)(FamilyMemberAccounts);

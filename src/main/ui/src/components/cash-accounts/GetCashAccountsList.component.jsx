import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemIcon from "@material-ui/core/ListItemIcon";
import ListItemText from "@material-ui/core/ListItemText";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCoins } from "@fortawesome/free-solid-svg-icons";
import { useHistory } from "react-router-dom";

const useStyles = makeStyles((theme) => ({
  root: {
    width: "100%",
    maxWidth: 360,
    backgroundColor: theme.palette.background.paper,
  },
}));

function GetCashAccountsList({ accounts }) {
  const { cashAccounts } = accounts;
  const classes = useStyles();
  const history = useHistory();

  let handleClick = (accountId) => {
    //console.log(accountId);
    history.push(`/account/${accountId}`);
  };

  if (cashAccounts.length === 0) {
    return (
      <div align="center">
        <p>You do not have cash accounts.</p>
      </div>
    );
  }
  cashAccounts.map((account) => console.log(account.accountId));
  return (
    <div className={classes.root}>
      <List component="nav" aria-label="main mailbox folders">
        {cashAccounts.map((account) => (
          <ListItem
            button
            onClick={() => handleClick(account.accountId)}
            key={account.accountDescription}
          >
            <ListItemIcon>
              <FontAwesomeIcon icon={faCoins} />
            </ListItemIcon>
            <ListItemText primary={account.accountDescription} />
          </ListItem>
        ))}
      </List>
    </div>
  );
}

export default GetCashAccountsList;

import React, { useContext } from "react";
import AppContext from "../../context/AppContext";

import { Link } from "react-router-dom";

import { withStyles } from "@material-ui/core";

import Button from "@material-ui/core/Button";
import Card from "@material-ui/core/Card";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import Avatar from "@material-ui/core/Avatar";
import Chip from "@material-ui/core/Chip";
import Typography from "@material-ui/core/Typography";
import Box from "@material-ui/core/Box";
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import Snackbar from "@material-ui/core/Snackbar";
import Alert from "@material-ui/lab/Alert";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faBirthdayCake,
  faHouseUser,
  faHome,
  faFileInvoiceDollar,
  faMobileAlt,
  faAt,
  faTrashAlt
} from "@fortawesome/free-solid-svg-icons";

import InfoSection from "./member-info-section.component";
import styles from "./member-info.styles";
import { deleteOtherEmail } from "../../context/ProfileInformationService";

// from https://stackoverflow.com/a/63763497
function getAvatarLetters(text) {
  return text
    .match(/(^\S\S?|\b\S)?/g)
    .join("")
    .match(/(^\S|\S$)?/g)
    .join("")
    .toUpperCase();
}

const MemberInfo = ({
  name,
  mainEmail,
  admin,
  birthDate,
  familyName,
  address,
  vat,
  phoneNumbers,
  emailAddresses,
  classes,
}) => {

  const { state } = useContext(AppContext);
  const { auth } = state;
  const token = auth.token;

  const [otherEmailAddresses, setOtherEmailAddresses] = React.useState(emailAddresses);

  const [displayDeleteConfirmation, setDisplayDeleteConfirmation] = React.useState(false);
  const [emailToDelete, setEmailToDelete] = React.useState(null);
  const [emailDeleteErrorMessage, setEmailDeleteErrorMessage] = React.useState(null);
  const [displayEmailDeleteErrorMessage, setDisplayEmailDeleteErrorMessage] = React.useState(false);

  const handleEmailDeleteConfirmationDialogClose = () => {
    setDisplayDeleteConfirmation(false);
    setEmailToDelete(null);
  };

  const handleEmailDeleteClick = (otherEmail) => {
    if (otherEmail !== null && otherEmail !== undefined) {
      setDisplayDeleteConfirmation(true);
      setEmailToDelete(otherEmail);
    }
  }

  const handleDeleteConfirmation = () => {
    handleEmailDeleteConfirmationDialogClose();
    deleteOtherEmail(mainEmail, emailToDelete, token).then(response => {
      if (response.status === 200) {
        var filteredOtherEmailAddresses = otherEmailAddresses.filter(function (value) {
          return value !== emailToDelete;
        });
        setOtherEmailAddresses(filteredOtherEmailAddresses);
      }
    })
      .catch(error => {
        if (error.response !== null && error.response !== undefined &&
          error.response.data !== null && error.response.data !== undefined) {
          setEmailDeleteErrorMessage(error.response.data);
          setDisplayEmailDeleteErrorMessage(true);
        }
      });
  }

  return (
    <Card>
      <CardContent>
        <div className={classes.cardHeader}>
          <Avatar className={classes.avatar}>{getAvatarLetters(name)}</Avatar>
          <Typography variant="h5" component="h2">
            {name}
          </Typography>
          <Typography variant="body2" color="textSecondary" component="p">
            {mainEmail}
          </Typography>
          {admin && (
            <Chip
              className={classes.chip}
              color="primary"
              size="small"
              label="Family Admin"
            // onClick={handleClick}
            />
          )}
        </div>
        <Box display={"flex"} className={classes.infoSection}>
          <Box flex={1}>
            <InfoSection
              icon={faBirthdayCake}
              label={"Birth Date"}
              text={birthDate}
            />
          </Box>
          <Box flex={1}>
            <InfoSection
              icon={faHouseUser}
              label={"Family Name"}
              text={familyName}
            />
          </Box>
        </Box>
        <Box display={"flex"} className={classes.infoSection}>
          <Box flex={"auto"}>
            <InfoSection icon={faHome} label={"Address"} text={address} />
          </Box>
        </Box>
        <Box display={"flex"} className={classes.infoSection}>
          <Box flex={"auto"}>
            <InfoSection
              icon={faFileInvoiceDollar}
              label={"VAT Number"}
              text={vat}
            />
          </Box>
        </Box>
        <Box display={"flex"} className={classes.infoSection}>
          {phoneNumbers.length > 0 && (
            <Box flex={1}>
              <Typography variant="body2" color="textSecondary" component="p">
                <FontAwesomeIcon icon={faMobileAlt} /> Phone Numbers
                  </Typography>
              {phoneNumbers.map((phone) => (
                <Typography key={phone} variant="body2" component="p">
                  {phone}
                </Typography>
              ))}
            </Box>
          )}
          {otherEmailAddresses.length > 0 && (
            <Box flex={1}>
              <Typography variant="body2" color="textSecondary" component="p">
                <FontAwesomeIcon icon={faAt} /> Other Email Addresses
                  </Typography>
              {otherEmailAddresses.map((email) => (
                <Typography key={email} variant="body2" component="p">
                  <div className={classes.otherEmailItemText}>{email}</div>
                  <div className={classes.otherEmailItemDeleteIcon}><FontAwesomeIcon
                    icon={faTrashAlt} onClick={() => handleEmailDeleteClick(email)} /></div>
                </Typography>
              ))}
            </Box>
          )}
        </Box>
      </CardContent>
      <CardActions classes={{ spacing: classes.addEmailButtonAlignRight }}>
        <Button component={Link} to={`/members/${mainEmail}/emails`} size="small">
          Add Email
          </Button>
      </CardActions>

      <Dialog
        open={displayDeleteConfirmation}
        onClose={handleEmailDeleteConfirmationDialogClose}>
        <DialogTitle id="alert-dialog-title">{"Email delete confirmation"}</DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description">
            Are you sure you want to delete {emailToDelete}? This action cannot be undone.
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleDeleteConfirmation} color="warning">
            Confirm
          </Button>
          <Button onClick={handleEmailDeleteConfirmationDialogClose} color="primary" autoFocus>
            Cancel
          </Button>
        </DialogActions>
      </Dialog>

      {emailDeleteErrorMessage !== null && (
        <Snackbar
          anchorOrigin={{
            vertical: "bottom",
            horizontal: "center",
          }}
          autoHideDuration={3000}
          open={displayEmailDeleteErrorMessage}
          onClose={setDisplayEmailDeleteErrorMessage}>
          <Alert severity="error">
            {emailDeleteErrorMessage}
          </Alert>
        </Snackbar>
      )}

    </Card>
  )
};

export default withStyles(styles)(MemberInfo);

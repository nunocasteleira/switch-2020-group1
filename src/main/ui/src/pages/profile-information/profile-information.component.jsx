import React, { useContext, useEffect } from "react";
import AppContext from "../../context/AppContext";
import { fetchUser } from "../../context/Actions";
import { withStyles } from "@material-ui/core/styles";

import Box from "@material-ui/core/Box";
import Typography from "@material-ui/core/Typography";
import CircularProgress from "@material-ui/core/CircularProgress";
import MemberInfo from "../../components/member-info/member-info.component";

import styles from "./profile-information.styles";

const ProfileInformation = ({ classes }) => {
  const { state, dispatch } = useContext(AppContext);
  const { userInfo, userId, auth } = state;
  const { loading, error, data } = userInfo;

  useEffect(() => {
    const request = {
      uri: `/members/${userId}`,
      request: {
        method: "get",
        headers: new Headers({
          Authorization: "Bearer " + auth.token,
        }),
      },
    };
    fetchUser(dispatch, request);
  }, [auth.token, dispatch, userId]);

  if (loading === true) {
    return <CircularProgress />;
  } else {
    if (error !== null) {
      return (
        <Typography variant="body2" component="p">
          {error}
        </Typography>
      );
    } else {
      return (
        <Box className={classes.paper}>
          <MemberInfo
            name={data.name}
            mainEmail={data.mainEmailAddress}
            admin={data.admin}
            birthDate={data.birthDate}
            familyName={data.familyName}
            address={data.address}
            vat={data.vat}
            phoneNumbers={data.phoneNumbers}
            emailAddresses={data.emailAddresses}
          />
        </Box>
      );
    }
  }
};

export default withStyles(styles)(ProfileInformation);

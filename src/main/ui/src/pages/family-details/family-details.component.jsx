import React, { useContext, useEffect } from "react";
import AppContext from "../../context/AppContext";
import { fetchFamilyDetails } from "../../context/Actions";

import { withStyles } from "@material-ui/core/styles";

import CircularProgress from "@material-ui/core/CircularProgress";
import Typography from "@material-ui/core/Typography";

import FamilyInfo from "../../components/family-info/family-info.component";

import styles from "./family-details.styles";

const FamilyDetails = ({ classes }) => {
  const { state, dispatch } = useContext(AppContext);
  const { familyDetails, familyId, auth } = state;
  const { loading, error, data } = familyDetails;
  const { familyName, adminId, registrationDate } = data;

  useEffect(() => {
    const request = {
      uri: `/families/${familyId}`,
      request: {
        method: "get",
        headers: new Headers({
          Authorization: "Bearer " + auth.token,
        }),
      },
    };
    fetchFamilyDetails(dispatch, request);
  }, [auth.token, dispatch, familyId]);

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
        <FamilyInfo
          familyName={familyName}
          adminName={adminId}
          adminId={adminId}
          registrationDate={registrationDate}
        />
      );
    }
  }
};

export default withStyles(styles)(FamilyDetails);

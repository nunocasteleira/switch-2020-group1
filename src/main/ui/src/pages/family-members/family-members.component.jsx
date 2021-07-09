import React, { useContext, useEffect } from "react";
import { Link } from "react-router-dom";
import AppContext from "../../context/AppContext";
import { fetchFamilyMembers } from "../../context/Actions";
import { withStyles } from "@material-ui/core/styles";

import Button from "@material-ui/core/Button";
import Box from "@material-ui/core/Box";
import Typography from "@material-ui/core/Typography";
import CircularProgress from "@material-ui/core/CircularProgress";
import FamilyMember from "../../components/family-member/family-member.component";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPlus } from "@fortawesome/free-solid-svg-icons";

import styles from "./family-members.styles";

const FamilyMembers = ({ classes }) => {
  const { state, dispatch } = useContext(AppContext);
  const { familyId, familyMembers, auth } = state;
  const { loading, error, members } = familyMembers;

  useEffect(() => {
    const data = {
      uri: `/families/${familyId}/members`,
      request: {
        method: "get",
        headers: new Headers({
          Authorization: "Bearer " + auth.token,
        }),
      },
    };
    fetchFamilyMembers(dispatch, data);
  }, []);

  if (loading) {
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
          {members.map((member) => (
            <FamilyMember
                key={member.name}
                name={member.name}
                key={member.email}
                email={member.email}
            />
          ))}
          <Button
            variant="outlined"
            color="primary"
            className={classes.button}
            startIcon={<FontAwesomeIcon icon={faPlus} />}
            component={Link}
            to={`/families/${familyId}/members/add`}>
            Add Member
          </Button>
        </Box>
      );
    }
  }
};

export default withStyles(styles)(FamilyMembers);

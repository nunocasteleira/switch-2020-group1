import React from "react";

import Typography from "@material-ui/core/Typography";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

const InfoSection = ({ icon, label, text }) => {
  return (
    <>
      <Typography variant="body2" color="textSecondary" component="p">
        <FontAwesomeIcon icon={icon} /> {label}
      </Typography>
      <Typography variant="body2" component="p">
        {text}
      </Typography>
    </>
  );
};

export default InfoSection;

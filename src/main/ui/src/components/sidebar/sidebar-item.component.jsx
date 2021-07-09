import React, {useContext} from "react";
import { NavLink } from "react-router-dom";

import ListItem from "@material-ui/core/ListItem";
import ListItemIcon from "@material-ui/core/ListItemIcon";
import ListItemText from "@material-ui/core/ListItemText";
import ListSubheader from "@material-ui/core/ListSubheader";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faHouseUser,
  faUsers,
  faProjectDiagram,
  faWallet,
  faTags,
  faAddressCard,
  faMoneyCheckAlt,
} from "@fortawesome/free-solid-svg-icons";
import AppContext from "../../context/AppContext";

const systemManagerItems = [
{
    to: "/family",
    icon: <FontAwesomeIcon icon={faHouseUser} />,
    label: "Family",
  },
  {
    to: "/categories",
    icon: <FontAwesomeIcon icon={faTags} />,
    label: "Categories",
  },
]

const manageItems = [
  {
    to: "/family",
    icon: <FontAwesomeIcon icon={faHouseUser} />,
    label: "Family",
  },
  {
    to: "/families/:familyId/members",
    icon: <FontAwesomeIcon icon={faUsers} />,
    label: "Members",
  },
  {
    to: "/families/:familyId/relationships",
    icon: <FontAwesomeIcon icon={faProjectDiagram} />,
    label: "Relationships",
  },
  {
    to: "/family-account",
    icon: <FontAwesomeIcon icon={faWallet} />,
    label: "Family Account",
  },
  {
    to: "/categories",
    icon: <FontAwesomeIcon icon={faTags} />,
    label: "Categories",
  },
];

const memberItems = [
  {
    to: "/members/:personId",
    icon: <FontAwesomeIcon icon={faAddressCard} />,
    label: "My Profile",
  },
  {
    to: "/accounts/:personId",
    icon: <FontAwesomeIcon icon={faMoneyCheckAlt} />,
    label: "My Accounts",
  },
];

export const SystemManagerListItems = ({classes}) => {

  const { state } = useContext(AppContext);
  const { userId, familyId } = state;

  const ReplaceContextVariables = (link) => {
    return link.replace(":familyId", familyId)
               .replace(":personId", userId);
  }

  return (
      <>
        <ListSubheader inset>Manage</ListSubheader>
        {systemManagerItems.map((item) => (
            <ListItem
                button
                component={NavLink}
                to={ReplaceContextVariables(item.to)}
                key={item.label}
                activeClassName={classes.active}>
              <ListItemIcon className={classes.icon}>{item.icon}</ListItemIcon>
              <ListItemText primary={item.label} />
            </ListItem>
        ))}
      </>
  );
}

export const AdminListItems = ({ classes }) => {

  const { state } = useContext(AppContext);
  const { userId, familyId } = state;

  const ReplaceContextVariables = (link) => {
    return link.replace(":familyId", familyId)
        .replace(":personId", userId);
  }

  return (
    <>
      <ListSubheader inset>Manage</ListSubheader>
      {manageItems.map((item) => (
        <ListItem
          button
          component={NavLink}
          to={ReplaceContextVariables(item.to)}
          key={item.label}
          activeClassName={classes.active}>
          <ListItemIcon className={classes.icon}>{item.icon}</ListItemIcon>
          <ListItemText primary={item.label} />
        </ListItem>
      ))}
    </>
  );
};

export const MemberListItems = ({ classes }) => {

  const { state } = useContext(AppContext);
  const { userId, familyId } = state;

  const ReplaceContextVariables = (link) => {
    return link.replace(":familyId", familyId)
               .replace(":personId", userId);
  }

  return (
    <>
      <ListSubheader inset>Family Member</ListSubheader>
      {memberItems.map((item) => (
        <ListItem
          button
          component={NavLink}
          to={ReplaceContextVariables(item.to)}
          key={item.label}
          activeClassName={classes.active}>
          <ListItemIcon className={classes.icon}>{item.icon}</ListItemIcon>
          <ListItemText primary={item.label} />
        </ListItem>
      ))}
    </>
  );
};

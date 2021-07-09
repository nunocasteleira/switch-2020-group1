import React, {useContext} from "react";
import Drawer from "@material-ui/core/Drawer";
import Divider from "@material-ui/core/Button";

import {withStyles} from "@material-ui/core/styles";

import {AdminListItems, MemberListItems, SystemManagerListItems} from "./sidebar-item.component";

import styles from "./sidebar.styles";
import AppContext from "../../context/AppContext";

const Sidebar = ({classes}) => {

    const { state } = useContext(AppContext);
    const {auth} = state;
    const role = auth.role;

    function checkAdminRole(role) {
        return role === "ROLE_FAMILY_ADMIN";
    }
    function checkSystemManager(role) {
        return role === "ROLE_SYSTEM_MANAGER";
    }

    if (role.find(checkSystemManager)) {
        return (
            <Drawer
                className={classes.drawer}
                variant="permanent"
                classes={{
                    paper: classes.drawerPaper,
                }}
                anchor="left"
            >
                <div className={classes.toolbar}/>
                <Divider/>
                <SystemManagerListItems classes={classes}/>
            </Drawer>
        );
    } else if (role.find(checkAdminRole)) {
        return (
            <Drawer
                className={classes.drawer}
                variant="permanent"
                classes={{
                    paper: classes.drawerPaper,
                }}
                anchor="left"
            >
                <div className={classes.toolbar}/>
                <Divider/>
                <AdminListItems classes={classes}/>
                <Divider/>
                <MemberListItems classes={classes}/>
            </Drawer>
        );
    } else {
        return (
            <Drawer
                className={classes.drawer}
                variant="permanent"
                classes={{
                    paper: classes.drawerPaper,
                }}
                anchor="left"
            >
                <div className={classes.toolbar}/>
                <Divider/>
                <MemberListItems classes={classes}/>
            </Drawer>
        );
    }
};

export default withStyles(styles)(Sidebar);

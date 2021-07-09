import { createStyles } from "@material-ui/core/styles";

const drawerWidth = 240;

const styles = (theme) =>
  createStyles({
    drawer: {
      width: drawerWidth,
      flexShrink: 0,
    },
    drawerPaper: {
      position: "relative",
      whiteSpace: "nowrap",
      width: drawerWidth,
      zIndex: 0,
    },
    // necessary for content to be below app bar
    toolbar: theme.mixins.toolbar,
    icon: {
      fontSize: 20,
      paddingLeft: 12,
    },
    active: {
      fontWeight: "600",
      color: theme.palette.primary.main,
    },
  });

export default styles;

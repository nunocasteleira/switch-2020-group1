import { createStyles } from "@material-ui/core/styles";

const styles = (theme) =>
  createStyles({
    root: {
      position: "absolute",
      flexGrow: 1,
      width: "100%",
    },
    toolbar: {
      paddingRight: 24, // keep right padding when drawer closed
    },
    title: {
      flexGrow: 1,
    },
  welcomeMessage: {
    padding: theme.spacing(0, 4, 0, 1),
  },
  avatar: {
      width: theme.spacing(3),
      height: theme.spacing(3),
  },
  });

export default styles;

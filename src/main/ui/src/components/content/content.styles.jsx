import { createStyles } from "@material-ui/core/styles";

const styles = (theme) =>
  createStyles({
    appBarSpacer: theme.mixins.toolbar,
    content: {
      flexGrow: 1,
      minHeight: "calc(100vh - 68px)"
    },
    container: {
      display: "flex",
      paddingTop: theme.spacing(4),
      paddingBottom: theme.spacing(4),
      alignItems: "center",
      justifyContent: "center",
    },
  });

export default styles;

import { createStyles } from "@material-ui/core/styles";

const styles = (theme) =>
  createStyles({
    paper: {
      marginTop: theme.spacing(8),
      display: "flex",
      flexDirection: "column",
      minWidth: 480,
      minHeight: 320,
      padding: theme.spacing(4),
      width: "100%",
      height: "100%",
    },
    heading: {
      display: "flex",
      flexDirection: "row",
      alignItems: "flex-end",
      justifyContent: "space-between",
      marginBottom: theme.spacing(4),
    },
    datePickers: {
      "&> *": {
        margin: theme.spacing(0, 2),
      },
    },
    actionButtons: {
      "&> *": {
        margin: theme.spacing(2, 2, 0, 0),
      },
    },
  });

export default styles;

import { createStyles } from "@material-ui/core/styles";

const styles = (theme) =>
  createStyles({
    paper: {
      padding: theme.spacing(2),
      display: "flex",
      overflow: "auto",
      flexFlow: "wrap",
      flexDirection: "row",
      "& > *": { margin: theme.spacing(1) },
    },
    button: {
      margin: theme.spacing(1),
    },
  });

export default styles;

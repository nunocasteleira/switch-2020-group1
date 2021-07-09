import { createStyles } from "@material-ui/core/styles";

const styles = (theme) =>
  createStyles({
    paper: {
      padding: theme.spacing(2),
      display: "flex",
      overflow: "auto",
      flexDirection: "column",
      width: 450,
    },
  });

export default styles;

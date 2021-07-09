import { createStyles } from "@material-ui/core/styles";

const styles = (theme) =>
  createStyles({
    root: {},
    content: {
      display: "flex",
      alignItems: "center",
    },
    avatar: {
      backgroundColor: theme.palette.primary.light,
      margin: theme.spacing(0, 1, 0, 0),
    },
  });

export default styles;

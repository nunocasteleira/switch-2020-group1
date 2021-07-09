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
      marginRight: 6,
    },
  });

export default styles;

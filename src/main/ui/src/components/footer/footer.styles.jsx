import { createStyles } from "@material-ui/core/styles";

const styles = (theme) =>
  createStyles({
    footer: {
      textAlign: "center",
      padding: theme.spacing(3, 2),
      marginTop: "auto",
      backgroundColor:
        theme.palette.type === "light"
          ? theme.palette.grey[200]
          : theme.palette.grey[800],
      marginBottom: 0
    },
  });

export default styles;

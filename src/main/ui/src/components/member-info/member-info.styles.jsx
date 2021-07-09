import { createStyles } from "@material-ui/core/styles";

const styles = (theme) =>
  createStyles({
    root: {},
    cardHeader: {
      display: "flex",
      flexDirection: "column",
      alignItems: "center",
      margin: theme.spacing(0, 0, 2, 0),
    },
    content: {
      display: "flex",
      alignItems: "baseline",
    },
    avatar: {
      backgroundColor: theme.palette.primary.light,
      margin: theme.spacing(0, 0, 1, 0),
    },
    infoSection: {
      padding: theme.spacing(1, 0),
    },
    chip: {
      margin: theme.spacing(1, 0, 0, 0),
    },
    addEmailButtonAlignRight: {
      justifyContent: "flex-end"
    },
    otherEmailItemText:{
      display: "inline"
    },
    otherEmailItemDeleteIcon:{
      float: "right"
    }
    //button: {
      //display: "flex",
      //alignItems: "flex-end",
    //}
  });

export default styles;

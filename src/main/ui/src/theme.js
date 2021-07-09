import { createMuiTheme } from "@material-ui/core/styles";

export default function getThemeByRole(roles) {
  if (roles !== undefined) {
    if (roles.find((role) => role === "ROLE_SYSTEM_MANAGER")) {
      // from https://material.io/resources/color/#!/?view.left=0&view.right=0&primary.color=E53935&secondary.color=3E2723
      return createMuiTheme({
        palette: {
          primary: {
            light: "#ff6f60",
            main: "#e53935",
            dark: "#ab000d",
            contrastText: "#000000",
          },
          secondary: {
            light: "#6a4f4b",
            main: "#3e2723",
            dark: "#1b0000",
            contrastText: "#fff",
          },
        },
      });
    }
    if (roles.find((role) => role === "ROLE_FAMILY_ADMIN")) {
      // default theme
      return createMuiTheme();
    }
    if (roles.find((role) => role === "ROLE_FAMILY_MEMBER")) {
      // from https://material.io/resources/color/#!/?view.left=0&view.right=0&primary.color=66BB6A&secondary.color=FFC107
      return createMuiTheme({
        palette: {
          primary: {
            light: "#98ee99",
            main: "#66bb6a",
            dark: "#338a3e",
            contrastText: "#000000",
          },
          secondary: {
            light: "#fff350",
            main: "#ffc107",
            dark: "#c79100",
            contrastText: "#000000",
          },
        },
      });
    }
  }
  return createMuiTheme();
}

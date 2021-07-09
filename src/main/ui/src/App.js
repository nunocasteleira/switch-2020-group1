import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import { useContext } from "react";
import AppContext from "./context/AppContext";

import "./App.css";
import { makeStyles, ThemeProvider } from "@material-ui/core/styles";
import getThemeByRole from "./theme.js";

import Header from "./components/header/header.component";
import Sidebar from "./components/sidebar/sidebar.component";
import Content from "./components/content/content.component";
import Footer from "./components/footer/footer.component";

import ProfileInformation from "./pages/profile-information/profile-information.component";
import AddEmail from "./pages/add-email/add-email.component";
import RelationshipsInformation from "./pages/relationship/Relationship.component";
import AddFamilyAndAdministrator from "./pages/family/family.component";
import FamilyDetails from "./pages/family-details/family-details.component";
import CreateRelationship from "./pages/relationship/CreateRelationship.component";
import EditRelationship from "./pages/relationship/ChangeRelationship.component";
import AddFamilyMember from "./pages/members/member.component";
import FamilyMembers from "./pages/family-members/family-members.component";
import SigninPage from "./pages/signin-page/signin-page.component";
import RecursiveTreeView from "./pages/categories/categoriesMaterial.component";
import CreatePersonalCashAccount from "./pages/accounts/CreatePersonalCashAccount";
import CreateBankAccount from "./pages/accounts/CreateBankAccount";
import AccountsMain from "./pages/accounts/AccountsMain";
import Transfer from "./pages/cash-accounts/Transfer.component";
import Payment from "./pages/cash-accounts/Payment.component";
import AccountPage from "./pages/account-page/account-page.component";

const useStyles = makeStyles((theme) => ({
  root: {
    display: "flex",
    flexDirection: "column",
    height: "100vh",
  },
  content: {
    display: "flex",
    flex: "1 1 auto",
  },
}));

function App() {
  const classes = useStyles();
  const { state } = useContext(AppContext);
  const { userId, auth } = state;
  const { role } = auth;

  let isSysMan = false;
  if (
    role !== undefined &&
    role.find((role) => role === "ROLE_SYSTEM_MANAGER")
  ) {
    isSysMan = true;
  }

  return (
    <ThemeProvider theme={getThemeByRole(role)}>
      <div className={classes.root}>
        <Router>
          <Header />
          {!userId ? (
            <SigninPage />
          ) : (
            <div className={classes.content}>
              <Sidebar />
              <Content>
                <Switch>
                  <Route exact path="/"></Route>
                  <Route exact path="/family">
                    {isSysMan ? (
                      <AddFamilyAndAdministrator />
                    ) : (
                      <FamilyDetails />
                    )}
                  </Route>
                  <Route exact path="/families/:familyId/members">
                    <FamilyMembers />
                  </Route>
                  <Route exact path="/families/:familyId/members/add">
                    <AddFamilyMember />
                  </Route>
                  <Route exact path="/families/:familyId/relationships">
                    <RelationshipsInformation />
                  </Route>
                  <Route exact path="/families/:familyId/relationships/create">
                    <CreateRelationship />
                  </Route>
                  <Route
                    exact
                    path="/families/:familyId/relationships/:relationshipId"
                  >
                    <EditRelationship />
                  </Route>
                  <Route exact path="/family-account">
                    <h1>Family Account</h1>
                  </Route>
                  <Route exact path="/categories">
                    <RecursiveTreeView />
                  </Route>
                  <Route exact path="/members/:personId">
                    <ProfileInformation />
                  </Route>
                  <Route exact path="/members/:personId/emails">
                    <AddEmail />
                  </Route>
                  <Route exact path="/accounts/create-personal-cash">
                    <CreatePersonalCashAccount />
                  </Route>
                  <Route exact path="/accounts/create-bank-account">
                    <CreateBankAccount />
                  </Route>
                  <Route exact path="/accounts/:personId">
                    <AccountsMain />
                  </Route>
                  <Route path="/account/:id">
                    <AccountPage />
                  </Route>
                  <Route exact path="/transfer/create">
                    <Transfer />
                  </Route>
                  <Route exact path="/payment/create">
                    <Payment />
                  </Route>
                </Switch>
              </Content>
            </div>
          )}
          <Footer />
        </Router>
      </div>
    </ThemeProvider>
  );
}

export default App;

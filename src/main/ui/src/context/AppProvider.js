import React, { useReducer } from "react";
import PropTypes from "prop-types";
import { Provider } from "./AppContext";
import reducer from "./Reducer";

const initialState = {
  userInfo: {
    loading: true,
    error: null,
    data: {},
  },
  signin: {
    loading: false,
    error: null,
  },
  auth: {
    token: "",
    personName: "",
    role: [],
  },
  familyMembers: {
    loading: false,
    error: null,
    members: [],
  },
  familyDetails: {
    loading: true,
    error: null,
    data: {},
  },
  userId: "",
  familyId: "",
};

const AppProvider = (props) => {
  const [state, dispatch] = useReducer(reducer, initialState);
  return (
    <Provider
      value={{
        state,
        dispatch,
      }}
    >
      {props.children}
    </Provider>
  );
};

AppProvider.propTypes = {
  children: PropTypes.node,
};

export default AppProvider;

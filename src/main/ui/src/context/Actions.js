import { signin } from "./SigninService";
import { makeRequest } from "./UnifiedService";

export const FETCH_USER_STARTED = "FETCH_USER_STARTED";
export const FETCH_USER_SUCCESS = "FETCH_USER_SUCCESS";
export const FETCH_USER_FAILURE = "FETCH_USER_FAILURE";

export const SIGNIN_USER_STARTED = "SIGNIN_USER_STARTED";
export const SIGNIN_USER_SUCCESS = "SIGNIN_USER_SUCCESS";
export const SIGNIN_USER_FAILURE = "SIGNIN_USER_FAILURE";

export const SIGNOUT_USER = "SIGNOUT_USER";

export const FETCH_FAMILY_MEMBERS_STARTED = "FETCH_FAMILY_MEMBERS_STARTED";
export const FETCH_FAMILY_MEMBERS_SUCCESS = "FETCH_FAMILY_MEMBERS_SUCCESS";
export const FETCH_FAMILY_MEMBERS_FAILURE = "FETCH_FAMILY_MEMBERS_FAILURE";

export const ADD_EMAIL_STARTED = "ADD_EMAIL_STARTED";
export const ADD_EMAIL_SUCCESS = "ADD_EMAIL_SUCCESS";
export const ADD_EMAIL_FAILURE = "ADD_EMAIL_FAILURE";

export const FETCH_FAMILY_DETAILS_STARTED = "FETCH_FAMILY_DETAILS_STARTED";
export const FETCH_FAMILY_DETAILS_SUCCESS = "FETCH_FAMILY_DETAILS_SUCCESS";
export const FETCH_FAMILY_DETAILS_FAILURE = "FETCH_FAMILY_DETAILS_FAILURE";

export function fetchUser(dispatch, data) {
  dispatch(fetchUserStarted());
  makeRequest(
    data,
    (result) => dispatch(fetchUserSuccess(result)),
    (error) => dispatch(fetchUserFailure(error))
  );
}

export function fetchUserStarted() {
  return {
    type: FETCH_USER_STARTED,
  };
}

export function fetchUserSuccess(user) {
  return {
    type: FETCH_USER_SUCCESS,
    payload: {
      data: user,
    },
  };
}

export function fetchUserFailure(message) {
  return {
    type: FETCH_USER_FAILURE,
    payload: {
      error: message,
    },
  };
}

export function signinUser(dispatch, signinData) {
  dispatch(signinUserStarted());
  signin(
    (result) => dispatch(signinUserSuccess(result)),
    (error) => dispatch(signinUserFailure(error)),
    signinData
  );
}

export function signinUserStarted() {
  return {
    type: SIGNIN_USER_STARTED,
  };
}

export function signinUserSuccess(loginData) {
  return {
    type: SIGNIN_USER_SUCCESS,
    payload: {
      userId: loginData.email,
      familyId: loginData.familyId,
      token: loginData.token,
      personName: loginData.personName,
      role: loginData.role,
    },
  };
}

export function signinUserFailure(message) {
  return {
    type: SIGNIN_USER_FAILURE,
    payload: {
      error: message,
    },
  };
}

export function signoutUser(dispatch) {
  dispatch(signoutUserStarted());
}

export function signoutUserStarted() {
  return {
    type: SIGNOUT_USER,
  };
}

export function fetchFamilyMembers(dispatch, data) {
  dispatch(fetchFamilyMembersStarted());
  makeRequest(
    data,
    (result) => dispatch(fetchFamilyMembersSuccess(result)),
    (error) => dispatch(fetchFamilyMembersFailure(error))
  );
}

export function fetchFamilyMembersStarted() {
  return {
    type: FETCH_FAMILY_MEMBERS_STARTED,
  };
}

export function fetchFamilyMembersSuccess(familyMembers) {
  return {
    type: FETCH_FAMILY_MEMBERS_SUCCESS,
    payload: {
      familyMembers: familyMembers.familyMembers,
    },
  };
}

export function fetchFamilyMembersFailure(message) {
  return {
    type: FETCH_FAMILY_MEMBERS_FAILURE,
    payload: {
      error: message,
    },
  };
}

export function addEmail(dispatch, data) {
  dispatch(addEmailStarted());
  return makeRequest(
    data,
    (result) => dispatch(addEmailSuccess(result)),
    (error) => dispatch(addEmailFailure(error))
  );
}

export function addEmailStarted() {
  return {
    type: ADD_EMAIL_STARTED,
  };
}

export function addEmailSuccess(emailAddresses) {
  return {
    type: ADD_EMAIL_SUCCESS,
    payload: {
      emailAddresses: emailAddresses.emailAddresses,
    },
  };
}

export function addEmailFailure(message) {
  return {
    type: ADD_EMAIL_FAILURE,
    payload: {
      error: message,
    },
  };
}

export function fetchFamilyDetails(dispatch, data) {
  dispatch(fetchFamilyDetailsStarted());
  makeRequest(
    data,
    (result) => dispatch(fetchFamilyDetailsSuccess(result)),
    (error) => dispatch(fetchFamilyDetailsFailure(error))
  );
}

export function fetchFamilyDetailsStarted() {
  return {
    type: FETCH_FAMILY_DETAILS_STARTED,
  };
}

export function fetchFamilyDetailsSuccess(familyDetails) {
  return {
    type: FETCH_FAMILY_DETAILS_SUCCESS,
    payload: {
      data: familyDetails,
    },
  };
}

export function fetchFamilyDetailsFailure(message) {
  return {
    type: FETCH_FAMILY_DETAILS_FAILURE,
    payload: {
      error: message,
    },
  };
}

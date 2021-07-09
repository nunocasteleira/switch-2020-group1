import {
  FETCH_USER_STARTED,
  FETCH_USER_SUCCESS,
  FETCH_USER_FAILURE,
  SIGNIN_USER_STARTED,
  SIGNIN_USER_SUCCESS,
  SIGNIN_USER_FAILURE,
  SIGNOUT_USER,
  FETCH_FAMILY_MEMBERS_STARTED,
  FETCH_FAMILY_MEMBERS_SUCCESS,
  FETCH_FAMILY_MEMBERS_FAILURE,
  ADD_EMAIL_STARTED,
  ADD_EMAIL_SUCCESS,
  ADD_EMAIL_FAILURE,
  FETCH_FAMILY_DETAILS_STARTED,
  FETCH_FAMILY_DETAILS_SUCCESS,
  FETCH_FAMILY_DETAILS_FAILURE,
} from "./Actions";

function reducer(state, action) {
  switch (action.type) {
    case FETCH_USER_STARTED:
      return {
        ...state,
        userInfo: {
          loading: true,
          error: null,
          data: {},
        },
      };
    case FETCH_USER_SUCCESS:
      return {
        ...state,
        userInfo: {
          loading: false,
          error: null,
          data: action.payload.data,
        },
      };
    case FETCH_USER_FAILURE:
      return {
        ...state,
        userInfo: {
          loading: false,
          error: action.payload.error,
          data: {},
        },
      };
    case SIGNIN_USER_STARTED:
      return {
        ...state,
        signin: {
          loading: true,
          error: null,
        },
      };
    case SIGNIN_USER_SUCCESS:
      return {
        ...state,
        signin: {
          loading: false,
          error: null,
        },
        userId: action.payload.userId,
        familyId: action.payload.familyId,
        auth: {
          token: action.payload.token,
          personName: action.payload.personName,
          role: action.payload.role,
        },
      };
    case SIGNIN_USER_FAILURE:
      return {
        ...state,
        signin: {
          loading: false,
          error: action.payload.error,
        },
      };
    case SIGNOUT_USER:
      return {
        ...state,
        auth: {
          token: "",
          personName: "",
          roles: [],
        },
        userId: "",
        familyId: "",
      };
    case FETCH_FAMILY_MEMBERS_STARTED:
      return {
        ...state,
        familyMembers: {
          loading: true,
          error: null,
          members: [],
        },
      };
    case FETCH_FAMILY_MEMBERS_SUCCESS:
      return {
        ...state,
        familyMembers: {
          loading: false,
          error: null,
          members: action.payload.familyMembers,
        },
      };
    case FETCH_FAMILY_MEMBERS_FAILURE:
      return {
        ...state,
        familyMembers: {
          loading: false,
          error: action.payload.error,
          members: [],
        },
      };
    case ADD_EMAIL_STARTED:
      return {
        ...state,
      };
    case ADD_EMAIL_SUCCESS:
      return {
        ...state,
        userInfo: {
          ...state.userInfo,
          data: {
            ...state.userInfo.data,
            emailAddresses: [action.payload.emailAddresses],
          },
        },
      };
    case ADD_EMAIL_FAILURE:
      return {
        ...state,
        userInfo: {
          ...state.userInfo,
          data: {
            emailAddresses: {
              ...state.userInfo.data.emailAddresses,
              error: action.payload.error,
            },
          },
        },
      };
    case FETCH_FAMILY_DETAILS_STARTED:
      return {
        ...state,
        familyDetails: {
          loading: true,
          error: null,
          data: {},
        },
      };
    case FETCH_FAMILY_DETAILS_SUCCESS:
      return {
        ...state,
        familyDetails: {
          loading: false,
          error: null,
          data: action.payload.data,
        },
      };
    case FETCH_FAMILY_DETAILS_FAILURE:
      return {
        ...state,
        familyDetails: {
          loading: false,
          error: action.payload.error,
          data: {},
        },
      };
    default:
      return state;
  }
}

export default reducer;

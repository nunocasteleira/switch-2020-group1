import axios from "axios";

const URI = "http://localhost:8080/api/auth/signin/";

export function signin(success, failure, signinData) {
  axios
    .post(URI, signinData)
    .then((response) => {
      if (
        response !== null &&
        response !== undefined &&
        response.data !== null &&
        response.data !== undefined
      ) {
        success(response.data);
      }
    })
    .catch((error) => {
      if (
        error.response !== null &&
        error.response !== undefined &&
        error.response.data !== null &&
        error.response.data !== undefined
      ) {
        failure(error.response.data);
      }
    });
}

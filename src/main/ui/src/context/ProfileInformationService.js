import axios from "axios";

export const appURI = "http://localhost:8080";

export function fetchUserProfile(success, failure, id) {
  fetch(`${appURI}/members/${id}`)
    .then((result) => {
      if (result.ok) {
        result
          .json()
          .then((result) => success(result))
          .catch((error) => failure(error));
      } else {
        result
          .text()
          .then((error) => Promise.reject(error))
          .catch((error) => failure(error));
      }
    })
    .catch((error) => failure(error.message));

  // .then((result) => result.json())
  // .then((result) => success(result))
  // .catch((error) => failure(error.message));
}

export function deleteOtherEmail(mainEmail, otherEmail, token) {
  return axios.delete(`http://localhost:8080/members/${mainEmail}/emails/${otherEmail}`, {
    headers: {
      'Authorization': `Bearer ${token}`
    }
  })
}

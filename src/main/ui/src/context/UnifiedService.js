export const appURI = process.env.REACT_APP_APP_URI_BASE_PATH;

export async function makeRequest(data, success, failure) {
  try {
    const result = await fetch(appURI + data.uri, data.request);
    if (result.ok) {
      result.json().then((result_2) => success(result_2));
    } else {
      result
        .text()
        .then((error) => Promise.reject(error))
        .catch((error) => failure(error));
    }
  } catch (error) {
    return failure(error.message);
  }
}

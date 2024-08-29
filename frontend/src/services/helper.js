import axios from "axios";

//backend url
const baseUrl = "http://localhost:8080/api/v1/";

export const customAxios = axios.create({
  baseURL: baseUrl,
});

import axios from "axios";
import urls from "../constants/apiUrl";

export const apiService = async () => {
  const response = await axios.get(urls.bestSellersUrl);
  console.log(response.data);
  return response.data;
};

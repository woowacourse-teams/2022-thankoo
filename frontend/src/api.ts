import axios from 'axios';
import { BASE_URL } from './constants/api';

const storedAccessToken = localStorage.getItem('token');
const defaultHeader = storedAccessToken && {
  headers: { Authorization: `Bearer ${storedAccessToken}` },
};

export const requestInstance = axios.create({
  baseURL: BASE_URL,
  ...defaultHeader,
});

requestInstance.prototype.updateAuth = function (accessToken) {
  this.constructor.defaults.headers = {
    Authorization: `Bearer ${accessToken}`,
  };
};

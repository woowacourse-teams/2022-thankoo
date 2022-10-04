import axios from 'axios';
import { BASE_URL } from '../constants/api';

const storedAccessToken = localStorage.getItem('token');
const defaultHeader = storedAccessToken && {
  headers: { Authorization: `Bearer ${storedAccessToken}` },
};

export const client = axios.create({
  baseURL: `${BASE_URL}/api`,
  ...defaultHeader,
});

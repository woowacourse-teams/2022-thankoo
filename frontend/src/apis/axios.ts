import axios, { AxiosError, AxiosRequestConfig } from 'axios';
import { BASE_URL } from '../constants/api';
import { ROUTE_PATH } from '../constants/routes';
import { ErrorType } from '../types/api';
import { urlQueryHandler } from '../utils/api';

const storedAccessToken = localStorage.getItem('token');
const defaultHeader = storedAccessToken && {
  headers: { Authorization: `Bearer ${storedAccessToken}` },
};

export const client = axios.create({
  baseURL: `${BASE_URL}/api`,
  // timeout: 5000,
  ...defaultHeader,
});

const ORGANIZATIONS_REQUIRED_DOMAINS = [
  'members',
  'coupons',
  'hearts',
  'coupon-serials',
  'reservations',
  'meetings',
];
const axiosDefaultRequestHandler = (config: AxiosRequestConfig, organizationId) => {
  if (!ORGANIZATIONS_REQUIRED_DOMAINS.some(domain => config.url?.includes(domain))) {
    return config;
  }

  if (config.method === 'get' || config.method === 'GET') {
    return {
      ...config,
      url: urlQueryHandler(config.url as string, `organization=${organizationId}`),
    };
  }

  return { ...config, data: { ...config.data, organizationId } };
};
export const interceptRequest = organizationId => {
  client.interceptors.request.use(config => axiosDefaultRequestHandler(config, organizationId));
};

const INVALID_AUTH_ERROR_CODE = 1003;
const INVALID_AUTH_STATUS = 401;
client.interceptors.response.use(
  response => {
    return response;
  },
  (error: AxiosError<ErrorType>) => {
    if (
      error.response?.status === INVALID_AUTH_STATUS ||
      error.response?.data.errorCode === INVALID_AUTH_ERROR_CODE
    ) {
      window.location.replace(ROUTE_PATH.SIGN_IN);
    }
    return Promise.reject(error);
  }
);

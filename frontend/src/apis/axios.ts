import axios, { AxiosError, AxiosRequestConfig } from 'axios';
import { BASE_URL } from '../constants/api';
import { ROUTE_PATH } from '../constants/routes';
import { ErrorType } from '../types/api';
import { urlQueryHandler } from '../utils/api';
import { clearAuth } from '../utils/auth';

const storedAccessToken = localStorage.getItem('token');
const defaultHeader = storedAccessToken && {
  headers: { Authorization: `Bearer ${storedAccessToken}` },
};

export const client = axios.create({
  baseURL: `${BASE_URL}/api`,
  ...defaultHeader,
});

const ORGANIZATIONS_REQUIRED_DOMAINS = ['members', 'coupons', 'hearts', 'reservations', 'meetings'];

export const injectOrganizationToRequest = (organizationId: string) => {
  client.interceptors.request.use(config => {
    const accessedDomain = config.url?.split('/')[1];

    if (
      !ORGANIZATIONS_REQUIRED_DOMAINS.some(organizaionRequiredDomain =>
        accessedDomain?.includes(organizaionRequiredDomain)
      )
    ) {
      return config;
    }

    if (typeof config.url !== 'string') {
      return config;
    }

    if (config.method === 'get' || config.method === 'GET') {
      return {
        ...config,
        url: urlQueryHandler(config.url, `organization=${organizationId}`),
      };
    }

    return { ...config, data: { ...config.data, organizationId } };
  });
};

const INVALID_MEMBER_ERROR_CODE = 2001;
const INVALID_AUTH_ERROR_CODE = 1003;
const INVALID_AUTH_STATUS = 401;
client.interceptors.response.use(
  response => {
    return response;
  },
  (error: AxiosError<ErrorType>) => {
    if (
      error.response?.status === INVALID_AUTH_STATUS ||
      error.response?.data.errorCode === INVALID_AUTH_ERROR_CODE ||
      error.response?.data.errorCode === INVALID_MEMBER_ERROR_CODE
    ) {
      clearAuth();
      window.location.replace(ROUTE_PATH.SIGN_IN);
    }

    return Promise.reject(error);
  }
);

import { AxiosError } from 'axios';
import { QueryClient } from 'react-query';
import { ROUTE_PATH } from '../constants/routes';
import { clearAuth } from '../utils/auth';

const INVALID_MEMBER_ERROR_CODE = 2001;
const INVALID_AUTH_STATUS = 401;

type AuthErrorResponse = {
  data: { errorCode: number };
};

const queryErrorHandler = error => {
  authErrorHandler(error);
};
const mutateErrorHandler = error => {
  authErrorHandler(error);
};
const retryHandler = (failureCount, error) => {
  console.log(error.response.status === INVALID_AUTH_STATUS);
  if (
    error.response.status === INVALID_AUTH_STATUS ||
    error.response.data.errorCode === INVALID_MEMBER_ERROR_CODE
  ) {
    return false;
  }

  return true;
};

const authErrorHandler = (error: AxiosError) => {
  const {
    data: { errorCode },
  } = error?.response as AuthErrorResponse;

  if (errorCode === INVALID_MEMBER_ERROR_CODE || error.response?.status === INVALID_AUTH_STATUS) {
    clearAuth();
    window.location.replace(`${ROUTE_PATH.SIGN_IN}`);
  }
};

const defaultQueryClientOptions = {
  queries: {
    onError: queryErrorHandler,
    retry: retryHandler,
  },
  mutations: {
    onError: mutateErrorHandler,
    retry: retryHandler,
  },
};

export const queryClient = new QueryClient({
  defaultOptions: defaultQueryClientOptions,
});

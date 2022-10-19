import { AxiosError } from 'axios';
import { QueryClient, QueryClientConfig } from 'react-query';
import { ROUTE_PATH } from '../constants/routes';
import { clearAuth } from '../utils/auth';

const INVALID_MEMBER_ERROR_CODE = 2001;
const INVALID_AUTH_ERROR_CODE = 1003;
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
  const defaultRetryCount = 3;

  if (
    error.response?.status === INVALID_AUTH_STATUS ||
    error.response?.data.errorCode === INVALID_MEMBER_ERROR_CODE ||
    error.response?.data.errorCode === INVALID_AUTH_ERROR_CODE ||
    failureCount === defaultRetryCount
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

const defaultOptions: QueryClientConfig = {
  defaultOptions: {
    queries: {
      onError: queryErrorHandler,
      retry: retryHandler,
      refetchOnWindowFocus: false,
      suspense: true,
    },
    mutations: {
      onError: mutateErrorHandler,
      retry: retryHandler,
    },
  },
};

export const queryClient = new QueryClient({
  ...defaultOptions,
});

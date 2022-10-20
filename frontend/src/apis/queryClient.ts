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
      retry: 3,
      refetchOnWindowFocus: false,
      suspense: true,
    },
    mutations: {
      onError: mutateErrorHandler,
      retry: 3,
    },
  },
};

export const queryClient = new QueryClient({
  ...defaultOptions,
});

import { AxiosError } from 'axios';
import { QueryClient } from 'react-query';
import { clearAuth } from '../utils/auth';

const INVALID_AUTH_ERROR_CODE = 2001;

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
  if (error.response.data.errorCode === INVALID_AUTH_ERROR_CODE) {
    return false;
  }

  return true;
};

const authErrorHandler = (error: AxiosError) => {
  const {
    data: { errorCode },
  } = error?.response as AuthErrorResponse;

  if (errorCode === INVALID_AUTH_ERROR_CODE) {
    clearAuth();
    window.location.reload();
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

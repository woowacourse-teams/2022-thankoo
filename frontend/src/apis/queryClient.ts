import { AxiosError } from 'axios';
import { QueryClient } from 'react-query';

type AuthErrorResponse = {
  data: { errorCode: number };
};

const authErrorHandler = (error: AxiosError) => {
  const {
    data: { errorCode },
  } = error?.response as AuthErrorResponse;

  if (errorCode === 1003) {
    localStorage.removeItem('token');
    window.location.reload();
  }
};
const retryHandler = (failureCount, error) => {
  if (error.response.data.errorCode === 1003) {
    return false;
  }

  return true;
};

const queryErrorHandler = error => {
  authErrorHandler(error);
};
const mutateErrorHandler = error => {
  authErrorHandler(error);
};

export const defaultQueryClientOptions = {
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

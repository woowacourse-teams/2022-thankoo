import { Axios, AxiosError, AxiosResponse } from 'axios';
import { QueryClient } from 'react-query';

type AuthErrorResponse = {
  data: { errorCode: number };
};

const authErrorHandler = (error: AxiosError) => {
  // error.
};
console.log('queryClient');

const queryErrorHandler = error => {
  console.log('error', error);

  const {
    data: { errorCode },
  } = error?.response as AuthErrorResponse;

  if (errorCode === 1003) {
    localStorage.removeItem('token');
    window.location.reload();
  }
};
const mutateErrorHandler = error => {
  error.status;
};

export const defaultQueryClientOptions = {
  queries: {
    onError: queryErrorHandler,
  },
  mutations: {
    onError: mutateErrorHandler,
  },
};

export const queryClient = new QueryClient({
  defaultOptions: defaultQueryClientOptions,
});

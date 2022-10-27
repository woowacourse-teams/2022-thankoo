import { AxiosError } from 'axios';

export type ErrorType = {
  errorCode: number;
  message: string;
};

export type QueryHandlers = {
  onSuccess: () => void;
  onError: (error: AxiosError<ErrorType>) => void;
};

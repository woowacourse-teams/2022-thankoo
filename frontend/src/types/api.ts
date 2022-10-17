import { AxiosError } from 'axios';

export interface ErrorType {
  errorCode: number;
  message: string;
}

export interface QueryHandlers {
  onSuccess: () => void;
  onError: (error: AxiosError<ErrorType>) => void;
}

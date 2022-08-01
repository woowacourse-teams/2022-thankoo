import { requestInstance } from '../apis/axios';

export const saveAuth = (accessToken: string) => {
  requestInstance.prototype.updateAuth(accessToken);
  localStorage.setItem('token', accessToken);
};

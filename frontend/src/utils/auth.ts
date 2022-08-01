import { client } from '../apis/axios';

export const saveAuth = (accessToken: string) => {
  client.prototype.updateAuth(accessToken);
  localStorage.setItem('token', accessToken);
};

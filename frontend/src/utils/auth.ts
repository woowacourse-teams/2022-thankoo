import { client } from '../apis/axios';

export const saveAuth = (accessToken: string) => {
  client.defaults.headers['Authorization'] = `Bearer ${accessToken}`;
  localStorage.setItem('token', accessToken);
};

export const clearAuth = () => {
  client.defaults.headers['Authorization'] = '';
  localStorage.removeItem('token');
};

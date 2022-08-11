import { API_PATH } from '../constants/api';
import { client } from './axios';

export const getUserProfile = async () => {
  const { data } = await client({
    method: 'GET',
    url: API_PATH.PROFILE,
  });

  return data;
};

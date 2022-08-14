import { API_PATH } from '../../constants/api';
import { client } from '../config/axios';

export const getUserProfileRequest = async () => {
  const { data } = await client({
    method: 'GET',
    url: API_PATH.PROFILE,
  });

  return data;
};

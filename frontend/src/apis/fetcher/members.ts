import { API_PATH } from '../../constants/api';
import { client } from '../config/axios';

export const getMembersRequest = async () => {
  const { data } = await client({ method: 'get', url: API_PATH.MEMBERS });

  return data;
};

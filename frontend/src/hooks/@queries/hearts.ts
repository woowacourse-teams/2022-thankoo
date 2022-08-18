import { AxiosResponse } from 'axios';
import { useQuery } from 'react-query';
import { API_PATH } from '../../constants/api';
import { client } from './../../apis/axios';

const QUERY_KEY = {
  heart: 'heart',
};

export const useGetHearts = () =>
  useQuery<AxiosResponse<any>>(QUERY_KEY.heart, getUserHeartsRequest);

//FETCHER
const getUserHeartsRequest = async () => {
  const { data } = await client({
    method: 'GET',
    url: API_PATH.GET_USER_HEARTS,
  });
  return data;
};

import { AxiosResponse } from 'axios';
import { useQuery } from 'react-query';
import { client } from '../../apis/axios';
import { API_PATH } from '../../constants/api';
import { UserProfile } from '../../types';

const QUERY_KEY = {
  members: 'members',
};

export const useGetMembers = () =>
  useQuery<AxiosResponse<UserProfile[]>>(QUERY_KEY.members, getMembersRequest);

/** FETCHER */

const getMembersRequest = async () => {
  const { data } = await client({ method: 'get', url: API_PATH.MEMBERS });

  return data;
};

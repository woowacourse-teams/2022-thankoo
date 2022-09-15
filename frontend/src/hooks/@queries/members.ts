import { useQuery } from 'react-query';
import { client } from '../../apis/axios';
import { API_PATH } from '../../constants/api';
import { UserProfile } from '../../types';

export const MEMBERS_QUERY_KEY = {
  members: 'members',
};

export const useGetMembers = () =>
  useQuery<UserProfile[]>(MEMBERS_QUERY_KEY.members, getMembersRequest);

/** FETCHER */

const getMembersRequest = async () => {
  const { data } = await client({ method: 'get', url: API_PATH.MEMBERS });

  return data;
};

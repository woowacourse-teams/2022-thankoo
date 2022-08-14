import { AxiosResponse } from 'axios';
import { useQuery } from 'react-query';
import { getMembersRequest } from '../../apis/fetcher/members';
import { UserProfile } from '../../types';

const QUERY_KEY = {
  members: 'members',
};

export const useGetMembers = () =>
  useQuery<AxiosResponse<UserProfile[]>>(QUERY_KEY.members, getMembersRequest);

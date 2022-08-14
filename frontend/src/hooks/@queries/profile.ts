import { useQuery } from 'react-query';
import { getUserProfileRequest } from '../../api/fetcher/profile';
import { UserProfile } from '../../types';

export const QUERY_KEY = {
  profile: 'profile',
};

export const useGetUserProfile = () => useQuery<UserProfile>('profile', getUserProfileRequest);

import { useQuery } from 'react-query';
import { getUserProfile } from '../../apis/profile';
import { UserProfile } from '../../types';

export const QUERY_KEY = {
  profile: 'profile',
};

export const useGetUserProfile = () => useQuery<UserProfile>('profile', getUserProfile);

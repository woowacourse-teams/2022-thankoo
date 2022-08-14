import { useQuery } from 'react-query';
import { getUserProfileRequest, getCouponExchangeCount } from '../../apis/fetcher/profile';
import { UserProfile } from '../../types';

export const QUERY_KEY = {
  profile: 'profile',
};

export const useGetUserProfile = ({ onSuccess = (data: UserProfile) => {} } = {}) =>
  useQuery<UserProfile>('profile', getUserProfileRequest, {
    onSuccess: (data: UserProfile) => {
      onSuccess?.(data);
    },
  });

export interface exchangeCount {
  sentCount: number;
  receivedCount: number;
}
export const useGetCouponExchangeCount = (
  { onSuccess: handleSuccess } = { onSuccess: (data: exchangeCount) => {} }
) =>
  useQuery<exchangeCount>('couponExchangeCount', getCouponExchangeCount, {
    onSuccess: data => {
      handleSuccess?.(data);
    },
  });

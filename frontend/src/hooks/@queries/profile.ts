import { useQuery } from 'react-query';
import { client } from '../../apis/axios';
import { API_PATH } from '../../constants/api';
import { UserProfile } from '../../types';
import { getCouponExchangeCount } from './../../apis/fetcher/profile';

export const useGetProfile = (
  { onSuccess: handleSuccess } = { onSuccess: (data: UserProfile) => {} }
) =>
  useQuery<UserProfile>(
    'profile',
    async () => {
      const { data } = await client({
        method: 'get',
        url: `${API_PATH.PROFILE}`,
      });

      return data;
    },
    {
      refetchOnWindowFocus: false,
      onSuccess: data => {
        handleSuccess?.(data);
      },
    }
  );

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

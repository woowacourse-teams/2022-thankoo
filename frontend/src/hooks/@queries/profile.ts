import { useQuery } from 'react-query';
import { UserProfile } from '../../types';
import { API_PATH } from '../../constants/api';
import { client } from '../../apis/axios';

export const QUERY_KEY = {
  profile: 'profile',
  couponExchangeCount: 'couponExchangeCount',
};

export const useGetUserProfile = ({ onSuccess = (data: UserProfile) => {} } = {}) =>
  useQuery<UserProfile>(QUERY_KEY.profile, getUserProfileRequest, {
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
  useQuery<exchangeCount>(QUERY_KEY.couponExchangeCount, getCouponExchangeCount, {
    onSuccess: data => {
      handleSuccess?.(data);
    },
  });

/** FETCHER */

const getUserProfileRequest = async () => {
  const { data } = await client({
    method: 'GET',
    url: API_PATH.PROFILE,
  });

  return data;
};

const getCouponExchangeCount = async () => {
  const { data } = await client({
    method: 'get',
    url: `${API_PATH.GET_COUPONS_EXCHANGE_COUNT}`,
  });
  return data;
};

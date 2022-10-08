import { useMutation, useQuery } from 'react-query';
import { API_PATH } from '../../constants/api';
import { client } from '../../apis/axios';
import { UserProfile } from '../../types/user';

export interface exchangeCount {
  sentCount: number;
  receivedCount: number;
}

export const PROFILE_QUERY_KEY = {
  profile: 'profile',
  couponExchangeCount: 'couponExchangeCount',
};

export const useGetUserProfile = ({ onSuccess = (data: UserProfile) => {} } = {}) =>
  useQuery<UserProfile>(PROFILE_QUERY_KEY.profile, getUserProfileRequest, {
    onSuccess: (data: UserProfile) => {
      onSuccess?.(data);
    },
  });

export const useGetCouponExchangeCount = (
  { onSuccess: handleSuccess } = { onSuccess: (data: exchangeCount) => {} }
) =>
  useQuery<exchangeCount>(PROFILE_QUERY_KEY.couponExchangeCount, getCouponExchangeCountRequest, {
    onSuccess: data => {
      handleSuccess?.(data);
    },
  });

export const usePutEditUserName = ({ onSuccess = () => {} } = {}) =>
  useMutation((name: string) => putEditUserNameRequest(name), {
    onSuccess: () => {
      onSuccess();
    },
  });

export const usePutEditUserProfileImage = ({ onSuccess = () => {} } = {}) =>
  useMutation((imageUrl: string) => putEditUserProfileImageRequest(imageUrl), {
    onSuccess: () => {
      onSuccess();
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

const getCouponExchangeCountRequest = async () => {
  const { data } = await client({
    method: 'get',
    url: API_PATH.GET_COUPONS_EXCHANGE_COUNT,
  });

  return data;
};

const putEditUserNameRequest = (name: string) =>
  client({
    method: 'put',
    url: API_PATH.PROFILE_NAME,
    data: {
      name,
    },
  });

const putEditUserProfileImageRequest = (imageUrl: string) =>
  client({
    method: 'put',
    url: API_PATH.PROFILE_IMAGE,
    data: {
      imageUrl,
    },
  });

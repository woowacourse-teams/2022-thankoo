import { AxiosError } from 'axios';
import { useMutation, useQuery } from 'react-query';
import { client } from '../../apis/axios';
import { API_PATH } from '../../constants/api';
import { ErrorType } from '../../types/api';
import { Coupon, CouponDetail } from '../../types/coupon';

const SENT_OR_RECEIVED_API_PATH = {
  received: API_PATH.RECEIVED_COUPONS_ALL,
  sent: API_PATH.SENT_COUPONS,
};

export const COUPON_QUERY_KEY = {
  coupon: 'coupon',
  couponDetail: 'couponDetail',
};

export const useGetCouponDetail = (
  couponId: number,
  { onSuccess = () => {}, onError = () => {} } = {}
) =>
  useQuery<CouponDetail>(
    [COUPON_QUERY_KEY.couponDetail, couponId],
    () => getCouponDetailRequest(couponId),
    {
      onSuccess: () => {
        onSuccess();
      },
      onError: () => {
        onError();
      },
      retry: false,
    }
  );

export const useGetCoupons = sentOrReceived =>
  useQuery<Coupon[]>([COUPON_QUERY_KEY.coupon, sentOrReceived], () =>
    getCouponsRequest(sentOrReceived)
  );

export const usePostCouponMutation = ({ receiverIds, content }, { onSuccess = () => {} } = {}) =>
  useMutation(() => postCouponRequest({ receiverIds, content }), {
    onSuccess: () => {
      onSuccess();
    },
  });

export const usePutCompleteCoupon = (
  couponId: number,
  { onSuccess = () => {}, onError = (error: AxiosError<ErrorType>) => {} } = {}
) =>
  useMutation(() => putCompleteCoupon(couponId), {
    onSuccess: () => {
      onSuccess();
    },
    onError: (error: AxiosError<ErrorType>) => {
      onError(error);
    },
    retry: false,
  });

/** FETCHER */

const getCouponsRequest = async sendOrReceived => {
  const { data } = await client({
    method: 'get',
    url: SENT_OR_RECEIVED_API_PATH[sendOrReceived],
  });

  return data;
};

const getCouponDetailRequest = async couponId => {
  const { data } = await client({
    method: 'get',
    url: `${API_PATH.GET_COUPON_DETAIL(couponId)}`,
  });

  return data;
};

const postCouponRequest = async ({ receiverIds, content }) => {
  const { data } = await client({
    method: 'post',
    url: API_PATH.SEND_COUPON,
    data: { receiverIds, content },
  });

  return data;
};

const putCompleteCoupon = async couponId => {
  const { data } = await client({ url: API_PATH.COMPLETE_COUPON(couponId), method: 'put' });

  return data;
};

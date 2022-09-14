import { useEffect } from 'react';
import { useMutation, useQuery, useQueryClient } from 'react-query';
import { client } from '../../apis/axios';
import { API_PATH } from '../../constants/api';
import { Coupon, CouponDetail } from '../../types';

const SENT_OR_RECEIVED_API_PATH = {
  받은: API_PATH.RECEIVED_COUPONS_ALL,
  보낸: API_PATH.SENT_COUPONS,
};

export const COUPON_QUERY_KEY = {
  coupon: 'coupon',
  couponDetail: 'couponDetail',
};

export const useGetCouponDetail = (couponId, { onSuccess = () => {}, onError = () => {} } = {}) =>
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

export const useGetCoupons = sentOrReceived => {
  const queryClient = useQueryClient();

  useEffect(() => {
    const defaultSentOrReceived = '보낸';

    queryClient.prefetchQuery([COUPON_QUERY_KEY.coupon, defaultSentOrReceived], () =>
      getCouponsRequest(SENT_OR_RECEIVED_API_PATH[API_PATH.SENT_COUPONS])
    );
  }, []);

  return useQuery<Coupon[]>([COUPON_QUERY_KEY.coupon, sentOrReceived], () =>
    getCouponsRequest(SENT_OR_RECEIVED_API_PATH[sentOrReceived])
  );
};

export const usePostCouponMutation = ({ receiverIds, content }, { onSuccess = () => {} } = {}) =>
  useMutation(() => postCouponRequest({ receiverIds, content }), {
    onSuccess: () => {
      onSuccess();
    },
  });

/** FETCHER */

const getCouponsRequest = async url => {
  const { data } = await client({
    method: 'get',
    url,
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

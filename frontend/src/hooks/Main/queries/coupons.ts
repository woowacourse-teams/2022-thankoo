import { useEffect } from 'react';
import { useQuery, useQueryClient } from 'react-query';
import { client } from '../../../apis/axios';
import { API_PATH } from '../../../constants/api';
import { Coupon } from '../../../types';

const SENT_OR_RECEIVED_API_PATH = {
  받은: API_PATH.RECEIVED_COUPONS_ALL,
  보낸: API_PATH.SENT_COUPONS,
};

const QUERY_KEY = {
  coupon: 'coupon',
};

export const useGetCoupons = sentOrReceived => {
  const queryClient = useQueryClient();

  useEffect(() => {
    const defaultSentOrReceived = '보낸';

    queryClient.prefetchQuery([QUERY_KEY.coupon, defaultSentOrReceived], async () => {
      const { data } = await client({
        method: 'get',
        url: API_PATH.SENT_COUPONS,
      });

      return data;
    });
  }, []);

  return useQuery<Coupon[]>([QUERY_KEY.coupon, sentOrReceived], async () => {
    const { data } = await client({
      method: 'get',
      url: SENT_OR_RECEIVED_API_PATH[sentOrReceived],
    });

    return data;
  });
};

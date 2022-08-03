import { useQuery } from 'react-query';
import { client } from '../../../apis/axios';
import { API_PATH } from '../../../constants/api';
import { Coupon } from '../../../types';

const SENT_OR_RECEIVED_API_PATH = {
  받은: API_PATH.RECEIVED_COUPONS_NOT_USED,
  보낸: API_PATH.SENT_COUPONS,
};

export const useGetCoupons = sentOrReceived =>
  useQuery<Coupon[]>(['coupon', sentOrReceived], async () => {
    const { data } = await client({
      method: 'get',
      url: SENT_OR_RECEIVED_API_PATH[sentOrReceived],
    });
    return data;
  });

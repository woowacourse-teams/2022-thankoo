import { useQuery } from 'react-query';
import { client } from '../../../apis/axios';
import { API_PATH } from '../../../constants/api';
import { CouponDetail } from '../../../types';

export const useGetCouponDetail = couponId =>
  useQuery<CouponDetail>(['couponDetail', couponId], async () => {
    const { data } = await client({
      method: 'get',
      url: `${API_PATH.GET_COUPON_DETAIL(couponId)}`,
    });

    return data;
  });

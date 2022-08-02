import { useState } from 'react';
import { useQuery } from 'react-query';
import { client } from '../../apis/axios';
import { API_PATH } from '../../constants/api';
import { Coupon, CouponType } from '../../types';

const COUPON_STATUS_PRIORITY = {
  not_used: 0,
  reserving: 1,
  reserved: 2,
};

const useMain = () => {
  const [currentType, setCurrentType] = useState<CouponType>('entire');

  const { data, isLoading, error } = useQuery<Coupon[]>('coupon', async () => {
    const { data } = await client({
      method: 'get',
      url: `${API_PATH.RECEIVED_COUPONS_NOT_USED}`,
    });
    return data;
  });

  const couponsByType = data?.filter(
    coupon => coupon.content.couponType === currentType || currentType === 'entire'
  );

  const orderedCoupons = couponsByType?.sort(
    (coupon1, coupon2) =>
      COUPON_STATUS_PRIORITY[coupon1.status] - COUPON_STATUS_PRIORITY[coupon2.status]
  );

  return { setCurrentType, orderedCoupons, isLoading, error, currentType };
};

export default useMain;

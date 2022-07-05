import { useState } from 'react';
import { useQuery } from 'react-query';
import axios from 'axios';
import { CouponType, Coupon } from '../../types';

const useMain = () => {
  const [currentType, setCurrentType] = useState<CouponType>('entire');
  console.log('useMainCalled', currentType);

  const { data, isLoading, error } = useQuery<Coupon[]>('coupon', async () => {
    const { data } = await axios.get('http://localhost:3000/api/members/me/received-coupons');
    return data;
  });

  const couponsByType = data?.filter(
    coupon => coupon.content.couponType === currentType || currentType === 'entire'
  );

  return { setCurrentType, couponsByType, isLoading, error, currentType };
};

export default useMain;

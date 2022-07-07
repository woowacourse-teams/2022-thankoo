import { useState } from 'react';
import { CouponType } from '../../types';

const useEnterCouponContent = () => {
  const [couponType, setCouponType] = useState<CouponType>('coffee');

  return { couponType, setCouponType };
};

export default useEnterCouponContent;

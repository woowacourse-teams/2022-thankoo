import { useState } from 'react';
import { useQuery } from 'react-query';
import axios from 'axios';
import { CouponType, Coupon } from '../../types';
import { authAtom } from '../../recoil/atom';
import { useRecoilValue } from 'recoil';
import { BASE_URL } from '../../constants';

const useMain = () => {
  const { accessToken, memberId } = useRecoilValue(authAtom);
  const [currentType, setCurrentType] = useState<CouponType>('entire');

  const { data, isLoading, error } = useQuery<Coupon[]>('coupon', async () => {
    const { data } = await axios({
      method: 'get',
      url: `${BASE_URL}/api/coupons/received`,
      headers: { Authorization: `Bearer ${accessToken}` },
    });
    return data;
  });

  const couponsByType = data?.filter(
    coupon => coupon.content.couponType === currentType || currentType === 'entire'
  );

  return { setCurrentType, couponsByType, isLoading, error, currentType };
};

export default useMain;

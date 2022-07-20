import axios from 'axios';
import { useState } from 'react';
import { useQuery } from 'react-query';
import { useRecoilValue } from 'recoil';
import { API_PATH } from '../../constants/api';
import { authAtom } from '../../recoil/atom';
import { Coupon, CouponType } from '../../types';

const useMain = () => {
  const { accessToken, memberId } = useRecoilValue(authAtom);
  const [currentType, setCurrentType] = useState<CouponType>('entire');

  const { data, isLoading, error } = useQuery<Coupon[]>('coupon', async () => {
    const { data } = await axios({
      method: 'get',
      url: `${API_PATH.RECEIVED_COUPONS_NOT_USED}`,
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

import axios from 'axios';
import { useState } from 'react';
import { useQuery } from 'react-query';
import { API_PATH } from '../../constants/api';
import { Coupon } from '../../types';

const orderByList = ['date', 'name'];
const orderByObject = { date: '날짜 순', name: '이름 순' };

const useResrvations = () => {
  const [orderBy, setOrderBy] = useState('date');
  const { data: reservations } = useQuery(
    'reservations',
    async () => {
      const { data } = await axios({ method: 'get', url: `${API_PATH.RESERVATIONS}` });

      return data;
    },
    { refetchOnWindowFocus: false }
  );
  const [checkedCoupons, setCheckedCoupons] = useState<Coupon[]>([]);
  const checkCoupon = (coupon: Coupon) => {
    setCheckedCoupons(prev => [...prev, coupon]);
  };
  const uncheckCoupon = (coupon: Coupon) => {
    setCheckedCoupons(prev =>
      prev.filter(checkedCoupon => checkedCoupon.couponHistoryId !== coupon.couponHistoryId)
    );
  };
  const toggleCoupon = (coupon: Coupon) => {
    if (isCheckedCoupon(coupon)) {
      uncheckCoupon(coupon);
      return;
    }
    checkCoupon(coupon);
  };
  const isCheckedCoupon = (coupon: Coupon) =>
    checkedCoupons?.some(checkCoupon => checkCoupon.couponHistoryId === coupon.couponHistoryId);

  return {
    orderBy,
    setOrderBy,
    reservations,
    checkCoupon,
    uncheckCoupon,
    toggleCoupon,
    orderByList,
    orderByObject,
    isCheckedCoupon,
  };
};

export default useResrvations;

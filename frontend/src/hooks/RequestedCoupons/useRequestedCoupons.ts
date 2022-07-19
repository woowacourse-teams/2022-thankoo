import axios from 'axios';
import { useState } from 'react';
import { useQuery } from 'react-query';
import { API_PATH } from '../../constants/api';
import { Coupon } from '../../types';

const orderByList = ['date', 'name'];
const orderByObject = { date: '날짜 순', name: '이름 순' };

const useRequestedCoupons = () => {
  const [orderBy, setOrderBy] = useState('date');
  const { data: requestedCoupons } = useQuery(
    'requested',
    async () => {
      const { data } = await axios({ method: 'get', url: `${API_PATH.RESERVATIONS}` });

      return data;
    },
    { refetchOnWindowFocus: false }
  );
  const [clickedCoupons, setClickedCoupons] = useState<Coupon[]>([]);

  return {
    orderBy,
    setOrderBy,
    requestedCoupons,
    clickedCoupons,
    setClickedCoupons,
    orderByList,
    orderByObject,
  };
};

export default useRequestedCoupons;

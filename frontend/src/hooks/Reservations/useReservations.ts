import axios from 'axios';
import { useState } from 'react';
import { useQuery } from 'react-query';
import { API_PATH } from '../../constants/api';

const orderByList = ['date', 'name'];
const orderByObject = { date: '날짜 순', name: '이름 순' };

const useResrvations = () => {
  const accessToken = localStorage.getItem('token');

  const [orderBy, setOrderBy] = useState('date');
  const { data: reservations } = useQuery(
    'reservations',
    async () => {
      const { data } = await axios({
        method: 'get',
        url: `${API_PATH.RESERVATIONS}`,
        headers: { Authorization: `Bearer ${accessToken}` },
      });

      return data;
    },
    { refetchOnWindowFocus: false }
  );

  // reservations?.sort();

  return {
    orderBy,
    setOrderBy,
    reservations,
    orderByList,
    orderByObject,
  };
};

export default useResrvations;

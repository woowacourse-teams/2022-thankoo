import axios from 'axios';
import { useState } from 'react';
import { useQuery } from 'react-query';
import { API_PATH } from '../../constants/api';

const orderByList = ['date', 'name'];
const orderByObject = { date: '받은 예약', name: '보낸 예약' };

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

  return {
    orderBy,
    setOrderBy,
    reservations,
    orderByList,
    orderByObject,
  };
};

export default useResrvations;

import axios from 'axios';
import { useEffect, useState } from 'react';
import { useQuery, useQueryClient } from 'react-query';
import { API_PATH } from '../../constants/api';

const orderByList = ['received', 'sent'];
const orderByObject = { received: '받은 예약', sent: '보낸 예약' };

const useResrvations = () => {
  const accessToken = localStorage.getItem('token');
  const queryClient = useQueryClient();

  const [orderBy, setOrderBy] = useState('received');
  const { data: reservationsReceived } = useQuery(
    'reservationsReceived',
    async () => {
      const { data } = await axios({
        method: 'get',
        url: `${API_PATH.RESERVATIONS_RECEIVED}`,
        headers: { Authorization: `Bearer ${accessToken}` },
      });

      return data;
    },
    { refetchOnWindowFocus: false, retry: false }
  );
  const { data: reservationSent } = useQuery('reservationsSent', async () => {
    const { data } = await axios({
      method: 'get',
      url: `${API_PATH.RESERVATIONS_SENT}`,

      headers: { Authorization: `Bearer ${accessToken}` },
    });

    return data;
  });

  useEffect(() => {
    queryClient.invalidateQueries('reservations');
  }, [orderBy]);

  return {
    orderBy,
    setOrderBy,
    reservations: { received: reservationsReceived, sent: reservationSent },
    orderByList,
    orderByObject,
  };
};

export default useResrvations;

import { useEffect, useState } from 'react';
import { useQuery, useQueryClient } from 'react-query';
import { client } from '../../apis/config/axios';
import { API_PATH } from '../../constants/api';

const orderByList = ['received', 'sent'];
const orderByObject = { received: '받은 예약', sent: '보낸 예약' };

const useResrvations = () => {
  const queryClient = useQueryClient();

  const [orderBy, setOrderBy] = useState('received');
  const { data: reservations } = useQuery(
    ['reservations', orderBy],
    async () => {
      const { data } = await client({
        method: 'get',
        url: orderBy === 'received' ? API_PATH.RESERVATIONS_RECEIVED : API_PATH.RESERVATIONS_SENT,
      });

      return data;
    },
    { refetchOnWindowFocus: false, retry: false }
  );

  useEffect(() => {
    queryClient.invalidateQueries('reservations');
  }, [orderBy]);

  return {
    orderBy,
    setOrderBy,
    reservations,
    orderByList,
    orderByObject,
  };
};

export default useResrvations;

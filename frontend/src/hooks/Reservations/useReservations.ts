import { useEffect } from 'react';
import { useQuery, useQueryClient } from 'react-query';
import { useRecoilState } from 'recoil';
import { client } from '../../apis/axios';
import { API_PATH } from '../../constants/api';
import { ReservationNavAtom } from '../../recoil/atom';
import { useGetReservations } from '../@queries/reservation';

const orderByList = ['received', 'sent'];
const orderByObject = { received: '받은 예약', sent: '보낸 예약' };

const useResrvations = () => {
  const [orderBy, setOrderBy] = useRecoilState(ReservationNavAtom);
  const { data: reservations } = useGetReservations(orderBy);

  return {
    orderBy,
    setOrderBy,
    reservations,
    orderByList,
    orderByObject,
  };
};

export default useResrvations;

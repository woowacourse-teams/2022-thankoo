import { useRecoilState } from 'recoil';
import { ReservationNavAtom } from '../../recoil/atom';
import { useGetReservations } from '../@queries/reservation';

type ReservationOrderType = 'sent' | 'received';

const useResrvations = () => {
  const [orderBy, setOrderBy] = useRecoilState<ReservationOrderType>(ReservationNavAtom);
  const { data: reservations } = useGetReservations(orderBy);

  return {
    orderBy,
    setOrderBy,
    reservations,
  };
};

export default useResrvations;

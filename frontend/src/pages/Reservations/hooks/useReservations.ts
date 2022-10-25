import { useRecoilState } from 'recoil';
import { ReservationNavAtom } from '../../../recoil/atom';

export type ReservationOrderType = 'sent' | 'received';

const useReservations = () => {
  const [orderBy, setOrderBy] = useRecoilState<ReservationOrderType>(ReservationNavAtom);

  return {
    orderBy,
    setOrderBy,
  };
};

export default useReservations;

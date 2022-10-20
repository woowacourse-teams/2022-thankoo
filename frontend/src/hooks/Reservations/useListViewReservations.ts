import { useGetReservations } from './../@queries/reservation';
import { ReservationOrderType } from './useReservations';

const useListViewReservations = (orderBy: ReservationOrderType) => {
  const { data: reservations } = useGetReservations(orderBy);

  return { reservations };
};

export default useListViewReservations;

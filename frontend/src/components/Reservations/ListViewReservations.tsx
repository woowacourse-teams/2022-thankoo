import styled from '@emotion/styled';
import { ReservationOrderType } from '../../hooks/Reservations/useReservations';
import useListViewReservations from './../../hooks/Reservations/useListViewReservations';
import NoReservation from './../@shared/noContent/NoReservation';
import ListReservationItem from './ListReservationItem';

const ListViewReservations = ({ orderBy }: { orderBy: ReservationOrderType }) => {
  const { reservations } = useListViewReservations(orderBy);

  return (
    <S.ListView>
      {reservations.length > 0 ? (
        reservations.map(reservation => (
          <ListReservationItem
            key={reservation.reservationId}
            transmitStatus={orderBy}
            {...reservation}
          />
        ))
      ) : (
        <NoReservation />
      )}
    </S.ListView>
  );
};

const S = {
  ListView: styled.div`
    display: flex;
    flex-direction: column;
    gap: 10px;
    height: 70vh;
    overflow: auto;
  `,
};

export default ListViewReservations;

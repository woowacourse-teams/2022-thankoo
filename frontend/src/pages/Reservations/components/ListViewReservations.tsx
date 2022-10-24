import styled from '@emotion/styled';
import NoReservation from '../../../components/@shared/noContent/NoReservation';
import useListViewReservations from '../hook/useListViewReservations';
import { ReservationOrderType } from '../hook/useReservations';
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

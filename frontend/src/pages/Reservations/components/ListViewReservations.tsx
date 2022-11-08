import styled from '@emotion/styled';
import NoReservation from '../../../components/@shared/noContent/NoReservation';
import useListViewReservations from '../hooks/useListViewReservations';
import { ReservationOrderType } from '../hooks/useReservations';
import ListViewReservation from './ListViewReservation';

type ListViewReservationsProps = {
  orderBy: ReservationOrderType;
};

const ListViewReservations = ({ orderBy }: ListViewReservationsProps) => {
  const { reservations } = useListViewReservations(orderBy);

  return (
    <S.ListView>
      {reservations.length > 0 ? (
        reservations.map(reservation => (
          <ListViewReservation
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

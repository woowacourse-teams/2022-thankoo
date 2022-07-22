import styled from '@emotion/styled';
import { Link } from 'react-router-dom';
import ArrowBackButton from '../components/@shared/ArrowBackButton';
import Header from '../components/@shared/Header';
import HeaderText from '../components/@shared/HeaderText';
import PageLayout from '../components/@shared/PageLayout';
import TabsNav from '../components/@shared/TabsNav';
import Reservation from '../components/Reservations/Reservation';
import useReservations from '../hooks/Reservations/useReservations';

const Reservations = () => {
  const { reservations, orderBy, setOrderBy, orderByList, orderByObject } = useReservations();

  return (
    <PageLayout>
      <Header>
        <Link to='/'>
          <ArrowBackButton />
        </Link>
        <HeaderText>예약 목록</HeaderText>
      </Header>
      <S.Body>
        <TabsNav
          selectableTabs={orderByList}
          currentTab={orderBy}
          tabList={orderByObject}
          onChangeTab={setOrderBy}
        />
        <S.ListView>
          {reservations[orderBy]?.map(reservation => (
            <Reservation key={reservation.reservationId} order={orderBy} {...reservation} />
          ))}
        </S.ListView>
      </S.Body>
    </PageLayout>
  );
};

export default Reservations;

const S = {
  Body: styled.section`
    display: flex;
    flex-direction: column;
    padding: 2rem 15px;
    color: white;
    gap: 15px;
  `,
  ListView: styled.div`
    display: flex;
    flex-direction: column;
    gap: 10px;
    height: 70vh;
    overflow: auto;
  `,
};

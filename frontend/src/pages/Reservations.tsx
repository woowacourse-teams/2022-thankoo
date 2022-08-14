import styled from '@emotion/styled';
import Header from '../components/@shared/Header';
import HeaderText from '../components/@shared/HeaderText';
import PageLayout from '../components/@shared/PageLayout';
import TabsNav from '../components/@shared/TabsNav';
import UserProfileButton from '../components/@shared/UserProfileButton';
import BottomNavBar from '../components/PageButton/BottomNavBar';
import Reservation from '../components/Reservations/Reservation';
import useReservations from '../hooks/Reservations/useReservations';
import NoReservation from './../components/@shared/noContent/NoReservation copy';

const Reservations = () => {
  const { reservations, orderBy, setOrderBy, orderByList, orderByObject } = useReservations();

  return (
    <PageLayout>
      <Header>
        <S.UserProfile>
          <UserProfileButton />
        </S.UserProfile>
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
          {reservations?.length > 0 ? (
            reservations.map(reservation => (
              <Reservation key={reservation.reservationId} order={orderBy} {...reservation} />
            ))
          ) : (
            <NoReservation />
          )}
        </S.ListView>
        <BottomNavBar />
      </S.Body>
    </PageLayout>
  );
};

export default Reservations;

const S = {
  UserProfile: styled.div`
    display: flex;
    justify-content: flex-end;
    width: 100%;
  `,
  Body: styled.section`
    display: flex;
    flex-direction: column;
    padding: 5px 3vw;
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

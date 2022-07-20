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
        <HeaderText>요청된 쿠폰함</HeaderText>
      </Header>
      <S.Body>
        <S.OrderTabsNav
          selectableTabs={orderByList}
          currentTab={orderBy}
          tabList={orderByObject}
          onChangeTab={setOrderBy}
        />
        <S.ListView>
          {reservations?.map(coupon => (
            <Reservation coupon={coupon} onClickReservation={() => {}} />
          ))}
        </S.ListView>
      </S.Body>
    </PageLayout>
  );
};

{
}

export default Reservations;

const S = {
  Body: styled.section`
    padding: 5rem 0 2rem;
    color: white;
  `,
  ListView: styled.div`
    display: flex;
    flex-direction: column;
    gap: 10px;
    padding: 0 5px;
    height: 60vh;
    overflow: auto;
  `,
  OrderTabsNav: styled(TabsNav)`
    margin-bottom: 8px;
  `,
};

import { css } from '@emotion/react';
import styled from '@emotion/styled';
import HeaderText from '../layout/HeaderText';
import Reservation from '../components/Reservations/Reservation';
import useReservations from '../hooks/Reservations/useReservations';
import NoReservation from '../components/@shared/noContent/NoReservation';
import MainPageLayout from '../layout/MainPageLayout';

const ReservationNav = ['received', 'sent'];

const Reservations = () => {
  const { reservations, orderBy, setOrderBy } = useReservations();

  return (
    <MainPageLayout>
      <S.CouponStatusNavWrapper>
        <S.SliderDiv length={2} current={ReservationNav.indexOf(orderBy)} />
        <S.CouponStatusNav
          onClick={() => {
            setOrderBy('received');
          }}
          selected={orderBy === 'received'}
        >
          <S.HeaderText>받은 예약</S.HeaderText>
        </S.CouponStatusNav>
        <S.CouponStatusNav
          onClick={() => {
            setOrderBy('sent');
          }}
          selected={orderBy === 'sent'}
        >
          <S.HeaderText>보낸 예약</S.HeaderText>
        </S.CouponStatusNav>
      </S.CouponStatusNavWrapper>
      <S.Body>
        <S.ListView>
          {reservations?.length > 0 ? (
            reservations.map(reservation => (
              <Reservation
                key={reservation.reservationId}
                transmitStatus={orderBy}
                {...reservation}
              />
            ))
          ) : (
            <NoReservation />
          )}
        </S.ListView>
      </S.Body>
    </MainPageLayout>
  );
};

export default Reservations;

type SliderDivProps = {
  length: number;
  current: number;
};

type CouponStatusNavProps = {
  selected: boolean;
};

const S = {
  Body: styled.section`
    height: 100%;
    display: flex;
    flex-direction: column;
    padding: 5px 0;
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
  CouponStatusNavWrapper: styled.div`
    position: relative;
    display: flex;
    justify-content: space-around;
    width: 100%;
    cursor: pointer;
  `,
  HeaderText: styled(HeaderText)`
    cursor: pointer;
    position: relative;
    //드래그 금지
    -webkit-touch-callout: none;
    -webkit-user-select: none;
    -khtml-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
  `,
  SliderDiv: styled.div<SliderDivProps>`
    width: ${({ length }) => `${100 / length}%`};
    height: 103%;
    border-bottom: white solid 2px;
    position: absolute;
    left: 0;
    top: 0;

    transition: all ease-in-out 0.1s;
    left: ${({ current, length }) => `${(100 / length) * current}%`};
  `,
  CouponStatusNav: styled.div<CouponStatusNavProps>`
    display: flex;
    justify-content: center;
    width: 100%;
    padding: 1rem;
    background-color: #232323;
    ${({ selected }) =>
      !selected
        ? css`
            color: #8e8e8e;
          `
        : css`
            color: white;
          `};
  `,
};

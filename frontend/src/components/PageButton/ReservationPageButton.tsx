import { css } from '@emotion/react';
import styled from '@emotion/styled';
import NotificationsActiveIcon from '@mui/icons-material/NotificationsActive';
import { Link, useLocation } from 'react-router-dom';
import { ROUTE_PATH } from '../../constants/routes';
import { useGetReservations } from '../../hooks/@queries/reservation';

const ReservationPageButton = () => {
  const location = useLocation();
  const { data } = useGetReservations('received');

  return (
    <S.Link to={ROUTE_PATH.RESERVATIONS}>
      <S.ButtonWrapper active={location.pathname === ROUTE_PATH.RESERVATIONS}>
        <S.IconWrapper>
          <S.Icon />
        </S.IconWrapper>
        <p>예약</p>
        {data && data.length !== 0 && <S.Count>{data.length}</S.Count>}
      </S.ButtonWrapper>
    </S.Link>
  );
};

export default ReservationPageButton;

type ButtonProps = {
  active: boolean;
};

const S = {
  Link: styled(Link)`
    margin-top: 1px;
    line-height: 8px;
    p {
      font-size: 12px;
    }
  `,
  ButtonWrapper: styled.div<ButtonProps>`
    position: relative;
    opacity: 0.5;
    ${({ active }) =>
      active &&
      css`
        opacity: 1;
      `};
  `,
  Icon: styled(NotificationsActiveIcon)`
    border-radius: 50%;
    padding: 0.5rem;

    transition: all ease-in;
    transition-duration: 0.2s;
    -webkit-transition-duration: 0.2s;
  `,
  IconWrapper: styled.div`
    transform: scale(1.6);
    margin-bottom: 0.5rem;
  `,
  Count: styled.span`
    position: absolute;
    top: -7px;
    right: -7px;
    display: flex;
    align-items: center;
    justify-content: center;
    width: 1.3em;
    height: 1.3em;
    border-radius: 50%;
    background-color: ${({ theme }) => theme.primary};
    font-size: 1em;
  `,
};

import { css } from '@emotion/react';
import styled from '@emotion/styled';
import NotificationsActiveIcon from '@mui/icons-material/NotificationsActive';
import { Link, useLocation } from 'react-router-dom';
import { ROUTE_PATH } from '../../constants/routes';

const ReservationPageButton = () => {
  const location = useLocation();

  return (
    <S.Link to={ROUTE_PATH.RESERVATIONS}>
      <S.ButtonWrapper active={location.pathname === ROUTE_PATH.RESERVATIONS}>
        <S.Icon />
        <p>예약</p>
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
    line-height: 8px;
    p {
      font-size: 12px;
    }
  `,
  ButtonWrapper: styled.div<ButtonProps>`
    opacity: 0.5;
    ${({ active }) =>
      active &&
      css`
        opacity: 1;
        transform: scale(1.1);
      `};
  `,
  Icon: styled(NotificationsActiveIcon)`
    transform: scale(1.1);
    border-radius: 50%;
    padding: 0.5rem;

    transition: all ease-in;
    transition-duration: 0.2s;
    -webkit-transition-duration: 0.2s;

    &:active {
      background-color: ${({ theme }) => theme.button.active.background};
    }
  `,
};

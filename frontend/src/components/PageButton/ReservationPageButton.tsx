import { Link, useLocation } from 'react-router-dom';
import styled from '@emotion/styled';
import { ROUTE_PATH } from '../../constants/routes';
import NotificationsActiveIcon from '@mui/icons-material/NotificationsActive';
import { css } from '@emotion/react';

const ReservationPageButton = () => {
  const location = useLocation();

  return (
    <Link to={ROUTE_PATH.RESERVATIONS}>
      <Icon active={location.pathname === ROUTE_PATH.RESERVATIONS} />
    </Link>
  );
};

export default ReservationPageButton;

type ButtonProps = {
  active: boolean;
};

const Icon = styled(NotificationsActiveIcon)<ButtonProps>`
  transform: scale(1.1);
  border-radius: 50%;
  padding: 0.5rem;

  transition: all ease-in;
  transition-duration: 0.2s;
  -webkit-transition-duration: 0.2s;

  ${({ active }) =>
    active &&
    css`
      background-color: tomato;
    `}

  &:active {
    background-color: ${({ theme }) => theme.button.active.background};
  }
`;

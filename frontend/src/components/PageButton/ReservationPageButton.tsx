import { Link } from 'react-router-dom';
import styled from '@emotion/styled';
import { ROUTE_PATH } from '../../constants/routes';
import NotificationsActiveIcon from '@mui/icons-material/NotificationsActive';

const ReservationPageButton = () => {
  return (
    <Link to={ROUTE_PATH.RESERVATIONS}>
      <Icon />
    </Link>
  );
};

export default ReservationPageButton;

const Icon = styled(NotificationsActiveIcon)`
  transform: scale(1.1);
  border-radius: 50%;
  padding: 0.5rem;

  transition: all ease-in;
  transition-duration: 0.2s;
  -webkit-transition-duration: 0.2s;

  &:active {
    background-color: ${({ theme }) => theme.button.active.background};
  }
`;
